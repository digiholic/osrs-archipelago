package gg.archipelago.Tasks;

import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;

public class QuestTask extends APTask{
    private final long _ID;
    private final Quest _questToCheck;
    private boolean _isCompleted = false;


    public QuestTask(long ID, Quest questToCheck){
        _ID = ID;
        _questToCheck = questToCheck;

    }
    @Override
    public void CheckPlayerStatus(Client client) {
        _isCompleted = _questToCheck.getState(client) == QuestState.FINISHED;
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
        return SpriteID.TAB_QUESTS;
    }

    @Override
    public boolean ShouldDisplayPanel() {
        return true;
    }

    @Override
    public String GetName() {
        return _questToCheck.getName();
    }

    @Override
    public long GetID() {
        return _ID;
    }
}
