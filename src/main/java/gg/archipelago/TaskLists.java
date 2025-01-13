package gg.archipelago;

import gg.archipelago.Tasks.*;
import gg.archipelago.Tasks.EdgevilleMonasteryTask;
import gg.archipelago.Tasks.OpenLockboxTask;
import gg.archipelago.data.LocationData;
import net.runelite.api.*;
import net.runelite.client.game.SpriteManager;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskLists {
    public static final long base_id = 0x070000;
    public static int taskCount = 0;
    private static final String repository_address = "https://raw.githubusercontent.com/digiholic/osrs-archipelago-logic/";

    private static final boolean DEBUG = true;
    private static final String debugDataSource = "C:\\Users\\digiholic\\git\\osrs-archipelago\\src\\main\\resources\\gg\\archipelago\\tasks.csv";

    private static List<APTask> allTasksCached = new ArrayList<>();
    public static List<APTask> GetAllTasks(String data_version){
        List<LocationData> locs = GetTaskCSVs(data_version);
        taskCount = 0;
        // For Legacy purposes, this is the data as it was on V1.2 of the Archipelago World. This was the last time
        // the data was hard-coded instead of being generated by the logic CSV files. If there is no version number
        // associated with the current slot, use that list.
        if (data_version == null || data_version.isEmpty() || locs == null){
            allTasksCached = defaultTasks;
        } else {
            allTasksCached = new ArrayList<>();
            for (LocationData loc : locs){
                APTask task = APTask.CreateFromLocationCSVRow(loc, base_id + taskCount++);
                if (task != null)
                    allTasksCached.add(task);
            }
        }

        return allTasksCached;
    }

    public static APTask GetTaskByID(long id){
        var filter = allTasksCached.stream().filter(apTask -> apTask.GetID() == id).findFirst();
        if (filter.isEmpty()){
            return null;
        } else {
            return filter.get();
        }
    }

    public static final Map<APTask, BufferedImage> loadedSprites = new HashMap<APTask, BufferedImage>();

    public static void LoadImages(SpriteManager spriteManager){
        for(APTask task : allTasksCached){
            //All tasks use an icon file offset of 0 so we can just assume that without needing to populate it
            loadedSprites.put(task, spriteManager.getSprite(task.GetSpriteID(), 0));
        }
    }

    public static List<LocationData> GetTaskCSVs(String dataVersion){
        if (dataVersion == null || dataVersion.isEmpty()) return null;
        String taggedRepoAddress = repository_address+dataVersion;
        List<LocationData> tasks = new ArrayList<>();
        try {
            BufferedReader in = null;
            if (DEBUG){
                FileReader fileIn = new FileReader(debugDataSource);
                in = new BufferedReader(fileIn);
            } else {
                URL repo = new URL(taggedRepoAddress+"/locations.csv");
                in = new BufferedReader(
                        new InputStreamReader(repo.openStream()));

            }
            parse_csv_file(tasks, in);
            in.close();
        }
        catch (IOException e) {
            System.err.print(e.getMessage());
            return null;
        }
        return tasks;
    }

    private static void parse_csv_file(List<LocationData> tasks, BufferedReader in) throws IOException {
        String inputLine;
        while ((inputLine = in.readLine()) != null){
            // If the line is the optional header, skip it
            if (inputLine.startsWith("Location Name")) continue;

            List<String> row = new ArrayList<>();
            // Gnarly regex to capture CSV pattern without splitting on commas in quotes
            Matcher m = Pattern.compile("(?:,|\\n|^)(\"(?:(?:\"\")*[^\"]*)*\"|[^\",\\n]*|(?:\\n|$))")
                    .matcher(inputLine);
            while (m.find()){
                String match = m.group();
                if (match.startsWith(",")) match = match.substring(1);
                match = match.replace("\"","");
                row.add(match);
            }
            tasks.add(new LocationData(row));
        }
    }


    private static final List<APTask> defaultTasks = List.of(
            new QuestTask(base_id + taskCount++, Quest.COOKS_ASSISTANT),
            new QuestTask(base_id + taskCount++, Quest.DEMON_SLAYER),
            new QuestTask(base_id + taskCount++, Quest.THE_RESTLESS_GHOST),
            new QuestTask(base_id + taskCount++, Quest.ROMEO__JULIET),
            new QuestTask(base_id + taskCount++, Quest.SHEEP_SHEARER),
            new QuestTask(base_id + taskCount++, Quest.SHIELD_OF_ARRAV),
            new QuestTask(base_id + taskCount++, Quest.ERNEST_THE_CHICKEN),
            new QuestTask(base_id + taskCount++, Quest.VAMPYRE_SLAYER),
            new QuestTask(base_id + taskCount++, Quest.IMP_CATCHER),
            new QuestTask(base_id + taskCount++, Quest.PRINCE_ALI_RESCUE),
            new QuestTask(base_id + taskCount++, Quest.DORICS_QUEST),
            new QuestTask(base_id + taskCount++, Quest.BLACK_KNIGHTS_FORTRESS),
            new QuestTask(base_id + taskCount++, Quest.WITCHS_POTION),
            new QuestTask(base_id + taskCount++, Quest.THE_KNIGHTS_SWORD),
            new QuestTask(base_id + taskCount++, Quest.GOBLIN_DIPLOMACY),
            new QuestTask(base_id + taskCount++, Quest.PIRATES_TREASURE),
            new QuestTask(base_id + taskCount++, Quest.RUNE_MYSTERIES),
            new QuestTask(base_id + taskCount++, Quest.MISTHALIN_MYSTERY),
            new QuestTask(base_id + taskCount++, Quest.THE_CORSAIR_CURSE),
            new QuestTask(base_id + taskCount++, Quest.X_MARKS_THE_SPOT),
            new QuestTask(base_id + taskCount++, Quest.BELOW_ICE_MOUNTAIN),
            new QuestTask(base_id + taskCount++, Quest.DRAGON_SLAYER_I),
            new VarbitTask(base_id + taskCount++, "Activate the Rock Skin Prayer", SpriteID.SKILL_PRAYER, Prayer.ROCK_SKIN.getVarbit(), 1),
            new VarbitTask(base_id + taskCount++,"Activate the Protect Item Prayer", SpriteID.SKILL_PRAYER, Prayer.PROTECT_ITEM.getVarbit(), 1),
            new EdgevilleMonasteryTask(base_id + taskCount++),
            new CastSpellTask(base_id + taskCount++, "Bones To Bananas"),
            new CastSpellTask(base_id + taskCount++, "Varrock Teleport"),
            new CastSpellTask(base_id + taskCount++, "Lumbridge Teleport"),
            new CastSpellTask(base_id + taskCount++, "Falador Teleport"),
            new CraftRunesTask(base_id + taskCount++, "Craft some Air Runes", ItemID.AIR_RUNE, -1),
            new CraftRunesTask(base_id + taskCount++, "Craft some runes with a Mind Core", ItemID.MIND_RUNE, ItemID.MIND_CORE),
            new CraftRunesTask(base_id + taskCount++, "Craft some runes with a Body Core", ItemID.BODY_RUNE, ItemID.BODY_CORE),
            new ChatMessageTask(base_id + taskCount++,"Craft an Unblessed Holy Symbol",SpriteID.SKILL_CRAFTING, "You put some string on your holy symbol."),
            new ChatMessageTask(base_id + taskCount++,"Cut a Sapphire",SpriteID.SKILL_CRAFTING,"You cut the sapphire."),
            new ChatMessageTask(base_id + taskCount++,"Cut an Emerald",SpriteID.SKILL_CRAFTING, "You cut the emerald."),
            new ChatMessageTask(base_id + taskCount++,"Cut a Ruby",SpriteID.SKILL_CRAFTING, "You cut the ruby."),
            new ChatMessageTask(base_id + taskCount++,"Cut a Diamond",SpriteID.SKILL_CRAFTING, "You cut the diamond."),
            new ChatMessageTask(base_id + taskCount++,"Mine a Blurite Ore",SpriteID.SKILL_MINING, "You manage to mine some blurite."),
            new ChatMessageTask(base_id + taskCount++,"Crush a Barronite Deposit",SpriteID.SKILL_MINING, "You crush a barronite deposit with your hammer."),
            new ChatMessageTask(base_id + taskCount++,"Mine some Silver Ore",SpriteID.SKILL_MINING, "You manage to mine some silver."),
            new ChatMessageTask(base_id + taskCount++,"Mine some Coal",SpriteID.SKILL_MINING, "You manage to mine some coal."),
            new ChatMessageTask(base_id + taskCount++,"Mine some Gold Ore",SpriteID.SKILL_MINING, "You manage to mine some gold."),
            new ChatMessageTask(base_id + taskCount++,"Smelt an Iron Bar",SpriteID.SKILL_SMITHING, "You retrieve a bar of iron."),
            new ChatMessageTask(base_id + taskCount++,"Smelt a Silver Bar",SpriteID.SKILL_SMITHING, "You retrieve a bar of silver from the furnace."),
            new ChatMessageTask(base_id + taskCount++,"Smelt a Steel Bar",SpriteID.SKILL_SMITHING, "You retrieve a bar of steel."),
            new ChatMessageTask(base_id + taskCount++,"Smelt a Gold Bar",SpriteID.SKILL_SMITHING, "You retrieve a bar of gold from the furnace."),
            new ChatMessageTask(base_id + taskCount++,"Catch some Anchovies",SpriteID.SKILL_FISHING, "You catch some anchovies."),
            new ChatMessageTask(base_id + taskCount++,"Catch a Trout",SpriteID.SKILL_FISHING, "You catch a trout."),
            new ChatMessageTask(base_id + taskCount++,"Prepare a Tetra",SpriteID.SKILL_FISHING, "You successfully prepare the Tetra."),
            new ChatMessageTask(base_id + taskCount++,"Catch a Lobster",SpriteID.SKILL_FISHING, "You catch a lobster."),
            new ChatMessageTask(base_id + taskCount++,"Catch a Swordfish",SpriteID.SKILL_FISHING, "You catch a swordfish."),
            new ChatMessageTask(base_id + taskCount++,"Bake a Redberry Pie",SpriteID.SKILL_COOKING, "You successfully bake a delicious redberry pie."),
            new ChatMessageTask(base_id + taskCount++,"Cook a Stew",SpriteID.SKILL_COOKING, "You cook some stew."),
            new ChatMessageTask(base_id + taskCount++,"Bake an Apple Pie",SpriteID.SKILL_COOKING, "You successfully bake a traditional apple pie."),
            new ChatMessageTask(base_id + taskCount++,"Bake a Cake",SpriteID.SKILL_COOKING, "You successfully bake a cake."),
            new ChatMessageTask(base_id + taskCount++,"Bake a Meat Pizza",SpriteID.SKILL_COOKING, "You add the meat to the pizza."),
            new BurnLogsTask(base_id + taskCount++, BurnLogsTask.LogType.OAK),
            new BurnLogsTask(base_id + taskCount++, BurnLogsTask.LogType.WILLOW),
            new ChatMessageTask(base_id + taskCount++, "Sail on a Canoe",SpriteID.SKILL_WOODCUTTING, "Your canoe sinks into the water after the hard journey."),
            new ChatMessageTask(base_id + taskCount++, "Chop some Oak Logs",SpriteID.SKILL_WOODCUTTING, "You get some oak logs."),
            new ChatMessageTask(base_id + taskCount++, "Chop some Willow Logs",SpriteID.SKILL_WOODCUTTING, "You get some willow logs."),
            new KillTask(base_id + taskCount++, "Jeff"),
            new KillTask(base_id + taskCount++, "Goblin"),
            new KillTask(base_id + taskCount++, "Monkey"),
            new KillTask(base_id + taskCount++, "Barbarian"),
            new KillTask(base_id + taskCount++, "Giant Frog"),
            new KillTask(base_id + taskCount++, "Zombie"),
            new KillTask(base_id + taskCount++, "Guard"),
            new KillTask(base_id + taskCount++, "Hill Giant"),
            new KillTask(base_id + taskCount++, "Deadly Red Spider"),
            new KillTask(base_id + taskCount++, "Moss Giant"),
            new KillTask(base_id + taskCount++, "Catablepon"),
            new KillTask(base_id + taskCount++, "Ice Giant"),
            new KillTask(base_id + taskCount++, "Lesser Demon"),
            new KillTask(base_id + taskCount++, "Ogress Shaman"),
            new KillTask(base_id + taskCount++, "Obor"),
            new KillTask(base_id + taskCount++, "Bryophyta"),
            new TotalXPTask(base_id + taskCount++, 5000),
            new CombatLevelTask(base_id + taskCount++, 5),
            new TotalXPTask(base_id + taskCount++, 10000),
            new TotalLevelTask(base_id + taskCount++, 50),
            new TotalXPTask(base_id + taskCount++, 25000),
            new TotalLevelTask(base_id + taskCount++, 100),
            new TotalXPTask(base_id + taskCount++, 50000),
            new CombatLevelTask(base_id + taskCount++, 15),
            new TotalLevelTask(base_id + taskCount++, 150),
            new TotalXPTask(base_id + taskCount++, 75000),
            new CombatLevelTask(base_id + taskCount++, 25),
            new TotalXPTask(base_id + taskCount++, 100000),
            new TotalLevelTask(base_id + taskCount++, 200),
            new TotalXPTask(base_id + taskCount++, 125000),
            new CombatLevelTask(base_id + taskCount++, 30),
            new TotalLevelTask(base_id + taskCount++, 250),
            new TotalXPTask(base_id + taskCount++, 150000),
            new TotalLevelTask(base_id + taskCount++, 300),
            new CombatLevelTask(base_id + taskCount++, 40),
            new OpenLockboxTask(base_id + taskCount++, ItemID.SIMPLE_LOCKBOX),
            new OpenLockboxTask(base_id + taskCount++, ItemID.ELABORATE_LOCKBOX),
            new OpenLockboxTask(base_id + taskCount++, ItemID.ORNATE_LOCKBOX)
    );
}
