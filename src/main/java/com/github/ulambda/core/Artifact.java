package com.github.ulambda.core;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.json.JSONObject;
import com.github.ulambda.utils.AssetManager;
import com.github.ulambda.core.Artifacts;

public interface Artifact extends Equippable{
    ArtifactSet set();
    int rarity();
    int level();
    Stat statType();
    //substats
    default double statValue(){ 
        return Artifacts.getMainStatValue(rarity(), level(), statType()); 
    }
    default Map<Stat, Double> stats() {
        return Map.of(statType(), statValue());
    }
}

record Flower(ArtifactSet set, int rarity, int level) implements Artifact{
    public Stat statType(){return Stat.FlatHP;}
}
record Feather(ArtifactSet set, int rarity, int level) implements Artifact{
    public Stat statType(){return Stat.FlatATK;}
}

record Sands(ArtifactSet set, int rarity, int level, Stat statType) implements Artifact{
    public Sands{
        if(!allowlist().contains(statType)) 
            throw new IllegalArgumentException(statType + "is an invalid stat type for " + this.getClass());
    }
    private static List<Stat> allowlist(){
        return List.of(
            Stat.HPPercent,
            Stat.DEFPercent,
            Stat.ATKPercent,
            Stat.EnergyRecharge,
            Stat.ElementalMastery
        );
    }
}

record Goblet(ArtifactSet set, int rarity, int level, Stat statType) implements Artifact{
    public Goblet{
        if(!allowlist().contains(statType)) 
            throw new IllegalArgumentException(statType + "is an invalid stat type for " + this.getClass());
    }
    private static List<Stat> allowlist(){
        return List.of(
            Stat.HPPercent,
            Stat.DEFPercent,
            Stat.ATKPercent,
            Stat.ElementalMastery,
            Stat.PhysicalDMGBonus,
            Stat.PyroDMGBonus,
            Stat.CryoDMGBonus,
            Stat.GeoDMGBonus,
            Stat.DendroDMGBonus,
            Stat.ElectroDMGBonus,
            Stat.HydroDMGBonus,
            Stat.AnemoDMGBonus,
            Stat.HealingBonus
        );
    }
}
record Circlet(ArtifactSet set, int rarity, int level, Stat statType) implements Artifact {
    public Circlet {
        if (!allowlist().contains(statType))
            throw new IllegalArgumentException(statType + " is an invalid stat type for " + this.getClass());
    }
    private static List<Stat> allowlist() {
        return List.of(
            Stat.CritRate,
            Stat.CritDMG,
            Stat.HPPercent,
            Stat.DEFPercent,
            Stat.ATKPercent,
            Stat.ElementalMastery,
            Stat.HealingBonus
        );
    }
}


record ArtifactSet(
    String setName,
    Map<Stat, Double> twoPiece, Map<Stat,Double> fourPiece
){
    public static ArtifactSet empty(){
        return new ArtifactSet("", Map.of(), Map.of());
    }
}


// public class Artifact implements Equippable{

//     @Override public Map<Stat, Double> stats() {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'stats'");
//     }
    

//     class SubStats implements MutableStatTable {
//         Map<Stat, Double> stats = new HashMap<>();
//         public Map<Stat, Double> stats(){return stats;}


//         /**
//          * Roll a substat and increase the value of the stat based on the quality
//          * @note if substats has 4 subs, then rolling will just increase the value of the stat
//          * @note 
//          * @param stat
//          * @param quality
//          * @return
//          */
//         public double roll(Stat stat, Artifacts.RollQuality quality){
//             return 0;
//         }
//     }
// }

/**
 * Artifact object represents an artifact in the game.
 * @note Artifacts are equippable to a character.
 * @note Artifacts are mutable stat tables that are built up and compiled into an immutable instance.
 * @note Artifacts are often built by using optimizers to maximize the character's rotation damage output.
 */
// public class Artifact implements Equippable, MutableStatTable{
//     public static enum ArtifactType { FLOWER, FEATHER, SANDS, GOBLET, CIRCLET };
//     ArtifactSetBonus set;
//     Artifact.ArtifactType type;
//     int rarity;
//     int level;
//     Stat mainStatType;
//     SubStats substats;


//     public Artifact(Artifact.ArtifactType type, int level, int rarity, Stat mainStatType, ArtifactSetBonus set){
//         assert Artifacts.levelCheck(level, rarity);
//         assert rarity >= 1 && rarity <= 5;
//         this.type = type;
//         this.level = level;
//         this.rarity = rarity;
//         this.mainStatType = mainStatType;
//         this.set = set;
//         substats = new SubStats(this, 
//             Stat.None, 0, 
//             Stat.None, 0, 
//             Stat.None, 0, 
//             Stat.None, 0);
//     }

