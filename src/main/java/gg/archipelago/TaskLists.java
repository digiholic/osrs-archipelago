package gg.archipelago;

import gg.archipelago.Tasks.*;
import net.runelite.api.Prayer;
import net.runelite.api.Quest;

import java.util.ArrayList;
import java.util.List;

public class TaskLists {
    public static final int base_id = 0x070000;
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
            new VarbitTask(base_id + 22, Prayer.ROCK_SKIN.getVarbit(), 1),
            new VarbitTask(base_id + 23, Prayer.PROTECT_ITEM.getVarbit(), 1),
//            Pray at the Edgeville Monastery
//            Cast Bones To Bananas
//            Teleport to Varrock
//            Teleport to Lumbridge
//            Teleport to Falador
//            Craft an Air Rune
//            Craft runes with a Mind Core
//            Craft runes with a Body Core
//            Make an Unblessed Symbol
            new ChatMessageTask(base_id + 33, "You cut the sapphire."),
            new ChatMessageTask(base_id + 34, "You cut the emerald."),
            new ChatMessageTask(base_id + 35, "You cut the ruby."),
            new ChatMessageTask(base_id + 36, "You cut the diamond."),
            new ChatMessageTask(base_id + 37, "You manage to mine some blurite."),
            new ChatMessageTask(base_id + 38, "You crush a barronite deposit with your hammer."),
            new ChatMessageTask(base_id + 39, "You manage to mine some silver."),
            new ChatMessageTask(base_id + 40, "You manage to mine some coal."),
            new ChatMessageTask(base_id + 41, "You manage to mine some gold."),
            new ChatMessageTask(base_id + 42, "You retrieve a bar of iron."),
            new ChatMessageTask(base_id + 43, "You retrieve a bar of silver from the furnace."),
            new ChatMessageTask(base_id + 44, "You retrieve a bar of steel."),
            new ChatMessageTask(base_id + 45, "You manage to mine some gold."),
            new ChatMessageTask(base_id + 46, "You catch some anchovies."),
            new ChatMessageTask(base_id + 47, "You catch a trout."),
            new ChatMessageTask(base_id + 48, "You successfully prepare the Tetra."),
            new ChatMessageTask(base_id + 49, "You catch a lobster."),
            new ChatMessageTask(base_id + 50, "You catch a swordfish."),
//            Bake a Redberry Pie
//            Cook some Stew
            new ChatMessageTask(base_id + 53, "You successfully bake a traditional apple pie."),
            new ChatMessageTask(base_id + 54, "You successfully bake a cake."),
            new ChatMessageTask(base_id + 55, "You add the meat to the pizza."),
//            Burn some Oak Logs
//            Burn some Willow Logs
            new ChatMessageTask(base_id + 58, "Your canoe sinks into the water after the hard journey."),
            new KillTask(base_id + 59, "You get some oak logs."),
            new KillTask(base_id + 60, "You get some willow logs."),
            new KillTask(base_id + 61, "Jeff"),
            new KillTask(base_id + 62, "Goblin"),
            new KillTask(base_id + 63, "Monkey"),
            new KillTask(base_id + 64, "Barbarian"),
//            1 Unique Skull Sceptre Piece
            new KillTask(base_id + 66, "Giant Frog"),
            new KillTask(base_id + 67, "Zombie"),
            new KillTask(base_id + 68, "Guard"),
            new KillTask(base_id + 69, "Hill Giant"),
//            2 Unique Skull Sceptre Pieces
            new KillTask(base_id + 71, "Deadly Red Spider"),
            new KillTask(base_id + 72, "Moss Giant"),
            new KillTask(base_id + 73, "Catablepon"),
//            3 Unique Skull Sceptre Pieces
            new KillTask(base_id + 75, "Ice Giant"),
//            Obtain a Skull Sceptre
            new KillTask(base_id + 77, "Lesser Demon"),
            new KillTask(base_id + 78, "Ogress Shaman"),
            new KillTask(base_id + 79, "Obor"),
            new KillTask(base_id + 80, "Bryophyta"),
            new TotalXPTask(base_id + 81, 5000),
            new CombatLevelTask(base_id + 82, 5),
            new TotalXPTask(base_id + 83, 10000),
            new TotalLevelTask(base_id + 84, 50),
            new TotalXPTask(base_id + 85, 25000),
            new TotalLevelTask(base_id + 86, 100),
            new TotalXPTask(base_id + 87, 50000),
            new CombatLevelTask(base_id + 88, 15),
            new TotalLevelTask(base_id + 89, 150),
            new TotalXPTask(base_id + 90, 75000),
            new CombatLevelTask(base_id + 91, 25),
            new TotalXPTask(base_id + 92, 100000),
            new TotalLevelTask(base_id + 93, 200),
            new TotalXPTask(base_id + 94, 125000),
            new CombatLevelTask(base_id + 95, 30),
            new TotalLevelTask(base_id + 96, 250),
            new TotalXPTask(base_id + 97, 150000),
            new TotalLevelTask(base_id + 98, 300),
            new CombatLevelTask(base_id + 99, 40)
//            Stronghold of Security Floor 1
//            Stronghold of Security Floor 2
//            Open a Simple Lockbox
//            Open an Elaborate Lockbox
//            Open an Ornate Lockbox

    );
}
