package gg.archipelago.Tasks;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Skill;
import net.runelite.api.SpriteID;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.plugins.PluginDescriptor;

public class  BurnLogsTask extends StateTrackingTask{

    private final long _ID;

    private String _name;
    private int _required_level;
    private int _xp_gained;
    private int _previousFiremakingXP;

    private final String logs_burning_message = "The fire catches and the logs begin to burn.";
    public BurnLogsTask(Long ID, LogType logType){
        _ID = ID;

        switch(logType){
            case OAK:
                _name = "Burn an Oak Log";
                _required_level = 15;
                _xp_gained = 60;
                break;
            case WILLOW:
                _name = "Burn a Willow Log";
                _required_level = 30;
                _xp_gained = 90;
        }
    }

    public BurnLogsTask(Long ID, String name, int required_level, int xp_gained){
        _ID = ID;
        _name = name;
        _required_level = required_level;
        _xp_gained = xp_gained;
    }
    @Override
    public void CheckChatMessage(String message) {
        // Check for burning log message
        if (message.equalsIgnoreCase(logs_burning_message)){
            checkTriggered = true;
        }
    }
    @Override
    public void CheckMobKill(NPC npc) { }
    @Override
    public void CheckPlayerStatus(Client client) { }
    @Override
    public void OnMenuOption(MenuOptionClicked event) { }

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
        return SpriteID.SKILL_FIREMAKING;
    }

    @Override
    public boolean ShouldDisplayPanel() {
        return true;
    }

    @Override
    boolean CheckInitialStateOK(Client client) {
        // Check for Firemaking level requirement
        if (!(client.getRealSkillLevel(Skill.FIREMAKING) >= _required_level)) return false;
        _previousFiremakingXP = client.getSkillExperience(Skill.FIREMAKING);
        return true;
    }

    @Override
    boolean CheckPostTriggerStateOK(Client client) {
        // Check for XP increase for specific log
        return client.getSkillExperience(Skill.FIREMAKING) == _previousFiremakingXP + _xp_gained;
    }

    public enum LogType{
        OAK,
        WILLOW
    }
}
