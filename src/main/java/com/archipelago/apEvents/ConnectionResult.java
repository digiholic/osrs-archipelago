package com.archipelago.apEvents;

import ArchipelagoMW.APClient;
import ArchipelagoMW.Archipelago;
import ArchipelagoMW.LocationTracker;
import ArchipelagoMW.SlotData;
import ArchipelagoMW.patches.SavePatch;
import ArchipelagoMW.teams.PlayerManager;
import ArchipelagoMW.teams.TeamManager;
import ArchipelagoMW.ui.RewardMenu.ArchipelagoRewardScreen;
import ArchipelagoMW.ui.connection.ConnectionPanel;
import ArchipelagoMW.util.DeathLinkHelper;
import com.archipelago.OSRSClient;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import downfall.patches.EvilModeCharacterSelect;
import gg.archipelago.client.events.ArchipelagoEventListener;
import gg.archipelago.client.events.ConnectionResultEvent;
import gg.archipelago.client.helper.DeathLink;

import java.util.Collections;

public class ConnectionResult {

    @ArchipelagoEventListener
    public void onConnectionResult(ConnectionResultEvent event) {
        String msg;
        switch (event.getResult()) {
            case SlotAlreadyTaken:
                msg = "Slot already in use.";
                break;
            case Success:
                msg = "Connected Starting Game.";
                break;
            case InvalidSlot:
                msg = "Invalid Slot Name. Please make sure you typed it correctly.";
                break;
            case InvalidPassword:
                msg = "Invalid Password";
                break;
            case IncompatibleVersion:
                msg = "Server Rejected our connection due to an incompatible communication protocol.";
                break;
            default:
                msg = "Unknown Error";
        }
        ConnectionPanel.connectionResultText = msg;

        if (event.getResult() != gg.archipelago.client.network.ConnectionResult.Success)
            return;


        LocationTracker.reset();
        LocationTracker.scoutAllLocations();
        DataStorageGet.loadRequestId = APClient.apClient.dataStorageGet(Collections.singleton(SavePatch.AP_SAVE_STRING));
    }

    public static void start() {

        Archipelago.logger.info("about to parse slot data");
        try {

            Archipelago.logger.info("character: " + character.name);
            Archipelago.logger.info("heart: " + APClient.slotData.finalAct);
            Archipelago.logger.info("seed: " + APClient.slotData.seed);
            Archipelago.logger.info("ascension: " + APClient.slotData.ascension);

            CardCrawlGame.chosenCharacter = character.chosenClass;
            if (Loader.isModLoaded("downfall"))
                EvilModeCharacterSelect.evilMode = APClient.slotData.downfall == 1;

            if (APClient.slotData.deathLink > 0) {
                DeathLink.setDeathLinkEnabled(true);
            }

            DeathLinkHelper.update.sendDeath = false;

            Settings.isFinalActAvailable = (APClient.slotData.finalAct == 1);
            SeedHelper.setSeed(APClient.slotData.seed);

            AbstractDungeon.isAscensionMode = (APClient.slotData.ascension > 0);
            AbstractDungeon.ascensionLevel = APClient.slotData.ascension;

            AbstractDungeon.generateSeeds();
            Settings.seedSet = true;

            CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.MAIN_MENU;
            CardCrawlGame.mainMenuScreen.isFadingOut = true;
            CardCrawlGame.mainMenuScreen.fadeOutMusic();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
