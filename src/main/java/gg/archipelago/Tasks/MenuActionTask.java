package gg.archipelago.Tasks;

import gg.archipelago.ArchipelagoPlugin;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.util.Text;

public class MenuActionTask extends APTask{
    private final long _ID;
    private boolean _isCompleted = false;
    private String _name;
    private int _spriteID;
    private final String _menuAction;
    private final String _menuTarget;


    public MenuActionTask(long ID, String name, int spriteID, String menuAction, String menuTarget){
        _ID = ID;
        _name = name;
        _spriteID = spriteID;
        _menuAction = menuAction;
        _menuTarget = menuTarget;
    }

    @Override
    public void CheckChatMessage(ChatMessage event) { }
    @Override
    public void CheckMobKill(NPC npc) { }
    @Override
    public void CheckPlayerStatus(Client client) { }
    @Override
    public void OnGameTick(Client client) { }
    @Override
    public void OnMenuOption(MenuOptionClicked event) {
        if (event.getMenuOption().equalsIgnoreCase(_menuAction) &&
                Text.removeTags(event.getMenuTarget()).equalsIgnoreCase(_menuTarget)){
            SetCompleted();
        }
    }

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
