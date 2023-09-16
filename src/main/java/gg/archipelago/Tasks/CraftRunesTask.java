package gg.archipelago.Tasks;

import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;

import java.util.Arrays;
import java.util.Optional;

public class CraftRunesTask extends StateTrackingTask{

    private final long _ID;

    private int _required_level;
    private int _essence_id;
    private int _previousEssenceCount;
    private int _previousRunecraftXP;

    private RuneType _runeType;
    public CraftRunesTask(Long ID, RuneType runeType){
        _ID = ID;
        _runeType = runeType;

        switch (runeType){
            case AIR_RUNE:
                _required_level = 1;
                _essence_id = ItemID.RUNE_ESSENCE;
                break;
            case MIND_RUNE:
                _required_level = 2;
                _essence_id = ItemID.MIND_CORE;
                break;
            case BODY_RUNE:
                _required_level = 20;
                _essence_id = ItemID.BODY_CORE;
                break;
        }
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
        }
    }

    @Override
    public String GetName() {
        switch (_runeType){
            case AIR_RUNE:
                return "Craft some Air Runes";
            case MIND_RUNE:
                return "Craft some Mind Runes using a Mind Core";
            case BODY_RUNE:
                return "Craft some Body Runes using a Body Core";
            default:
                return "Unknown Runecraft Task";
        }
    }

    @Override
    public long GetID() { return _ID; }
    @Override
    public int GetSpriteID() { return SpriteID.SKILL_RUNECRAFT; }
    @Override
    public boolean ShouldDisplayPanel() { return true; }
    @Override
    boolean CheckInitialStateOK(Client client) {
        if (!(client.getRealSkillLevel(Skill.RUNECRAFT) >= _required_level)) return false;
        _previousRunecraftXP = client.getSkillExperience(Skill.RUNECRAFT);
        ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
        if (inventory != null) {
            Item[] currentItems = inventory.getItems();
            final int finalItem_id = _essence_id;
            final int[] item_count = {0}; //anti lambda capture shenanigans
            Arrays.stream(currentItems).filter(item -> item.getId() == finalItem_id)
                    .forEach(item -> item_count[0] += item.getQuantity());
            _previousEssenceCount = item_count[0];
        }

        //Check for level, presence of essence, optional presence of core
        return _previousEssenceCount > 0;
    }

    @Override
    boolean CheckPostTriggerStateOK(Client client) {
        boolean hasGainedXP = client.getSkillExperience(Skill.RUNECRAFT) > _previousRunecraftXP;
        int currentEssenceCount = 0;
        ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
        if (inventory != null) {
            Item[] currentItems = inventory.getItems();
            final int finalItem_id = _essence_id;
            Optional<Item> essenceStack =
                    Arrays.stream(currentItems).filter(item -> item.getId() == finalItem_id).findFirst();
            currentEssenceCount = essenceStack.map(Item::getQuantity).orElse(0);
        }

        boolean hasSpentEssence = currentEssenceCount < _previousEssenceCount;
        //Check for any increase in RC XP and increase in item count for rune
        return hasGainedXP && hasSpentEssence;
    }

    public enum RuneType {
        AIR_RUNE,
        MIND_RUNE,
        BODY_RUNE
    }
}
