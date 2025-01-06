package gg.archipelago.Tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.util.Text;

@Slf4j
public class CastSpellTask extends StateTrackingTask{

    private final long _ID;

    private String _name;
    private String _spell_name;

    private int _previousMagicXP;
    private boolean shouldCancel = false;

    public CastSpellTask(Long ID, String spell){
        _ID = ID;
        _name = "Cast "+spell;
        _spell_name = spell;
    }

    public CastSpellTask(Long ID, String name, String spell){
        _ID = ID;
        _name = name;
        _spell_name = spell;
    }

    @Override
    public void CheckChatMessage(String message) { }
    @Override
    public void CheckMobKill(NPC npc) { }
    @Override
    public void CheckPlayerStatus(Client client) { }
    @Override
    public void OnMenuOption(MenuOptionClicked event) {
        if (checkTriggered){
            MenuAction action = event.getMenuAction();
            // If we're pending a check and we do any menu options that aren't "Targetting a spell", un-set the trigger next tick
            if (action != MenuAction.WIDGET_TARGET &&
                action != MenuAction.WIDGET_TARGET_ON_GAME_OBJECT &&
                action != MenuAction.WIDGET_TARGET_ON_NPC &&
                action != MenuAction.WIDGET_TARGET_ON_GROUND_ITEM &&
                action != MenuAction.WIDGET_TARGET_ON_PLAYER &&
                action != MenuAction.WIDGET_TARGET_ON_WIDGET) {
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
    public String GetName() {
        return _name;
    }

    @Override
    public long GetID() {
        return _ID;
    }

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
        return true;
    }

    @Override
    boolean CheckPostTriggerStateOK(Client client) {
        //If you've gained magic XP and nothing has broken the condition yet, you've done it.
        return client.getSkillExperience(Skill.MAGIC) > _previousMagicXP;
    }
}
