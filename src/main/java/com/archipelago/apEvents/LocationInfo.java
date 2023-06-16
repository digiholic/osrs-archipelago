package com.archipelago.apEvents;

import gg.archipelago.client.events.ArchipelagoEventListener;
import gg.archipelago.client.events.LocationInfoEvent;

public class LocationInfo {
    @ArchipelagoEventListener
    public void onLocationInfo(LocationInfoEvent event)
    {
        //LocationTracker.addToScoutedLocations(event.locations);
    }
}
