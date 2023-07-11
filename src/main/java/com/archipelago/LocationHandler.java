package com.archipelago;

import com.archipelago.data.LocationData;
import com.archipelago.data.LocationNames;
import net.runelite.api.SpriteID;
import net.runelite.client.game.SpriteManager;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.Collectors;

public class LocationHandler {
    public static List<LocationData> AllLocations = List.of(
            new LocationData(0x070000L,LocationNames.Q_Cooks_Assistant, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x070001L,LocationNames.Q_Demon_Slayer, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x070002L,LocationNames.Q_Restless_Ghost, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x070003L,LocationNames.Q_Romeo_Juliet, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x070004L,LocationNames.Q_Sheep_Shearer, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x070005L,LocationNames.Q_Shield_of_Arrav, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x070006L,LocationNames.Q_Ernest_the_Chicken, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x070007L,LocationNames.Q_Vampyre_Slayer, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x070008L,LocationNames.Q_Imp_Catcher, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x070009L,LocationNames.Q_Prince_Ali_Rescue, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x07000AL,LocationNames.Q_Dorics_Quest, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x07000BL,LocationNames.Q_Black_Knights_Fortress, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x07000CL,LocationNames.Q_Witchs_Potion, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x07000DL,LocationNames.Q_Knights_Sword, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x07000EL,LocationNames.Q_Goblin_Diplomacy, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x07000FL,LocationNames.Q_Pirates_Treasure, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x070010L,LocationNames.Q_Rune_Mysteries, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x070011L,LocationNames.Q_Misthalin_Mystery, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x070012L,LocationNames.Q_Corsair_Curse, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x070013L,LocationNames.Q_X_Marks_the_Spot, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x070014L,LocationNames.Q_Below_Ice_Mountain, SpriteID.TAB_QUESTS, 0, true),
            new LocationData(0x070040L,LocationNames.QP_Cooks_Assistant, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x070041L,LocationNames.QP_Demon_Slayer, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x070042L,LocationNames.QP_Restless_Ghost, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x070043L,LocationNames.QP_Romeo_Juliet, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x070044L,LocationNames.QP_Sheep_Shearer, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x070045L,LocationNames.QP_Shield_of_Arrav, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x070046L,LocationNames.QP_Ernest_the_Chicken, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x070047L,LocationNames.QP_Vampyre_Slayer, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x070048L,LocationNames.QP_Imp_Catcher, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x070049L,LocationNames.QP_Prince_Ali_Rescue, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x07004AL,LocationNames.QP_Dorics_Quest, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x07004BL,LocationNames.QP_Black_Knights_Fortress, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x07004CL,LocationNames.QP_Witchs_Potion, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x07004DL,LocationNames.QP_Knights_Sword, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x07004EL,LocationNames.QP_Goblin_Diplomacy, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x07004FL,LocationNames.QP_Pirates_Treasure, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x070050L,LocationNames.QP_Rune_Mysteries, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x070051L,LocationNames.QP_Misthalin_Mystery, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x070052L,LocationNames.QP_Corsair_Curse, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x070053L,LocationNames.QP_X_Marks_the_Spot, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x070054L,LocationNames.QP_Below_Ice_Mountain, SpriteID.TAB_QUESTS, 0, false),
            new LocationData(0x070018L,LocationNames.Guppy, SpriteID.SKILL_PRAYER, 0, true),
            new LocationData(0x070019L,LocationNames.Cavefish, SpriteID.SKILL_PRAYER, 0, true),
            new LocationData(0x07001AL,LocationNames.Tetra, SpriteID.SKILL_PRAYER, 0, true),
            new LocationData(0x07001DL,LocationNames.Barronite_Deposit, SpriteID.SKILL_MINING, 0, true),
            new LocationData(0x07001EL,LocationNames.Oak_Log, SpriteID.SKILL_WOODCUTTING, 0, true),
            new LocationData(0x07001FL,LocationNames.Willow_Log, SpriteID.SKILL_WOODCUTTING, 0, true),
            new LocationData(0x070020L,LocationNames.Catch_Lobster, SpriteID.SKILL_FISHING, 0, true),
            new LocationData(0x070021L,LocationNames.Catch_Swordfish, SpriteID.SKILL_FISHING, 0, true),
            new LocationData(0x070023L,LocationNames.Mine_Silver, SpriteID.SKILL_MINING, 0, true),
            new LocationData(0x070024L,LocationNames.Mine_Coal, SpriteID.SKILL_MINING, 0, true),
            new LocationData(0x070025L,LocationNames.Mine_Gold, SpriteID.SKILL_MINING, 0, true),
            new LocationData(0x070026L,LocationNames.Smelt_Silver, SpriteID.SKILL_SMITHING, 0, true),
            new LocationData(0x070027L,LocationNames.Smelt_Steel, SpriteID.SKILL_SMITHING, 0, true),
            new LocationData(0x070028L,LocationNames.Smelt_Gold, SpriteID.SKILL_SMITHING, 0, true),
            new LocationData(0x070029L,LocationNames.Cut_Sapphire, SpriteID.SKILL_CRAFTING, 0, true),
            new LocationData(0x07002AL,LocationNames.Cut_Emerald, SpriteID.SKILL_CRAFTING, 0, true),
            new LocationData(0x07002BL,LocationNames.Cut_Ruby, SpriteID.SKILL_CRAFTING, 0, true),
            new LocationData(0x07002DL,LocationNames.Bake_Apple_Pie, SpriteID.SKILL_COOKING, 0, true),
            new LocationData(0x07002EL,LocationNames.Bake_Cake, SpriteID.SKILL_COOKING, 0, true),
            new LocationData(0x07002FL,LocationNames.Bake_Meat_Pizza, SpriteID.SKILL_COOKING, 0, true),
            new LocationData(0x070033L,LocationNames.K_Lesser_Demon, SpriteID.SKILL_ATTACK, 0, true),
            new LocationData(0x070034L,LocationNames.K_Ogress_Shaman, SpriteID.SKILL_ATTACK, 0, true),
            new LocationData(0x070035L,LocationNames.K_Obor, SpriteID.SKILL_ATTACK, 0, true),
            new LocationData(0x070036L,LocationNames.K_Bryo, SpriteID.SKILL_ATTACK, 0, true),
            new LocationData(0x070037L,LocationNames.Total_XP_5000, SpriteID.SKILL_TOTAL, 0, true),
            new LocationData(0x070038L,LocationNames.Total_XP_25000, SpriteID.SKILL_TOTAL, 0, true),
            new LocationData(0x070039L,LocationNames.Total_XP_50000, SpriteID.SKILL_TOTAL, 0, true),
            new LocationData(0x07003AL,LocationNames.Total_Level_50, SpriteID.SKILL_TOTAL, 0, true),
            new LocationData(0x07003BL,LocationNames.Total_Level_100, SpriteID.SKILL_TOTAL, 0, true),
            new LocationData(0x07003CL,LocationNames.Total_Level_200, SpriteID.SKILL_TOTAL, 0, true),
            new LocationData(0x07003DL,LocationNames.Combat_Level_10, SpriteID.SKILL_TOTAL, 0, true),
            new LocationData(0x07003EL,LocationNames.Combat_Level_25, SpriteID.SKILL_TOTAL, 0, true),
            new LocationData(0x07003FL,LocationNames.Combat_Level_50, SpriteID.SKILL_TOTAL, 0, true)
    );

