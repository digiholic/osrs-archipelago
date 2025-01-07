package gg.archipelago.Tasks;

import gg.archipelago.Tasks.StateTrackingTask;
import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;

import java.util.Arrays;
import java.util.Optional;

public class OpenLockboxTask extends StateTrackingTask {

    private final long _ID;
    private final int _lockboxItemID;

    private int _previousLockboxCount = 0;

    public OpenLockboxTask(Long ID, int lockboxItemID){
        _ID = ID;
        _lockboxItemID = lockboxItemID;
    }

    @Override
    public void CheckChatMessage(String message) { }
    @Override
    public void CheckMobKill(NPC npc) { }
    @Override
    public void CheckPlayerStatus(Client client) { }
    @Override
    public void OnMenuOption(MenuOptionClicked event) {
        //Check for Runecraft option
        if (event.getMenuOption().equalsIgnoreCase("Open")){
            checkTriggered = true;
        }
    }

    @Override
    public String GetName() {
        switch(_lockboxItemID){
            case ItemID.SIMPLE_LOCKBOX:
                return "Open a Simple Lockbox";
            case ItemID.ELABORATE_LOCKBOX:
                return "Open an Elaborate Lockbox";
            case ItemID.ORNATE_LOCKBOX:
                return "Open an Ornate Lockbox";
            default:
                return "Open an Unknown Lockbox";
        }
    }

    @Override
    public long GetID() {
        return _ID;
    }

    @Override
    public int GetSpriteID() {
        return SpriteID.SKILL_THIEVING;
    }

    @Override
    public boolean ShouldDisplayPanel() {
        return true;
    }

    @Override
    boolean CheckResetCondition(Client client) {
        return true;
    }

    @Override
    boolean CheckInitialStateOK(Client client) {
        _previousLockboxCount = 0;
        ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
        if (inventory != null) {
            Item[] currentItems = inventory.getItems();
            final int[] item_count = {0}; //anti lambda capture shenanigans
            Arrays.stream(currentItems).filter(item -> item.getId() == _lockboxItemID)
                    .forEach(item -> item_count[0] += item.getQuantity());
            _previousLockboxCount = item_count[0];
        }

        //Check for level, presence of essence, optional presence of core
        return _previousLockboxCount > 0;
    }

    @Override
    boolean CheckPostTriggerStateOK(Client client) {
        int currentLockboxCount = _previousLockboxCount;
        ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
        if (inventory != null) {
            Item[] currentItems = inventory.getItems();
            final int[] item_count = {0}; //anti lambda capture shenanigans
            Arrays.stream(currentItems).filter(item -> item.getId() == _lockboxItemID)
                    .forEach(item -> item_count[0] += item.getQuantity());
            currentLockboxCount =item_count[0];
        }

        //Check for level, presence of essence, optional presence of core
        return _previousLockboxCount > currentLockboxCount;
    }
}
