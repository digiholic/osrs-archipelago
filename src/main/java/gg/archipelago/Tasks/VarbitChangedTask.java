package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuOptionClicked;

public class VarbitChangedTask extends APTask{
    private final long _ID;
    private final int _varbitToCheck;
    private boolean _isCompleted = false;
    private String _name;
    private int _spriteID;
    private boolean _isInitialized;
    private int _previousVarbitValue;

    public VarbitChangedTask(long ID, String name, int SpriteID, int varbitToCheck){
        _ID = ID;
        _name = name;
        _spriteID = SpriteID;
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
    public void OnMenuOption(MenuOptionClicked event) { }
    @Override
    public void CheckChatMessage(String message) { }
    @Override
    public void CheckMobKill(NPC npc) { }
    @Override
    public boolean IsCompleted() {
        return _isCompleted;
    }

    @Override
    public int GetSpriteID() {
        return _spriteID;
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
    public boolean CanManuallyActivate() {
        return true;
    }
}
