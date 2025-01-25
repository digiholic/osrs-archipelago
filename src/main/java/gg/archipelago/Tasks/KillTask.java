package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.SpriteID;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;

public class KillTask extends APTask{
    private final long _ID;
    private final String _mobName;
    private boolean _isCompleted = false;


    public KillTask(long ID, String mobName){
        _ID = ID;
        _mobName = mobName;

    }

    @Override
    public void CheckMobKill(NPC npc) {
        if (_mobName.equalsIgnoreCase(npc.getName())){
            _isCompleted = true;
        }
    }

    @Override
    public void CheckPlayerStatus(Client client) { }
    @Override
    public void OnGameTick(Client client) { }
    @Override
    public void OnMenuOption(MenuOptionClicked event) { }
    @Override
    public void CheckChatMessage(ChatMessage event) { }
    @Override
    public boolean IsCompleted() {
        return _isCompleted;
    }

    @Override
    public int GetSpriteID() {
        return SpriteID.TAB_COMBAT;
    }

    @Override
    public boolean ShouldDisplayPanel() {
        return true;
    }

    @Override
    public String GetName() {
        return String.format("Kill %s",_mobName);
    }
    @Override
    public void SetCompleted() { _isCompleted = true; }

    @Override
    public long GetID() {
        return _ID;
    }


    @Override
    public boolean CanManuallyActivate() {
        return true;
    }
}
