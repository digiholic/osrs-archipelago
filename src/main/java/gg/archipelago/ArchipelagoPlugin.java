package gg.archipelago;

import com.google.gson.Gson;
import dev.koifysh.archipelago.events.ReceiveItemEvent;
import gg.archipelago.Tasks.APTask;
import gg.archipelago.data.ItemData;
import gg.archipelago.data.ItemNames;
import com.google.inject.Provides;
import javax.inject.Inject;

import dev.koifysh.archipelago.ClientStatus;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.ComponentID;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetModalMode;
import net.runelite.client.RuneLite;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ChatboxInput;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.Text;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
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
	private EventBus eventBus;
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
	@Inject
	private Gson gson;
	@Inject
	private OverlayManager overlayManager;
	@Inject
	private IllegalRegionOverlay overlay;

	public boolean currentlyLoggedIn;
	public boolean connected;
	private boolean pendingConnection;

	protected List<APTask> activeTasks = new ArrayList<>();

	private ArchipelagoPanel panel;
	private APClient apClient;
	private int modIconIndex = -1;
	private final List<ItemData> collectedItems = new ArrayList<>();

	//This boolean will become true when we log in, and will be set back to false in the first game tick.
	//This lets us check if the logged in player should auto-connect to AP
	private boolean justLoggedIn = false;

	private Queue<String[]> queuedMessages = new LinkedList<>();
	private boolean isDisplayingPopup = false;

	private NavigationButton navButton;

	private SlotData slotData;

	private String dataPackageLocation;
	private DataPackage dataPackage;
	private long lastItemReceivedIndex = 0;

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
		apClient = new APClient(this, gson, eventBus);

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
		overlayManager.add(overlay);

		panel.RegisterListeners(eventBus);
	}

	@Override
	protected void shutDown() throws Exception
	{
		clientToolbar.removeNavigation(navButton);
		plugin = null;
		if (apClient != null && apClient.isConnected())
		{
			apClient.disconnect();
		}
		clientThread.invoke(() -> client.runScript(ScriptID.CHAT_PROMPT_INIT));
		overlayManager.remove(overlay);

		panel.UnregisterListeners(eventBus);
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
		//client.addChatMessage(ChatMessageType.GAMEMESSAGE, "AP", "Changed to State: "+gameStateChanged.getGameState().name(), null);
		log.debug("Changed to State: "+gameStateChanged.getGameState().name());

		if (gameStateChanged.getGameState() == GameState.LOGGED_IN && !currentlyLoggedIn)
		{
			dataPackageLocation = RuneLite.RUNELITE_DIR + "/APData/" + client.getAccountHash() + ".save";
			log.info("Logged in, checking for AP Save file at "+dataPackageLocation);
			loadDataPackage();
			currentlyLoggedIn = true;
			justLoggedIn = true;
			if (!apClient.isConnected() && !dataPackage.address.isEmpty() && !dataPackage.port.isEmpty() && !dataPackage.slotName.isEmpty()) {
				ConnectToAPServer(dataPackage.address, dataPackage.port, dataPackage.slotName, dataPackage.password);
			} else {
				DisplayNetworkMessage("No automatic reconnect defined for this character, please connect manually");
			}
		}
		else if (gameStateChanged.getGameState() == GameState.LOGIN_SCREEN){
			currentlyLoggedIn = false;
			if (connected){
				connected = false;
				apClient.disconnect();

				panel.ConnectionStateChanged(false);
			}
		}
	}
	@Subscribe
	public void onGameTick(GameTick tick){
		for (APTask task : activeTasks){
			task.OnGameTick(client);
		}
	}

	@Subscribe
	public void onClientTick(ClientTick t)
	{
		//client.addChatMessage(ChatMessageType.GAMEMESSAGE, "AP", client.getSelectedWidget().getName(), null);
		
		if (justLoggedIn && client.getLocalPlayer().getName() != null){
			// If we just logged in, and the data package's stored seed doesn't match the one we're connected to,
			// Disconnect immediately before checks get sent
			if (connected && !dataPackage.seed.equals(apClient.getRoomInfo().seedName)){
				apClient.disconnect();
				log.info("Detected log in of a non-auto-reconnect player. Disconnecting from server to avoid mixing checks");
				DisplayNetworkMessage("This Archipelago Slot is not associated with this Character. " +
						"Please use the proper character or update connection information, or press \"Connect\" "+
						"to update this slot to this character");
			}
			justLoggedIn = false;
		}
		if (currentlyLoggedIn && connected){
			checkStatus();
			SendChecks();

			if (!isDisplayingPopup && queuedMessages.size() > 0 && client.getWidget((161 << 16) | 13) != null){
				String[] msg = queuedMessages.poll();
				isDisplayingPopup = true;
				clientThread.invokeLater(() -> {
					DisplayPopupMessage(msg[0],msg[1]);
				});
			}
		}
		// If we've lost connection since last tick, we want to update the button.
		panel.UpdateStatusButton(apClient.isConnected());
	}

	@Subscribe
	public void onNpcLootReceived(final NpcLootReceived npcLootReceived)
	{
		final NPC npc = npcLootReceived.getNpc();
		for(APTask task : activeTasks){
			task.CheckMobKill(npc);
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		for (APTask task : activeTasks){
			task.CheckChatMessage(event);
		}

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
						client.addChatMessage(ChatMessageType.GAMEMESSAGE, "AP", "<img=" + modIconIndex + ">"+"You have not unlocked the ability to equip this item", null);
					}
				}
			}

			if (event.getWidget() != null){
				String widgetName = Text.removeTags(event.getWidget().getName());

				//Disallow teleports to locked areas
				if (widgetName.equalsIgnoreCase("Varrock Teleport")){
					if (getCollectedItems().stream().noneMatch(it -> it.name.equals("Area: Central Varrock"))){
						event.consume();
						client.addChatMessage(ChatMessageType.GAMEMESSAGE, "AP", "<img=" + modIconIndex + ">"+"You have not unlocked that map region yet.", null);
					}
				}

				if (widgetName.equalsIgnoreCase("Lumbridge Teleport") || widgetName.equalsIgnoreCase("Lumbridge Home Teleport")){
					if (getCollectedItems().stream().noneMatch(it -> it.name.equals("Area: Lumbridge"))){
						event.consume();
						client.addChatMessage(ChatMessageType.GAMEMESSAGE, "AP", "<img=" + modIconIndex + ">"+"You have not unlocked that map region yet.", null);
					}
				}

				if (widgetName.equalsIgnoreCase("Falador Teleport")){
					if (getCollectedItems().stream().noneMatch(it -> it.name.equals("Area: Falador"))){
						event.consume();
						client.addChatMessage(ChatMessageType.GAMEMESSAGE, "AP", "<img=" + modIconIndex + ">"+"You have not unlocked that map region yet.", null);
					}
				}
			}
		}

		for (APTask task : activeTasks){
			task.OnMenuOption(event);
		}
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{
		ItemContainer container = event.getItemContainer();

		if (container == client.getItemContainer(InventoryID.INVENTORY))
		{
			Item[] items = container.getItems();
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
			int itemTier = (int) getCollectedItems().stream().filter(it -> it.name.equals(ItemNames.Progressive_Range_Weapon) || it.name.equals(ItemNames.Progressive_Range_Weapon_Legacy)).count();
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
		// Whether it succeeds or fails, we are no longer waiting for an answer, so we can un-set this right away
		pendingConnection = false;
		// If we've just reconnected, check all our tasks and items
		if (newConnectionState){
			activeTasks = new ArrayList<>();
			for(long id : apClient.getLocationManager().getCheckedLocations()){
				APTask task = TaskLists.GetTaskByID(id);
				if (task != null){
					task.SetCompleted();
					activeTasks.add(task);
				}
			}

			for(long id : apClient.getLocationManager().getMissingLocations()){
				APTask task = TaskLists.GetTaskByID(id);
				if (task != null){
					activeTasks.add(task);
				}
			}

			activeTasks = activeTasks.stream().sorted(Comparator.comparing(APTask::GetID)).collect(Collectors.toList());

			if (dataPackage != null && dataPackage.slotName.isEmpty()){
				String connectedAddress = apClient.getConnectedAddress();
				String address = connectedAddress.substring(0, connectedAddress.lastIndexOf(':'));
				String port = connectedAddress.substring(connectedAddress.lastIndexOf(':')+1);

				dataPackage.address = address;
				dataPackage.port = port;
				dataPackage.slotName = apClient.getMyName();
				dataPackage.password = apClient.getPassword();
				dataPackage.seed = apClient.getRoomInfo().seedName;

				log.info("Detected first log in or connection, storing data");
				saveDataPackage();
			}
		}

		if (connected != newConnectionState)
			panel.ConnectionStateChanged(newConnectionState);
		connected = newConnectionState;
	}

	public void ReceiveItem(ReceiveItemEvent event){
		//Check against our local copy of the last item received index to add it to the inventory
		if (event.getIndex() >= lastItemReceivedIndex){
			addCollectedItem(ItemHandler.ItemsById.get(event.getItemID()));
			lastItemReceivedIndex = event.getIndex();
			//Check against the datapaackage to determine if we popup a message
			if (event.getIndex() > dataPackage.lastItemReceivedIndex){
				dataPackage.lastItemReceivedIndex = event.getIndex();
				String messageBody = "Received from " +
						event.getPlayerName() +
						" at " +
						event.getLocationName();
				QueuePopupMessage(event.getItemName(), messageBody);
				saveDataPackage();
			}
		}
	}

	public void DisplayChatMessage(String msg)
	{
		if (config.apMessages()){
			clientThread.invoke(() ->
					client.addChatMessage(ChatMessageType.GAMEMESSAGE, "AP", "<img=" + modIconIndex + ">"+msg, null)
			);
		}
	}

	private void checkStatus()
	{
		for (APTask task : activeTasks){
			task.CheckPlayerStatus(client);
		}

		if(Quest.DRAGON_SLAYER_I.getState(client) == QuestState.FINISHED){
			apClient.setGameState(ClientStatus.CLIENT_GOAL);
		}
	}

	public void ConnectToAPServer(){
		if (pendingConnection) return;
		DisplayNetworkMessage("Connecting...");
		pendingConnection = true;
		ConnectToAPServer(config.address(), config.port(), config.slotname(), config.password());
	}

	public void ConnectToAPServer(String address, String port, String slotName, String password)
	{
		String uri = address+":"+port;
		log.info(uri);
		apClient.newConnection(this, uri, slotName, password);
	}

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
		Widget chatboxTypedText = client.getWidget(ComponentID.CHATBOX_INPUT);

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
		Collection<Long> checkedLocations = activeTasks.stream()
				.filter(APTask::IsCompleted)
				.map(APTask::GetID)
				.collect(Collectors.toList());

		if (apClient != null && apClient.isConnected()){
			apClient.checkLocations(checkedLocations);
		}
	}

	public void addCollectedItem(ItemData item){
		if (item == null){
			log.warn("Null Item received");
			return;
		}
		log.info("Received item: "+item.name);
		collectedItems.add(item);
		UpdateAvailableChunks();
	}

	private void UpdateAvailableChunks(){
		List<String> unlockedRegions = new ArrayList<>();
		for (ItemData item : collectedItems){
			String regions = ItemHandler.RegionNamesToChunkIdString.getOrDefault(item.name, null);
			if (regions != null) unlockedRegions.add(regions);
		}
		String csv = Text.toCSV(unlockedRegions);
		//TODO replace dependency on regionlocker with custom solution. Good enough for now though
		configManager.setConfiguration("regionlocker", "unlockedRegions", csv);
	}

	private void saveDataPackage() {
		try {
			File dataPackageFile = new File(dataPackageLocation);

			dataPackageFile.getParentFile().mkdirs();
			dataPackageFile.createNewFile();

			FileWriter writer = new FileWriter(dataPackageFile);

			String s = gson.toJson(dataPackage);
			gson.toJson(dataPackage, writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			log.error("unable to save DataPackage.");
		}
	}

	private void loadDataPackage() {
		try {
			FileInputStream fileInput = new FileInputStream(dataPackageLocation);
			dataPackage = gson.fromJson(new InputStreamReader(fileInput, StandardCharsets.UTF_8), DataPackage.class);
			fileInput.close();

		} catch (IOException e) {
			log.info("no dataPackage found creating a new one.");
			dataPackage = new DataPackage();
			saveDataPackage();
		}
	}

	public void ClaimCarePack(long itemId){
		dataPackage.claimedCarePacks.add(itemId);
		saveDataPackage();
	}

	public int CheckClaimedCarePacks(long itemId){
		return Collections.frequency(dataPackage.claimedCarePacks, itemId);
	}

	public List<ItemData> getCollectedItems() {
		return collectedItems;
	}
	public void DisplayNetworkMessage(String message){
		panel.DisplayNetworkMessage(message);
	}
	public void SetSlotData(SlotData data){

		this.slotData = data;
		TaskLists.GetAllTasks(slotData.data_csv_tag);
		ItemHandler.GetAllItems(slotData.data_csv_tag);

		//loadSprites();
		clientThread.invoke(() -> TaskLists.LoadImages(spriteManager));
		clientThread.invoke(() -> ItemHandler.LoadImages(spriteManager));
	}

	public String getCurrentPlayerName(){
		if (client.getLocalPlayer() == null) return "";
		return client.getLocalPlayer().getName();
	}

	/////////// SPRITES ///////////
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
			BufferedImage image = ImageUtil.loadImageResource(this.getClass(), "chat_icon.png");
			return ImageUtil.getImageIndexedSprite(image, client);
		}
		catch (RuntimeException ex)
		{
			log.debug("Unable to load image: ", ex);
		}
		return null;
	}
	/////////// END SPRITES ///////////

	/////////// POPUP ///////////
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
	/////////// END POPUP ///////////
}
