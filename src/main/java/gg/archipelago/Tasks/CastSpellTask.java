package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Skill;
import net.runelite.api.SpriteID;
import net.runelite.api.events.MenuOptionClicked;

public class CastSpellTask extends StateTrackingTask{

    private final long _ID;

    private String _name;
    private int _required_level;
    private int _xp_gained;
    private int _previousMagicXP;

    public CastSpellTask(Long ID, SpellToCast spell){
        _ID = ID;

        switch(spell){
            case BONES_TO_BANANAS:
                _name = "Cast Bones to Bananas";
                _required_level = 15;
                _xp_gained = 25;
                break;
            case VARROCK_TELE:
                _name = "Teleport to Varrock";
                _required_level = 25;
                _xp_gained = 35;
                break;
            case LUMBRIDGE_TELE:
                _name = "Teleport to Lumbridge";
                _required_level = 31;
                _xp_gained = 41;
                break;
            case FALADOR_TELE:
                _name = "Teleport to Falador";
                _required_level = 37;
                _xp_gained = 48;
                break;
        }
    }

    public CastSpellTask(Long ID, String name, int required_level, int xp_gained){
        _ID = ID;
        _name = name;
        _required_level = required_level;
        _xp_gained = xp_gained;
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
