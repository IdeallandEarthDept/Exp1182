package com.deeplake.exp1182.worldgen.ores;

import net.minecraftforge.common.ForgeConfigSpec;

public class OresConfig {

    public static ForgeConfigSpec.IntValue MYSTERIOUS_VEINSIZE;
    public static ForgeConfigSpec.IntValue MYSTERIOUS_AMOUNT;
    public static ForgeConfigSpec.IntValue OVERWORLD_VEINSIZE;
    public static ForgeConfigSpec.IntValue OVERWORLD_AMOUNT;
    public static ForgeConfigSpec.IntValue DEEPSLATE_VEINSIZE;
    public static ForgeConfigSpec.IntValue DEEPSLATE_AMOUNT;
    public static ForgeConfigSpec.IntValue NETHER_VEINSIZE;
    public static ForgeConfigSpec.IntValue NETHER_AMOUNT;
    public static ForgeConfigSpec.IntValue END_VEINSIZE;
    public static ForgeConfigSpec.IntValue END_AMOUNT;

    public static void registerCommonConfig(ForgeConfigSpec.Builder COMMON_BUILDER) {
        COMMON_BUILDER.comment("Settings for ore generation").push("ores");

        MYSTERIOUS_VEINSIZE = COMMON_BUILDER
                .comment("Veinsize of our ore in the mysterious dimension")
                .defineInRange("mysteriousVeinsize", 25, 1, Integer.MAX_VALUE);
        MYSTERIOUS_AMOUNT = COMMON_BUILDER
                .comment("Amount of veines of our ore in the mysterious dimension")
                .defineInRange("mysteriousAmount", 10, 1, Integer.MAX_VALUE);
        OVERWORLD_VEINSIZE = COMMON_BUILDER
                .comment("Veinsize of our ore in the overworld dimension")
                .defineInRange("overworldVeinsize", 5, 1, Integer.MAX_VALUE);
        OVERWORLD_AMOUNT = COMMON_BUILDER
                .comment("Amount of veines of our ore in the overworld dimension")
                .defineInRange("overworldAmount", 3, 1, Integer.MAX_VALUE);
        DEEPSLATE_VEINSIZE = COMMON_BUILDER
                .comment("Veinsize of our ore in the overworld dimension but for deepslate")
                .defineInRange("deepslateVeinsize", 5, 1, Integer.MAX_VALUE);
        DEEPSLATE_AMOUNT = COMMON_BUILDER
                .comment("Amount of veines of our ore in the overworld dimension but for deepslate")
                .defineInRange("deepslateAmount", 3, 1, Integer.MAX_VALUE);
        NETHER_VEINSIZE = COMMON_BUILDER
                .comment("Veinsize of our ore in the nether dimension")
                .defineInRange("netherVeinsize", 5, 1, Integer.MAX_VALUE);
        NETHER_AMOUNT = COMMON_BUILDER
                .comment("Amount of veines of our ore in the nether dimension")
                .defineInRange("netherAmount", 3, 1, Integer.MAX_VALUE);
        END_VEINSIZE = COMMON_BUILDER
                .comment("Veinsize of our ore in the end dimension")
                .defineInRange("endVeinsize", 10, 1, Integer.MAX_VALUE);
        END_AMOUNT = COMMON_BUILDER
                .comment("Amount of veines of our ore in the end dimension")
                .defineInRange("endAmount", 6, 1, Integer.MAX_VALUE);

        COMMON_BUILDER.pop();
    }

}
