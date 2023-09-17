package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuOptionClicked;

public class ChatMessageTask extends APTask{
    private final long _ID;
    private final String _messageToCheck;
    private boolean _isCompleted = false;
    private String _name;
    private int _spriteID;

    public ChatMessageTask(String name, int spriteID, long ID, String messageToCheck){
        _name = name;
        _spriteID = spriteID;
        _ID = ID;
        _messageToCheck = messageToCheck;

    }

    @Override
    public void CheckChatMessage(String message) {
        if (message.equalsIgnoreCase(_messageToCheck)){
            _isCompleted = true;
        }
    }

    @Override
    public void CheckMobKill(NPC npc) { }
    @Override
    public void CheckPlayerStatus(Client client) { }
    @Override
    public void OnGameTick(Client client) { }
    @Override
    public void OnMenuOption(MenuOptionClicked event) {

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
}