//     public void set(SubStats substats){
//         this.substats = substats;
//     }

//     public double mainStatAmount(){
//         return Artifacts.getMainStatValue(rarity, level, mainStatType);
//     }
    
//     public Map<Stat, Double> stats(){
//         return Map.of(
//             mainStatType, mainStatAmount(),
//             substats.subStat1Type, substats.subStat1Amount,
//             substats.subStat2Type, substats.subStat2Amount,
//             substats.subStat3Type, substats.subStat3Amount,
//             substats.subStat4Type, substats.subStat4Amount
//         );
//     }
// }

// class Flower extends Artifact{
//     public Flower(int level, int rarity, ArtifactSetBonus set){
//         super(ArtifactType.FLOWER, level, rarity, Stat.FlatHP, set);

//     }
// }

// class Feather extends Artifact{
//     public Feather(int level, int rarity, ArtifactSetBonus set){
//         super(ArtifactType.FEATHER, level, rarity, Stat.FlatATK, set);
//     }
// }

// class Sands extends Artifact{
//     public Sands(int level, int rarity, Stat mainStatType, ArtifactSetBonus set){
//         super(ArtifactType.SANDS, level, rarity, mainStatType, set);
//         assert mainStatType.equals(Stat.HPPercent) ||
//             mainStatType.equals(Stat.DEFPercent) ||
//             mainStatType.equals(Stat.ATKPercent) ||
//             mainStatType.equals(Stat.EnergyRecharge) ||
//             mainStatType.equals(Stat.ElementalMastery);
//     }
// }

// class Goblet extends Artifact{
//     public Goblet(int level, int rarity, Stat mainStatType, ArtifactSetBonus set){
//         super(ArtifactType.GOBLET, level, rarity, mainStatType, set);
//         assert mainStatType.equals(Stat.HPPercent) ||
//             mainStatType.equals(Stat.DEFPercent) ||
//             mainStatType.equals(Stat.ATKPercent) ||
//             mainStatType.equals(Stat.ElementalMastery) ||
//             mainStatType.equals(Stat.PhysicalDMGBonus) ||
//             mainStatType.equals(Stat.PyroDMGBonus) ||
//             mainStatType.equals(Stat.CryoDMGBonus) ||
//             mainStatType.equals(Stat.GeoDMGBonus) ||
//             mainStatType.equals(Stat.DendroDMGBonus) ||
//             mainStatType.equals(Stat.ElectroDMGBonus) ||
//             mainStatType.equals(Stat.HydroDMGBonus) ||
//             mainStatType.equals(Stat.AnemoDMGBonus);
//             mainStatType.equals(Stat.HealingBonus);
//     }
// }

// class Circlet extends Artifact{
//     public Circlet(int level, int rarity, Stat mainStatType, ArtifactSetBonus set){
//         super(ArtifactType.CIRCLET, level, rarity, mainStatType, set);
//         assert mainStatType.equals(Stat.CritRate) ||
//             mainStatType.equals(Stat.CritDMG) ||
//             mainStatType.equals(Stat.HPPercent) ||
//             mainStatType.equals(Stat.DEFPercent) ||
//             mainStatType.equals(Stat.ATKPercent) ||
//             mainStatType.equals(Stat.ElementalMastery) ||
//             mainStatType.equals(Stat.HealingBonus);
//     }
// }






// class SubStats implements StatTable{
//     final Artifact parent;
//     int rarity;
//     Stat subStat1Type = Stat.None;
//     double subStat1Amount = 0;
//     int subStat1Rolls = 0;
//     Stat subStat2Type = Stat.None;
//     double subStat2Amount = 0;
//     int subStat2Rolls = 0;
//     Stat subStat3Type = Stat.None;
//     double subStat3Amount = 0;
//     int subStat3Rolls = 0;
//     Stat subStat4Type = Stat.None;
//     double subStat4Amount = 0;
//     int subStat4Rolls = 0;
    

//     public Map<Stat, Double> stats(){
//         return Map.of(
//             subStat1Type, subStat1Amount,
//             subStat2Type, subStat2Amount,
//             subStat3Type, subStat3Amount,
//             subStat4Type, subStat4Amount
//         );
//     }


//     public SubStats(Artifact parent, 
//         Stat subStat1Type, double subStat1RollQuality, 
//         Stat subStat2Type, double subStat2RollQuality, 
//         Stat subStat3Type, double subStat3RollQuality, 
//         Stat subStat4Type, double subStat4RollQuality){

