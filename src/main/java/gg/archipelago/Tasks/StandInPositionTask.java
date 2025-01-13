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
    private final WorldPoint[] _locationPoints;

    public StandInPositionTask(long ID, String name, int spriteID, int x, int y, int plane){
        _ID = ID;
        _name = name;
        _spriteID = spriteID;
        _locationPoints = new WorldPoint[] { new WorldPoint(x, y, plane) };
    }

    public StandInPositionTask(long ID, String name, int spriteID, int[] positions){
        _ID = ID;
        _name = name;
        _spriteID = spriteID;
        _locationPoints = new WorldPoint[positions.length / 3];
        for (int i=0; i < positions.length; i = i+3){
            _locationPoints[i / 3] = new WorldPoint(positions[i], positions[i+1], positions[i+2]);
        }
    }

    @Override
    public void OnGameTick(Client client) {
        WorldPoint point = client.getLocalPlayer().getWorldLocation();
        for (WorldPoint p : _locationPoints) {
            if (point.equals(p)){
                SetCompleted();
                return;
            }
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
