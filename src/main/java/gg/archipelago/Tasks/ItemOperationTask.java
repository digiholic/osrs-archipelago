package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuOptionClicked;

public class ItemOperationTask extends APTask {
    private final long _ID;
    private final int _itemID;
    private final String _option;
    private boolean _isCompleted = false;
    private final String _name;
    private final int _spriteID;

    public ItemOperationTask(long ID, String name, int spriteID, String option, int itemID){
        _ID = ID;
        _name = name;
        _spriteID = spriteID;
        _itemID = itemID;
        _option = option;
    }

    @Override
    public void OnMenuOption(MenuOptionClicked event) {
        if (event.getMenuOption().equalsIgnoreCase(_option) && event.getItemId() == _itemID && event.isItemOp()){
            _isCompleted = true;
        }
    }

    @Override
    public void CheckMobKill(NPC npc) { }
    @Override
    public void CheckPlayerStatus(Client client) { }
    @Override
    public void OnGameTick(Client client) { }
    @Override
    public void CheckChatMessage(String message) { }

    @Override
    public boolean IsCompleted() {
        return _isCompleted;
    }
    @Override
    public void SetCompleted() { _isCompleted = true; }

    @Override
    public int GetSpriteID() {
        return _spriteID;
    }

    @Override
    public boolean ShouldDisplayPanel() {
        return true;
    }

    @Override
    public String GetName() {
        return _name;
    }

    @Override
    public long GetID() {
        return _ID;
    }


    @Override
    public boolean CanManuallyActivate() {
        return true;
    }
}
