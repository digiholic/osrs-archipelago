package gg.archipelago.Tasks;

public class CastSpellTask extends StateTrackingTask{

    private long _ID;
    private String _spell;

    public CastSpellTask(Long ID, String spell){
        _ID = ID;
        _spell = spell;
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
    }

    @Override
    public long GetID() {
        return _ID;
    }

    @Override
    boolean CheckInitialStateOK(Client client) {
        // Check to make sure you have the level for the spell
        return false;
    }

    @Override
    boolean CheckPostTriggerStateOK(Client client) {
        // Check if you've gained enough XP for the spell after casting
        return false;
    }
}
