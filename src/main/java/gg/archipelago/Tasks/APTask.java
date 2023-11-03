package gg.archipelago.Tasks;

import net.runelite.api.NPC;
import net.runelite.api.Client;
import net.runelite.api.SpriteID;
import net.runelite.api.events.MenuOptionClicked;

public abstract class APTask {
    public abstract String GetName();
    public abstract long GetID();
    public abstract boolean IsCompleted();
    public abstract void SetCompleted();
    public abstract int GetSpriteID();
    public abstract boolean ShouldDisplayPanel();

    public abstract void CheckChatMessage(String message);
    public abstract void CheckMobKill(NPC npc);
    public abstract void CheckPlayerStatus(Client client);
    public abstract void OnGameTick(Client client);
    public abstract void OnMenuOption(MenuOptionClicked event);

    public abstract boolean CanManuallyActivate();
}

