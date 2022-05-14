package com.deeplake.exp1182.datagen;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.setup.ModBlocks;
import com.deeplake.exp1182.setup.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TutItemTags extends ItemTagsProvider {

    public TutItemTags(DataGenerator generator, BlockTagsProvider blockTags, ExistingFileHelper helper) {
        super(generator, blockTags, Main.MOD_ID, helper);
    }

    @Override
    protected void addTags() {
//        tag(Tags.Items.ORES)
//                .add(ModBlocks.MYSTERIOUS_ORE_OVERWORLD_ITEM.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_NETHER_ITEM.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_END_ITEM.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_DEEPSLATE_ITEM.get());
//        tag(Tags.Items.INGOTS)
//                .add(ModItems.MYSTERIOUS_INGOT.get());
//        tag(ModBlocks.MYSTERIOUS_ORE_ITEM)
//                .add(ModBlocks.MYSTERIOUS_ORE_OVERWORLD_ITEM.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_NETHER_ITEM.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_END_ITEM.get())
//                .add(ModBlocks.MYSTERIOUS_ORE_DEEPSLATE_ITEM.get());
    }

    @Override
    public String getName() {
        return "Tutorial Tags";
    }
}