    public static final Map<LocationData, BufferedImage> loadedSprites = new HashMap<LocationData, BufferedImage>();

    public static final String OAK_MESSAGE = "You get some oak logs.";
    public static final String WILLOW_MESSAGE = "You get some willow logs.";
    public static final String SAPPHIRE_MESSAGE = "You cut the sapphire.";
    public static final String EMERALD_MESSAGE = "You cut the emerald.";
    public static final String RUBY_MESSAGE = "You cut the ruby.";
    public static final String DIAMOND_MESSAGE = "You cut the diamond.";
    public static final String COAL_MINED_MESSAGE = "You manage to mine some coal.";
    public static final String SILVER_MINED_MESSAGE = "You manage to mine some silver.";
    public static final String GOLD_MINED_MESSAGE = "You manage to mine some gold.";
    public static final String STEEL_SMELTED_MESSAGE = "You retrieve a bar of steel.";
    public static final String SILVER_SMELTED_MESSAGE = "You retrieve a bar of silver from the furnace.";
    public static final String GOLD_SMELTED_MESSAGE = "You retrieve a bar of gold from the furnace.";
    public static final String APPLE_PIE_MESSAGE = "You successfully bake a traditional apple pie.";
    public static final String CAKE_MESSAGE = "You successfully bake a cake.";
    public static final String PIZZA_MESSAGE = "You add the meat to the pizza.";
    public static final String CRUSH_BARRONITE_MESSAGE = "You crush a barronite deposit with your hammer.";
    public static final String TETRA_MESSAGE = "You successfully prepare the Tetra.";
    public static final String CAVEFISH_MESSAGE = "You successfully prepare the Cavefish.";
    public static final String GUPPY_MESSAGE = "You successfully prepare the Guppy.";
    public static final String LOBSTER_MESSAGE = "You catch a lobster.";
    public static final String SWORDFISH_MESSAGE = "You catch a swordfish.";

    public static LocationData GetLocationByName(String name){
        return AllLocations.stream()
                .filter(loc -> name.equals(loc.name))
                .findFirst()
                .orElse(null);
    }

    public static Dictionary<Long, LocationData> LocationsById = new Hashtable<>(){{
        for(LocationData loc : AllLocations){
            put(loc.id, loc);
        }
    }};

    public static void LoadImages(SpriteManager spriteManager){
        for(LocationData loc : AllLocations){
            loadedSprites.put(loc, spriteManager.getSprite(loc.icon_id, loc.icon_file));
        }
    }
}
