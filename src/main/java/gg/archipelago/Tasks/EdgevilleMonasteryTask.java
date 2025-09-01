package gg.archipelago.Tasks;

import gg.archipelago.Tasks.APTask;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;

public class EdgevilleMonasteryTask extends APTask {
    private final long _ID;
    private boolean _isCompleted;
    private final String _category;
    private int _currentRegionID;
    public EdgevilleMonasteryTask(long ID, String category)
    {
        _ID = ID;
        _category = category;
    }
    @Override
    public void CheckPlayerStatus(Client client) {
        Player player = client.getLocalPlayer();
        if (player == null) {
            _currentRegionID = -1;
        } else {
            _currentRegionID = WorldPoint.fromLocalInstance(client, player.getLocalLocation()).getRegionID();
        }
    }

    @Override
    public void OnGameTick(Client client) { }
    @Override
    public void OnMenuOption(Client client, MenuOptionClicked event) { }

    @Override
    public boolean CanManuallyActivate() { return true; }

    @Override
    public void CheckChatMessage(Client client, ChatMessage event) {
        // If the player is not currently in the monastery chunk, exit without checking.
        if (_currentRegionID != 12086) return;

        String[] splitMessages = event.getMessage().split("<br>");
        for (String msg : splitMessages) {
            String _message_B = "You boost your Prayer points.";
            String _message_A = "You recharge your Prayer points.";
            if (msg.equalsIgnoreCase(_message_A) || msg.equals(_message_B)){
                _isCompleted = true;
            }
        }
    }
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
        return SpriteID.SKILL_PRAYER;
    }

    @Override
    public boolean ShouldDisplayPanel() {
        return true;
    }

    @Override
    public String GetName() {
        return "Pray at the Edgeville Monastery";
    }

    @Override
    public long GetID() {
        return _ID;
    }

    @Override
    public String GetCategory() { return _category; }
}
