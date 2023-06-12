package com.archipelago;

import com.google.inject.Provides;
import javax.inject.Inject;

import gg.archipelago.APClient.APWebSocket;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;

@Slf4j
@PluginDescriptor(
	name = "Archipelago Randomizer"
)
public class ArchipelagoPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ArchipelagoConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{

		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Provides
    ArchipelagoConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ArchipelagoConfig.class);
	}

	boolean Q_Cooks_Assistant;
	boolean Q_Demon_Slayer;
	boolean Q_Restless_Ghost;
	boolean Q_Romeo_Juliet;
	boolean Q_Sheep_Shearer;
	boolean Q_Shield_of_Arrav;
	boolean Q_Ernest_the_Chicken;
	boolean Q_Vampyre_Slayer;
	boolean Q_Imp_Catcher;
	boolean Q_Prince_Ali_Rescue;
	boolean Q_Dorics_Quest;
	boolean Q_Black_Knights_Fortress;
	boolean Q_Witchs_Potion;
	boolean Q_Knights_Sword;
	boolean Q_Goblin_Diplomacy;
	boolean Q_Pirates_Treasure;
	boolean Q_Rune_Mysteries;
	boolean Q_Misthalin_Mystery;
	boolean Q_Corsair_Curse;
	boolean Q_X_Marks_the_Spot;
	boolean Q_Below_Ice_Mountain;
	boolean Stronghold_Of_Security;
	boolean Simple_Lockbox;
	boolean Elaborate_Lockbox;
	boolean Ornate_Lockbox;
	boolean Guppy;
	boolean Cavefish;
	boolean Tetra;
	boolean Mind_Core;
	boolean Body_Core;
	boolean Barronite_Deposit;
	boolean Beginner_Clue;
	boolean Edgeville_Altar;
	boolean Oak_Log;
	boolean Willow_Log;
	boolean Catch_Lobster;
	boolean Catch_Swordfish;
	boolean Holy_Symbol;
	boolean Mine_Silver;
	boolean Mine_Coal;
	boolean Mine_Gold;
	boolean Smelt_Silver;
	boolean Smelt_Steel;
	boolean Smelt_Gold;
	boolean Cut_Sapphire;
	boolean Cut_Emerald;
	boolean Cut_Ruby;
	boolean Cut_Diamond;
	boolean K_Lesser_Demon;
	boolean K_Ogress_Shaman;
	boolean K_Obor;
	boolean K_Bryo;
	boolean Bake_Apple_Pie;
	boolean Bake_Cake;
	boolean Bake_Meat_Pizza;
	boolean Total_XP_5000;
	boolean Total_XP_25000;
	boolean Total_XP_50000;
	boolean Total_Level_50;
	boolean Total_Level_100;
	boolean Total_Level_200;
	boolean Combat_Level_10;
	boolean Combat_Level_25;
	boolean Combat_Level_50;
	boolean Q_Dragon_Slayer;

	private void checkStatus(){
		Q_Cooks_Assistant = Quest.COOKS_ASSISTANT.getState(client) == QuestState.FINISHED;
		Q_Demon_Slayer = Quest.DEMON_SLAYER.getState(client) == QuestState.FINISHED;
		Q_Restless_Ghost = Quest.THE_RESTLESS_GHOST.getState(client) == QuestState.FINISHED;
		Q_Romeo_Juliet = Quest.ROMEO__JULIET.getState(client) == QuestState.FINISHED;
		Q_Sheep_Shearer = Quest.SHEEP_SHEARER.getState(client) == QuestState.FINISHED;
		Q_Shield_of_Arrav = Quest.SHIELD_OF_ARRAV.getState(client) == QuestState.FINISHED;
		Q_Ernest_the_Chicken = Quest.ERNEST_THE_CHICKEN.getState(client) == QuestState.FINISHED;
		Q_Vampyre_Slayer = Quest.VAMPYRE_SLAYER.getState(client) == QuestState.FINISHED;
		Q_Imp_Catcher = Quest.IMP_CATCHER.getState(client) == QuestState.FINISHED;
		Q_Prince_Ali_Rescue = Quest.PRINCE_ALI_RESCUE.getState(client) == QuestState.FINISHED;
		Q_Dorics_Quest = Quest.DORICS_QUEST.getState(client) == QuestState.FINISHED;
		Q_Black_Knights_Fortress = Quest.BLACK_KNIGHTS_FORTRESS.getState(client) == QuestState.FINISHED;
		Q_Witchs_Potion = Quest.WITCHS_POTION.getState(client) == QuestState.FINISHED;
		Q_Knights_Sword = Quest.THE_KNIGHTS_SWORD.getState(client) == QuestState.FINISHED;
		Q_Goblin_Diplomacy = Quest.GOBLIN_DIPLOMACY.getState(client) == QuestState.FINISHED;
		Q_Pirates_Treasure = Quest.PIRATES_TREASURE.getState(client) == QuestState.FINISHED;
		Q_Rune_Mysteries = Quest.RUNE_MYSTERIES.getState(client) == QuestState.FINISHED;
		Q_Misthalin_Mystery = Quest.MISTHALIN_MYSTERY.getState(client) == QuestState.FINISHED;
		Q_Corsair_Curse = Quest.THE_CORSAIR_CURSE.getState(client) == QuestState.FINISHED;
		Q_X_Marks_the_Spot = Quest.X_MARKS_THE_SPOT.getState(client) == QuestState.FINISHED;
		Q_Below_Ice_Mountain = Quest.BELOW_ICE_MOUNTAIN.getState(client) == QuestState.FINISHED;
		//Stronghold_Of_Security = "Stronghold of Security"
		//Simple_Lockbox = "Open a Simple Lockbox"
		//Elaborate_Lockbox = "Open an Elaborate Lockbox"
		//Ornate_Lockbox = "Open an Ornate Lockbox"
		//Mind_Core = "Craft runes with a Mind Core"
		//Body_Core = "Craft runes with a Body Core"
		//Beginner_Clue = "Beginner Clue Completion"
		//Edgeville_Altar = "Pray at the Edgeville Monastery"
		//Catch_Lobster = "Catch a Lobster"
		//Catch_Swordfish = "Catch a Swordfish"
		//Holy_Symbol = "Make a Holy Symbol"
		Total_XP_5000 = client.getOverallExperience() > 5000;
		Total_XP_25000 = client.getOverallExperience() > 25000;
		Total_XP_50000 = client.getOverallExperience() > 50000;
		Total_Level_50 = client.getTotalLevel() > 50;
		Total_Level_100 = client.getTotalLevel() > 100;
		Total_Level_200 = client.getTotalLevel() > 200;
		Combat_Level_10 = client.getLocalPlayer().getCombatLevel() >= 10;
		Combat_Level_25 = client.getLocalPlayer().getCombatLevel() >= 25;
		Combat_Level_50 = client.getLocalPlayer().getCombatLevel() >= 50;
		Q_Dragon_Slayer = Quest.DRAGON_SLAYER_I.getState(client) == QuestState.FINISHED;
	}

	@Subscribe
	public void onNpcLootReceived(final NpcLootReceived npcLootReceived)
	{
		final NPC npc = npcLootReceived.getNpc();
		final String name = npc.getName();
		if ("Lesser Demon".equals(name)) {
			K_Lesser_Demon = true;
		}
		if ("Ogress Shaman".equals(name)) {
			K_Ogress_Shaman = true;
		}
		if ("Obor".equals(name)) {
			K_Obor = true;
		}
		if ("Bryophyta".equals(name)) {
			K_Bryo = true;
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
	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		var message = event.getMessage();
		if (event.getType() != ChatMessageType.SPAM)
		{
			return;
		}
		else if (OAK_MESSAGE.equals(message))
			Oak_Log = true;
		else if (WILLOW_MESSAGE.equals(message))
			Willow_Log = true;
		else if (SAPPHIRE_MESSAGE.equals(message))
			Cut_Sapphire = true;
		else if (EMERALD_MESSAGE.equals(message))
			Cut_Emerald = true;
		else if (RUBY_MESSAGE.equals(message))
			Cut_Ruby = true;
		else if (DIAMOND_MESSAGE.equals(message))
			Cut_Diamond = true;
		else if (COAL_MINED_MESSAGE.equals(message))
			Mine_Coal = true;
		else if (SILVER_MINED_MESSAGE.equals(message))
			Mine_Silver = true;
		else if (GOLD_MINED_MESSAGE.equals(message))
			Mine_Gold = true;
		else if (STEEL_SMELTED_MESSAGE.equals(message))
			Smelt_Steel = true;
		else if (SILVER_SMELTED_MESSAGE.equals(message))
			Smelt_Silver = true;
		else if (GOLD_SMELTED_MESSAGE.equals(message))
			Smelt_Gold = true;
		else if (CAKE_MESSAGE.equals(message))
			Bake_Cake = true;
		else if (APPLE_PIE_MESSAGE.equals(message))
			Bake_Apple_Pie = true;
		else if (PIZZA_MESSAGE.equals(message))
			Bake_Meat_Pizza = true;
		else if (CRUSH_BARRONITE_MESSAGE.equals(message))
			Barronite_Deposit = true;
		else if (GUPPY_MESSAGE.equals(message))
			Guppy = true;
		else if (CAVEFISH_MESSAGE.equals(message))
			Cavefish = true;
		else if (TETRA_MESSAGE.equals(message))
			Tetra = true;
	}

	private void ConnectToAPServer(String url, int port, String slotName, String password){
		URI.createURI(url, port);
		APWebSocket sock = new APWebSocket(url, null);
		try (Socket socket = new Socket(url, port)){

		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
