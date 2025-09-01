package gg.archipelago.Tasks;

import gg.archipelago.ArchipelagoPlugin;
import gg.archipelago.data.NameOrIDDataSource;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;

public class ChatMessageTask extends APTask{
    private final long _ID;
    private final String _messageToCheck;
    private boolean _isCompleted = false;
    private String _name;
    private final String _category;
    private NameOrIDDataSource _sprite;

    public ChatMessageTask(long ID, String name, String category, NameOrIDDataSource sprite, String messageToCheck){
        _ID = ID;
        _name = name;
        _category = category;
        _sprite = sprite;
        _messageToCheck = messageToCheck;
    }

    @Override
    public void CheckChatMessage(Client client, ChatMessage event) {
        String[] splitMessages = event.getMessage().split("<br>");
        for (String msg : splitMessages) {
            if (msg.equalsIgnoreCase(_messageToCheck.replace("<player>", ArchipelagoPlugin.plugin.getCurrentPlayerName()))){
                _isCompleted = true;
            }
        }
    }

    @Override
    public void CheckMobKill(Client client, NPC npc) { }
    @Override
    public void CheckPlayerStatus(Client client) { }
    @Override
    public void OnGameTick(Client client) { }
    @Override
    public void OnMenuOption(Client client, MenuOptionClicked event) {

    }

    @Override
    public boolean IsCompleted() {
        return _isCompleted;
    }
    @Override
    public void SetCompleted() { _isCompleted = true; }

    @Override
    public int GetSpriteID() {
        if (_sprite.isID)
            return _sprite.idValue;
        else return APTask.IconByName(_sprite.nameValue);
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


    @Override
    public boolean CanManuallyActivate() {
        return true;
    }
}
