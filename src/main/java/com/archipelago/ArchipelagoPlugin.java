package com.archipelago;

import com.archipelago.data.ItemData;
import com.archipelago.data.ItemNames;
import com.archipelago.data.LocationData;
import com.archipelago.data.LocationNames;
import com.google.inject.Provides;
import javax.inject.Inject;
import javax.swing.*;

import com.sun.jna.platform.unix.X11;
import gg.archipelago.client.ClientStatus;
import gg.archipelago.client.LocationManager;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetModalMode;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ChatboxInput;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.Text;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@PluginDescriptor(
	name = "Archipelago Randomizer"
)
public class ArchipelagoPlugin extends Plugin
{
	public static ArchipelagoPlugin plugin;

	@Inject
	private Client client;
	@Inject
	private ClientThread clientThread;
	@Inject
	private ArchipelagoConfig config;
	@Inject
	private ClientToolbar clientToolbar;
	@Inject
	private SpriteManager spriteManager;
	@Inject
	private ConfigManager configManager;

	private ArchipelagoPanel panel;
	private OSRSClient apClient;
	private int modIconIndex = -1;
	private final List<ItemData> collectedItems = new ArrayList<>();

	public boolean loggedIn;
	public boolean connected;
	public long lastItemReceivedIndex = -1;
	//This boolean will become true when we log in, and will be set back to false in the first game tick.
	//This lets us check if the logged in player should auto-connect to AP
	private boolean justLoggedIn = false;

	private Queue<String[]> queuedMessages = new LinkedList<>();
	private boolean isDisplayingPopup = false;

