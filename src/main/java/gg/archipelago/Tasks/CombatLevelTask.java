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
    void CheckPlayerStatus(Client client) {
        _isCompleted = client.getLocalPlayer().getCombatLevel() >= _combatLevelRequired;
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
