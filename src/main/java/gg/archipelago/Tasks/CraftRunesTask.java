package gg.archipelago.Tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class CraftRunesTask extends StateTrackingTask{

    private final long _ID;

    private String _name;

    private int _rune_id;
    //if any essence is acceptable, pass -1 for "Any"
    private int _essence_id;

    private int _previousRunecraftXP;
    private int _previousRuneCount;
    private boolean _shouldCancel = false;

    public CraftRunesTask(Long ID, String name, int rune_id, int essence_id ){
        _ID = ID;
        _name = name;
        _rune_id = rune_id;
        _essence_id = essence_id;
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
        if (event.getMenuOption().equalsIgnoreCase("Craft-rune")){
            checkTriggered = true;
            _shouldCancel = false;
            log.info("Runecraft task is primed.");
        } else {
            _shouldCancel = true;
            _previousRunecraftXP = 0;
            _previousRuneCount = 0;
        }
    }

    @Override
    public String GetName() {
        return _name;
    }

    @Override
    public long GetID() { return _ID; }
    @Override
    public int GetSpriteID() { return SpriteID.SKILL_RUNECRAFT; }
    @Override
    public boolean ShouldDisplayPanel() { return true; }

    @Override
    boolean CheckResetCondition(Client client) {
        return _shouldCancel;
    }

    @Override
    boolean CheckInitialStateOK(Client client) {
        //If the essence ID is -1, it means "Any essence" and we don't need to check for it
        boolean hasEssence = _essence_id == -1 || CheckInventoryFor(client, _essence_id) > 0;
        _previousRunecraftXP = client.getSkillExperience(Skill.RUNECRAFT);
        _previousRuneCount = CheckInventoryFor(client, _rune_id);

        return hasEssence;
    }

    @Override
    boolean CheckPostTriggerStateOK(Client client) {
        boolean hasGainedXP = client.getSkillExperience(Skill.RUNECRAFT) > _previousRunecraftXP;
        boolean hasGainedRunes = CheckInventoryFor(client, _rune_id) > _previousRuneCount;

        //Check for any increase in RC XP and increase in item count for rune
        return hasGainedXP && hasGainedRunes;
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
