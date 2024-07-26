package gg.archipelago.Tasks;

import gg.archipelago.data.LocationData;
import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;

import java.util.List;

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


    // Task Parser Constructor
    private static final String QUEST = "QuestTask";
    private static final String VARBIT = "VarbitTask";
    private static final String SPELL = "SpellTask";
    private static final String CRAFT_RUNES = "CraftRuneTask";
    private static final String CHAT = "ChatMessageTask";
    private static final String BURN = "BurnLogsTask";
    private static final String KILL = "KillTask";
    private static final String XP = "TotalXPTask";
    private static final String COMBAT = "CombatLevelTask";
    private static final String TOTAL = "TotalLevelTask";
    public static APTask CreateFromLocationCSVRow(LocationData row, long locationId){
        List<String> args = row.getPluginTaskArgs();
        switch(row.getPluginTaskType()){
            case QUEST:
                //remove the prefix to get the quest name
                String quest_name = row.getLocationName().replace("Quest: ","");
                return new QuestTask(locationId, Quest.valueOf(quest_name));
            case VARBIT:
                return new VarbitTask(locationId, row.getLocationName(),
                        Integer.parseInt(args.get(0)),
                        Integer.parseInt(args.get(1)),
                        Integer.parseInt(args.get(2)));
            case SPELL:
                return new CastSpellTask(locationId, row.getLocationName(),
                        Integer.parseInt(args.get(0)),
                        Integer.parseInt(args.get(1)));
            case CRAFT_RUNES:
                return new CraftRunesTask(locationId, row.getLocationName(),
                        Integer.parseInt(args.get(0)),
                        Integer.parseInt(args.get(1)));
            case CHAT:
                return new ChatMessageTask(locationId, row.getLocationName(),
                        Integer.parseInt(args.get(0)),
                        args.get(1));
            case BURN:
                return new BurnLogsTask(locationId, row.getLocationName(),
                        Integer.parseInt(args.get(0)),
                        Integer.parseInt(args.get(1)));
            case KILL:
                return new KillTask(locationId, args.get(0));
            case XP:
                return new TotalXPTask(locationId, Integer.parseInt(args.get(0)));
            case COMBAT:
                return new CombatLevelTask(locationId, Integer.parseInt(args.get(0)));
            case TOTAL:
                return new TotalLevelTask(locationId, Integer.parseInt(args.get(0)));
            default:
                // If it's nothing above, it's a unique task we have to check by name.
                switch(row.getLocationName()){
                    case "Pray at the Edgeville Monastery": return new EdgevilleMonasteryTask(locationId);
                    case "Open a Simple Lockbox": return new OpenLockboxTask(locationId, ItemID.SIMPLE_LOCKBOX);
                    case "Open an Elaborate Lockbox": return new OpenLockboxTask(locationId, ItemID.ELABORATE_LOCKBOX);
                    case "Open an Ornate Lockbox": return new OpenLockboxTask(locationId, ItemID.ORNATE_LOCKBOX);
                }
        }
        return null;
    }
}

