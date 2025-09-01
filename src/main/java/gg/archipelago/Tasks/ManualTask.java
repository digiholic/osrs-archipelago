package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Skill;
import net.runelite.api.gameval.SpriteID;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;

import java.util.Locale;

public class ManualTask extends APTask {
    private final long _ID;
    private boolean _isCompleted;
    private final String _name;
    private final String _category;

    public ManualTask(long ID, String name, String category){
        _ID = ID;
        _name = name;
        _category = category;
    }
    @Override
    public void CheckPlayerStatus(Client client) { }

    @Override
    public void OnGameTick(Client client) { }
    @Override
    public void OnMenuOption(Client client, MenuOptionClicked event) { }

    @Override
    public boolean CanManuallyActivate() { return true; }

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
        return APTask.IconByName(_category);
    }

    @Override
    public boolean ShouldDisplayPanel() {
        return true;
    }

    @Override
    public String GetName() {
        return _name;
    }

    @Override
    public long GetID() {
        return _ID;
    }

    @Override
    public String GetCategory() { return _category; }
}
