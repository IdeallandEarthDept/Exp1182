package com.deeplake.exp1182.blocks.demo;

import net.minecraftforge.common.ForgeConfigSpec;

public class WorldBossConfig {

    public static ForgeConfigSpec.DoubleValue X;
    public static ForgeConfigSpec.DoubleValue Y;
    public static ForgeConfigSpec.DoubleValue Z;
//    public static ForgeConfigSpec.IntValue INGOTS_PER_ORE;
//    public static ForgeConfigSpec.IntValue ENERGY_CAPACITY;
//    public static ForgeConfigSpec.IntValue ENERGY_RECEIVE;
//    public static ForgeConfigSpec.IntValue ENERGY_GENERATE;

    public static void registerServerConfig(ForgeConfigSpec.Builder SERVER_BUILDER) {
        SERVER_BUILDER.comment("Settings for the WorldBoss").push("world_boss");

        X = SERVER_BUILDER
                .comment("World Boss Spawn X").defineInRange("spawnX", 0, -Float.MAX_VALUE, Float.MAX_VALUE);

        Y = SERVER_BUILDER
                .comment("World Boss Spawn Y").defineInRange("spawnY", 100, -Float.MAX_VALUE, Float.MAX_VALUE);

        Z = SERVER_BUILDER
                .comment("World Boss Spawn Z").defineInRange("spawnZ", 0, -Float.MAX_VALUE, Float.MAX_VALUE);


//        ENERGY_RECEIVE = SERVER_BUILDER
//                .comment("How much energy the generator can receive per side")
//                .defineInRange("receive", 1000, 1, Integer.MAX_VALUE);
//        ENERGY_GENERATE = SERVER_BUILDER
//                .comment("How much energy is needed to process one ore block")
//                .defineInRange("generate", 500, 1, Integer.MAX_VALUE);

        SERVER_BUILDER.pop();
    }

    public static void registerCommonConfig(ForgeConfigSpec.Builder common_builder) {
        common_builder.comment("Settings for the WorldBoss").push("world_boss");

        X = common_builder
                .comment("World Boss Spawn X").defineInRange("spawnX", 0, -Float.MAX_VALUE, Float.MAX_VALUE);

        Y = common_builder
                .comment("World Boss Spawn Y").defineInRange("spawnY", 100, -Float.MAX_VALUE, Float.MAX_VALUE);

        Z = common_builder
                .comment("World Boss Spawn Z").defineInRange("spawnZ", 0, -Float.MAX_VALUE, Float.MAX_VALUE);


//        ENERGY_RECEIVE = SERVER_BUILDER
//                .comment("How much energy the generator can receive per side")
//                .defineInRange("receive", 1000, 1, Integer.MAX_VALUE);
//        ENERGY_GENERATE = SERVER_BUILDER
//                .comment("How much energy is needed to process one ore block")
//                .defineInRange("generate", 500, 1, Integer.MAX_VALUE);

        common_builder.pop();
    }
}
