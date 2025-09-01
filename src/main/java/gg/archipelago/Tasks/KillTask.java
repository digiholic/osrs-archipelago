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
    private final String _category;

    public KillTask(long ID, String category, String mobName){
        _ID = ID;
        _category = category;
        _mobName = mobName;

    }

    @Override
    public void CheckMobKill(Client client, NPC npc) {
        if (_mobName.equalsIgnoreCase(npc.getName())){
            _isCompleted = true;
        }
    }

    @Override
    public void CheckPlayerStatus(Client client) { }
    @Override
    public void OnGameTick(Client client) { }
    @Override
    public void OnMenuOption(Client client, MenuOptionClicked event) { }
    @Override
    public void CheckChatMessage(Client client, ChatMessage event) { }
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
    public String GetCategory() { return _category; }


    @Override
    public boolean CanManuallyActivate() {
        return true;
    }
}