//         this.parent = parent;
//         this.rarity = parent.rarity;

//         assert subStat1Type != parent.mainStatType;
//         assert subStat1Type != subStat2Type || subStat2Type.equals(Stat.None);
//         assert subStat1Type != subStat3Type || subStat2Type.equals(Stat.None);
//         assert subStat1Type != subStat4Type || subStat2Type.equals(Stat.None);
//         assert subStat2Type != subStat3Type || subStat2Type.equals(Stat.None);
//         assert subStat2Type != subStat4Type || subStat2Type.equals(Stat.None);
//         assert subStat3Type != subStat4Type || subStat2Type.equals(Stat.None);


//         this.subStat1Type = subStat1Type;
//         this.subStat2Type = subStat2Type;
//         this.subStat3Type = subStat3Type;
//         this.subStat4Type = subStat4Type;
//         subStat1Amount = Artifacts.getSubStatValue(rarity, subStat1Type) * subStat1RollQuality;
//         subStat2Amount = Artifacts.getSubStatValue(rarity, subStat2Type) * subStat2RollQuality;
//         subStat3Amount = Artifacts.getSubStatValue(rarity, subStat3Type) * subStat3RollQuality;
//         subStat4Amount = Artifacts.getSubStatValue(rarity, subStat4Type) * subStat4RollQuality;
//     }

//     public static SubStats of(Artifact parent){
//         assert parent.rarity == 1 || parent.rarity == 2;
//         return new SubStats(
//             parent, 
//             Stat.None, 0, 
//             Stat.None, 0, 
//             Stat.None, 0, 
//             Stat.None, 0
//         );
//     }


//     public static SubStats of(Artifact parent, Stat stat, int quality){
//         assert parent.rarity == 2 || parent.rarity == 3;
//         return new SubStats(
//             parent, 
//             stat, quality, 
//             Stat.None, 0, 
//             Stat.None, 0, 
//             Stat.None, 0
//         );
//     }

//     public static SubStats of(Artifact parent, Stat stat, int quality, Stat stat2, int quality2){
//         assert parent.rarity == 3 || parent.rarity == 4;
//         return new SubStats(
//             parent, 
//             stat, quality, 
//             stat2, quality2, 
//             Stat.None, 0,
//             Stat.None, 0
//         );
//     }

//     public static SubStats of(Artifact parent, Stat stat, int quality, Stat stat2, int quality2, Stat stat3, int quality3){
//         assert parent.rarity == 4 || parent.rarity == 5;
//         return new SubStats(
//             parent, 
//             stat, quality, 
//             stat2, quality2, 
//             stat3, quality3, 
//             Stat.None, 0
//         );  
//     }

//     public static SubStats of(Artifact parent, Stat stat, int quality, Stat stat2, int quality2, Stat stat3, int quality3, Stat stat4, int quality4){
//         assert parent.rarity == 5;
//         return new SubStats(
//             parent, 
//             stat, quality, 
//             stat2, quality2, 
//             stat3, quality3, 
//             stat4, quality4
//         ); 
//     }

//     public int numRolls(){
//         return subStat1Rolls + subStat2Rolls + subStat3Rolls + subStat4Rolls;
//     }

//     public void rollSubStat(Stat stat, Artifacts.RollQuality quality){
//         if(stat.equals(Stat.None)) throw new IllegalArgumentException("Stat cannot be None");
//         if(numRolls() >= 10) throw new IllegalArgumentException("Too many rolls");

//         if(subStat1Type.equals(Stat.None)){
//             subStat1Type = stat;
//         }
//         else if(subStat2Type.equals(Stat.None)){
//             subStat2Type = stat;
//         }
//         else if(subStat3Type.equals(Stat.None)){
//             subStat3Type = stat;
//         }
//         else if(subStat4Type.equals(Stat.None)){
//             subStat4Type = stat;
//         }

//         double base = Artifacts.getSubStatValue(rarity, stat);
//         if(subStat1Type.equals(stat)){
//             subStat1Amount += base * quality.multiplier;
//             subStat1Rolls++;
//         }
//         else if(subStat2Type.equals(stat)){
//             subStat2Amount += base * quality.multiplier;
//             subStat2Rolls++;
//         }
//         else if(subStat3Type.equals(stat)){
//             subStat3Amount += base * quality.multiplier;
//             subStat3Rolls++;
//         }
//         else if(subStat4Type.equals(stat)){
//             subStat4Amount += base * quality.multiplier;
//             subStat4Rolls++;
//         }
            
//         else throw new IllegalArgumentException("Stat not in substats");
//     }
// }



