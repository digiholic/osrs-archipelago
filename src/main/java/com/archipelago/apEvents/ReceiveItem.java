package com.archipelago.apEvents;

import com.archipelago.ArchipelagoPlugin;
import com.archipelago.ItemHandler;
import gg.archipelago.client.events.ArchipelagoEventListener;
import gg.archipelago.client.events.ReceiveItemEvent;

import java.text.MessageFormat;

public class ReceiveItem {

    @ArchipelagoEventListener
    public void onReceiveItem(ReceiveItemEvent event) {
        if (event.getIndex() >= ArchipelagoPlugin.plugin.lastItemReceivedIndex){
            ArchipelagoPlugin.plugin.addCollectedItem(ItemHandler.ItemsById.get(event.getItemID()));
            ArchipelagoPlugin.plugin.lastItemReceivedIndex = event.getIndex();
            //Don't send messages for items received on startup:
            if (event.getIndex() > 0){
                String messageBody = "Received from " +
                        event.getPlayerName() +
                        " at " +
                        event.getLocationName();
                ArchipelagoPlugin.plugin.QueuePopupMessage(event.getItemName(), messageBody);
            }
        }
    }
}