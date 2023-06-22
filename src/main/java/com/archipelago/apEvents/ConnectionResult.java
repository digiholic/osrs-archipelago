package com.archipelago.apEvents;

import com.archipelago.ArchipelagoPanel;
import gg.archipelago.client.events.ArchipelagoEventListener;
import gg.archipelago.client.events.ConnectionResultEvent;
import gg.archipelago.client.helper.DeathLink;

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

        ArchipelagoPanel.apPanel.statusText.setText(msg);
        if (event.getResult() != gg.archipelago.client.network.ConnectionResult.Success)
            return;


        //LocationTracker.reset();
        //LocationTracker.scoutAllLocations();
        //DataStorageGet.loadRequestId = APClient.apClient.dataStorageGet(Collections.singleton(SavePatch.AP_SAVE_STRING));
    }
}
