package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuOptionClicked;

public class TotalXPTask extends APTask{
    private final long _ID;
    private final int _totalXPRequired;
    private boolean _isCompleted = false;


    public TotalXPTask(long ID, int totalXPRequired){
        _ID = ID;
        _totalXPRequired = totalXPRequired;

    }
    @Override
    void CheckPlayerStatus(Client client) {
        _isCompleted = client.getOverallExperience() >= _totalXPRequired;
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
