package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Skill;
import net.runelite.api.SpriteID;
import net.runelite.api.events.MenuOptionClicked;

public class CastSpellTask extends StateTrackingTask{

    private final long _ID;

    private int _required_level;
    private int _xp_gained;
    private int _previousMagicXP;
    private SpellToCast _spell;

    public CastSpellTask(Long ID, SpellToCast spell){
        _ID = ID;
        _spell = spell;

        switch(spell){
            case BONES_TO_BANANAS:
                _required_level = 15;
                _xp_gained = 25;
                break;
            case VARROCK_TELE:
                _required_level = 25;
                _xp_gained = 35;
                break;
            case LUMBRIDGE_TELE:
                _required_level = 31;
                _xp_gained = 41;
                break;
            case FALADOR_TELE:
                _required_level = 37;
                _xp_gained = 48;
                break;
        }
    }

    @Override
    public void CheckChatMessage(String message) { }
    @Override
    public void CheckMobKill(NPC npc) { }
    @Override
    public void CheckPlayerStatus(Client client) { }
    @Override
    public void OnMenuOption(MenuOptionClicked event) {
        // Check for cast action
        if (event.getMenuOption().equalsIgnoreCase("Cast")){
            checkTriggered = true;
        }
    }

    @Override
    public String GetName() {
        switch(_spell){
            case BONES_TO_BANANAS:
                return "Cast Bones to Bananas";
            case VARROCK_TELE:
                return "Teleport to Varrock";
            case LUMBRIDGE_TELE:
                    return "Teleport to Lumbridge";
            case FALADOR_TELE:
                return "Teleport to Falador";
            default:
                return "Cast an unknown spell";
        }
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
    boolean CheckInitialStateOK(Client client) {
        // Check to make sure you have the level for the spell
        if (!(client.getRealSkillLevel(Skill.MAGIC) >= _required_level)) return false;
        _previousMagicXP = client.getSkillExperience(Skill.MAGIC);
        return true;
    }

    @Override
    boolean CheckPostTriggerStateOK(Client client) {
        // Check if you've gained enough XP for the spell after casting
        return client.getSkillExperience(Skill.MAGIC) == _previousMagicXP + _xp_gained;
    }

    public enum SpellToCast{
        BONES_TO_BANANAS,
        VARROCK_TELE,
        LUMBRIDGE_TELE,
        FALADOR_TELE
    }
}
