package gg.archipelago.apEvents;

import gg.archipelago.ArchipelagoPlugin;
import gg.archipelago.ItemHandler;
import dev.koifysh.archipelago.events.ArchipelagoEventListener;
import dev.koifysh.archipelago.events.ReceiveItemEvent;

public class ReceiveItem {

    @ArchipelagoEventListener
    public void onReceiveItem(ReceiveItemEvent event) {
        ArchipelagoPlugin.plugin.ReceiveItem(event);
    }
}