package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Skill;
import net.runelite.api.SpriteID;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;

public class LevelMilestoneTask extends APTask{
    private final long _ID;
    private final int _milestoneLevelRequired;
    private boolean _isCompleted = false;
    private final String _category;

    public LevelMilestoneTask(long ID, String category, int milestoneLevelRequired){
        _ID = ID;
        _category = category;
        _milestoneLevelRequired = milestoneLevelRequired;

    }
    @Override
    public void CheckPlayerStatus(Client client) {
        for (Skill skill : Skill.values()){
            if (skill == Skill.OVERALL) continue; //Depracated, skip it
            if (skill == Skill.HITPOINTS && _milestoneLevelRequired <= 10) continue; //HP doesn't count for your first level 10

            // We only need one skill to be valid, if we get it, then we can exit early.
            if (client.getRealSkillLevel(skill) >= _milestoneLevelRequired) {
                _isCompleted = true;
                return;
            }
        }
    }
    @Override
    public void OnGameTick(Client client) { }
    @Override
    public void OnMenuOption(Client client, MenuOptionClicked event) { }
    @Override
    public void CheckChatMessage(Client client, ChatMessage event) { }
    @Override
    public void CheckMobKill(Client client, NPC npc) { }
    @Override
    public boolean IsCompleted() {
        return _isCompleted;
    }

    @Override
    public int GetSpriteID() {
        return SpriteID.SKILL_TOTAL;
    }

    @Override
    public boolean ShouldDisplayPanel() {
        return true;
    }
    @Override
    public void SetCompleted() { _isCompleted = true; }

    @Override
    public String GetName() {
        return String.format("Achieve your first Level %d",_milestoneLevelRequired);
    }

    @Override
    public long GetID() {
        return _ID;
    }

    @Override
    public String GetCategory() { return _category; }

    @Override
    public boolean CanManuallyActivate() {
        return false;
    }
}
