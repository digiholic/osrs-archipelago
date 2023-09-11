package gg.archipelago.Tasks;

public class CraftRunesTask extends StateTrackingTask{

    private long _ID;
    private String _runeType;
    private boolean _usesCore;

    public CraftRunesTask(Long ID, String runeType, boolean usesCore){
        _ID = ID;
        _runeType = runeType;
        _usesCore = usesCore;
    }

    @Override
    public void CheckChatMessage(String message) { }
    @Override
    public void CheckMobKill(NPC npc) { }
    @Override
    public void CheckPlayerStatus(Client client) { }
    @Override
    public void OnMenuOption(MenuOptionClicked event) {
        //Check for Runecraft option
    }

    @Override
    public long GetID() {
        return _ID;
    }

    @Override
    boolean CheckInitialStateOK(Client client) {
        //Check for level, presence of essence, optional presence of core
        return false;
    }

    @Override
    boolean CheckPostTriggerStateOK(Client client) {
        //Check for any increase in RC XP and increase in item count for rune
        return false;
    }
}
