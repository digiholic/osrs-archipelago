package gg.archipelago.apEvents;

import gg.archipelago.ArchipelagoPlugin;
import dev.koifysh.archipelago.events.ReceiveItemEvent;
import net.runelite.client.eventbus.Subscribe;

public class ReceiveItem {

    @Subscribe
    public void onReceiveItemEvent(ReceiveItemEvent event) {
        ArchipelagoPlugin.plugin.ReceiveItem(event);
    }
}