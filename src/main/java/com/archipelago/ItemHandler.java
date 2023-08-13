package com.archipelago;

import com.archipelago.data.ItemData;
import com.archipelago.data.ItemNames;
import com.archipelago.data.LocationData;
import net.runelite.api.Item;
import net.runelite.api.ItemID;
import net.runelite.api.SpriteID;
import net.runelite.client.game.SpriteManager;

import java.awt.image.BufferedImage;
import java.util.*;

public class ItemHandler {
    public static List<ItemData> AllItems = List.of(
            new ItemData(0x07001DL, ItemNames.Progressive_Armor, SpriteID.MAP_ICON_HELMET_SHOP, 0),
            new ItemData(0x07001EL, ItemNames.Progressive_Weapons, SpriteID.MAP_ICON_SWORD_SHOP, 0),
            new ItemData(0x07001FL, ItemNames.Progressive_Tools, SpriteID.MAP_ICON_MINING_SHOP, 0),
            new ItemData(0x070020L, ItemNames.Progressive_Range_Armor, SpriteID.MAP_ICON_TANNERY, 0),
            new ItemData(0x070021L, ItemNames.Progressive_Range_Weapon, SpriteID.MAP_ICON_ARCHERY_SHOP, 0),
            new ItemData(0x070022L, ItemNames.Progressive_Magic, SpriteID.MAP_ICON_MAGIC_SHOP, 0),
            new ItemData(0x070000L, ItemNames.Lumbridge, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070001L, ItemNames.Lumbridge_Swamp, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070002L, ItemNames.Lumbridge_Farms, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070003L, ItemNames.HAM_Hideout, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070004L, ItemNames.Draynor_Village, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070005L, ItemNames.Draynor_Manor, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070006L, ItemNames.Wizards_Tower, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070007L, ItemNames.Al_Kharid, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070008L, ItemNames.Citharede_Abbey, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070009L, ItemNames.South_Of_Varrock, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x07000AL, ItemNames.Central_Varrock, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x07000BL, ItemNames.Varrock_Palace, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x07000CL, ItemNames.East_Of_Varrock, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x07000DL, ItemNames.West_Varrock, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x07000EL, ItemNames.Edgeville, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x07000FL, ItemNames.Barbarian_Village, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070010L, ItemNames.Monastery, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070011L, ItemNames.Ice_Mountain, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070012L, ItemNames.Dwarven_Mines, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070013L, ItemNames.Falador, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070014L, ItemNames.Falador_Farm, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070015L, ItemNames.Crafting_Guild, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070016L, ItemNames.Rimmington, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070017L, ItemNames.Port_Sarim, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070018L, ItemNames.Mudskipper_Point, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x070019L, ItemNames.Karamja, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x07001AL, ItemNames.Crandor, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x07001BL, ItemNames.Corsair_Cove, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0),
            new ItemData(0x07001CL, ItemNames.Wilderness, SpriteID.MINIMAP_ORB_WORLD_MAP_PLANET, 0)
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

    public static ItemData GetItemByName(String name) {
        return AllItems.stream()
                .filter(item -> name.equals(item.name))
                .findFirst()
                .orElse(null);
    }

    public static Dictionary<Long, ItemData> ItemsById = new Hashtable<>() {{
        for (ItemData item : AllItems) {
            put(item.id, item);
        }
    }};

    public static String MetalTierByCount(int count) {
        switch (count) {
            default:
                return "Bronze";
            case 1:
                return "Iron";
            case 2:
                return "Steel";
            case 3:
                return "Black";
            case 4:
                return "Mithril";
            case 5:
                return "Adamant";
            case 6:
                return "Rune";
        }
    }

    public static String RangeArmorTierByCount(int count) {
        switch (count) {
            default:
                return "No Ranged Armor";
            case 1:
                return "Ranged Armor: Leather";
            case 2:
                return "Ranged Armor: Studded Leather";
            case 3:
                return "Ranged Armor: Green Dragonhide";
        }
    }

    public static String RangeWeaponTierByCount(int count) {
        switch (count) {
            default:
                return "Bows: Wooden, Arrows: Iron";
            case 1:
                return "Bows: Oak, Arrows: Steel";
            case 2:
                return "Bows: Willow, Arrows: Mith";
            case 3:
                return "Bows: Maple, Arrows: Adamant";
        }
    }

    public static String MagicTierByCount(int count) {
        switch (count) {
            default:
                return "Strike Spells (Mind Runes)";
            case 1:
                return "Bolt Spells (Chaos Runes)";
            case 2:
                return "Blast Spells (Death Runes)";
        }
    }

    public static void LoadImages(SpriteManager spriteManager) {
        for (ItemData item : AllItems) {
            loadedSprites.put(item, spriteManager.getSprite(item.icon_id, item.icon_file));
        }
    }


    public static final Map<Integer, Integer[]> MetalWeaponsPermittedByTier = new Hashtable<>() {
        {
            put(0, new Integer[]{
                    ItemID.BRONZE_2H_AXE, ItemID.BRONZE_2H_SWORD, ItemID.BRONZE_AXE, ItemID.BRONZE_BATTLEAXE,
                    ItemID.BRONZE_CLAWS, ItemID.BRONZE_DAGGER, ItemID.BRONZE_DAGGERP, ItemID.BRONZE_DAGGERP_5670,
                    ItemID.BRONZE_DAGGERP_5688, ItemID.BRONZE_HALBERD, ItemID.BRONZE_HASTA, ItemID.BRONZE_HASTAKP,
                    ItemID.BRONZE_HASTAP, ItemID.BRONZE_HASTAP_11382, ItemID.BRONZE_HASTAP_11384,
                    ItemID.BRONZE_LONGSWORD, ItemID.BRONZE_MACE, ItemID.BRONZE_PICKAXE, ItemID.BRONZE_SCIMITAR,
                    ItemID.BRONZE_SPEAR, ItemID.BRONZE_SPEARKP, ItemID.BRONZE_SPEARP, ItemID.BRONZE_SPEARP_5704,
                    ItemID.BRONZE_SPEARP_5718, ItemID.BRONZE_SWORD, ItemID.BRONZE_WARHAMMER
            });
            put(1, new Integer[]{
                    ItemID.IRON_2H_AXE, ItemID.IRON_2H_SWORD, ItemID.IRON_AXE, ItemID.IRON_BATTLEAXE,
                    ItemID.IRON_CLAWS, ItemID.IRON_DAGGER, ItemID.IRON_DAGGERP, ItemID.IRON_DAGGERP_5668,
                    ItemID.IRON_DAGGERP_5686, ItemID.IRON_HALBERD, ItemID.IRON_HASTA, ItemID.IRON_HASTAKP,
                    ItemID.IRON_HASTAP, ItemID.IRON_HASTAP_11389, ItemID.IRON_HASTAP_11391,
                    ItemID.IRON_LONGSWORD, ItemID.IRON_MACE, ItemID.IRON_PICKAXE, ItemID.IRON_SCIMITAR,
                    ItemID.IRON_SPEAR, ItemID.IRON_SPEARKP, ItemID.IRON_SPEARP, ItemID.IRON_SPEARP_5706,
                    ItemID.IRON_SPEARP_5720, ItemID.IRON_SWORD, ItemID.IRON_WARHAMMER
            });
            put(2, new Integer[]{
                    ItemID.STEEL_2H_AXE, ItemID.STEEL_2H_SWORD, ItemID.STEEL_AXE, ItemID.STEEL_BATTLEAXE,
                    ItemID.STEEL_CLAWS, ItemID.STEEL_DAGGER, ItemID.STEEL_DAGGERP, ItemID.STEEL_DAGGERP_5672,
                    ItemID.STEEL_DAGGERP_5690, ItemID.STEEL_HALBERD, ItemID.STEEL_HASTA, ItemID.STEEL_HASTAKP,
                    ItemID.STEEL_HASTAP, ItemID.STEEL_HASTAP_11396, ItemID.STEEL_HASTAP_11398,
                    ItemID.STEEL_LONGSWORD, ItemID.STEEL_MACE, ItemID.STEEL_PICKAXE, ItemID.STEEL_SCIMITAR,
                    ItemID.STEEL_SPEAR, ItemID.STEEL_SPEARKP, ItemID.STEEL_SPEARP, ItemID.STEEL_SPEARP_5708,
                    ItemID.STEEL_SPEARP_5722, ItemID.STEEL_SWORD, ItemID.STEEL_WARHAMMER
            });
            put(3, new Integer[]{
                    ItemID.BLACK_2H_AXE, ItemID.BLACK_2H_SWORD, ItemID.BLACK_AXE, ItemID.BLACK_BATTLEAXE,
                    ItemID.BLACK_CLAWS, ItemID.BLACK_DAGGER, ItemID.BLACK_DAGGERP, ItemID.BLACK_DAGGERP_5682,
                    ItemID.BLACK_DAGGERP_5700, ItemID.BLACK_HALBERD, ItemID.BLACK_LONGSWORD, ItemID.BLACK_MACE,
                    ItemID.BLACK_PICKAXE, ItemID.BLACK_SCIMITAR, ItemID.BLACK_SPEAR, ItemID.BLACK_SPEARKP,
                    ItemID.BLACK_SPEARP, ItemID.BLACK_SPEARP_5734, ItemID.BLACK_SPEARP_5736, ItemID.BLACK_SWORD,
                    ItemID.BLACK_WARHAMMER
            });
            put(4, new Integer[]{
                    ItemID.MITHRIL_2H_AXE, ItemID.MITHRIL_2H_SWORD, ItemID.MITHRIL_AXE, ItemID.MITHRIL_BATTLEAXE,
                    ItemID.MITHRIL_CLAWS, ItemID.MITHRIL_DAGGER, ItemID.MITHRIL_DAGGERP, ItemID.MITHRIL_DAGGERP_5674,
                    ItemID.MITHRIL_DAGGERP_5692, ItemID.MITHRIL_HALBERD, ItemID.MITHRIL_HASTA, ItemID.MITHRIL_HASTAKP,
                    ItemID.MITHRIL_HASTAP, ItemID.MITHRIL_HASTAP_11403, ItemID.MITHRIL_HASTAP_11405,
                    ItemID.MITHRIL_LONGSWORD, ItemID.MITHRIL_MACE, ItemID.MITHRIL_PICKAXE, ItemID.MITHRIL_SCIMITAR,
                    ItemID.MITHRIL_SPEAR, ItemID.MITHRIL_SPEARKP, ItemID.MITHRIL_SPEARP, ItemID.MITHRIL_SPEARP_5710,
                    ItemID.MITHRIL_SPEARP_5724, ItemID.MITHRIL_SWORD, ItemID.MITHRIL_WARHAMMER
            });
            put(5, new Integer[]{
                    ItemID.ADAMANT_2H_AXE, ItemID.ADAMANT_2H_SWORD, ItemID.ADAMANT_AXE, ItemID.ADAMANT_BATTLEAXE,
                    ItemID.ADAMANT_CLAWS, ItemID.ADAMANT_DAGGER, ItemID.ADAMANT_DAGGERP, ItemID.ADAMANT_DAGGERP_5676,
                    ItemID.ADAMANT_DAGGERP_5694, ItemID.ADAMANT_HALBERD, ItemID.ADAMANT_HASTA, ItemID.ADAMANT_HASTAKP,
                    ItemID.ADAMANT_HASTAP, ItemID.ADAMANT_HASTAP_11410, ItemID.ADAMANT_HASTAP_11412,
                    ItemID.ADAMANT_LONGSWORD, ItemID.ADAMANT_MACE, ItemID.ADAMANT_PICKAXE, ItemID.ADAMANT_SCIMITAR,
                    ItemID.ADAMANT_SPEAR, ItemID.ADAMANT_SPEARKP, ItemID.ADAMANT_SPEARP, ItemID.ADAMANT_SPEARP_5712,
                    ItemID.ADAMANT_SPEARP_5726, ItemID.ADAMANT_SWORD, ItemID.ADAMANT_WARHAMMER
            });
            put(6, new Integer[]{
                    ItemID.RUNE_2H_AXE, ItemID.RUNE_2H_SWORD, ItemID.RUNE_AXE, ItemID.RUNE_BATTLEAXE,
                    ItemID.RUNE_CLAWS, ItemID.RUNE_DAGGER, ItemID.RUNE_DAGGERP, ItemID.RUNE_DAGGERP_5678,
                    ItemID.RUNE_DAGGERP_5696, ItemID.RUNE_HALBERD, ItemID.RUNE_HASTA, ItemID.RUNE_HASTAKP,
                    ItemID.RUNE_HASTAP, ItemID.RUNE_HASTAP_11417, ItemID.RUNE_HASTAP_11419,
                    ItemID.RUNE_LONGSWORD, ItemID.RUNE_MACE, ItemID.RUNE_PICKAXE, ItemID.RUNE_SCIMITAR,
                    ItemID.RUNE_SPEAR, ItemID.RUNE_SPEARKP, ItemID.RUNE_SPEARP, ItemID.RUNE_SPEARP_5714,
                    ItemID.RUNE_SPEARP_5728, ItemID.RUNE_SWORD, ItemID.RUNE_WARHAMMER
            });
        }
    };

    public static final List<Integer> MetalWeaponItemIds = new ArrayList<Integer>(){{
        for (int i=0;i<MetalWeaponsPermittedByTier.size();i++){
            Collections.addAll(this,MetalWeaponsPermittedByTier.get(i));
        }
    }};

    public static Map<Integer, Integer[]> MetalArmorPermittedByTier = new Hashtable<>() {
        {
            put(0, new Integer[]{
                    ItemID.BRONZE_BOOTS, ItemID.BRONZE_CHAINBODY, ItemID.BRONZE_GLOVES, ItemID.BRONZE_MED_HELM,
                    ItemID.BRONZE_FULL_HELM, ItemID.BRONZE_FULL_HELM_T, ItemID.BRONZE_FULL_HELM_G,
                    ItemID.BRONZE_KITESHIELD, ItemID.BRONZE_KITESHIELD_T, ItemID.BRONZE_KITESHIELD_G,
                    ItemID.BRONZE_PLATEBODY, ItemID.BRONZE_PLATEBODY_T, ItemID.BRONZE_PLATEBODY_G,
                    ItemID.BRONZE_PLATELEGS, ItemID.BRONZE_PLATELEGS_T, ItemID.BRONZE_PLATELEGS_G,
                    ItemID.BRONZE_PLATESKIRT, ItemID.BRONZE_PLATESKIRT_T, ItemID.BRONZE_PLATESKIRT_G,
                    ItemID.BRONZE_SQ_SHIELD
            });
            put(1, new Integer[]{
                    ItemID.IRON_BOOTS, ItemID.IRON_CHAINBODY, ItemID.IRON_GLOVES, ItemID.IRON_MED_HELM,
                    ItemID.IRON_FULL_HELM, ItemID.IRON_FULL_HELM_T, ItemID.IRON_FULL_HELM_G,
                    ItemID.IRON_KITESHIELD, ItemID.IRON_KITESHIELD_T, ItemID.IRON_KITESHIELD_G,
                    ItemID.IRON_PLATEBODY, ItemID.IRON_PLATEBODY_T, ItemID.IRON_PLATEBODY_G,
                    ItemID.IRON_PLATELEGS, ItemID.IRON_PLATELEGS_T, ItemID.IRON_PLATELEGS_G,
                    ItemID.IRON_PLATESKIRT, ItemID.IRON_PLATESKIRT_T, ItemID.IRON_PLATESKIRT_G,
                    ItemID.IRON_SQ_SHIELD
            });
            put(2, new Integer[]{
                    ItemID.STEEL_BOOTS, ItemID.STEEL_CHAINBODY, ItemID.STEEL_GLOVES, ItemID.STEEL_MED_HELM,
                    ItemID.STEEL_FULL_HELM, ItemID.STEEL_FULL_HELM_T, ItemID.STEEL_FULL_HELM_G,
                    ItemID.STEEL_KITESHIELD, ItemID.STEEL_KITESHIELD_T, ItemID.STEEL_KITESHIELD_G,
                    ItemID.STEEL_PLATEBODY, ItemID.STEEL_PLATEBODY_T, ItemID.STEEL_PLATEBODY_G,
                    ItemID.STEEL_PLATELEGS, ItemID.STEEL_PLATELEGS_T, ItemID.STEEL_PLATELEGS_G,
                    ItemID.STEEL_PLATESKIRT, ItemID.STEEL_PLATESKIRT_T, ItemID.STEEL_PLATESKIRT_G,
                    ItemID.STEEL_SQ_SHIELD,
                    ItemID.STEEL_HERALDIC_HELM, ItemID.STEEL_HERALDIC_HELM_8684, ItemID.STEEL_HERALDIC_HELM_8686,
                    ItemID.STEEL_HERALDIC_HELM_8688, ItemID.STEEL_HERALDIC_HELM_8690, ItemID.STEEL_HERALDIC_HELM_8692,
                    ItemID.STEEL_HERALDIC_HELM_8694, ItemID.STEEL_HERALDIC_HELM_8696, ItemID.STEEL_HERALDIC_HELM_8698,
                    ItemID.STEEL_HERALDIC_HELM_8700, ItemID.STEEL_HERALDIC_HELM_8702, ItemID.STEEL_HERALDIC_HELM_8704,
                    ItemID.STEEL_HERALDIC_HELM_8706, ItemID.STEEL_HERALDIC_HELM_8708, ItemID.STEEL_HERALDIC_HELM_8710,
                    ItemID.STEEL_HERALDIC_HELM_8712,
                    ItemID.STEEL_KITESHIELD_8746, ItemID.STEEL_KITESHIELD_8748, ItemID.STEEL_KITESHIELD_8750,
                    ItemID.STEEL_KITESHIELD_8752, ItemID.STEEL_KITESHIELD_8754, ItemID.STEEL_KITESHIELD_8756,
                    ItemID.STEEL_KITESHIELD_8758, ItemID.STEEL_KITESHIELD_8760, ItemID.STEEL_KITESHIELD_8762,
                    ItemID.STEEL_KITESHIELD_8764, ItemID.STEEL_KITESHIELD_8766, ItemID.STEEL_KITESHIELD_8768,
                    ItemID.STEEL_KITESHIELD_8770, ItemID.STEEL_KITESHIELD_8772, ItemID.STEEL_KITESHIELD_8774,
                    ItemID.STEEL_KITESHIELD_8776

            });
            put(3, new Integer[]{
                    ItemID.BLACK_BOOTS, ItemID.BLACK_CHAINBODY, ItemID.BLACK_GLOVES, ItemID.BLACK_MED_HELM,
                    ItemID.BLACK_FULL_HELM, ItemID.BLACK_FULL_HELM_T, ItemID.BLACK_FULL_HELM_G,
                    ItemID.BLACK_KITESHIELD, ItemID.BLACK_KITESHIELD_T, ItemID.BLACK_KITESHIELD_G,
                    ItemID.BLACK_PLATEBODY, ItemID.BLACK_PLATEBODY_T, ItemID.BLACK_PLATEBODY_G,
                    ItemID.BLACK_PLATELEGS, ItemID.BLACK_PLATELEGS_T, ItemID.BLACK_PLATELEGS_G,
                    ItemID.BLACK_PLATESKIRT, ItemID.BLACK_PLATESKIRT_T, ItemID.BLACK_PLATESKIRT_G,
                    ItemID.BLACK_SQ_SHIELD,
                    ItemID.BLACK_HELM_H1, ItemID.BLACK_HELM_H2, ItemID.BLACK_HELM_H3, ItemID.BLACK_HELM_H4,
                    ItemID.BLACK_PLATEBODY_H1, ItemID.BLACK_PLATEBODY_H2, ItemID.BLACK_PLATEBODY_H3,
                    ItemID.BLACK_PLATEBODY_H4, ItemID.BLACK_PLATEBODY_H5,
                    ItemID.BLACK_SHIELD_H1, ItemID.BLACK_SHIELD_H2, ItemID.BLACK_SHIELD_H3,
                    ItemID.BLACK_SHIELD_H4,ItemID.BLACK_SHIELD_H5
            });
            put(4, new Integer[]{
                    ItemID.MITHRIL_BOOTS, ItemID.MITHRIL_CHAINBODY, ItemID.MITHRIL_GLOVES, ItemID.MITHRIL_MED_HELM,
                    ItemID.MITHRIL_FULL_HELM, ItemID.MITHRIL_FULL_HELM_T, ItemID.MITHRIL_FULL_HELM_G,
                    ItemID.MITHRIL_KITESHIELD, ItemID.MITHRIL_KITESHIELD_T, ItemID.MITHRIL_KITESHIELD_G,
                    ItemID.MITHRIL_PLATEBODY, ItemID.MITHRIL_PLATEBODY_T, ItemID.MITHRIL_PLATEBODY_G,
                    ItemID.MITHRIL_PLATELEGS, ItemID.MITHRIL_PLATELEGS_T, ItemID.MITHRIL_PLATELEGS_G,
                    ItemID.MITHRIL_PLATESKIRT, ItemID.MITHRIL_PLATESKIRT_T, ItemID.MITHRIL_PLATESKIRT_G,
                    ItemID.MITHRIL_SQ_SHIELD
            });
            put(5, new Integer[]{
                    ItemID.ADAMANT_BOOTS, ItemID.ADAMANT_CHAINBODY, ItemID.ADAMANT_GLOVES, ItemID.ADAMANT_MED_HELM,
                    ItemID.ADAMANT_FULL_HELM, ItemID.ADAMANT_FULL_HELM_T, ItemID.ADAMANT_FULL_HELM_G,
                    ItemID.ADAMANT_KITESHIELD, ItemID.ADAMANT_KITESHIELD_T, ItemID.ADAMANT_KITESHIELD_G,
                    ItemID.ADAMANT_PLATEBODY, ItemID.ADAMANT_PLATEBODY_T, ItemID.ADAMANT_PLATEBODY_G,
                    ItemID.ADAMANT_PLATELEGS, ItemID.ADAMANT_PLATELEGS_T, ItemID.ADAMANT_PLATELEGS_G,
                    ItemID.ADAMANT_PLATESKIRT, ItemID.ADAMANT_PLATESKIRT_T, ItemID.ADAMANT_PLATESKIRT_G,
                    ItemID.ADAMANT_SQ_SHIELD,
                    ItemID.ADAMANT_HELM_H1, ItemID.ADAMANT_HELM_H2, ItemID.ADAMANT_HELM_H3, ItemID.ADAMANT_HELM_H4,
                    ItemID.ADAMANT_PLATEBODY_H1, ItemID.ADAMANT_PLATEBODY_H2, ItemID.ADAMANT_PLATEBODY_H3,
                    ItemID.ADAMANT_PLATEBODY_H4, ItemID.ADAMANT_PLATEBODY_H5,
                    ItemID.ADAMANT_SHIELD_H1, ItemID.ADAMANT_SHIELD_H2, ItemID.ADAMANT_SHIELD_H3,
                    ItemID.ADAMANT_SHIELD_H4,ItemID.ADAMANT_SHIELD_H5,
                    ItemID.ADAMANT_KITESHIELD_6894, ItemID.ADAMANT_KITESHIELD_22127, ItemID.ADAMANT_KITESHIELD_22129,
                    ItemID.ADAMANT_KITESHIELD_22131, ItemID.ADAMANT_KITESHIELD_22133, ItemID.ADAMANT_KITESHIELD_22135,
                    ItemID.ADAMANT_KITESHIELD_22137, ItemID.ADAMANT_KITESHIELD_22139, ItemID.ADAMANT_KITESHIELD_22141,
                    ItemID.ADAMANT_KITESHIELD_22143, ItemID.ADAMANT_KITESHIELD_22145, ItemID.ADAMANT_KITESHIELD_22147,
                    ItemID.ADAMANT_KITESHIELD_22149, ItemID.ADAMANT_KITESHIELD_22151, ItemID.ADAMANT_KITESHIELD_22153,
                    ItemID.ADAMANT_KITESHIELD_22155, ItemID.ADAMANT_KITESHIELD_22157, ItemID.ADAMANT_HERALDIC_HELM,
                    ItemID.ADAMANT_HERALDIC_HELM_22161, ItemID.ADAMANT_HERALDIC_HELM_22163,
                    ItemID.ADAMANT_HERALDIC_HELM_22165, ItemID.ADAMANT_HERALDIC_HELM_22167,
                    ItemID.ADAMANT_HERALDIC_HELM_22169, ItemID.ADAMANT_HERALDIC_HELM_22171,
                    ItemID.ADAMANT_HERALDIC_HELM_22173, ItemID.ADAMANT_HERALDIC_HELM_22175,
                    ItemID.ADAMANT_HERALDIC_HELM_22177, ItemID.ADAMANT_HERALDIC_HELM_22179,
                    ItemID.ADAMANT_HERALDIC_HELM_22181, ItemID.ADAMANT_HERALDIC_HELM_22183,
                    ItemID.ADAMANT_HERALDIC_HELM_22185, ItemID.ADAMANT_HERALDIC_HELM_22187,
                    ItemID.ADAMANT_HERALDIC_HELM_22189
            });
            put(6, new Integer[]{
                    ItemID.RUNE_BOOTS, ItemID.RUNE_CHAINBODY, ItemID.RUNE_GLOVES, ItemID.RUNE_MED_HELM,
                    ItemID.RUNE_FULL_HELM, ItemID.RUNE_FULL_HELM_T, ItemID.RUNE_FULL_HELM_G,
                    ItemID.RUNE_KITESHIELD, ItemID.RUNE_KITESHIELD_T, ItemID.RUNE_KITESHIELD_G,
                    ItemID.RUNE_PLATEBODY, ItemID.RUNE_PLATEBODY_T, ItemID.RUNE_PLATEBODY_G,
                    ItemID.RUNE_PLATELEGS, ItemID.RUNE_PLATELEGS_T, ItemID.RUNE_PLATELEGS_G,
                    ItemID.RUNE_PLATESKIRT, ItemID.RUNE_PLATESKIRT_T, ItemID.RUNE_PLATESKIRT_G,
                    ItemID.RUNE_SQ_SHIELD,
                    ItemID.RUNE_HELM_H1, ItemID.RUNE_HELM_H2, ItemID.RUNE_HELM_H3, ItemID.RUNE_HELM_H4,
                    ItemID.RUNE_PLATEBODY_H1, ItemID.RUNE_PLATEBODY_H2, ItemID.RUNE_PLATEBODY_H3,
                    ItemID.RUNE_PLATEBODY_H4, ItemID.RUNE_PLATEBODY_H5,
                    ItemID.RUNE_SHIELD_H1, ItemID.RUNE_SHIELD_H2, ItemID.RUNE_SHIELD_H3,
                    ItemID.RUNE_SHIELD_H4,ItemID.RUNE_SHIELD_H5,
                    ItemID.RUNE_HERALDIC_HELM, ItemID.RUNE_HERALDIC_HELM_8466, ItemID.RUNE_HERALDIC_HELM_8468,
                    ItemID.RUNE_HERALDIC_HELM_8470, ItemID.RUNE_HERALDIC_HELM_8472, ItemID.RUNE_HERALDIC_HELM_8474,
                    ItemID.RUNE_HERALDIC_HELM_8476, ItemID.RUNE_HERALDIC_HELM_8478, ItemID.RUNE_HERALDIC_HELM_8480,
                    ItemID.RUNE_HERALDIC_HELM_8482, ItemID.RUNE_HERALDIC_HELM_8484, ItemID.RUNE_HERALDIC_HELM_8486,
                    ItemID.RUNE_HERALDIC_HELM_8488, ItemID.RUNE_HERALDIC_HELM_8490, ItemID.RUNE_HERALDIC_HELM_8492,
                    ItemID.RUNE_HERALDIC_HELM_8494, ItemID.RUNE_KITESHIELD_8714, ItemID.RUNE_KITESHIELD_8716,
                    ItemID.RUNE_KITESHIELD_8718, ItemID.RUNE_KITESHIELD_8720, ItemID.RUNE_KITESHIELD_8722,
                    ItemID.RUNE_KITESHIELD_8724, ItemID.RUNE_KITESHIELD_8726, ItemID.RUNE_KITESHIELD_8728,
                    ItemID.RUNE_KITESHIELD_8730, ItemID.RUNE_KITESHIELD_8732, ItemID.RUNE_KITESHIELD_8734,
                    ItemID.RUNE_KITESHIELD_8736, ItemID.RUNE_KITESHIELD_8738, ItemID.RUNE_KITESHIELD_8740,
                    ItemID.RUNE_KITESHIELD_8742, ItemID.RUNE_KITESHIELD_8744
            });
        }
    };

    public static final List<Integer> MetalArmorItemIds = new ArrayList<Integer>(){{
        for (int i=0;i<MetalArmorPermittedByTier.size();i++){
            Collections.addAll(this,MetalArmorPermittedByTier.get(i));
        }
    }};

    public static Map<Integer, Integer[]> RangeArmorPermittedByTier = new Hashtable<>() {
        {
            put(0, new Integer[]{ });
            put(1, new Integer[] {
                    ItemID.LEATHER_GLOVES, ItemID.LEATHER_BOOTS, ItemID.LEATHER_COWL,
                    ItemID.LEATHER_BODY, ItemID.LEATHER_BODY_G,
                    ItemID.LEATHER_CHAPS, ItemID.LEATHER_CHAPS_G
            });
            put(2, new Integer[] {
                    ItemID.STUDDED_BODY, ItemID.STUDDED_BODY_T, ItemID.STUDDED_BODY_G,
                    ItemID.STUDDED_CHAPS, ItemID.STUDDED_CHAPS_T, ItemID.STUDDED_CHAPS_G,
                    ItemID.COIF, ItemID.LEATHER_VAMBRACES, ItemID.STUDDED_BODY_26264,
            });
            put(3, new Integer[] {
                    ItemID.GREEN_DHIDE_BODY, ItemID.GREEN_DHIDE_BODY_T, ItemID.GREEN_DHIDE_BODY_G,
                    ItemID.GREEN_DHIDE_CHAPS, ItemID.GREEN_DHIDE_CHAPS_T, ItemID.GREEN_DHIDE_CHAPS_G,
                    ItemID.GREEN_DHIDE_SHIELD, ItemID.GREEN_DHIDE_VAMBRACES
            });
        }
    };

    public static final List<Integer> RangeArmorItemIds = new ArrayList<Integer>(){{
        for (int i=0;i<RangeArmorPermittedByTier.size();i++){
            Collections.addAll(this,RangeArmorPermittedByTier.get(i));
        }
    }};

    public static Map<Integer, Integer[]> RangeWeaponsPermittedByTier = new Hashtable<>() {
        {
            put(0, new Integer[] {
                    ItemID.BOW_AND_ARROW, ItemID.SHORTBOW, ItemID.LONGBOW,
                    ItemID.BRONZE_ARROW, ItemID.BRONZE_ARROW_11700, ItemID.BRONZE_ARROWP, ItemID.BRONZE_ARROWP_5616, ItemID.BRONZE_ARROWP_5622,
                    ItemID.IRON_ARROW, ItemID.IRON_ARROW_11701, ItemID.IRON_ARROWP, ItemID.IRON_ARROWP_5617, ItemID.IRON_ARROWP_5623
            });
            put(1, new Integer[] {
                    ItemID.OAK_SHORTBOW, ItemID.OAK_LONGBOW,
                    ItemID.STEEL_ARROW, ItemID.STEEL_ARROW_11702,
                    ItemID.STEEL_ARROWP, ItemID.STEEL_ARROWP_5618, ItemID.STEEL_ARROWP_5624
            });
            put(2, new Integer[] {
                    ItemID.WILLOW_SHORTBOW, ItemID.WILLOW_LONGBOW,
                    ItemID.MITHRIL_ARROW, ItemID.MITHRIL_ARROW_7552, ItemID.MITHRIL_ARROW_11703,
                    ItemID.MITHRIL_ARROWP, ItemID.MITHRIL_ARROWP_5619, ItemID.MITHRIL_ARROWP_5625
            });
            put(3, new Integer[] {
                    ItemID.MAPLE_SHORTBOW, ItemID.MAPLE_LONGBOW,
                    ItemID.ADAMANT_ARROW, ItemID.ADAMANT_ARROW_20388,
                    ItemID.ADAMANT_ARROWP, ItemID.ADAMANT_ARROWP_5620, ItemID.ADAMANT_ARROWP_5626
            });
        }};

    public static final List<Integer> RangeWeaponItemIds = new ArrayList<Integer>(){{
        for (int i=0;i<RangeWeaponsPermittedByTier.size();i++){
            Collections.addAll(this,RangeWeaponsPermittedByTier.get(i));
        }
    }};
}
