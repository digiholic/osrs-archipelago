package com.archipelago.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

@Data
@AllArgsConstructor
public class LocationData {
    public int id;
    public String name;

    public static List<LocationData> AllLocations = List.of(
            new LocationData(0x070000, LocationNames.Q_Cooks_Assistant),
            new LocationData(0x070001, LocationNames.Q_Demon_Slayer),
            new LocationData(0x070002, LocationNames.Q_Restless_Ghost),
            new LocationData(0x070003, LocationNames.Q_Romeo_Juliet),
            new LocationData(0x070004, LocationNames.Q_Sheep_Shearer),
            new LocationData(0x070005, LocationNames.Q_Shield_of_Arrav),
            new LocationData(0x070006, LocationNames.Q_Ernest_the_Chicken),
            new LocationData(0x070007, LocationNames.Q_Vampyre_Slayer),
            new LocationData(0x070008, LocationNames.Q_Imp_Catcher),
            new LocationData(0x070009, LocationNames.Q_Prince_Ali_Rescue),
            new LocationData(0x07000A, LocationNames.Q_Dorics_Quest),
            new LocationData(0x07000B, LocationNames.Q_Black_Knights_Fortress),
            new LocationData(0x07000C, LocationNames.Q_Witchs_Potion),
            new LocationData(0x07000D, LocationNames.Q_Knights_Sword),
            new LocationData(0x07000E, LocationNames.Q_Goblin_Diplomacy),
            new LocationData(0x07000F, LocationNames.Q_Pirates_Treasure),
            new LocationData(0x070010, LocationNames.Q_Rune_Mysteries),
            new LocationData(0x070011, LocationNames.Q_Misthalin_Mystery),
            new LocationData(0x070012, LocationNames.Q_Corsair_Curse),
            new LocationData(0x070013, LocationNames.Q_X_Marks_the_Spot),
            new LocationData(0x070014, LocationNames.Q_Below_Ice_Mountain),
            new LocationData(0x070040, LocationNames.QP_Cooks_Assistant),
            new LocationData(0x070041, LocationNames.QP_Demon_Slayer),
            new LocationData(0x070042, LocationNames.QP_Restless_Ghost),
            new LocationData(0x070043, LocationNames.QP_Romeo_Juliet),
            new LocationData(0x070044, LocationNames.QP_Sheep_Shearer),
            new LocationData(0x070045, LocationNames.QP_Shield_of_Arrav),
            new LocationData(0x070046, LocationNames.QP_Ernest_the_Chicken),
            new LocationData(0x070047, LocationNames.QP_Vampyre_Slayer),
            new LocationData(0x070048, LocationNames.QP_Imp_Catcher),
            new LocationData(0x070049, LocationNames.QP_Prince_Ali_Rescue),
            new LocationData(0x07004A, LocationNames.QP_Dorics_Quest),
            new LocationData(0x07004B, LocationNames.QP_Black_Knights_Fortress),
            new LocationData(0x07004C, LocationNames.QP_Witchs_Potion),
            new LocationData(0x07004D, LocationNames.QP_Knights_Sword),
            new LocationData(0x07004E, LocationNames.QP_Goblin_Diplomacy),
            new LocationData(0x07004F, LocationNames.QP_Pirates_Treasure),
            new LocationData(0x070050, LocationNames.QP_Rune_Mysteries),
            new LocationData(0x070051, LocationNames.QP_Misthalin_Mystery),
            new LocationData(0x070052, LocationNames.QP_Corsair_Curse),
            new LocationData(0x070053, LocationNames.QP_X_Marks_the_Spot),
            new LocationData(0x070054, LocationNames.QP_Below_Ice_Mountain),
            new LocationData(0x070015, LocationNames.Simple_Lockbox),
            new LocationData(0x070016, LocationNames.Elaborate_Lockbox),
            new LocationData(0x070017, LocationNames.Ornate_Lockbox),
            new LocationData(0x070018, LocationNames.Guppy),
            new LocationData(0x070019, LocationNames.Cavefish),
            new LocationData(0x07001A, LocationNames.Tetra),
            new LocationData(0x07001B, LocationNames.Mind_Core),
            new LocationData(0x07001C, LocationNames.Body_Core),
            new LocationData(0x07001D, LocationNames.Barronite_Deposit),
            new LocationData(0x07001E, LocationNames.Oak_Log),
            new LocationData(0x07001F, LocationNames.Willow_Log),
            new LocationData(0x070020, LocationNames.Catch_Lobster),
            new LocationData(0x070021, LocationNames.Catch_Swordfish),
            new LocationData(0x070022, LocationNames.Holy_Symbol),
            new LocationData(0x070023, LocationNames.Mine_Silver),
            new LocationData(0x070024, LocationNames.Mine_Coal),
            new LocationData(0x070025, LocationNames.Mine_Gold),
            new LocationData(0x070026, LocationNames.Smelt_Silver),
            new LocationData(0x070027, LocationNames.Smelt_Steel),
            new LocationData(0x070028, LocationNames.Smelt_Gold),
            new LocationData(0x070029, LocationNames.Cut_Sapphire),
            new LocationData(0x07002A, LocationNames.Cut_Emerald),
            new LocationData(0x07002B, LocationNames.Cut_Ruby),
            new LocationData(0x07002C, LocationNames.Cut_Diamond),
            new LocationData(0x07002D, LocationNames.Bake_Apple_Pie),
            new LocationData(0x07002E, LocationNames.Bake_Cake),
            new LocationData(0x07002F, LocationNames.Bake_Meat_Pizza),
            new LocationData(0x070030, LocationNames.Stronghold_Of_Security),
            new LocationData(0x070031, LocationNames.Beginner_Clue),
            new LocationData(0x070032, LocationNames.Edgeville_Altar),
            new LocationData(0x070033, LocationNames.K_Lesser_Demon),
            new LocationData(0x070034, LocationNames.K_Ogress_Shaman),
            new LocationData(0x070035, LocationNames.K_Obor),
            new LocationData(0x070036, LocationNames.K_Bryo),
            new LocationData(0x070037, LocationNames.Total_XP_5000),
            new LocationData(0x070038, LocationNames.Total_XP_25000),
            new LocationData(0x070039, LocationNames.Total_XP_50000),
            new LocationData(0x07003A, LocationNames.Total_Level_50),
            new LocationData(0x07003B, LocationNames.Total_Level_100),
            new LocationData(0x07003C, LocationNames.Total_Level_200),
            new LocationData(0x07003D, LocationNames.Combat_Level_10),
            new LocationData(0x07003E, LocationNames.Combat_Level_25),
            new LocationData(0x07003F, LocationNames.Combat_Level_50)
    );

    public static Dictionary<String, LocationData> LocationsByName = new Hashtable<>(){{
        for(LocationData loc : AllLocations){
            put(loc.name, loc);
        }
    }};
    public static Dictionary<Integer, LocationData> LocationsById = new Hashtable<>(){{
        for(LocationData loc : AllLocations){
            put(loc.id, loc);
        }
    }};
}