package com.archipelago;

import com.archipelago.data.ItemData;
import com.archipelago.data.ItemNames;
import net.runelite.api.SpriteID;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

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
            default: return "No Armor";
            case 1:return "Leather";
            case 2: return "Studded Leather";
            case 3: return "Green Dragonhide";
        }
    }

    public static String RangeWeaponTierByCount(int count){
        switch (count){
            default: return "Wooden Bows, Arrows up to Iron";
            case 1: return "Bows up to Oak, Arrows up to Steel";
            case 2: return "Bows up to Willow, Arrows up to Mith";
            case 3: return "Bows up to Maple, Arrows up to Adamant";
        }
    }

    public static String MagicTierByCount(int count){
        switch (count){
            default: return "Strike Spells (Mind Runes)";
            case 1: return "Bolt Spells (Chaos Runes)";
            case 2: return "Blast Spells (Death Runes)";
        }
    }
}
