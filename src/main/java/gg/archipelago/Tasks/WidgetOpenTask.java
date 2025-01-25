package gg.archipelago.Tasks;

import gg.archipelago.ArchipelagoPlugin;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;

public class WidgetOpenTask extends APTask{
    private final long _ID;
    private boolean _isCompleted = false;
    private final String _name;
    private final int _spriteID;
    private final int _widgetID;

    public WidgetOpenTask(long ID, String name, int spriteID, int widgetID){
        _ID = ID;
        _name = name;
        _spriteID = spriteID;
        _widgetID = widgetID;
    }

    @Override
    public void CheckChatMessage(ChatMessage event) { }

    @Override
    public void CheckMobKill(NPC npc) { }
    @Override
    public void CheckPlayerStatus(Client client) { }
    @Override
    public void OnGameTick(Client client) {
        if (client.getWidget(_widgetID) != null && !client.getWidget(_widgetID).isHidden()){
            SetCompleted();
        }
    }
    @Override
    public void OnMenuOption(MenuOptionClicked event) { }

    @Override
    public boolean IsCompleted() {
        return _isCompleted;
    }
    @Override
    public void SetCompleted() { _isCompleted = true; }

    @Override
    public int GetSpriteID() {
        return _spriteID;
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
    public boolean CanManuallyActivate() {
        return true;
    }
}
