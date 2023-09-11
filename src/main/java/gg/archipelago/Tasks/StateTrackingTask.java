package gg.archipelago.Tasks;

import net.runelite.api.Client;
import net.runelite.api.NPC;

public abstract class StateTrackingTask extends APTask {
    private boolean _isCompleted = false;

    //Updated on game tick. If the state is what we want to be before an action triggers
    private boolean prevStateOK = false;
    //One a triggering event (casting a spell, using an item, etc.) is fired, this is set to true, to check the next tick's state

    private boolean checkTriggered = false;

    @Override
    public void OnGameTick(Client client){
        //If the triggering event happened and our state matches our post-triggered state requirements, we've completed the task
        if (checkTriggered){
            if (CheckPostTriggerStateOK(client)){
                _isCompleted = true;
            }
            // If the triggering event happened and we did not match the post-triggered state, then back to square one.
            else {
                prevStateOK = false;
                checkTriggered = false;
            }
        }
        //If the triggering event has not happened yet, keep track of if our state is good for the first step
        else if (CheckInitialStateOK(client)){
            prevStateOK = true;
        }
    }

    abstract boolean CheckInitialStateOK(Client client);
    abstract boolean CheckPostTriggerStateOK(Client client);

    @Override
    public boolean IsCompleted() {
        return _isCompleted;
    }
}
