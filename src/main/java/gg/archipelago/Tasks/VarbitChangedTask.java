package gg.archipelago.Tasks;

import gg.archipelago.data.NameOrIDDataSource;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;

public class VarbitChangedTask extends APTask{
    private final long _ID;
    private final int _varbitToCheck;
    private boolean _isCompleted = false;
    private final String _name;
    private final String _category;
    private final NameOrIDDataSource _sprite;
    private boolean _isInitialized;
    private int _previousVarbitValue;

    public VarbitChangedTask(long ID, String name, String category, NameOrIDDataSource sprite, int varbitToCheck){
        _ID = ID;
        _name = name;
        _category = category;
        _sprite = sprite;
        _varbitToCheck = varbitToCheck;
    }

    @Override
    public void CheckPlayerStatus(Client client) {
        if (!_isInitialized){
            _previousVarbitValue = client.getServerVarbitValue(_varbitToCheck);
            _isInitialized = true;
        }


        if (client.getServerVarbitValue(_varbitToCheck) != _previousVarbitValue)
            _isCompleted = true;
    }

    @Override
    public void OnGameTick(Client client) { }
    @Override
    public void OnMenuOption(Client client, MenuOptionClicked event) { }
    @Override
    public void CheckChatMessage(Client client, ChatMessage event) { }
    @Override
    public void CheckMobKill(Client client, NPC npc) { }
    @Override
    public boolean IsCompleted() {
        return _isCompleted;
    }

    @Override
    public int GetSpriteID() {
        if (_sprite.isID)
            return _sprite.idValue;
        else return APTask.IconByName(_sprite.nameValue);
    }
    @Override
    public void SetCompleted() { _isCompleted = true; }

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
