package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
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
        _isCompleted = client.getLocalPlayer().getCombatLevel() >= _combatLevelRequired;
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
