package com.deeplake.exp1182.datagen;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.setup.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TutBlockTags extends BlockTagsProvider {

    public TutBlockTags(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, Main.MOD_ID, helper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.CLIMBABLE).add(ModBlocks.FLAME_LADDER.get());
//        tag(BlockTags.MINEABLE_WITH_PICKAXE)
//                .add(ModBlocks.GENERATOR.get())
//                .add(ModBlocks.POWERGEN.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_OVERWORLD.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_NETHER.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_END.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_DEEPSLATE.get());
//        tag(BlockTags.NEEDS_IRON_TOOL)
//                .add(ModBlocks.GENERATOR.get())
//                .add(ModBlocks.POWERGEN.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_OVERWORLD.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_NETHER.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_END.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_DEEPSLATE.get());
//        tag(Tags.Blocks.ORES)
//                .add(ModBlocks.MYSTERIOUS_ORE_OVERWORLD.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_NETHER.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_END.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_DEEPSLATE.get());
//        tag(ModBlocks.MYSTERIOUS_ORE)
//                .add(ModBlocks.MYSTERIOUS_ORE_OVERWORLD.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_NETHER.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_END.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_DEEPSLATE.get());
    }

    @Override
    public String getName() {
        return "Tutorial Tags";
    }
}