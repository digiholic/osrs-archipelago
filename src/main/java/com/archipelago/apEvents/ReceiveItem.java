package com.archipelago.apEvents;

import ArchipelagoMW.ui.RewardMenu.ArchipelagoRewardScreen;
import gg.archipelago.client.events.ArchipelagoEventListener;
import gg.archipelago.client.events.ReceiveItemEvent;

public class ReceiveItem {

    @ArchipelagoEventListener
    public void onReceiveItem(ReceiveItemEvent event) {
        if(event.getIndex() > ArchipelagoRewardScreen.receivedItemsIndex) {
            // only increase counter, actual items get fetched when you open the reward screen.
            ArchipelagoRewardScreen.rewardsQueued += 1;
        }
    }
}
