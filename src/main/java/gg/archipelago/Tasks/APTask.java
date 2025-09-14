package gg.archipelago.Tasks;

import gg.archipelago.data.LocationData;
import gg.archipelago.data.NameOrIDDataSource;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Quest;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.gameval.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class APTask {
    public abstract String GetName();
    public abstract long GetID();
    public abstract String GetCategory();
    public abstract boolean IsCompleted();
    public abstract void SetCompleted();
    public abstract int GetSpriteID();
    public abstract boolean ShouldDisplayPanel();

    public abstract void CheckChatMessage(Client client, ChatMessage event);
    public abstract void CheckMobKill(Client client, NPC npc);
    public abstract void CheckPlayerStatus(Client client);
    public abstract void OnGameTick(Client client);
    public abstract void OnMenuOption(Client client, MenuOptionClicked event);

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
    private static final String EQUIP = "EquipItemTask";
    private static final String OBTAIN = "GetItemTask";
    private static final String WIDGET = "WidgetOpenTask";


    public static APTask CreateFromLocationCSVRow(LocationData row, long locationId){
        Quest[] allQuests = Quest.values();
        Map<String, Quest> QuestsByName = new HashMap<>();
        for (Quest q : allQuests){
            QuestsByName.put(q.getName(), q);
        }

        List<String> args = row.getPluginTaskArgs();
        switch(row.getPluginTaskType()){
            case QUEST:
                //remove the prefix to get the quest name
                String quest_name = row.getLocationName().replace("Quest: ","");
                if (QuestsByName.containsKey((quest_name))) {
                    return new QuestTask(locationId, row.getCategory(), QuestsByName.get(quest_name));
                } else {
                    return new ManualTask(locationId, quest_name, row.getCategory());
                }

            case VARBIT:
                return new VarbitTask(locationId, row.getLocationName(), row.getCategory(),
                        new NameOrIDDataSource(args.get(0)),
                        Integer.parseInt(args.get(1)),
                        Integer.parseInt(args.get(2)));
            case VARBIT_CHANGED:
                return new VarbitChangedTask(locationId, row.getLocationName(), row.getCategory(),
                        new NameOrIDDataSource(args.get(0)),
                        Integer.parseInt(args.get(1)));
            case SPELL:
                return new CastSpellTask(locationId, row.getLocationName(), row.getCategory(),
                        args.get(0));
            case CRAFT_RUNES:
                return new CraftRunesTask(locationId, row.getLocationName(), row.getCategory(),
                        Integer.parseInt(args.get(0)),
                        Integer.parseInt(args.get(1)));
            case CHAT:
                return new ChatMessageTask(locationId, row.getLocationName(), row.getCategory(),
                        new NameOrIDDataSource(args.get(0)),
                        args.get(1));
            case MENU:
                return new MenuActionTask(locationId, row.getLocationName(), row.getCategory(),
                        new NameOrIDDataSource(args.get(0)),
                        args.get(1), args.get(2));
            case BURN:
                return new BurnLogsTask(locationId, row.getLocationName(), row.getCategory(),
                        Integer.parseInt(args.get(0)),
                        Integer.parseInt(args.get(1)));
            case KILL:
                return new KillTask(locationId, row.getCategory(), args.get(0));
            case XP:
                return new TotalXPTask(locationId, row.getCategory(), Integer.parseInt(args.get(0)));
            case COMBAT:
                return new CombatLevelTask(locationId, row.getCategory(), Integer.parseInt(args.get(0)));
            case TOTAL:
                return new TotalLevelTask(locationId, row.getCategory(), Integer.parseInt(args.get(0)));
            case MILESTONE:
                return new LevelMilestoneTask(locationId, row.getCategory(), Integer.parseInt(args.get(0)));
            case STAND_POSITION:
                return new StandInPositionTask(locationId, row.getLocationName(), row.getCategory(),
                        new NameOrIDDataSource(args.get(0)), Integer.parseInt(args.get(1)),
                        Integer.parseInt(args.get(2)), Integer.parseInt(args.get(3)));
            case STAND_AREA:
                return new StandInAreaTask(locationId, row.getLocationName(), row.getCategory(), new NameOrIDDataSource(args.get(0)),
                        Integer.parseInt(args.get(1)),Integer.parseInt(args.get(2)),Integer.parseInt(args.get(3)),
                        Integer.parseInt(args.get(4)),Integer.parseInt(args.get(5)),Integer.parseInt(args.get(6)));
            case EMOTE_AREA:
                return new EmoteInAreaTask(locationId, row.getLocationName(), row.getCategory(), new NameOrIDDataSource(args.get(0)), args.get(1),
                        Integer.parseInt(args.get(2)),Integer.parseInt(args.get(3)),Integer.parseInt(args.get(4)),
                        Integer.parseInt(args.get(5)),Integer.parseInt(args.get(6)),Integer.parseInt(args.get(7)));
            case ITEM:
                return new ItemOperationTask(locationId, row.getLocationName(), row.getCategory(),
                        new NameOrIDDataSource(args.get(0)), args.get(1),
                        Integer.parseInt(args.get(2)));
            case EQUIP:
                return new EquipItemTask(locationId, row.getLocationName(), row.getCategory(),
                        new NameOrIDDataSource(args.get(0)), new NameOrIDDataSource(args.get(1)));
            case OBTAIN:
                return new GetItemTask(locationId, row.getLocationName(), row.getCategory(),
                        new NameOrIDDataSource(args.get(0)), new NameOrIDDataSource(args.get(1)));
            case WIDGET:
                return new WidgetOpenTask(locationId, row.getLocationName(), row.getCategory(),
                        new NameOrIDDataSource(args.get(0)),
                        Integer.parseInt(args.get(1)));
            default:
                // If it's nothing above, it's a unique task we have to check by name.
                switch(row.getLocationName()){
                    case "Pray at the Edgeville Monastery": return new EdgevilleMonasteryTask(locationId, row.getCategory());
                    case "Open a Simple Lockbox": return new OpenLockboxTask(locationId, row.getCategory(), ItemID.CAMDOZAAL_VAULT_CHEST_LOW);
                    case "Open an Elaborate Lockbox": return new OpenLockboxTask(locationId, row.getCategory(), ItemID.CAMDOZAAL_VAULT_CHEST_MID);
                    case "Open an Ornate Lockbox": return new OpenLockboxTask(locationId, row.getCategory(), ItemID.CAMDOZAAL_VAULT_CHEST_HIGH);
                    case "Have the Apothecary Make a Strength Potion": return new BuyPotionTask(locationId, row.getCategory());
                    case "Telegrab a Gold Bar from the Varrock Bank": return new TelegrabGoldBarTask(locationId, row.getCategory());
                    default: return new ManualTask(locationId, row.getLocationName(), row.getCategory());
                }
        }
    }


    protected static int IconByName(String name){
        switch(name){
            case "Agility":
                return SpriteID.Staticons.AGILITY;
            case "Attack":
                return SpriteID.Staticons.ATTACK;
            case "Combat":
                return SpriteID.SideIcons.COMBAT;
            case "Construction":
                return SpriteID.Staticons2.CONSTRUCTION;
            case "Cooking":
                return SpriteID.Staticons.COOKING;
            case "Crafting":
                return SpriteID.Staticons.CRAFTING;
            case "Defence":
                return SpriteID.Staticons.DEFENCE;
            case "Farming":
                return SpriteID.Staticons2.FARMING;
            case "Firemaking":
                return SpriteID.Staticons.FIREMAKING;
            case "Fishing":
                return SpriteID.Staticons.FISHING;
            case "Fletching":
                return SpriteID.Staticons.FLETCHING;
            case "Herblore":
                return SpriteID.Staticons.HERBLORE;
            case "Hitpoints":
                return SpriteID.Staticons.HITPOINTS;
            case "Hunter":
                return SpriteID.Staticons2.HUNTER;
            case "Magic":
                return SpriteID.Staticons.MAGIC;
            case "Mining":
                return SpriteID.Staticons.MINING;
            case "Prayer":
                return SpriteID.Staticons.PRAYER;
            case "quest":
                return SpriteID.SideIcons.QUEST;
            case "Ranged":
                return SpriteID.Staticons.RANGED;
            case "Runecraft":
                return SpriteID.Staticons2.RUNECRAFT;
            case "Slayer":
                return SpriteID.Staticons2.SLAYER;
            case "Smithing":
                return SpriteID.Staticons.SMITHING;
            case "Strength":
                return SpriteID.Staticons.STRENGTH;
            case "subquest":
                return SpriteID.SideIcons.ACHIEVEMENT_DIARIES;
            case "Thieving":
                return SpriteID.Staticons.THIEVING;
            case "Woodcutting":
                return SpriteID.Staticons.WOODCUTTING;
            default:
                return SpriteID.WorldmapIcon.PLANET;
        }
    }
}

