package com.archipelago.apEvents;

import ArchipelagoMW.patches.SavePatch;
import ArchipelagoMW.ui.connection.ArchipelagoPreGameScreen;
import ArchipelagoMW.ui.mainMenu.ArchipelagoMainMenuButton;
import gg.archipelago.client.events.ArchipelagoEventListener;
import gg.archipelago.client.events.RetrievedEvent;

public class DataStorageGet {

    public static int loadRequestId;

    @ArchipelagoEventListener
    public void dataStorageGet(RetrievedEvent event) {
        if (event.getRequestID() == loadRequestId) {
            if (event.getString(SavePatch.AP_SAVE_STRING) != null && !event.getString(SavePatch.AP_SAVE_STRING).isEmpty()) {
                SavePatch.compressedSave = event.getString(SavePatch.AP_SAVE_STRING);
                ArchipelagoMainMenuButton.archipelagoPreGameScreen.connectionPanel.resumeSave.show();
            } else {
                ArchipelagoMainMenuButton.archipelagoPreGameScreen.screen = ArchipelagoPreGameScreen.APScreen.team;
            }
        }
    }
}

