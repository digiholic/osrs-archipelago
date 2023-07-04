package com.archipelago.apEvents;

import com.archipelago.ArchipelagoPlugin;
import com.archipelago.ItemHandler;
import gg.archipelago.client.events.ArchipelagoEventListener;
import gg.archipelago.client.events.ReceiveItemEvent;

public class ReceiveItem {

    @ArchipelagoEventListener
    public void onReceiveItem(ReceiveItemEvent event) {
        if (event.getIndex() > ArchipelagoPlugin.plugin.lastItemReceivedIndex){
            ArchipelagoPlugin.plugin.addCollectedItem(ItemHandler.ItemsById.get(event.getItemID()));
            ArchipelagoPlugin.plugin.lastItemReceivedIndex = event.getIndex();
        }
    }
}