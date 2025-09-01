package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.SpriteID;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;

public class TotalXPTask extends APTask{
    private final long _ID;
    private final int _totalXPRequired;
    private boolean _isCompleted = false;
    private final String _category;

    public TotalXPTask(long ID, String category, int totalXPRequired){
        _ID = ID;
        _category = category;
        _totalXPRequired = totalXPRequired;

    }
    @Override
    public void CheckPlayerStatus(Client client) {
        if (client.getOverallExperience() >= _totalXPRequired)
            _isCompleted = true;
    }
    @Override
    public void OnGameTick(Client client) { }
    @Override
    public void OnMenuOption(Client client, MenuOptionClicked event) { }

    @Override
    public boolean CanManuallyActivate() {
        return false;
    }

    @Override
    public void CheckChatMessage(Client client, ChatMessage event) { }
    @Override
    public void CheckMobKill(Client client, NPC npc) { }
    @Override
    public boolean IsCompleted() {
        return _isCompleted;
    }
    @Override
    public void SetCompleted() { _isCompleted = true; }
    @Override
    public int GetSpriteID() {
        return SpriteID.SKILL_TOTAL;
    }

    @Override
    public boolean ShouldDisplayPanel() {
        return true;
    }

    @Override
    public String GetName() {
        return String.format("Total XP %,d", _totalXPRequired);
    }

    @Override
    public long GetID() {
        return _ID;
    }

    @Override
    public String GetCategory() { return _category; }
}