	@Provides
	ArchipelagoConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ArchipelagoConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		plugin = this;
		panel = new ArchipelagoPanel(this, config);
		apClient = new OSRSClient(this);

		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "panel_icon.png");

		NavigationButton navButton = NavigationButton.builder()
				.tooltip("Archipelago Randomizer")
				.icon(icon)
				.priority(20)
				.panel(panel)
				.build();

		clientToolbar.addNavigation(navButton);

		clientThread.invoke(() -> LocationHandler.LoadImages(spriteManager));
		clientThread.invoke(() -> ItemHandler.LoadImages(spriteManager));

		loadSprites();
		clientThread.invoke(() -> client.runScript(ScriptID.CHAT_PROMPT_INIT));
	}

	@Override
	protected void shutDown() throws Exception
	{
		plugin = null;
		if (apClient != null && apClient.isConnected())
		{
			apClient.disconnect();
		}
		clientThread.invoke(() -> client.runScript(ScriptID.CHAT_PROMPT_INIT));
	}

	@Subscribe
	public void onScriptCallbackEvent(ScriptCallbackEvent event)
	{
		if (!event.getEventName().equals("setChatboxInput"))
		{
			return;
		}

		updateChatbox();
	}

	@Subscribe
	public void onBeforeRender(BeforeRender event)
	{
		updateChatbox(); // this stops flickering when typing
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			loggedIn = true;
			justLoggedIn = true;
			loadSprites();
		}
		else if (gameStateChanged.getGameState() == GameState.LOGIN_SCREEN){
			loggedIn = false;
			if (connected){
				connected = false;
				apClient.disconnect();
			}
		}
	}

	@Subscribe
	public void onClientTick(ClientTick t)
	{
		if (justLoggedIn && client.getLocalPlayer().getName() != null){
			//If we've just logged in with a character that's stored as our autoreconnect, connect immediately.
			if (!connected && config.autoreconnect().equals(client.getLocalPlayer().getName())){
				ConnectToAPServer();
				log.info("Detected log in of autoreconnect, connecting to AP server");
			}
			//If there is no autoreconnect set, and we're already connected to the AP server, set autoreconnect
			else if (config.autoreconnect().isBlank() && connected){
				configManager.setConfiguration("Archipelago", "autoreconnect", client.getLocalPlayer().getName());
				log.info("Detected first log in or connection, setting autoreconnect");
			}
			//If there _is_ an autoreconnect, we are currently connected, and the player we log in isn't the reconnect
			//IMMEDIATELY disconnect, do not pass go, do not collect $200
			else if (connected && !config.autoreconnect().equals(client.getLocalPlayer().getName())){
				apClient.disconnect();
				log.info("Detected log in of a non-auto-reconnect player. Disconnecting from server to avoid mixing checks");
			}
			justLoggedIn = false;
		}
		if (loggedIn && connected){
			checkStatus();
			SendChecks();

			if (!isDisplayingPopup && queuedMessages.size() > 0){
				String[] msg = queuedMessages.poll();
				isDisplayingPopup = true;
				clientThread.invokeLater(() -> {
					DisplayPopupMessage(msg[0],msg[1]);
				});
			}
		}
	}

	public void QueuePopupMessage(String header, String body){
		log.info("Queueing popup message for "+body);
		queuedMessages.add(new String[]{header, body});
	}
	private void DisplayPopupMessage(String header, String body){
		WidgetNode widgetNode = client.openInterface((161 << 16) | 13, 660, WidgetModalMode.MODAL_CLICKTHROUGH);
		log.info("Opening popup message for "+body);
		client.runScript(3343, header, body, -1);

		clientThread.invokeLater(() -> {
			Widget w = client.getWidget(660, 1);
			if (w.getWidth() > 0) {
				return false;
			}

			client.closeInterface(widgetNode, true);
			log.info("Closing interface message for "+body);
			isDisplayingPopup = false;
			return true;
		});

	}

	@Subscribe
	public void onNpcLootReceived(final NpcLootReceived npcLootReceived)
	{
		final NPC npc = npcLootReceived.getNpc();
		final String name = npc.getName();
		if ("Lesser Demon".equalsIgnoreCase(name)) {
			SetCheckByName(LocationNames.K_Lesser_Demon, true);
		}
		if ("Ogress Shaman".equalsIgnoreCase(name)) {
			SetCheckByName(LocationNames.K_Ogress_Shaman, true);
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		var message = event.getMessage();

		if (LocationHandler.OAK_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Oak_Log, true);
		else if (LocationHandler.WILLOW_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Willow_Log, true);
		else if (LocationHandler.SAPPHIRE_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Cut_Sapphire, true);
		else if (LocationHandler.EMERALD_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Cut_Emerald, true);
		else if (LocationHandler.RUBY_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Cut_Ruby, true);
		else if (LocationHandler.DIAMOND_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Cut_Diamond, true);
		else if (LocationHandler.COAL_MINED_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Mine_Coal, true);
		else if (LocationHandler.SILVER_MINED_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Mine_Silver, true);
		else if (LocationHandler.GOLD_MINED_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Mine_Gold, true);
		else if (LocationHandler.STEEL_SMELTED_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Smelt_Steel, true);
		else if (LocationHandler.SILVER_SMELTED_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Smelt_Silver, true);
		else if (LocationHandler.GOLD_SMELTED_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Smelt_Gold, true);
		else if (LocationHandler.CAKE_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Bake_Cake, true);
		else if (LocationHandler.APPLE_PIE_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Bake_Apple_Pie, true);
		else if (LocationHandler.PIZZA_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Bake_Meat_Pizza, true);
		else if (LocationHandler.CRUSH_BARRONITE_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Barronite_Deposit, true);
		else if (LocationHandler.GUPPY_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Guppy, true);
		else if (LocationHandler.CAVEFISH_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Cavefish, true);
		else if (LocationHandler.TETRA_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Tetra, true);
		else if (LocationHandler.LOBSTER_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Catch_Lobster, true);

		if (event.getName() == null || client.getLocalPlayer() == null
				|| client.getLocalPlayer().getName() == null || !apClient.isConnected())
			return;

		boolean isLocalPlayer =
				Text.standardize(event.getName()).equalsIgnoreCase(Text.standardize(client.getLocalPlayer().getName()));

		if (isLocalPlayer)
		{
			String text = "<img=" + modIconIndex + ">"+ Text.removeTags(event.getName());
			event.getMessageNode().setName(text);
		}
	}

	@Subscribe
	public void onChatboxInput(ChatboxInput chatboxInput)
	{
		final String message = chatboxInput.getValue();

		String command = extractCommand(message);
		if ("!ap".equals(command)){
			String cmd = message.substring(3);
			apClient.sendChat(cmd);
			log.info("Sending string to AP: "+cmd);
			chatboxInput.consume();

			clientThread.invokeLater((() -> DisplayPopupMessage("Test 1", "Test 2")));
		}
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event){
		if (connected){
			if (event.getMenuOption().equals("Wear") || event.getMenuOption().equals("Wield")){
				//If we are equipping an item
				if (event.getItemId() != -1){
					if (!IsItemAllowed((event.getItemId()))){
						event.consume();
						client.addChatMessage(ChatMessageType.GAMEMESSAGE, "AP", "You have not unlocked the ability to equip this item", null);
					}
				}
			}
		}
	}

	private boolean IsItemAllowed(int itemId){
		if (ItemHandler.MetalWeaponItemIds.contains(itemId)){
			int itemTier = (int) getCollectedItems().stream().filter(it -> it.name.equals(ItemNames.Progressive_Weapons)).count();
			for (int i=0; i <= itemTier; i++){
				if (Arrays.asList(ItemHandler.MetalWeaponsPermittedByTier.get(i)).contains(itemId)){
					return true;
				}
			}
			return false;
		} else if (ItemHandler.MetalArmorItemIds.contains(itemId)){
			int itemTier = (int) getCollectedItems().stream().filter(it -> it.name.equals(ItemNames.Progressive_Armor)).count();
			for (int i=0; i <= itemTier; i++){
				if (Arrays.asList(ItemHandler.MetalArmorPermittedByTier.get(i)).contains(itemId)){
					return true;
				}
			}
			return false;
		} else if (ItemHandler.RangeWeaponItemIds.contains(itemId)){
			int itemTier = (int) getCollectedItems().stream().filter(it -> it.name.equals(ItemNames.Progressive_Range_Weapon)).count();
			for (int i=0; i <= itemTier; i++){
				if (Arrays.asList(ItemHandler.RangeWeaponsPermittedByTier.get(i)).contains(itemId)){
					return true;
				}
			}
			return false;
		} else if (ItemHandler.RangeArmorItemIds.contains(itemId)){
			int itemTier = (int) getCollectedItems().stream().filter(it -> it.name.equals(ItemNames.Progressive_Range_Armor)).count();
			for (int i=0; i <= itemTier; i++){
				if (Arrays.asList(ItemHandler.RangeArmorPermittedByTier.get(i)).contains(itemId)){
					return true;
				}
			}
			return false;
		}
		//If it's not in any of those lists, it's fine
		return true;
	}

	public void SetConnectionState(boolean newConnectionState){
		if (connected != newConnectionState)
			panel.ConnectionStateChanged(newConnectionState);
		connected = newConnectionState;

		if (config.autoreconnect().isBlank() && connected && loggedIn){
			configManager.setConfiguration("Archipelago", "autoreconnect", client.getLocalPlayer().getName());
			log.info("Detected first log in or connection, setting autoreconnect");
		}
		UpdateCollectedChecks();
	}

	private void loadSprites()
	{
		clientThread.invoke(() ->
		{
			//If we already have the mod icon, we don't need to do it again
			if (modIconIndex > -1) return;

			IndexedSprite[] modIcons = client.getModIcons();
			List<IndexedSprite> newList = new ArrayList<>();

			modIconIndex = modIcons.length;

			final IndexedSprite sprite = getIndexedSpriteEmbedded();

			newList.add(sprite);

			IndexedSprite[] newAry = Arrays.copyOf(modIcons, modIcons.length + newList.size());
			System.arraycopy(newList.toArray(new IndexedSprite[0]), 0, newAry, modIcons.length, newList.size());
			client.setModIcons(newAry);
		});
	}

	private IndexedSprite getIndexedSpriteEmbedded()
	{
		try
		{
			log.debug("Loading: {}", "chat_icon.png");
			BufferedImage image = ImageUtil.loadImageResource(this.getClass(), "chat_icon.png");
			return ImageUtil.getImageIndexedSprite(image, client);
		}
		catch (RuntimeException ex)
		{
			log.debug("Unable to load image: ", ex);
		}

		return null;
	}

	private void SetCheckByName(String name, boolean status)
	{
		LocationData locData = LocationHandler.GetLocationByName(name);
		if (locData != null)
			LocationCheckStates.put(locData, status);
		else
			log.error("Null key for "+name);
	}

	public void DisplayChatMessage(String msg)
	{
		clientThread.invoke(() ->
				client.addChatMessage(ChatMessageType.GAMEMESSAGE, "AP", "<img=" + modIconIndex + ">"+msg, null)
		);
	}

	private void checkStatus()
	{
		SetCheckByName(LocationNames.Q_Cooks_Assistant,        Quest.COOKS_ASSISTANT.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Demon_Slayer,           Quest.DEMON_SLAYER.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Restless_Ghost,         Quest.THE_RESTLESS_GHOST.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Romeo_Juliet,           Quest.ROMEO__JULIET.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Sheep_Shearer,          Quest.SHEEP_SHEARER.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Shield_of_Arrav,        Quest.SHIELD_OF_ARRAV.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Ernest_the_Chicken,     Quest.ERNEST_THE_CHICKEN.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Vampyre_Slayer,         Quest.VAMPYRE_SLAYER.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Imp_Catcher,            Quest.IMP_CATCHER.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Prince_Ali_Rescue,      Quest.PRINCE_ALI_RESCUE.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Dorics_Quest,           Quest.DORICS_QUEST.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Black_Knights_Fortress, Quest.BLACK_KNIGHTS_FORTRESS.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Witchs_Potion,          Quest.WITCHS_POTION.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Knights_Sword,          Quest.THE_KNIGHTS_SWORD.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Goblin_Diplomacy,       Quest.GOBLIN_DIPLOMACY.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Pirates_Treasure,       Quest.PIRATES_TREASURE.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Rune_Mysteries,         Quest.RUNE_MYSTERIES.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Misthalin_Mystery,      Quest.MISTHALIN_MYSTERY.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Corsair_Curse,          Quest.THE_CORSAIR_CURSE.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_X_Marks_the_Spot,       Quest.X_MARKS_THE_SPOT.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.Q_Below_Ice_Mountain,     Quest.BELOW_ICE_MOUNTAIN.getState(client) == QuestState.FINISHED);

		SetCheckByName(LocationNames.QP_Cooks_Assistant,       Quest.COOKS_ASSISTANT.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Demon_Slayer,          Quest.DEMON_SLAYER.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Restless_Ghost,        Quest.THE_RESTLESS_GHOST.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Romeo_Juliet,          Quest.ROMEO__JULIET.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Sheep_Shearer,         Quest.SHEEP_SHEARER.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Shield_of_Arrav,       Quest.SHIELD_OF_ARRAV.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Ernest_the_Chicken,    Quest.ERNEST_THE_CHICKEN.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Vampyre_Slayer,        Quest.VAMPYRE_SLAYER.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Imp_Catcher,           Quest.IMP_CATCHER.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Prince_Ali_Rescue,     Quest.PRINCE_ALI_RESCUE.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Dorics_Quest,          Quest.DORICS_QUEST.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Black_Knights_Fortress,Quest.BLACK_KNIGHTS_FORTRESS.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Witchs_Potion,         Quest.WITCHS_POTION.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Knights_Sword,         Quest.THE_KNIGHTS_SWORD.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Goblin_Diplomacy,      Quest.GOBLIN_DIPLOMACY.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Pirates_Treasure,      Quest.PIRATES_TREASURE.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Rune_Mysteries,        Quest.RUNE_MYSTERIES.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Misthalin_Mystery,     Quest.MISTHALIN_MYSTERY.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Corsair_Curse,         Quest.THE_CORSAIR_CURSE.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_X_Marks_the_Spot,      Quest.X_MARKS_THE_SPOT.getState(client) == QuestState.FINISHED);
		SetCheckByName(LocationNames.QP_Below_Ice_Mountain,    Quest.BELOW_ICE_MOUNTAIN.getState(client) == QuestState.FINISHED);

		SetCheckByName(LocationNames.Total_XP_5000,            client.getOverallExperience() >= 5000);
		SetCheckByName(LocationNames.Total_XP_10000,           client.getOverallExperience() >= 10000);
		SetCheckByName(LocationNames.Total_XP_25000,           client.getOverallExperience() >= 25000);
		SetCheckByName(LocationNames.Total_XP_50000,           client.getOverallExperience() >= 50000);
		SetCheckByName(LocationNames.Total_XP_100000,          client.getOverallExperience() >= 100000);
		SetCheckByName(LocationNames.Total_Level_50,           client.getTotalLevel() >= 50);
		SetCheckByName(LocationNames.Total_Level_100,          client.getTotalLevel() >= 100);
		SetCheckByName(LocationNames.Total_Level_150,          client.getTotalLevel() >= 150);
		SetCheckByName(LocationNames.Total_Level_200,          client.getTotalLevel() >= 200);
		SetCheckByName(LocationNames.Combat_Level_5,           client.getLocalPlayer().getCombatLevel() >= 5);
		SetCheckByName(LocationNames.Combat_Level_15,          client.getLocalPlayer().getCombatLevel() >= 15);
		SetCheckByName(LocationNames.Combat_Level_25,          client.getLocalPlayer().getCombatLevel() >= 25);

		if(Quest.DRAGON_SLAYER_I.getState(client) == QuestState.FINISHED){
			apClient.setGameState(ClientStatus.CLIENT_GOAL);
		}
	}

	public void ConnectToAPServer()
	{
		String uri = config.address()+":"+config.port();
		log.info(uri);
		apClient.newConnection(this, uri, config.slotname(), config.password());
	}

	public Hashtable<LocationData, Boolean> LocationCheckStates = new Hashtable<LocationData, Boolean>(){{
		for (LocationData loc : LocationHandler.AllLocations) {
			put(loc, false);
		}
	}};

	private String extractCommand(String message)
	{
		int idx = message.indexOf(' ');
		if (idx == -1)
		{
			return message;
		}

		return message.substring(0, idx);
	}

	private void updateChatbox()
	{
		Widget chatboxTypedText = client.getWidget(WidgetInfo.CHATBOX_INPUT);

		if (chatboxTypedText == null || chatboxTypedText.isHidden() || !apClient.isConnected())
		{
			return;
		}

		String[] chatbox = chatboxTypedText.getText().split(":", 2);
		String rsn = Objects.requireNonNull(client.getLocalPlayer()).getName();

		String text = "<img=" + modIconIndex + ">" + Text.removeTags(rsn) + ":" + chatbox[1];
		chatboxTypedText.setText(text);
	}

	private void SendChecks()
	{
		Collection<Long> checkedLocations = LocationHandler.AllLocations.stream()
				.filter(loc -> LocationCheckStates.get(loc))
				.map(loc -> loc.id)
				.collect(Collectors.toList());

		if (apClient != null && apClient.isConnected()){
			apClient.checkLocations(checkedLocations);
		}

		SwingUtilities.invokeLater(panel::UpdateTaskStatus);
	}

	public List<ItemData> getCollectedItems() {
		return collectedItems;
	}

	public void addCollectedItem(ItemData item){
		log.info("Received item: "+item.name);
		collectedItems.add(item);
		panel.UpdateItems();
		UpdateAvailableChunks();
	}

	private void UpdateAvailableChunks(){
		List<String> unlockedRegions = new ArrayList<>();
		for (ItemData item : collectedItems){
			String regions = ItemHandler.RegionNamesToChunkIdString.getOrDefault(item.name, null);
			if (regions != null) unlockedRegions.add(regions);
		}
		String csv = Text.toCSV(unlockedRegions);
		//TODO replace dependency on regionlocker with custom solution. Good for testing though
		configManager.setConfiguration("regionlocker", "unlockedRegions", csv);
	}

	public void UpdateCollectedChecks(){
		for (Long locId : apClient.getLocationManager().getCheckedLocations()){
			LocationData loc = LocationHandler.LocationsById.get(locId);
			LocationCheckStates.put(loc, true);
		}
	}

	public void DisplayNetworkMessage(String message){
		panel.DisplayNetworkMessage(message);
	}
}
