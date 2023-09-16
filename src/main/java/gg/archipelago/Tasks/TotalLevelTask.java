package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.SpriteID;
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
    public void CheckPlayerStatus(Client client) {
        _isCompleted = client.getTotalLevel() >= _totalLevelRequired;
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
        return SpriteID.SKILL_TOTAL;
    }

    @Override
    public boolean ShouldDisplayPanel() {
        return true;
    }

    @Override
    public String GetName() {
        return String.format("Skill Total %d",_totalLevelRequired);
    }

    @Override
    public long GetID() {
        return _ID;
    }
}
