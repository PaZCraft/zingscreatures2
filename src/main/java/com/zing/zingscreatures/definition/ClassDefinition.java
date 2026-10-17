package com.zing.zingscreatures.definition;

import java.util.List;

public record ClassDefinition(
    String className,
    String classId,
    String classLetterSymbol,
    String classNameShort,
    String classIcon,
    int classNumber,
    EraTier eraTier,
    int classNumberTab,
    String classColor,
    String classDimensionOpenerRequirements,
    ClassCategory classCategory,
    String classInspiration,
    String weakness,
    String strength,
    String immune,
    WorldGenShape worldGenShapeDimension,
    String dimensionSurfaceBlock,
    String dimensionUndergroundBlock,
    String dimensionFluid,
    String dimensionAir,
    List<String> dimensionWorldGen,
    String classPowerups,
    String classUpgradeWeapons,
    String classUpgradeArmor,
    String classUpgradeTools,
    String classRuneBiomes,
    String dimensionName,
    boolean vanillaDimensionAlreadyExists,
    String gemstones,
    String creatures,
    String mobs,
    String biomes,
    String specialBiomes,
    String boss,
    String structures,
    String woodSets
) {
    public enum EraTier {
        ORIGIN,
        LEGEND,
        GENESIS,
        DIVISION,
        INFINITY,
        ETERNITY
    }

    public enum ClassCategory {
        CLASSICAL,
        VANILLA,
        TIME,
        HOLIDAY,
        FANTASY,
        NATURAL,
        SOUND,
        HOBBY
    }

    public enum WorldGenShape {
        OVERWORLD,
        VOID,
        END,
        NETHER,
        SIFT,
        CLASSIC,
        ISLAND,
        SKY,
        GEOMETRY,
        SPACE
    }

    public enum DamageRelation {
    WEAKNESS,
    STRENGTH,
    IMMUNITY
    }
}