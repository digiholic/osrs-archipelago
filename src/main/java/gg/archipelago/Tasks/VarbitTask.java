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
    void CheckPlayerStatus(Client client) {
        _isCompleted = client.getServerVarbitValue(_varbitToCheck) == _valueToCheck;
    }

    @Override
    void OnGameTick(Client client) { }
    @Override
    void OnMenuOption(MenuOptionClicked event) { }
    @Override
    void CheckChatMessage(String message) { }
    @Override
    void CheckMobKill(NPC npc) { }
    @Override
    boolean IsCompleted() {
        return _isCompleted;
    }

    @Override
    long GetID() {
        return _ID;
    }
}
