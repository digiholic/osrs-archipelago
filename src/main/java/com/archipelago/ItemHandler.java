package com.archipelago;

import com.archipelago.data.ItemData;
import com.archipelago.data.ItemNames;
import com.archipelago.data.LocationData;
import net.runelite.api.SpriteID;
import net.runelite.client.game.SpriteManager;

import java.awt.image.BufferedImage;
import java.util.*;

public class ItemHandler {
    public static List<ItemData> AllItems = List.of(
            new ItemData(0x07001DL, ItemNames.Progressive_Armor, SpriteID.MAP_ICON_HELMET_SHOP,0),
            new ItemData(0x07001EL, ItemNames.Progressive_Weapons, SpriteID.MAP_ICON_SWORD_SHOP,0),
            new ItemData(0x07001FL, ItemNames.Progressive_Tools, SpriteID.MAP_ICON_MINING_SHOP,0),
            new ItemData(0x070020L, ItemNames.Progressive_Range_Armor, SpriteID.MAP_ICON_TANNERY,0),
            new ItemData(0x070021L, ItemNames.Progressive_Range_Weapon, SpriteID.MAP_ICON_ARCHERY_SHOP,0),
            new ItemData(0x070022L, ItemNames.Progressive_Magic, SpriteID.MAP_ICON_MAGIC_SHOP,0),
            new ItemData(0x070000L, ItemNames.Lumbridge, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070001L, ItemNames.Lumbridge_Swamp, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070002L, ItemNames.Lumbridge_Farms, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070003L, ItemNames.HAM_Hideout, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070004L, ItemNames.Draynor_Village, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070005L, ItemNames.Draynor_Manor, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070006L, ItemNames.Wizards_Tower, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070007L, ItemNames.Al_Kharid, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070008L, ItemNames.Citharede_Abbey, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070009L, ItemNames.South_Of_Varrock, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x07000AL, ItemNames.Central_Varrock, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x07000BL, ItemNames.Varrock_Palace, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x07000CL, ItemNames.East_Of_Varrock, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x07000DL, ItemNames.West_Varrock, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x07000EL, ItemNames.Edgeville, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x07000FL, ItemNames.Barbarian_Village, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070010L, ItemNames.Monastery, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070011L, ItemNames.Ice_Mountain, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070012L, ItemNames.Dwarven_Mines, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070013L, ItemNames.Falador, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070014L, ItemNames.Falador_Farm, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070015L, ItemNames.Crafting_Guild, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070016L, ItemNames.Rimmington, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070017L, ItemNames.Port_Sarim, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070018L, ItemNames.Mudskipper_Point, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x070019L, ItemNames.Karamja, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x07001AL, ItemNames.Crandor, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x07001BL, ItemNames.Corsair_Cove, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0),
            new ItemData(0x07001CL, ItemNames.Wilderness, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET,0)
    );

    public static Map<String, String> RegionNamesToChunkIdString = Map.ofEntries(
            Map.entry(ItemNames.Lumbridge, "12850"),
            Map.entry(ItemNames.Lumbridge_Swamp, "12849,12593"),
            Map.entry(ItemNames.Lumbridge_Farms, "12851,12595"),
            Map.entry(ItemNames.HAM_Hideout, "12594"),
            Map.entry(ItemNames.Draynor_Village, "12338,12339"),
            Map.entry(ItemNames.Draynor_Manor, "12340"),
            Map.entry(ItemNames.Wizards_Tower, "12337"),
            Map.entry(ItemNames.Al_Kharid, "13107,13106,13362,13105,13104"),
            Map.entry(ItemNames.Citharede_Abbey, "13361,13617"),
            Map.entry(ItemNames.South_Of_Varrock, "13108,12852,12596"),
            Map.entry(ItemNames.Central_Varrock, "12853"),
            Map.entry(ItemNames.Varrock_Palace, "12854"),
            Map.entry(ItemNames.East_Of_Varrock, "13109,13110"),
            Map.entry(ItemNames.West_Varrock, "12598,12597"),
            Map.entry(ItemNames.Edgeville, "12342"),
            Map.entry(ItemNames.Barbarian_Village, "12341"),
            Map.entry(ItemNames.Monastery, "12086"),
            Map.entry(ItemNames.Ice_Mountain, "11830"),
            Map.entry(ItemNames.Dwarven_Mines, "12085,11829"),
            Map.entry(ItemNames.Falador, "12084,11828"),
            Map.entry(ItemNames.Falador_Farm, "12083,11827"),
            Map.entry(ItemNames.Crafting_Guild, "11571"),
            Map.entry(ItemNames.Rimmington, "11826,11570"),
            Map.entry(ItemNames.Port_Sarim, "12082,12081"),
            Map.entry(ItemNames.Mudskipper_Point, "11825,11824"),
            Map.entry(ItemNames.Karamja, "11569,11313"),
            Map.entry(ItemNames.Crandor, "11315,11314"),
            Map.entry(ItemNames.Corsair_Cove, "10284,10028"),
            Map.entry(ItemNames.Wilderness, "11836,11835,11834,11833,11832,11831,12092,12091,12090,12089,12088,12087,12348,12347,12346,12345,12344,12343,12604,12603,12602,12601,12600,12599,12860,12859,12858,12857,12856,12855,13116,13115,13114,13113,13112,13111,13372,13371,13370,13369,13368,13367")
    );

    public static final Map<ItemData, BufferedImage> loadedSprites = new HashMap<ItemData, BufferedImage>();

    public static ItemData GetItemByName(String name){
        return AllItems.stream()
                .filter(item -> name.equals(item.name))
                .findFirst()
                .orElse(null);
    }

    public static Dictionary<Long, ItemData> ItemsById = new Hashtable<>(){{
        for(ItemData item : AllItems){
            put(item.id, item);
        }
    }};

    public static String MetalTierByCount(int count){
        switch (count){
            default: return "Bronze";
            case 1: return "Iron";
            case 2: return "Steel";
            case 3: return "Black";
            case 4: return "Mithril";
            case 5: return "Adamant";
            case 6: return "Rune";
        }
    }

    public static String RangeArmorTierByCount(int count){
        switch (count){
            default: return "No Ranged Armor";
            case 1:return "Ranged Armor: Leather";
            case 2: return "Ranged Armor: Studded Leather";
            case 3: return "Ranged Armor: Green Dragonhide";
        }
    }

    public static String RangeWeaponTierByCount(int count){
        switch (count){
            default: return "Bows: Wooden, Arrows: Iron";
            case 1: return "Bows: Oak, Arrows: Steel";
            case 2: return "Bows: Willow, Arrows: Mith";
            case 3: return "Bows: Maple, Arrows: Adamant";
        }
    }

    public static String MagicTierByCount(int count){
        switch (count){
            default: return "Strike Spells (Mind Runes)";
            case 1: return "Bolt Spells (Chaos Runes)";
            case 2: return "Blast Spells (Death Runes)";
        }
    }

    public static void LoadImages(SpriteManager spriteManager){
        for(ItemData item : AllItems){
            loadedSprites.put(item, spriteManager.getSprite(item.icon_id, item.icon_file));
        }
    }
}
