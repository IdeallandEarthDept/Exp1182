package com.deeplake.exp1182.datagen;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.setup.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TutBlockStates  extends BlockStateProvider {

    public TutBlockStates(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, Main.MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerGenerator();
        registerPowergen();
        registerPortal();

//        simpleBlock(ModBlocks.MYSTERIOUS_ORE_OVERWORLD.get());
//        simpleBlock(ModBlocks.MYSTERIOUS_ORE_NETHER.get());
//        simpleBlock(ModBlocks.MYSTERIOUS_ORE_END.get());
//        simpleBlock(ModBlocks.MYSTERIOUS_ORE_DEEPSLATE.get());

//        simpleBlock(ModBlocks.FLAME_BG1.get());
//        simpleBlock(ModBlocks.FLAME_BG2.get());
//        simpleBlock(ModBlocks.FLAME_BG3.get());
//        simpleBlock(ModBlocks.FLAME_BG4.get());
//        simpleBlock(ModBlocks.SPAWN_BOSS.get());
//        simpleBlock(ModBlocks.BLOCK_MOTOR_X.get());
//        simpleBlock(ModBlocks.SP_GLASS.get());
        simpleBlock(ModBlocks.FLAME_FLOOR_NO_MUSIC.get());
//        simpleBlock(ModBlocks.MJDS_GATE2.get());
//        simpleBlock(ModBlocks.MJDS_GATE3.get());
//        simpleBlock(ModBlocks.MJDS_GATE4.get());
    }

    private void registerPortal() {
//        Block block = ModBlocks.PORTAL_BLOCK.get();
//        ResourceLocation side = modLoc("block/portal_side");
//        ResourceLocation top = modLoc("block/portal_top");
//        simpleBlock(block, models().cube(block.getRegistryName().getPath(), side, top, side, side, side, side));
    }

    private void registerGenerator() {
        // Using CustomLoaderBuilder we can define a json file for our model that will use our baked model
//        BlockModelBuilder generatorModel = models().getBuilder(ModBlocks.GENERATOR.get().getRegistryName().getPath())
//                .parent(models().getExistingFile(mcLoc("cube")))
//                .customLoader((blockModelBuilder, helper) -> new CustomLoaderBuilder<BlockModelBuilder>(GENERATOR_LOADER, blockModelBuilder, helper) { })
//                .end();
//        directionalBlock(ModBlocks.GENERATOR.get(), generatorModel);
    }

    private void registerPowergen() {
//        BlockModelBuilder frame = models().getBuilder("block/powergen/main");
//        frame.parent(models().getExistingFile(mcLoc("cube")));
//
//        floatingCube(frame, 0f, 0f, 0f, 1f, 16f, 1f);
//        floatingCube(frame, 15f, 0f, 0f, 16f, 16f, 1f);
//        floatingCube(frame, 0f, 0f, 15f, 1f, 16f, 16f);
//        floatingCube(frame, 15f, 0f, 15f, 16f, 16f, 16f);
//
//        floatingCube(frame, 1f, 0f, 0f, 15f, 1f, 1f);
//        floatingCube(frame, 1f, 15f, 0f, 15f, 16f, 1f);
//        floatingCube(frame, 1f, 0f, 15f, 15f, 1f, 16f);
//        floatingCube(frame, 1f, 15f, 15f, 15f, 16f, 16f);
//
//        floatingCube(frame, 0f, 0f, 1f, 1f, 1f, 15f);
//        floatingCube(frame, 15f, 0f, 1f, 16f, 1f, 15f);
//        floatingCube(frame, 0f, 15f, 1f, 1f, 16f, 15f);
//        floatingCube(frame, 15f, 15f, 1f, 16f, 16f, 15f);
//
//        floatingCube(frame, 1f, 1f, 1f, 15f, 15f, 15f);
//
//        frame.texture("window", modLoc("block/powergen_window"));
//        frame.texture("particle", modLoc("block/powergen_off"));
//
//        createPowergenModel(ModBlocks.POWERGEN.get(), frame);
    }

    private void floatingCube(BlockModelBuilder builder, float fx, float fy, float fz, float tx, float ty, float tz) {
        builder.element()
                .from(fx, fy, fz)
                .to(tx, ty, tz)
                .allFaces((direction, faceBuilder) -> faceBuilder.texture("#window"))
                .end();
    }

    private void createPowergenModel(Block block, BlockModelBuilder frame) {
        BlockModelBuilder singleOff = models().getBuilder("block/powergen/singleoff")
                .element().from(3, 3, 3).to(13, 13, 13).face(Direction.DOWN).texture("#single").end().end()
                .texture("single", modLoc("block/powergen_off"));
        BlockModelBuilder singleOn = models().getBuilder("block/powergen/singleon")
                .element().from(3, 3, 3).to(13, 13, 13).face(Direction.DOWN).texture("#single").end().end()
                .texture("single", modLoc("block/powergen_on"));

        MultiPartBlockStateBuilder bld = getMultipartBuilder(block);

        bld.part().modelFile(frame).addModel();

        BlockModelBuilder[] models = new BlockModelBuilder[] { singleOff, singleOn };
        for (int i = 0 ; i < 2 ; i++) {
            boolean powered = i == 1;
            bld.part().modelFile(models[i]).addModel().condition(BlockStateProperties.POWERED, powered);
            bld.part().modelFile(models[i]).rotationX(180).addModel().condition(BlockStateProperties.POWERED, powered);
            bld.part().modelFile(models[i]).rotationX(90).addModel().condition(BlockStateProperties.POWERED, powered);
            bld.part().modelFile(models[i]).rotationX(270).addModel().condition(BlockStateProperties.POWERED, powered);
            bld.part().modelFile(models[i]).rotationY(90).rotationX(90).addModel().condition(BlockStateProperties.POWERED, powered);
            bld.part().modelFile(models[i]).rotationY(270).rotationX(90).addModel().condition(BlockStateProperties.POWERED, powered);
        }
    }
}