package com.deeplake.exp1182.datagen;

import com.deeplake.exp1182.setup.ModBlocks;
import com.deeplake.exp1182.setup.ModItems;
import net.minecraft.data.DataGenerator;

public class TutLootTables extends BaseLootTableProvider {

    public TutLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
//        lootTables.put(ModBlocks.GENERATOR.get(), createStandardTable("generator", ModBlocks.GENERATOR.get(), ModBlocks.GENERATOR_BE.get()));
//        lootTables.put(ModBlocks.POWERGEN.get(), createStandardTable("powergen", ModBlocks.POWERGEN.get(), ModBlocks.POWERGEN_BE.get()));
//        lootTables.put(ModBlocks.MYSTERIOUS_ORE_OVERWORLD.get(), createSilkTouchTable("mysterious_ore_overworld", ModBlocks.MYSTERIOUS_ORE_OVERWORLD.get(), ModItems.RAW_MYSTERIOUS_CHUNK.get(), 1, 3));
//        lootTables.put(ModBlocks.MYSTERIOUS_ORE_NETHER.get(), createSilkTouchTable("mysterious_ore_nether", ModBlocks.MYSTERIOUS_ORE_NETHER.get(), ModItems.RAW_MYSTERIOUS_CHUNK.get(), 1, 3));
//        lootTables.put(ModBlocks.MYSTERIOUS_ORE_END.get(), createSilkTouchTable("mysterious_ore_end", ModBlocks.MYSTERIOUS_ORE_END.get(), ModItems.RAW_MYSTERIOUS_CHUNK.get(), 1, 3));
//        lootTables.put(ModBlocks.MYSTERIOUS_ORE_DEEPSLATE.get(), createSilkTouchTable("mysterious_ore_deepslate", ModBlocks.MYSTERIOUS_ORE_DEEPSLATE.get(), ModItems.RAW_MYSTERIOUS_CHUNK.get(), 1, 3));
    }
}