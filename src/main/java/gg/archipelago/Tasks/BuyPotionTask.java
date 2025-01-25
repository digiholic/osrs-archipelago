package gg.archipelago.Tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.util.Text;

import java.util.Arrays;

@Slf4j
public class BuyPotionTask extends StateTrackingTask{

    private final long _ID;

    private final String _name = "Have the Apothecary Make a Strength Potion";

    private boolean _shouldCancel = false;

    private int _previousPotionCount = 0;

    public BuyPotionTask(Long ID){
        _ID = ID;
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
        if (event.getMenuOption().startsWith("Buy-") &&
                Text.removeTags(event.getMenuTarget()).equalsIgnoreCase("Strength potion")){
            checkTriggered = true;
            _shouldCancel = false;
            log.info("BuyPotion task is primed.");
        } else {
            _shouldCancel = true;
        }
    }

    @Override
    public String GetName() {
        return _name;
    }

    @Override
    public long GetID() { return _ID; }
    @Override
    public int GetSpriteID() { return SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET; }
    @Override
    public boolean ShouldDisplayPanel() { return true; }

    @Override
    boolean CheckResetCondition(Client client) {
        return _shouldCancel;
    }

    @Override
    boolean CheckInitialStateOK(Client client) {
        _previousPotionCount = CheckInventoryFor(client, ItemID.STRENGTH_POTION4);

        return true;
    }

    @Override
    boolean CheckPostTriggerStateOK(Client client) {
        int currentPotionCount = CheckInventoryFor(client, ItemID.STRENGTH_POTION4);
        return currentPotionCount > _previousPotionCount;
    }

    private int CheckInventoryFor(Client client, int itemID) {
        ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
        if (inventory != null) {
            Item[] currentItems = inventory.getItems();
            final int finalItem_id = itemID;
            final int[] item_count = {0}; //anti lambda capture shenanigans
            Object[] items = Arrays.stream(currentItems).filter(item -> item.getId() == finalItem_id).toArray();

            Arrays.stream(currentItems).filter(item -> item.getId() == finalItem_id)
                    .forEach(item -> item_count[0] += item.getQuantity());
            return item_count[0];
        }
        return 0;
    }
}
