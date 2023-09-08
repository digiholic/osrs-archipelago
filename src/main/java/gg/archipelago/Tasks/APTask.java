package gg.archipelago.Tasks;

import net.runelite.api.NPC;
import net.runelite.api.Client;
import net.runelite.api.events.MenuOptionClicked;

public abstract class APTask {
    abstract void CheckChatMessage(String message);
    abstract void CheckMobKill(NPC npc);
    abstract void CheckPlayerStatus(Client client);
    abstract void OnGameTick(Client client);
    abstract void OnMenuOption(MenuOptionClicked event);
    abstract boolean IsCompleted();
    abstract long GetID();
}

