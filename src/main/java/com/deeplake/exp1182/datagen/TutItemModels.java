package com.deeplake.exp1182.datagen;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.setup.ModBlocks;
import com.deeplake.exp1182.setup.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TutItemModels extends ItemModelProvider {

    public TutItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
//        withExistingParent(ModBlocks.MYSTERIOUS_ORE_OVERWORLD_ITEM.get().getRegistryName().getPath(), modLoc("block/mysterious_ore_overworld"));
//        withExistingParent(ModBlocks.MYSTERIOUS_ORE_NETHER_ITEM.get().getRegistryName().getPath(), modLoc("block/mysterious_ore_nether"));
//        withExistingParent(ModBlocks.MYSTERIOUS_ORE_END_ITEM.get().getRegistryName().getPath(), modLoc("block/mysterious_ore_end"));
//        withExistingParent(ModBlocks.MYSTERIOUS_ORE_DEEPSLATE_ITEM.get().getRegistryName().getPath(), modLoc("block/mysterious_ore_deepslate"));

//        withExistingParent(ModBlocks.BLOCK_MOTOR_Y.get().asItem().getRegistryName().getPath(), modLoc("block/"+"motor_y"));
//        withExistingParent(ModBlocks.BLOCK_MOTOR_X.get().asItem().getRegistryName().getPath(), modLoc("block/"+"motor_x"));
////        withExistingParent(ModBlocks.FLAME_BG2_ITEM.get().getRegistryName().getPath(), modLoc("block/"+"flame_bg2"));
////        withExistingParent(ModBlocks.FLAME_BG3_ITEM.get().getRegistryName().getPath(), modLoc("block/"+"flame_bg3"));
////        withExistingParent(ModBlocks.FLAME_BG4_ITEM.get().getRegistryName().getPath(), modLoc("block/"+"flame_bg4"));
////        withExistingParent(ModBlocks.FLAME_LADDER_ITEM.get().getRegistryName().getPath(), modLoc("block/"+"flame_ladder"));
////        withExistingParent(ModBlocks.FLAME_FLOOR_ITEM.get().getRegistryName().getPath(), modLoc("block/"+"flame_wall"));
////        withExistingParent(ModBlocks.SPAWN_BOSS.get().asItem().getRegistryName().getPath(), modLoc("block/"+"spawn_boss"));
//        withExistingParent(ModItems.MONSTER_REMOVAL.get().getRegistryName().getPath(), modLoc("item/monster_removal"));
//        singleTexture(ModItems.ITEM_DEBUG.get().getRegistryName().getPath(), modLoc("item/template_spawn_egg"));
//        singleTexture(ModItems.TP_BACK.get().getRegistryName().getPath(), modLoc("item/tp_back"));
//        singleTexture(ModItems.TP_GO.get().getRegistryName().getPath(), modLoc("item/tp_go"));
//        singleTexture(ModBlocks.SPAWN_BOSS_ITEM.get().getRegistryName().getPath(), modLoc("block/spawn_boss"));

//        singleTexture(ModItems.ITEM_DEBUG.get().getRegistryName().getPath(),
//                mcLoc("item/generated"),
//                "layer0", modLoc("item/item_debug"));
//        singleTexture(ModItems.TP_BACK.get().getRegistryName().getPath(),
//                mcLoc("item/generated"),
//                "layer0", modLoc("item/tp_back"));
//        singleTexture(ModItems.TP_GO.get().getRegistryName().getPath(),
//                mcLoc("item/generated"),
//                "layer0", modLoc("item/tp_go"));
//        singleTexture(ModItems.BULLET1.get().getRegistryName().getPath(),
//                mcLoc("item/generated"),
//                "layer0", modLoc("item/bullet1"));
        singleTexture(ModItems.BULLET3.get().getRegistryName().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/bullet3"));

//        withExistingParent(ModBlocks.GENERATOR_ITEM.get().getRegistryName().getPath(), modLoc("block/generator"));
//        withExistingParent(ModBlocks.POWERGEN_ITEM.get().getRegistryName().getPath(), modLoc("block/powergen/main"));
//        withExistingParent(ModBlocks.PORTAL_ITEM.get().getRegistryName().getPath(), modLoc("block/portal"));
//
//        withExistingParent(ModItems.THIEF_EGG.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
//
//
//        singleTexture(ModItems.RAW_MYSTERIOUS_CHUNK.get().getRegistryName().getPath(),
//                mcLoc("item/generated"),
//                "layer0", modLoc("item/raw_mysterious_chunk"));
//        singleTexture(ModItems.MYSTERIOUS_INGOT.get().getRegistryName().getPath(),
//                mcLoc("item/generated"),
//                "layer0", modLoc("item/mysterious_ingot"));
    }
}
