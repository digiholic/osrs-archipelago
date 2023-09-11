package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuOptionClicked;

public class CollectionLogTask extends APTask{
    private final long _ID;
    private final String _logName;
    private final int _checksRequired;
    private boolean _isCompleted = false;


    public CollectionLogTask(long ID, String logName, int checksRequired){
        _ID = ID;
        _logName = logName;
        _checksRequired = checksRequired;
    }

    @Override
    public void CheckPlayerStatus(Client client) {
        //Check if collection log of specific name has enough checks
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
