package gg.archipelago.Tasks;

import gg.archipelago.data.LocationData;
import net.runelite.api.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;

import java.util.List;
import java.util.Map;

public abstract class APTask {
    public abstract String GetName();
    public abstract long GetID();
    public abstract boolean IsCompleted();
    public abstract void SetCompleted();
    public abstract int GetSpriteID();
    public abstract boolean ShouldDisplayPanel();

    public abstract void CheckChatMessage(ChatMessage event);
    public abstract void CheckMobKill(NPC npc);
    public abstract void CheckPlayerStatus(Client client);
    public abstract void OnGameTick(Client client);
    public abstract void OnMenuOption(MenuOptionClicked event);

    public abstract boolean CanManuallyActivate();


    // Task Parser Constructor
    private static final String QUEST = "QuestTask";
    private static final String VARBIT = "VarbitTask";
    private static final String VARBIT_CHANGED = "VarbitChangedTask";
    private static final String SPELL = "SpellTask";
    private static final String CRAFT_RUNES = "CraftRunesTask";
    private static final String CHAT = "ChatMessageTask";
    private static final String MENU = "MenuActionTask";
    private static final String BURN = "BurnLogsTask";
    private static final String KILL = "KillTask";
    private static final String XP = "TotalXPTask";
    private static final String COMBAT = "CombatLevelTask";
    private static final String TOTAL = "TotalLevelTask";
    private static final String MILESTONE = "LevelMilestoneTask";
    private static final String STAND_POSITION = "StandInPositionTask";
    private static final String STAND_AREA = "StandInAreaTask";
    private static final String EMOTE_AREA = "EmoteInAreaTask";
    private static final String ITEM = "ItemOperationTask";


