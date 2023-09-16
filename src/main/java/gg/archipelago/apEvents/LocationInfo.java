package gg.archipelago.apEvents;

import gg.archipelago.ArchipelagoPlugin;
import gg.archipelago.client.events.ArchipelagoEventListener;
import gg.archipelago.client.events.LocationInfoEvent;

public class LocationInfo {
    @ArchipelagoEventListener
    public void onLocationInfo(LocationInfoEvent event)
    {
        System.out.println(event.locations);
    }
}
