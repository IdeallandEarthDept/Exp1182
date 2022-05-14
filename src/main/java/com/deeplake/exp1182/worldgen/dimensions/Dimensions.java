package com.deeplake.exp1182.worldgen.dimensions;

import com.deeplake.exp1182.Main;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class Dimensions {

    public static final ResourceKey<Level> MYSTERIOUS = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Main.MOD_ID, "mysterious"));

    public static void register() {
//        Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(Main.MOD_ID, "mysterious_chunkgen"),
//                MysteriousChunkGenerator.CODEC);
//        Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(Main.MOD_ID, "biomes"),
//                MysteriousBiomeProvider.CODEC);
    }
}
