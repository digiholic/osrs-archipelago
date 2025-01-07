package gg.archipelago.Tasks;

import gg.archipelago.Tasks.APTask;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.World;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.MenuOptionClicked;

public class StandInPositionTask extends APTask {
    private final long _ID;
    private final String _name;
    private boolean _isCompleted;
    private final int _spriteID;
    private final WorldPoint _locationPoint;

    public StandInPositionTask(long ID, String name, int spriteID, int x, int y, int plane){
        _ID = ID;
        _name = name;
        _spriteID = spriteID;
        _locationPoint = new WorldPoint(x, y, plane);
    }

    @Override
    public void OnGameTick(Client client) {
        WorldPoint point = client.getLocalPlayer().getWorldLocation();
        if (point.equals(_locationPoint)){
            SetCompleted();
        }
    }

    @Override
    public String GetName() { return _name; }
    @Override
    public long GetID() { return _ID; }
    @Override
    public boolean IsCompleted() { return _isCompleted; }
    @Override
    public void SetCompleted() { _isCompleted = true; }

    @Override
    public int GetSpriteID() { return _spriteID; }

    @Override
    public boolean ShouldDisplayPanel() { return true; }

    @Override
    public boolean CanManuallyActivate() { return true; }

    @Override
    public void CheckChatMessage(String message) { }

    @Override
    public void CheckMobKill(NPC npc) { }

    @Override
    public void CheckPlayerStatus(Client client) { }

    @Override
    public void OnMenuOption(MenuOptionClicked event) { }

}
