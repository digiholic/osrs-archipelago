package gg.archipelago.Tasks;

import gg.archipelago.data.NameOrIDDataSource;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;

public class ItemOperationTask extends APTask {
    private final long _ID;
    private final int _itemID;
    private final String _option;
    private boolean _isCompleted = false;
    private final String _name;
    private final NameOrIDDataSource _sprite;
    private final String _category;

    public ItemOperationTask(long ID, String name, String category, NameOrIDDataSource sprite, String option, int itemID){
        _ID = ID;
        _name = name;
        _category = category;
        _sprite = sprite;
        _itemID = itemID;
        _option = option;
    }

    @Override
    public void OnMenuOption(Client client, MenuOptionClicked event) {

        if (event.getMenuOption().equalsIgnoreCase(_option) && event.getItemId() == _itemID && event.isItemOp()){
            _isCompleted = true;
        }
    }

    @Override
    public void CheckMobKill(Client client, NPC npc) { }
    @Override
    public void CheckPlayerStatus(Client client) { }
    @Override
    public void OnGameTick(Client client) { }
    @Override
    public void CheckChatMessage(Client client, ChatMessage event) { }

    @Override
    public boolean IsCompleted() {
        return _isCompleted;
    }
    @Override
    public void SetCompleted() { _isCompleted = true; }

    @Override
    public int GetSpriteID() {
        if (_sprite.isID)
            return _sprite.idValue;
        else return APTask.IconByName(_sprite.nameValue);
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
    public String GetCategory() { return _category; }


    @Override
    public boolean CanManuallyActivate() {
        return true;
    }
}
