package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuOptionClicked;

public class ChatMessageTask extends APTask{
    private final long _ID;
    private final String _messageToCheck;
    private boolean _isCompleted = false;


    public ChatMessageTask(long ID, String messageToCheck){
        _ID = ID;
        _messageToCheck = messageToCheck;

    }

    @Override
    void CheckChatMessage(String message) {
        if (message.equalsIgnoreCase(_messageToCheck)){
            _isCompleted = true;
        }
    }

    @Override
    void CheckMobKill(NPC npc) { }
    @Override
    void CheckPlayerStatus(Client client) { }
    @Override
    void OnGameTick(Client client) { }
    @Override
    void OnMenuOption(MenuOptionClicked event) {

    }

    @Override
    boolean IsCompleted() {
        return _isCompleted;
    }

    @Override
    long GetID() {
        return _ID;
    }
}
