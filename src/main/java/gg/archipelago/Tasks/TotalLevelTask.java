package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuOptionClicked;

public class TotalLevelTask extends APTask{
    private final long _ID;
    private final int _totalLevelRequired;
    private boolean _isCompleted = false;


    public TotalLevelTask(long ID, int totalLevelRequired){
        _ID = ID;
        _totalLevelRequired = totalLevelRequired;

    }
    @Override
    void CheckPlayerStatus(Client client) {
        _isCompleted = client.getTotalLevel() >= _totalLevelRequired;
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
