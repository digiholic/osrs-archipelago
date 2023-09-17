package gg.archipelago;

import gg.archipelago.Tasks.*;
import gg.archipelago.data.LocationData;
import net.runelite.api.*;
import net.runelite.client.game.SpriteManager;

import java.awt.image.BufferedImage;
import java.util.*;

public class TaskLists {
    public static final long base_id = 0x070000;
    public static List<APTask> allTasks = List.of(
            new QuestTask(base_id, Quest.COOKS_ASSISTANT),
            new QuestTask(base_id + 1, Quest.DEMON_SLAYER),
            new QuestTask(base_id + 2, Quest.THE_RESTLESS_GHOST),
            new QuestTask(base_id + 3, Quest.ROMEO__JULIET),
            new QuestTask(base_id + 4, Quest.SHEEP_SHEARER),
            new QuestTask(base_id + 5, Quest.SHIELD_OF_ARRAV),
            new QuestTask(base_id + 6, Quest.ERNEST_THE_CHICKEN),
            new QuestTask(base_id + 7, Quest.VAMPYRE_SLAYER),
            new QuestTask(base_id + 8, Quest.IMP_CATCHER),
            new QuestTask(base_id + 9, Quest.PRINCE_ALI_RESCUE),
            new QuestTask(base_id + 10, Quest.DORICS_QUEST),
            new QuestTask(base_id + 11, Quest.BLACK_KNIGHTS_FORTRESS),
            new QuestTask(base_id + 12, Quest.WITCHS_POTION),
            new QuestTask(base_id + 13, Quest.THE_KNIGHTS_SWORD),
            new QuestTask(base_id + 14, Quest.GOBLIN_DIPLOMACY),
            new QuestTask(base_id + 15, Quest.PIRATES_TREASURE),
            new QuestTask(base_id + 16, Quest.RUNE_MYSTERIES),
            new QuestTask(base_id + 17, Quest.MISTHALIN_MYSTERY),
            new QuestTask(base_id + 18, Quest.THE_CORSAIR_CURSE),
            new QuestTask(base_id + 19, Quest.X_MARKS_THE_SPOT),
            new QuestTask(base_id + 20, Quest.BELOW_ICE_MOUNTAIN),
            new QuestTask(base_id + 21, Quest.DRAGON_SLAYER_I),
            new VarbitTask("Activate the Rock Skin Prayer", SpriteID.SKILL_PRAYER, base_id + 22, Prayer.ROCK_SKIN.getVarbit(), 1),
            new VarbitTask("Activate the Protect Item Prayer", SpriteID.SKILL_PRAYER, base_id + 23, Prayer.PROTECT_ITEM.getVarbit(), 1),
            new EdgevilleMonasteryTask(base_id + 24),
            new CastSpellTask(base_id + 25, CastSpellTask.SpellToCast.BONES_TO_BANANAS),
            new CastSpellTask(base_id + 26, CastSpellTask.SpellToCast.VARROCK_TELE),
            new CastSpellTask(base_id + 27, CastSpellTask.SpellToCast.LUMBRIDGE_TELE),
            new CastSpellTask(base_id + 28, CastSpellTask.SpellToCast.FALADOR_TELE),
            new CraftRunesTask(base_id + 29, CraftRunesTask.RuneType.AIR_RUNE),
            new CraftRunesTask(base_id + 30, CraftRunesTask.RuneType.MIND_RUNE),
            new CraftRunesTask(base_id + 31, CraftRunesTask.RuneType.BODY_RUNE),
            new ChatMessageTask("Craft an Unblessed Holy Symbol",SpriteID.SKILL_CRAFTING, base_id + 32, "You put some string on your holy symbol."),
            new ChatMessageTask("Cut a Sapphire",SpriteID.SKILL_CRAFTING, base_id + 33, "You cut the sapphire."),
            new ChatMessageTask("Cut an Emerald",SpriteID.SKILL_CRAFTING, base_id + 34, "You cut the emerald."),
            new ChatMessageTask("Cut a Ruby",SpriteID.SKILL_CRAFTING, base_id + 35, "You cut the ruby."),
            new ChatMessageTask("Cut a Diamond",SpriteID.SKILL_CRAFTING, base_id + 36, "You cut the diamond."),
            new ChatMessageTask("Mine a Blurite Ore",SpriteID.SKILL_MINING, base_id + 37, "You manage to mine some blurite."),
            new ChatMessageTask("Crush a Barronite Deposit",SpriteID.SKILL_MINING, base_id + 38, "You crush a barronite deposit with your hammer."),
            new ChatMessageTask("Mine some Silver Ore",SpriteID.SKILL_MINING, base_id + 39, "You manage to mine some silver."),
            new ChatMessageTask("Mine some Coal",SpriteID.SKILL_MINING, base_id + 40, "You manage to mine some coal."),
            new ChatMessageTask("Mine some Gold Ore",SpriteID.SKILL_MINING, base_id + 41, "You manage to mine some gold."),
            new ChatMessageTask("Smelt an Iron Bar",SpriteID.SKILL_SMITHING, base_id + 42, "You retrieve a bar of iron."),
            new ChatMessageTask("Smelt a Silver Bar",SpriteID.SKILL_SMITHING, base_id + 43, "You retrieve a bar of silver from the furnace."),
            new ChatMessageTask("Smelt a Steel Bar",SpriteID.SKILL_SMITHING, base_id + 44, "You retrieve a bar of steel."),
            new ChatMessageTask("Smelt a Gold BAr",SpriteID.SKILL_SMITHING, base_id + 45, "You retrieve a bar of gold from the furnace."),
            new ChatMessageTask("Catch some Anchovies",SpriteID.SKILL_FISHING, base_id + 46, "You catch some anchovies."),
            new ChatMessageTask("Catch a Trout",SpriteID.SKILL_FISHING, base_id + 47, "You catch a trout."),
            new ChatMessageTask("Prepare a Tetra",SpriteID.SKILL_FISHING, base_id + 48, "You successfully prepare the Tetra."),
            new ChatMessageTask("Catch a Lobster",SpriteID.SKILL_FISHING, base_id + 49, "You catch a lobster."),
            new ChatMessageTask("Catch a Swordfish",SpriteID.SKILL_FISHING, base_id + 50, "You catch a swordfish."),
            new ChatMessageTask("Bake a Redberry Pie",SpriteID.SKILL_COOKING, base_id + 51, "You successfully bake a delicious redberry pie."),
            new ChatMessageTask("Cook a Stew",SpriteID.SKILL_COOKING, base_id + 52, "You cook some stew."),
            new ChatMessageTask("Bake an Apple Pie",SpriteID.SKILL_COOKING, base_id + 53, "You successfully bake a traditional apple pie."),
            new ChatMessageTask("Bake a Cake",SpriteID.SKILL_COOKING, base_id + 54, "You successfully bake a cake."),
            new ChatMessageTask("Bake a Pizza",SpriteID.SKILL_COOKING, base_id + 55, "You add the meat to the pizza."),
            new BurnLogsTask(base_id + 56, BurnLogsTask.LogType.OAK),
            new BurnLogsTask(base_id + 57, BurnLogsTask.LogType.WILLOW),
            new ChatMessageTask("Sail on a Canoe",SpriteID.SKILL_WOODCUTTING, base_id + 58, "Your canoe sinks into the water after the hard journey."),
            new ChatMessageTask("Chop some Oak Logs",SpriteID.SKILL_WOODCUTTING, base_id + 59, "You get some oak logs."),
            new ChatMessageTask("Chop some Willow Logs",SpriteID.SKILL_WOODCUTTING, base_id + 60, "You get some willow logs."),
            new KillTask(base_id + 61, "Jeff"),
            new KillTask(base_id + 62, "Goblin"),
            new KillTask(base_id + 63, "Monkey"),
            new KillTask(base_id + 64, "Barbarian"),
            new KillTask(base_id + 65, "Giant Frog"),
            new KillTask(base_id + 66, "Zombie"),
            new KillTask(base_id + 67, "Guard"),
            new KillTask(base_id + 68, "Hill Giant"),
            new KillTask(base_id + 69, "Deadly Red Spider"),
            new KillTask(base_id + 70, "Moss Giant"),
            new KillTask(base_id + 71, "Catablepon"),
            new KillTask(base_id + 72, "Ice Giant"),
            new KillTask(base_id + 73, "Lesser Demon"),
            new KillTask(base_id + 74, "Ogress Shaman"),
            new KillTask(base_id + 75, "Obor"),
            new KillTask(base_id + 76, "Bryophyta"),
            new TotalXPTask(base_id + 77, 5000),
            new CombatLevelTask(base_id + 78, 5),
            new TotalXPTask(base_id + 79, 10000),
            new TotalLevelTask(base_id + 80, 50),
            new TotalXPTask(base_id + 81, 25000),
            new TotalLevelTask(base_id + 82, 100),
            new TotalXPTask(base_id + 83, 50000),
            new CombatLevelTask(base_id + 84, 15),
            new TotalLevelTask(base_id + 85, 150),
            new TotalXPTask(base_id + 86, 75000),
            new CombatLevelTask(base_id + 87, 25),
            new TotalXPTask(base_id + 88, 100000),
            new TotalLevelTask(base_id + 89, 200),
            new TotalXPTask(base_id + 90, 125000),
            new CombatLevelTask(base_id + 91, 30),
            new TotalLevelTask(base_id + 92, 250),
            new TotalXPTask(base_id + 93, 150000),
            new TotalLevelTask(base_id + 94, 300),
            new CombatLevelTask(base_id + 95, 40),
            new VarbitTask("Stronghold of Security Floor 1", SpriteID.TAB_QUESTS_BROWN_CHARACTER_SUMMARY, base_id + 96, Varbits.LEAGUE_RELIC_1, 1),
            new VarbitTask("Stronghold of Security Floor 1", SpriteID.TAB_QUESTS_BROWN_CHARACTER_SUMMARY, base_id + 97, Varbits.LEAGUE_RELIC_1, 1),
            new OpenLockboxTask(base_id + 98, ItemID.SIMPLE_LOCKBOX),
            new OpenLockboxTask(base_id + 99, ItemID.ELABORATE_LOCKBOX),
            new OpenLockboxTask(base_id + 100, ItemID.ORNATE_LOCKBOX)
    );

    public static APTask GetTaskByID(long id){
        return allTasks.stream().filter(apTask -> apTask.GetID() == id).findFirst().get();
    }

    public static final Map<APTask, BufferedImage> loadedSprites = new HashMap<APTask, BufferedImage>();

    public static void LoadImages(SpriteManager spriteManager){
        for(APTask task : allTasks){
            //All tasks use an icon file offset of 0 so we can just assume that without needing to populate it
            loadedSprites.put(task, spriteManager.getSprite(task.GetSpriteID(), 0));
        }
    }
}
