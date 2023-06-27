package com.archipelago;

import com.archipelago.data.LocationData;
import com.archipelago.data.LocationNames;
import com.google.inject.Provides;
import javax.inject.Inject;
import javax.swing.*;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
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
	private ArchipelagoPanel panel;
	private NavigationButton navButton;

	public Hashtable<LocationData, Boolean> LocationCheckStates = new Hashtable<LocationData, Boolean>(){{
		for (LocationData loc : LocationHandler.AllLocations){
			put(loc, false);
		}
	}};


	private void SendChecks(){
		Collection<Long> checkedLocations = LocationHandler.AllLocations.stream()
				.filter(loc -> LocationCheckStates.get(loc))
				.map(loc -> loc.id)
				.collect(Collectors.toList());

		if (OSRSClient.apClient != null && OSRSClient.Connected()){
			OSRSClient.apClient.checkLocations(checkedLocations);
		}
		SwingUtilities.invokeLater(panel::UpdateTaskStatus);
	}

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;
	@Inject
	private ArchipelagoConfig config;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private SkillIconManager skillIconManager;
	@Inject
	private SpriteManager spriteManager;

	@Override
	protected void startUp() throws Exception
	{
		panel = new ArchipelagoPanel(this, config, skillIconManager, spriteManager);

		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "panel_icon.png");

		navButton = NavigationButton.builder()
				.tooltip("Archipelago Randomizer")
				.icon(icon)
				.priority(20)
				.panel(panel)
				.build();

		clientToolbar.addNavigation(navButton);

		loadSprites();
		clientThread.invoke(() -> client.runScript(ScriptID.CHAT_PROMPT_INIT));

	}

	@Override
	protected void shutDown() throws Exception
	{
		if (OSRSClient.apClient != null && OSRSClient.Connected()){
			OSRSClient.apClient.disconnect();
		}
		clientThread.invoke(() -> client.runScript(ScriptID.CHAT_PROMPT_INIT));
	}

	@Provides
    ArchipelagoConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ArchipelagoConfig.class);
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


	private int modIconIndex = -1;
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
			//modIconIndex = modIcons.length;

			// copy the icons around as a hack to avoid
			// java.lang.ClassCastException: class [Lnet.runelite.api.IndexedSprite; cannot be cast to class [Lof; ([Lnet.runelite.api.IndexedSprite; is in unnamed module of loader 'app'; [Lof; is in unnamed module of loader net.runelite.client.rs.ClientLoader$1 @2ecb15df)
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

	private void SetCheckByName(String name, boolean status){
		LocationData locData = LocationHandler.GetLocationByName(name);
		if (locData != null)
			LocationCheckStates.put(locData, status);
		else
			log.error("Null key for "+name);
	}

	public void DisplayChatMessage(String msg){
		clientThread.invoke(() ->
				client.addChatMessage(ChatMessageType.GAMEMESSAGE, "AP", "<img=" + modIconIndex + ">"+msg, null)
		);
	}

	private void checkStatus(){
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

		SetCheckByName(LocationNames.Total_XP_5000,   client.getOverallExperience() > 5000);
		SetCheckByName(LocationNames.Total_XP_25000,  client.getOverallExperience() > 25000);
		SetCheckByName(LocationNames.Total_XP_50000,  client.getOverallExperience() > 50000);
		SetCheckByName(LocationNames.Total_Level_50,  client.getTotalLevel() > 50);
		SetCheckByName(LocationNames.Total_Level_100, client.getTotalLevel() > 100);
		SetCheckByName(LocationNames.Total_Level_200, client.getTotalLevel() > 200);
		SetCheckByName(LocationNames.Combat_Level_10, client.getLocalPlayer().getCombatLevel() >= 10);
		SetCheckByName(LocationNames.Combat_Level_25, client.getLocalPlayer().getCombatLevel() >= 25);
		SetCheckByName(LocationNames.Combat_Level_50, client.getLocalPlayer().getCombatLevel() >= 50);

		SetCheckByName(LocationNames.Q_Dragon_Slayer,Quest.DRAGON_SLAYER_I.getState(client) == QuestState.FINISHED);
	}

	private boolean connectState = false;

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			if (!connectState){
				panel.ConnectionStateChanged();
				connectState = true;
			}

			loadSprites();
		}
	}

	@Subscribe
	public void onClientTick(ClientTick t){
		/**
		if (OSRSClient.Connected() != connectState){
			connectState = OSRSClient.Connected();
			panel.ConnectionStateChanged();
		}*/

		checkStatus();
		SendChecks();
	}

	@Subscribe
	public void onNpcLootReceived(final NpcLootReceived npcLootReceived)
	{
		final NPC npc = npcLootReceived.getNpc();
		final String name = npc.getName();
		if ("Lesser Demon".equals(name)) {
			SetCheckByName(LocationNames.K_Lesser_Demon, true);
		}
		if ("Ogress Shaman".equals(name)) {
			SetCheckByName(LocationNames.K_Ogress_Shaman, true);
		}
		if ("Obor".equals(name)) {
			SetCheckByName(LocationNames.K_Obor, true);
		}
		if ("Bryophyta".equals(name)) {
			SetCheckByName(LocationNames.K_Bryo, true);
		}
	}


	final String OAK_MESSAGE = "You get some oak logs.";
	final String WILLOW_MESSAGE = "You get some willow logs.";
	final String SAPPHIRE_MESSAGE = "You cut the sapphire.";
	final String EMERALD_MESSAGE = "You cut the emerald.";
	final String RUBY_MESSAGE = "You cut the ruby.";
	final String DIAMOND_MESSAGE = "You cut the diamond.";
	final String COAL_MINED_MESSAGE = "You manage to mine some coal.";
	final String SILVER_MINED_MESSAGE = "You manage to mine some silver.";
	final String GOLD_MINED_MESSAGE = "You manage to mine some gold.";
	final String STEEL_SMELTED_MESSAGE = "You retrieve a bar of steel.";
	final String SILVER_SMELTED_MESSAGE = "You retrieve a bar of silver from the furnace.";
	final String GOLD_SMELTED_MESSAGE = "You retrieve a bar of gold from the furnace.";
	final String APPLE_PIE_MESSAGE = "You successfully bake a traditional apple pie.";
	final String CAKE_MESSAGE = "You successfully bake a cake.";
	final String PIZZA_MESSAGE = "You add the meat to the pizza.";
	final String CRUSH_BARRONITE_MESSAGE = "You crush a barronite deposit with your hammer.";
	final String TETRA_MESSAGE = "You successfully prepare the Tetra.";
	final String CAVEFISH_MESSAGE = "You successfully prepare the Cavefish.";
	final String GUPPY_MESSAGE = "You successfully prepare the Guppy.";
	final String LOBSTER_MESSAGE = "You catch a lobster.";
	final String SWORDFISH_MESSAGE = "You catch a swordfish.";

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		var message = event.getMessage();

		if (OAK_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Oak_Log, true);
		else if (WILLOW_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Willow_Log, true);
		else if (SAPPHIRE_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Cut_Sapphire, true);
		else if (EMERALD_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Cut_Emerald, true);
		else if (RUBY_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Cut_Ruby, true);
		else if (DIAMOND_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Cut_Diamond, true);
		else if (COAL_MINED_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Mine_Coal, true);
		else if (SILVER_MINED_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Mine_Silver, true);
		else if (GOLD_MINED_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Mine_Gold, true);
		else if (STEEL_SMELTED_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Smelt_Steel, true);
		else if (SILVER_SMELTED_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Smelt_Silver, true);
		else if (GOLD_SMELTED_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Smelt_Gold, true);
		else if (CAKE_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Bake_Cake, true);
		else if (APPLE_PIE_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Bake_Apple_Pie, true);
		else if (PIZZA_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Bake_Meat_Pizza, true);
		else if (CRUSH_BARRONITE_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Barronite_Deposit, true);
		else if (GUPPY_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Guppy, true);
		else if (CAVEFISH_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Cavefish, true);
		else if (TETRA_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Tetra, true);
		else if (LOBSTER_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Catch_Lobster, true);
		else if (SWORDFISH_MESSAGE.equals(message))
			SetCheckByName(LocationNames.Catch_Swordfish, true);

		if (event.getName() == null || client.getLocalPlayer() == null
				|| client.getLocalPlayer().getName() == null || !OSRSClient.Connected())
		{
			return;
		}

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
			OSRSClient.apClient.sendChat(cmd);
			log.info("Sending string to AP: "+cmd);
			chatboxInput.consume();
		}


	}

	private static String extractCommand(String message)
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

		if (chatboxTypedText == null || chatboxTypedText.isHidden() || !OSRSClient.Connected())
		{
			return;
		}

		String[] chatbox = chatboxTypedText.getText().split(":", 2);
		String rsn = Objects.requireNonNull(client.getLocalPlayer()).getName();

		String text = "<img=" + modIconIndex + ">" + Text.removeTags(rsn) + ":" + chatbox[1];
		chatboxTypedText.setText(text);
	}
	public void ConnectToAPServer(String url, int port, String slotName, String password){
		String protocol = "wss://";
		if (url.contains("localhost") || url.contains("127.0.0.1"))
			protocol = "ws://";
		String uri = protocol+url+":"+port;
		log.info(uri);
		OSRSClient.newConnection(this, uri, slotName, password);
	}
}
