package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.SpriteID;
import net.runelite.api.events.MenuOptionClicked;

public class CombatLevelTask extends APTask{
    private final long _ID;
    private final int _combatLevelRequired;
    private boolean _isCompleted = false;


    public CombatLevelTask(long ID, int combatLevelRequired){
        _ID = ID;
        _combatLevelRequired = combatLevelRequired;

    }
    @Override
    public void CheckPlayerStatus(Client client) {
        if (client.getLocalPlayer().getCombatLevel() >= _combatLevelRequired)
            _isCompleted = true;
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
    public void SetCompleted() { _isCompleted = true; }
    @Override
    public int GetSpriteID() { return SpriteID.SKILL_TOTAL; }
    @Override
    public boolean ShouldDisplayPanel() { return true; }
    @Override
    public String GetName() { return String.format("Combat Level %d", _combatLevelRequired); }
    @Override
    public long GetID() {
        return _ID;
    }
}
