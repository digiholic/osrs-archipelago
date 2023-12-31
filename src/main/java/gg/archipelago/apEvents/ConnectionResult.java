package gg.archipelago.apEvents;

import gg.archipelago.ArchipelagoPlugin;
import gg.archipelago.client.events.ArchipelagoEventListener;
import gg.archipelago.client.events.ConnectionResultEvent;

public class ConnectionResult {

    @ArchipelagoEventListener
    public void onConnectionResult(ConnectionResultEvent event) {
        if (event.getResult() == null) return;

        String msg;
        switch (event.getResult()) {
            case SlotAlreadyTaken:
                msg = "Slot already in use.";
                break;
            case Success:
                msg = "Connection Successful.";
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

        ArchipelagoPlugin.plugin.DisplayNetworkMessage(msg);
        ArchipelagoPlugin.plugin.SetConnectionState(event.getResult() == gg.archipelago.client.network.ConnectionResult.Success);
    }
}
