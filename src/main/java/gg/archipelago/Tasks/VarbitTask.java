package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuOptionClicked;

public class VarbitTask extends APTask{
    private final long _ID;
    private final int _varbitToCheck;
    private final int _valueToCheck;
    private boolean _isCompleted = false;


    public VarbitTask(long ID, int varbitToCheck, int valueToCheck){
        _ID = ID;
        _varbitToCheck = varbitToCheck;
        _valueToCheck = valueToCheck;
    }

    @Override
    public void CheckPlayerStatus(Client client) {
        _isCompleted = client.getServerVarbitValue(_varbitToCheck) == _valueToCheck;
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
    public long GetID() {
        return _ID;
    }
}
