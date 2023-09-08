package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuOptionClicked;

public class KillTask extends APTask{
    private final long _ID;
    private final String _mobName;
    private boolean _isCompleted = false;


    public KillTask(long ID, String mobName){
        _ID = ID;
        _mobName = mobName;

    }

    @Override
    void CheckMobKill(NPC npc) {
        if (_mobName.equalsIgnoreCase(npc.getName())){
            _isCompleted = true;
        }
    }

    @Override
    void CheckPlayerStatus(Client client) { }
    @Override
    void OnGameTick(Client client) { }
    @Override
    void OnMenuOption(MenuOptionClicked event) { }
    @Override
    void CheckChatMessage(String message) { }
    @Override
    boolean IsCompleted() {
        return _isCompleted;
    }
    @Override
    long GetID() {
        return _ID;
    }
}