    public static APTask CreateFromLocationCSVRow(LocationData row, long locationId){
        List<String> args = row.getPluginTaskArgs();
        switch(row.getPluginTaskType()){
            case QUEST:
                //remove the prefix to get the quest name
                String quest_name = row.getLocationName().replace("Quest: ","");
                return new QuestTask(locationId, QuestsByName.get(quest_name));
            case VARBIT:
                return new VarbitTask(locationId, row.getLocationName(),
                        Integer.parseInt(args.get(0)),
                        Integer.parseInt(args.get(1)),
                        Integer.parseInt(args.get(2)));
            case VARBIT_CHANGED:
                return new VarbitChangedTask(locationId, row.getLocationName(),
                        Integer.parseInt(args.get(0)),
                        Integer.parseInt(args.get(1)));
            case SPELL:
                return new CastSpellTask(locationId, row.getLocationName(),
                        args.get(0));
            case CRAFT_RUNES:
                return new CraftRunesTask(locationId, row.getLocationName(),
                        Integer.parseInt(args.get(0)),
                        Integer.parseInt(args.get(1)));
            case CHAT:
                return new ChatMessageTask(locationId, row.getLocationName(),
                        Integer.parseInt(args.get(0)),
                        args.get(1));
            case MENU:
                return new MenuActionTask(locationId, row.getLocationName(),
                        Integer.parseInt(args.get(0)),
                        args.get(1), args.get(2));
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
            case MILESTONE:
                return new LevelMilestoneTask(locationId, Integer.parseInt(args.get(0)));
            case STAND_POSITION:
                return new StandInPositionTask(locationId, row.getLocationName(),
                        Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)),
                        Integer.parseInt(args.get(2)), Integer.parseInt(args.get(3)));
            case STAND_AREA:
                return new StandInAreaTask(locationId, row.getLocationName(), Integer.parseInt(args.get(0)),
                        Integer.parseInt(args.get(1)),Integer.parseInt(args.get(2)),Integer.parseInt(args.get(3)),
                        Integer.parseInt(args.get(4)),Integer.parseInt(args.get(5)),Integer.parseInt(args.get(6)));
            case EMOTE_AREA:
                return new EmoteInAreaTask(locationId, row.getLocationName(), Integer.parseInt(args.get(0)), args.get(1),
                        Integer.parseInt(args.get(2)),Integer.parseInt(args.get(3)),Integer.parseInt(args.get(4)),
                        Integer.parseInt(args.get(5)),Integer.parseInt(args.get(6)),Integer.parseInt(args.get(7)));
            case ITEM:
                return new ItemOperationTask(locationId, row.getLocationName(),
                        Integer.parseInt(args.get(0)), args.get(1),
                        Integer.parseInt(args.get(2)));
            default:
                // If it's nothing above, it's a unique task we have to check by name.
                switch(row.getLocationName()){
                    case "Pray at the Edgeville Monastery": return new EdgevilleMonasteryTask(locationId);
                    case "Open a Simple Lockbox": return new OpenLockboxTask(locationId, ItemID.SIMPLE_LOCKBOX);
                    case "Open an Elaborate Lockbox": return new OpenLockboxTask(locationId, ItemID.ELABORATE_LOCKBOX);
                    case "Open an Ornate Lockbox": return new OpenLockboxTask(locationId, ItemID.ORNATE_LOCKBOX);
                    case "Have the Apothecary Make a Strength Potion": return new BuyPotionTask(locationId);
                    case "Telegrab a Gold Bar from the Varrock Bank": return new TelegrabGoldBarTask(locationId);
                    default: return new ManualTask(locationId, row.getLocationName());
                }
        }

    }

    // The Enum just doesn't exist at runtime. We have to make this map
    private static Map<String, Quest> QuestsByName = Map.ofEntries(
        Map.entry(Quest.COOKS_ASSISTANT.getName(), Quest.COOKS_ASSISTANT),
        Map.entry(Quest.DEMON_SLAYER.getName(), Quest.DEMON_SLAYER),
        Map.entry(Quest.THE_RESTLESS_GHOST.getName(), Quest.THE_RESTLESS_GHOST),
        Map.entry(Quest.ROMEO__JULIET.getName(), Quest.ROMEO__JULIET),
        Map.entry(Quest.SHEEP_SHEARER.getName(), Quest.SHEEP_SHEARER),
        Map.entry(Quest.SHIELD_OF_ARRAV.getName(), Quest.SHIELD_OF_ARRAV),
        Map.entry(Quest.ERNEST_THE_CHICKEN.getName(), Quest.ERNEST_THE_CHICKEN),
        Map.entry(Quest.VAMPYRE_SLAYER.getName(), Quest.VAMPYRE_SLAYER),
        Map.entry(Quest.IMP_CATCHER.getName(), Quest.IMP_CATCHER),
        Map.entry(Quest.PRINCE_ALI_RESCUE.getName(), Quest.PRINCE_ALI_RESCUE),
        Map.entry(Quest.DORICS_QUEST.getName(), Quest.DORICS_QUEST),
        Map.entry(Quest.BLACK_KNIGHTS_FORTRESS.getName(), Quest.BLACK_KNIGHTS_FORTRESS),
        Map.entry(Quest.WITCHS_POTION.getName(), Quest.WITCHS_POTION),
        Map.entry(Quest.THE_KNIGHTS_SWORD.getName(), Quest.THE_KNIGHTS_SWORD),
        Map.entry(Quest.GOBLIN_DIPLOMACY.getName(), Quest.GOBLIN_DIPLOMACY),
        Map.entry(Quest.PIRATES_TREASURE.getName(), Quest.PIRATES_TREASURE),
        Map.entry(Quest.RUNE_MYSTERIES.getName(), Quest.RUNE_MYSTERIES),
        Map.entry(Quest.MISTHALIN_MYSTERY.getName(), Quest.MISTHALIN_MYSTERY),
        Map.entry(Quest.THE_CORSAIR_CURSE.getName(), Quest.THE_CORSAIR_CURSE),
        Map.entry(Quest.X_MARKS_THE_SPOT.getName(), Quest.X_MARKS_THE_SPOT),
        Map.entry(Quest.BELOW_ICE_MOUNTAIN.getName(), Quest.BELOW_ICE_MOUNTAIN),
        Map.entry(Quest.DRAGON_SLAYER_I.getName(), Quest.DRAGON_SLAYER_I)
    );
}

