package gg.archipelago.Tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.util.Text;

@Slf4j
public class TelegrabGoldBarTask extends StateTrackingTask{

    private final long _ID;
    private final String _category;

    private final String _name = "Telegrab a Gold Bar from the Varrock Bank";
    private final String _spell_name = "Telekinetic Grab";

    private int _previousMagicXP;
    private boolean shouldCancel = false;
    private final WorldPoint _topleft_point;
    private final WorldPoint _bottomright_point;
    private boolean _in_area;
    public TelegrabGoldBarTask(Long ID, String category){
        _ID = ID;
        _category = category;
        _topleft_point = new WorldPoint(3187, 9834, 0);
        _bottomright_point = new WorldPoint(3196, 9818, 0);
    }

    @Override
    public void CheckChatMessage(Client client, ChatMessage event) { }
    @Override
    public void CheckMobKill(Client client, NPC npc) { }
    @Override
    public void CheckPlayerStatus(Client client) { }
    @Override
    public void OnMenuOption(Client client, MenuOptionClicked event) {
        if (checkTriggered){
            MenuAction action = event.getMenuAction();
            // If we're pending a check and we do any menu options that aren't "Targetting a spell", un-set the trigger next tick
            if (action != MenuAction.WIDGET_TARGET_ON_GROUND_ITEM || !event.getMenuTarget().contains("Gold bar")) {
                checkTriggered = false;
                shouldCancel = true;
            }
        }
        // Spells always have a widget set
        if (event.getWidget() != null){
            String widgetName = Text.removeTags(event.getWidget().getName());
            if (widgetName.equalsIgnoreCase(_spell_name)){
                checkTriggered = true;
                shouldCancel = false;
            }
        }
    }
    @Override
    public void OnGameTick(Client client) {
        super.OnGameTick(client);

        WorldPoint point = client.getLocalPlayer().getWorldLocation();
        boolean x_in_range = point.getX() >= _topleft_point.getX() && point.getX() <= _bottomright_point.getX();
        boolean y_in_range = point.getY() <= _topleft_point.getY() && point.getY() >= _bottomright_point.getY();
        boolean plane_in_range = point.getPlane() == _topleft_point.getPlane() && point.getPlane() == _bottomright_point.getPlane();
        _in_area = x_in_range && y_in_range && plane_in_range;
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
    public int GetSpriteID() {
        return SpriteID.SKILL_MAGIC;
    }

    @Override
    public boolean ShouldDisplayPanel() {
        return true;
    }

    @Override
    boolean CheckResetCondition(Client client) {
        return shouldCancel;
    }

    @Override
    boolean CheckInitialStateOK(Client client) {
        _previousMagicXP = client.getSkillExperience(Skill.MAGIC);
        return _in_area;
    }

    @Override
    boolean CheckPostTriggerStateOK(Client client) {
        //If you've gained magic XP and nothing has broken the condition yet, you've done it.
        return _in_area && client.getSkillExperience(Skill.MAGIC) > _previousMagicXP;
    }
}
