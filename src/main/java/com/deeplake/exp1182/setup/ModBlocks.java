package com.deeplake.exp1182.setup;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.blocks.*;
import com.deeplake.exp1182.blocks.motor.BlockMotorX;
import com.deeplake.exp1182.blocks.motor.BlockMotorY;
import com.deeplake.exp1182.blocks.tileentity.MotorTileEntityHorizontal;
import com.deeplake.exp1182.blocks.tileentity.MotorTileEntityVertical;
import com.deeplake.exp1182.blocks.tileentity.TileEntitySpawnBoss;
import com.deeplake.exp1182.worldgen.structures.PortalStructure;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.deeplake.exp1182.util.CommonDef.JUMP_FACTOR_MJDS;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);
    static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Main.MOD_ID);

    // Some common properties for our blocks and items
    public static final BlockBehaviour.Properties BLOCK_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).strength(2f).requiresCorrectToolForDrops();
//    public static final RegistryObject<Block> MYSTERIOUS_ORE_DEEPSLATE = BLOCKS.register("mysterious_ore_deepslate", () -> new Block(BLOCK_PROPERTIES));
//    public static final RegistryObject<Item> MYSTERIOUS_ORE_DEEPSLATE_ITEM = fromBlock(MYSTERIOUS_ORE_DEEPSLATE);
//    public static final RegistryObject<Block> MYSTERIOUS_ORE_END = BLOCKS.register("mysterious_ore_end", () -> new Block(BLOCK_PROPERTIES));
//    public static final RegistryObject<Item> MYSTERIOUS_ORE_END_ITEM = fromBlock(MYSTERIOUS_ORE_END);
//    public static final RegistryObject<Block> MYSTERIOUS_ORE_NETHER = BLOCKS.register("mysterious_ore_nether", () -> new Block(BLOCK_PROPERTIES));
//    public static final RegistryObject<Item> MYSTERIOUS_ORE_NETHER_ITEM = fromBlock(MYSTERIOUS_ORE_NETHER);
//    public static final RegistryObject<Block> MYSTERIOUS_ORE_OVERWORLD = BLOCKS.register("mysterious_ore_overworld", () -> new Block(BLOCK_PROPERTIES));
//    public static final RegistryObject<Item> MYSTERIOUS_ORE_OVERWORLD_ITEM = fromBlock(MYSTERIOUS_ORE_OVERWORLD);
//    public static final RegistryObject<MenuType<PowergenContainer>> POWERGEN_CONTAINER = Registration.CONTAINERS.register("powergen",
//            () -> IForgeMenuType.create((windowId, inv, data) -> new PowergenContainer(windowId, data.readBlockPos(), inv, inv.player)));
//    public static final TagKey<Block> MYSTERIOUS_ORE = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Main.MOD_ID, "mysterious_ore"));
//    public static final TagKey<Item> MYSTERIOUS_ORE_ITEM = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Main.MOD_ID, "mysterious_ore"));

    static final float MAX_BLAST_RESIST = 3600000.0F;

    //MJDS Blocks
    public static final BlockBehaviour.Properties BLOCK_PROP_MJDS = BlockBehaviour.Properties.of(Material.STONE).strength(-1f, MAX_BLAST_RESIST).noDrops().jumpFactor(JUMP_FACTOR_MJDS);

    static final String NAME_FLAME_BG = "flame_bg";
    static int index = 0;

    static RegistryObject<Block> getFlameBG()
    {
        index++;
        return registerWithItem(NAME_FLAME_BG+index, () -> new BaseBlockMJDS(BLOCK_PROP_MJDS));
    }


    public static final RegistryObject<Block> FLAME_BG1 = getFlameBG();

    public static final RegistryObject<Block> FLAME_BG2 = getFlameBG();

    public static final RegistryObject<Block> FLAME_BG3 = getFlameBG();

    public static final RegistryObject<Block> FLAME_BG4 = getFlameBG();

    public static final RegistryObject<Block> FLAME_FLOOR = registerWithItem("flame_wall", () -> new BaseBlockMJDS(BLOCK_PROP_MJDS));

    public static final BlockBehaviour.Properties BLOCK_PROP_LADDER = BlockBehaviour.Properties.of(Material.DECORATION).strength(-1f, MAX_BLAST_RESIST).noDrops().sound(SoundType.LADDER).noOcclusion().jumpFactor(JUMP_FACTOR_MJDS);

    public static final RegistryObject<Block> FLAME_LADDER = registerWithItem("flame_ladder", () -> new LadderBlockMJDS(BLOCK_PROP_LADDER));

    public static final RegistryObject<Block> SPAWN_BOSS = registerWithItem("spawn_boss", () -> new BlockSpawnBoss(BLOCK_PROP_MJDS));

    public static final RegistryObject<Block> BLOCK_MOTOR_X = registerWithItem("motor_x", () -> new BlockMotorX(BLOCK_PROP_MJDS));

    public static final RegistryObject<Block> BLOCK_MOTOR_Y = registerWithItem("motor_y", () -> new BlockMotorY(BLOCK_PROP_MJDS));


//    public static final RegistryObject<StructureFeature<JigsawConfiguration>> PORTAL = Registration.STRUCTURES.register("portal", PortalStructure::new);
//
//    public static final RegistryObject<PowergenBlock> POWERGEN = BLOCKS.register("powergen", PowergenBlock::new);
    public static final RegistryObject<BlockEntityType<TileEntitySpawnBoss>> TE_SPAWN_BOSS = BLOCK_ENTITIES.register("spawnboss", () -> BlockEntityType.Builder.of(TileEntitySpawnBoss::new, SPAWN_BOSS.get()).build(null));
    public static final RegistryObject<BlockEntityType<MotorTileEntityHorizontal>> TE_MOTOR_H = BLOCK_ENTITIES.register("te_motor_h", () -> BlockEntityType.Builder.of(MotorTileEntityHorizontal::new, BLOCK_MOTOR_X.get()).build(null));
    public static final RegistryObject<BlockEntityType<MotorTileEntityVertical>> TE_MOTOR_V = BLOCK_ENTITIES.register("te_motor_v", () -> BlockEntityType.Builder.of(MotorTileEntityVertical::new, BLOCK_MOTOR_Y.get()).build(null));
//    public static final RegistryObject<Item> POWERGEN_ITEM = fromBlock(POWERGEN);
//    public static final RegistryObject<GeneratorBlock> GENERATOR = BLOCKS.register("generator", GeneratorBlock::new);
//    public static final RegistryObject<BlockEntityType<GeneratorBE>> GENERATOR_BE = BLOCK_ENTITIES.register("generator", () -> BlockEntityType.Builder.of(GeneratorBE::new, GENERATOR.get()).build(null));
//    public static final RegistryObject<Item> GENERATOR_ITEM = fromBlock(GENERATOR);
//    public static final RegistryObject<Block> PORTAL_BLOCK = BLOCKS.register("portal", PortalBlock::new);
//    public static final RegistryObject<Item> PORTAL_ITEM = fromBlock(PORTAL_BLOCK);

    // Conveniance function: Take a RegistryObject<Block> and make a corresponding RegistryObject<Item> from it
    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ModItems.ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ModItems.ITEM_PROPERTIES));
    }

    public static RegistryObject<Block> registerWithItem(final String name, final Supplier<? extends Block> sup)
    {
        RegistryObject<Block> registryObject = BLOCKS.register(name, sup);
        fromBlock(registryObject);
        return registryObject;
    }
}
