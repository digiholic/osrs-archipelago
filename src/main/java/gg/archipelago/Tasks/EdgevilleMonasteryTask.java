package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Skill;
import net.runelite.api.SpriteID;
import net.runelite.api.events.MenuOptionClicked;

public class EdgevilleMonasteryTask extends APTask{
    private final long _ID;
    private boolean _isCompleted;

    public EdgevilleMonasteryTask(long ID){
        _ID = ID;
    }
    @Override
    public void CheckPlayerStatus(Client client) {
        int prayerLevel = client.getRealSkillLevel(Skill.PRAYER);
        if (prayerLevel > 31 && client.getBoostedSkillLevel(Skill.PRAYER) == prayerLevel+2){
            _isCompleted = true;
        }
    }

    @Override
    public void OnGameTick(Client client) { }
    @Override
    public void OnMenuOption(MenuOptionClicked event) { }
    @Override
    public void CheckChatMessage(String message) { }
    @Override
    public void CheckMobKill(NPC npc) { }
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
}
