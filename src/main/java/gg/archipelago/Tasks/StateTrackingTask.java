package gg.archipelago.Tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
        name = "Archipelago Randomizer"
)
public abstract class StateTrackingTask extends APTask {
    private boolean _isCompleted = false;

    //Updated on game tick. If the state is what we want to be before an action triggers
    private boolean prevStateOK = false;
    //One a triggering event (casting a spell, using an item, etc.) is fired, this is set to true, to check the next tick's state

    protected boolean checkTriggered = false;

    @Override
    public void OnGameTick(Client client){
        //If our previous state isn't set, don't bother checking the trigger or the post-state
        if (!prevStateOK){
            checkTriggered = false;
        }
        //If the triggering event happened and our state matches our post-triggered state requirements, we've completed the task
        if (checkTriggered){
            if (CheckPostTriggerStateOK(client)) {
                _isCompleted = true;
            }
            // If the triggering event happened and we did not match the post-triggered state, then back to square one.
            else if (CheckResetCondition(client)) {
                prevStateOK = false;
                checkTriggered = false;
            }
        }
        //If the triggering event has not happened yet, keep track of if our state is good for the first step
        else if (CheckInitialStateOK(client)){
            prevStateOK = true;
        }
    }
    abstract boolean CheckResetCondition(Client client);
    abstract boolean CheckInitialStateOK(Client client);
    abstract boolean CheckPostTriggerStateOK(Client client);

    @Override
    public boolean IsCompleted() { return _isCompleted; }
    @Override
    public void SetCompleted() { _isCompleted = true; }
    @Override
    public boolean CanManuallyActivate() {
        return true;
    }
}
