package gg.archipelago.Tasks;

import net.runelite.api.NPC;
import net.runelite.api.Client;
import net.runelite.api.events.MenuOptionClicked;

public abstract class APTask {
    public abstract void CheckChatMessage(String message);
    public abstract void CheckMobKill(NPC npc);
    public abstract void CheckPlayerStatus(Client client);
    public abstract void OnGameTick(Client client);
    public abstract void OnMenuOption(MenuOptionClicked event);
    public abstract boolean IsCompleted();
    public abstract long GetID();
}

