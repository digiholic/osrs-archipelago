package com.archipelago;

import com.archipelago.data.LocationData;
import com.archipelago.data.LocationNames;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LocationHandler {
    public static List<LocationData> AllLocations = List.of(
            new LocationData(0x070000L, LocationNames.Q_Cooks_Assistant),
            new LocationData(0x070001L, LocationNames.Q_Demon_Slayer),
            new LocationData(0x070002L, LocationNames.Q_Restless_Ghost),
            new LocationData(0x070003L, LocationNames.Q_Romeo_Juliet),
            new LocationData(0x070004L, LocationNames.Q_Sheep_Shearer),
            new LocationData(0x070005L, LocationNames.Q_Shield_of_Arrav),
            new LocationData(0x070006L,LocationNames.Q_Ernest_the_Chicken),
            new LocationData(0x070007L,LocationNames.Q_Vampyre_Slayer),
            new LocationData(0x070008L,LocationNames.Q_Imp_Catcher),
            new LocationData(0x070009L,LocationNames.Q_Prince_Ali_Rescue),
            new LocationData(0x07000AL,LocationNames.Q_Dorics_Quest),
            new LocationData(0x07000BL,LocationNames.Q_Black_Knights_Fortress),
            new LocationData(0x07000CL,LocationNames.Q_Witchs_Potion),
            new LocationData(0x07000DL,LocationNames.Q_Knights_Sword),
            new LocationData(0x07000EL,LocationNames.Q_Goblin_Diplomacy),
            new LocationData(0x07000FL,LocationNames.Q_Pirates_Treasure),
            new LocationData(0x070010L,LocationNames.Q_Rune_Mysteries),
            new LocationData(0x070011L,LocationNames.Q_Misthalin_Mystery),
            new LocationData(0x070012L,LocationNames.Q_Corsair_Curse),
            new LocationData(0x070013L,LocationNames.Q_X_Marks_the_Spot),
            new LocationData(0x070014L,LocationNames.Q_Below_Ice_Mountain),
            new LocationData(0x070055L, LocationNames.Q_Dragon_Slayer),
            new LocationData(0x070040L,LocationNames.QP_Cooks_Assistant),
            new LocationData(0x070041L,LocationNames.QP_Demon_Slayer),
            new LocationData(0x070042L,LocationNames.QP_Restless_Ghost),
            new LocationData(0x070043L,LocationNames.QP_Romeo_Juliet),
            new LocationData(0x070044L,LocationNames.QP_Sheep_Shearer),
            new LocationData(0x070045L,LocationNames.QP_Shield_of_Arrav),
            new LocationData(0x070046L,LocationNames.QP_Ernest_the_Chicken),
            new LocationData(0x070047L,LocationNames.QP_Vampyre_Slayer),
            new LocationData(0x070048L,LocationNames.QP_Imp_Catcher),
            new LocationData(0x070049L,LocationNames.QP_Prince_Ali_Rescue),
            new LocationData(0x07004AL,LocationNames.QP_Dorics_Quest),
            new LocationData(0x07004BL,LocationNames.QP_Black_Knights_Fortress),
            new LocationData(0x07004CL,LocationNames.QP_Witchs_Potion),
            new LocationData(0x07004DL,LocationNames.QP_Knights_Sword),
            new LocationData(0x07004EL,LocationNames.QP_Goblin_Diplomacy),
            new LocationData(0x07004FL,LocationNames.QP_Pirates_Treasure),
            new LocationData(0x070050L,LocationNames.QP_Rune_Mysteries),
            new LocationData(0x070051L,LocationNames.QP_Misthalin_Mystery),
            new LocationData(0x070052L,LocationNames.QP_Corsair_Curse),
            new LocationData(0x070053L,LocationNames.QP_X_Marks_the_Spot),
            new LocationData(0x070054L,LocationNames.QP_Below_Ice_Mountain),
            new LocationData(0x070015L,LocationNames.Simple_Lockbox),
            new LocationData(0x070016L,LocationNames.Elaborate_Lockbox),
            new LocationData(0x070017L,LocationNames.Ornate_Lockbox),
            new LocationData(0x070018L,LocationNames.Guppy),
            new LocationData(0x070019L,LocationNames.Cavefish),
            new LocationData(0x07001AL,LocationNames.Tetra),
            new LocationData(0x07001BL,LocationNames.Mind_Core),
            new LocationData(0x07001CL,LocationNames.Body_Core),
            new LocationData(0x07001DL,LocationNames.Barronite_Deposit),
            new LocationData(0x07001EL,LocationNames.Oak_Log),
            new LocationData(0x07001FL,LocationNames.Willow_Log),
            new LocationData(0x070020L,LocationNames.Catch_Lobster),
            new LocationData(0x070021L,LocationNames.Catch_Swordfish),
            new LocationData(0x070022L,LocationNames.Holy_Symbol),
            new LocationData(0x070023L,LocationNames.Mine_Silver),
            new LocationData(0x070024L,LocationNames.Mine_Coal),
            new LocationData(0x070025L,LocationNames.Mine_Gold),
            new LocationData(0x070026L,LocationNames.Smelt_Silver),
            new LocationData(0x070027L,LocationNames.Smelt_Steel),
            new LocationData(0x070028L,LocationNames.Smelt_Gold),
            new LocationData(0x070029L,LocationNames.Cut_Sapphire),
            new LocationData(0x07002AL,LocationNames.Cut_Emerald),
            new LocationData(0x07002BL,LocationNames.Cut_Ruby),
            new LocationData(0x07002CL,LocationNames.Cut_Diamond),
            new LocationData(0x07002DL,LocationNames.Bake_Apple_Pie),
            new LocationData(0x07002EL,LocationNames.Bake_Cake),
            new LocationData(0x07002FL,LocationNames.Bake_Meat_Pizza),
            new LocationData(0x070030L,LocationNames.Stronghold_Of_Security),
            new LocationData(0x070031L,LocationNames.Beginner_Clue),
            new LocationData(0x070032L,LocationNames.Edgeville_Altar),
            new LocationData(0x070033L,LocationNames.K_Lesser_Demon),
            new LocationData(0x070034L,LocationNames.K_Ogress_Shaman),
            new LocationData(0x070035L,LocationNames.K_Obor),
            new LocationData(0x070036L,LocationNames.K_Bryo),
            new LocationData(0x070037L,LocationNames.Total_XP_5000),
            new LocationData(0x070038L,LocationNames.Total_XP_25000),
            new LocationData(0x070039L,LocationNames.Total_XP_50000),
            new LocationData(0x07003AL,LocationNames.Total_Level_50),
            new LocationData(0x07003BL,LocationNames.Total_Level_100),
            new LocationData(0x07003CL,LocationNames.Total_Level_200),
            new LocationData(0x07003DL,LocationNames.Combat_Level_10),
            new LocationData(0x07003EL,LocationNames.Combat_Level_25),
            new LocationData(0x07003FL,LocationNames.Combat_Level_50)
    );

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
}
