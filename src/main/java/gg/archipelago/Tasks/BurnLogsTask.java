package gg.archipelago.Tasks;

public class BurnLogsTask extends StateTrackingTask{

    private long _ID;
    private String _logType;

    public BurnLogsTask(Long ID, String logType){
        _ID = ID;
        _logType = logType;
    }

    @Override
    public void CheckChatMessage(String message) {
        // Check for burning log message
    }
    @Override
    public void CheckMobKill(NPC npc) { }
    @Override
    public void CheckPlayerStatus(Client client) { }
    @Override
    public void OnMenuOption(MenuOptionClicked event) { }

    @Override
    public long GetID() {
        return _ID;
    }

    @Override
    boolean CheckInitialStateOK(Client client) {
        // Check for Firemaking level requirement
        return false;
    }

    @Override
    boolean CheckPostTriggerStateOK(Client client) {
        // Check for XP increase for specific log
        return false;
    }
}
