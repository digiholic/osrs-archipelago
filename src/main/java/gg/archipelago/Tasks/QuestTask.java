package gg.archipelago.Tasks;

import net.runelite.api.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;

public class QuestTask extends APTask{
    private final long _ID;
    private final Quest _questToCheck;
    private boolean _isCompleted = false;
    private final String _category;

    public QuestTask(long ID, String category, Quest questToCheck){
        _ID = ID;
        _category = category;
        _questToCheck = questToCheck;
    }
    @Override
    public void CheckPlayerStatus(Client client) {
        if (_questToCheck.getState(client) == QuestState.FINISHED)
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
        return SpriteID.TAB_QUESTS;
    }

    @Override
    public boolean ShouldDisplayPanel() {
        return true;
    }
    @Override
    public void SetCompleted() { _isCompleted = true; }

    @Override
    public String GetName() {
        return _questToCheck.getName();
    }

    @Override
    public long GetID() {
        return _ID;
    }
    @Override
    public String GetCategory() { return _category; }
    @Override
    public boolean CanManuallyActivate() {
        return false;
    }
}
