package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.util.Text;

public class EmoteInAreaTask extends APTask {
    private final long _ID;
    private final String _name;
    private boolean _isCompleted;
    private final int _spriteID;
    private final WorldPoint _topleft_point;
    private final WorldPoint _bottomright_point;
    private final String _emote;

    private boolean _in_area;

    public EmoteInAreaTask(long ID, String name, int spriteID, String emote, int tl_x, int tl_y, int tl_plane, int br_x, int br_y, int br_plane){
        _ID = ID;
        _name = name;
        _spriteID = spriteID;
        _emote = emote;
        _topleft_point = new WorldPoint(tl_x, tl_y, tl_plane);
        _bottomright_point = new WorldPoint(br_x, br_y, br_plane);
    }

    @Override
    public void OnMenuOption(MenuOptionClicked event) {
        if (event.getMenuOption().equalsIgnoreCase("Perform") && Text.removeTags(event.getMenuTarget()).equalsIgnoreCase(_emote)){
            if (_in_area)
                SetCompleted();
        }
    }
    @Override
    public void OnGameTick(Client client) {
        WorldPoint point = client.getLocalPlayer().getWorldLocation();
        boolean x_in_range = point.getX() >= _topleft_point.getX() && point.getX() <= _bottomright_point.getX();
        boolean y_in_range = point.getY() <= _topleft_point.getY() && point.getY() >= _bottomright_point.getY();
        boolean plane_in_range = point.getPlane() == _topleft_point.getPlane() && point.getPlane() == _bottomright_point.getPlane();
        _in_area = x_in_range && y_in_range && plane_in_range;
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
    public void CheckChatMessage(ChatMessage event) { }

    @Override
    public void CheckMobKill(NPC npc) { }

    @Override
    public void CheckPlayerStatus(Client client) { }

}
