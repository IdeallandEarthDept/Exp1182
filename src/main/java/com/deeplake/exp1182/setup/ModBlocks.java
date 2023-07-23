package com.deeplake.exp1182.setup;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.blocks.*;
import com.deeplake.exp1182.blocks.mechanics.BlockAchvDoor;
import com.deeplake.exp1182.blocks.motor.BlockMotorX;
import com.deeplake.exp1182.blocks.motor.BlockMotorY;
import com.deeplake.exp1182.blocks.motor.BlockMotorZ;
import com.deeplake.exp1182.blocks.tileentity.*;
import com.deeplake.exp1182.util.AdvancementUtil;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.deeplake.exp1182.util.CommonDef.JUMP_FACTOR_MJDS_BLOCK;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);
    static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MOD_ID);

    // Some common properties for our blocks and items
    public static final BlockBehaviour.Properties BLOCK_PROPERTIES = BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops();
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
    public static final BlockBehaviour.Properties BLOCK_PROP_MJDS = BlockBehaviour.Properties.of().strength(-1f, MAX_BLAST_RESIST).noLootTable().jumpFactor(JUMP_FACTOR_MJDS_BLOCK);
    public static final BlockBehaviour.Properties BLOCK_PROP_MJDS_AIR = BlockBehaviour.Properties.of().strength(-1f, MAX_BLAST_RESIST).noLootTable().jumpFactor(JUMP_FACTOR_MJDS_BLOCK).noCollission();
    public static final BlockBehaviour.Properties BLOCK_PROP_MJDS_DARKNESS = BlockBehaviour.Properties.of().strength(-1f, MAX_BLAST_RESIST).noLootTable().jumpFactor(JUMP_FACTOR_MJDS_BLOCK).noCollission().noOcclusion()
            .isViewBlocking(BaseBlockMJDS::neverDo)
            .isValidSpawn(BaseBlockMJDS::neverDoET)
            .isRedstoneConductor(BaseBlockMJDS::neverDo)
            .isSuffocating(BaseBlockMJDS::neverDo);
    public static final BlockBehaviour.Properties BLOCK_PROP_MJDS_GLASS = BlockBehaviour.Properties.of().strength(-1f, MAX_BLAST_RESIST).noLootTable().jumpFactor(JUMP_FACTOR_MJDS_BLOCK).noOcclusion();

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
    public static final RegistryObject<Block> BLACK_OUT = registerWithItem("black_out", () -> new BaseBlockMJDS(BLOCK_PROP_MJDS_AIR));
    public static final RegistryObject<Block> DARKNESS = registerWithItem("darkness", () -> new BlockDarkness(BLOCK_PROP_MJDS_DARKNESS));

    public static final RegistryObject<Block> FLAME_FLOOR = registerWithItem("flame_wall", () -> new BaseBlockMJDS(BLOCK_PROP_MJDS));
    public static final RegistryObject<Block> FLAME_FLOOR_NO_MUSIC = registerWithItem("flame_wall_quiet", () -> new BaseBlockNoMusic(BLOCK_PROP_MJDS));
    public static final RegistryObject<Block> FLAME_FLOOR_NO_ADV = registerWithItem("flame_wall_no_adv", () -> new BaseBlockNoAdv(BLOCK_PROP_MJDS));
    public static final RegistryObject<Block> SP_GLASS = registerWithItem("sp_glass", BlockWallGlass::new);
    public static final RegistryObject<Block> COVERED = registerWithItem("covered", BlockCovered::new);
    public static final RegistryObject<Block> BREAKABLE = registerWithItem("breakable", () -> new BlockBreakable(BLOCK_PROP_MJDS));
    public static final RegistryObject<Block> MJDS_GATE1 = registerWithItem("mjds_gate1", () -> new BlockAchvDoor(AdvancementUtil.GREAT_KEY));
    public static final RegistryObject<Block> MJDS_GATE2 = registerWithItem("mjds_gate2", () -> new BlockAchvDoor(AdvancementUtil.GREAT_KEY));
    public static final RegistryObject<Block> MJDS_GATE3 = registerWithItem("mjds_gate3", () -> new BlockAchvDoor(AdvancementUtil.GREAT_KEY));
    public static final RegistryObject<Block> MJDS_GATE4 = registerWithItem("mjds_gate4", () -> new BlockAchvDoor(AdvancementUtil.GREAT_KEY));
    public static final RegistryObject<Block> MJDS_GATE_FIRE = registerWithItem("mjds_gate_fire", () -> new BlockAchvDoor(AdvancementUtil.HOLY_WATER));

    public static final BlockBehaviour.Properties BLOCK_PROP_LADDER = BlockBehaviour.Properties.of().strength(-1f, MAX_BLAST_RESIST).noLootTable().sound(SoundType.LADDER).noOcclusion().jumpFactor(JUMP_FACTOR_MJDS_BLOCK);

    public static final RegistryObject<Block> FLAME_LADDER = registerWithItem("flame_ladder", () -> new LadderBlockMJDS(BLOCK_PROP_LADDER));

    public static final RegistryObject<Block> SPAWN_BOSS = registerWithItem("spawn_boss", () -> new BlockSpawnBoss(BLOCK_PROP_MJDS));
    public static final RegistryObject<Block> SPAWN_BULLET = registerWithItem("spawn_bullet", () -> new BlockSpawnBullet(BLOCK_PROP_MJDS));

    public static final RegistryObject<Block> BLOCK_MOTOR_X = registerWithItem("motor_x", () -> new BlockMotorX(BLOCK_PROP_MJDS));

    public static final RegistryObject<Block> BLOCK_MOTOR_Y = registerWithItem("motor_y", () -> new BlockMotorY(BLOCK_PROP_MJDS));
    public static final RegistryObject<Block> BLOCK_MOTOR_Z = registerWithItem("motor_z", () -> new BlockMotorZ(BLOCK_PROP_MJDS));

    public static final RegistryObject<Block> BOX_FEATHER = registerWithItem("box_1", () -> new BlockAchvBox(null, AdvancementUtil.FEATHER));
    public static final RegistryObject<Block> BOX_SHOES = registerWithItem("box_2", () -> new BlockAchvBox(null, AdvancementUtil.SHOES));
    public static final RegistryObject<Block> BOX_FOOD = registerWithItem("box_3", () -> new BlockAchvBox(null, AdvancementUtil.FOOD));
    public static final RegistryObject<Block> BOX_SHIELD_BRONZE = registerWithItem("box_4", () -> new BlockAchvBox(null, AdvancementUtil.SHIELD_BRONZE));
    public static final RegistryObject<Block> BOX_GREAT_KEY = registerWithItem("box_5", () -> new BlockAchvBox(null, AdvancementUtil.GREAT_KEY));
    public static final RegistryObject<Block> BOX_CANDLE = registerWithItem("box_6", () -> new BlockAchvBox(null, AdvancementUtil.CANDLE));
    public static final RegistryObject<Block> BOX_HOLY_WATER = registerWithItem("box_7", () -> new BlockAchvBox(null, AdvancementUtil.HOLY_WATER));
    public static final RegistryObject<Block> BOX_BALLOON = registerWithItem("box_8", () -> new BlockAchvBox(null, AdvancementUtil.BALLOON));
    public static final RegistryObject<Block> BOX_TRIANGLE = registerWithItem("box_9", () -> new BlockAchvBox(null, AdvancementUtil.TRIANGLE));



//    public static final RegistryObject<StructureFeature<JigsawConfiguration>> PORTAL = Registration.STRUCTURES.register("portal", PortalStructure::new);
//
//    public static final RegistryObject<PowergenBlock> POWERGEN = BLOCKS.register("powergen", PowergenBlock::new);
    public static final RegistryObject<BlockEntityType<TileEntitySpawnBoss>> TE_SPAWN_BOSS = BLOCK_ENTITIES.register("te_spawnboss", () -> BlockEntityType.Builder.of(TileEntitySpawnBoss::new, SPAWN_BOSS.get()).build(null));
    public static final RegistryObject<BlockEntityType<TileEntitySpawnBullet>> TE_SPAWN_BULLET = BLOCK_ENTITIES.register("te_spawn_bullet", () -> BlockEntityType.Builder.of(TileEntitySpawnBullet::new, SPAWN_BULLET.get()).build(null));
    public static final RegistryObject<BlockEntityType<MotorTileEntityHorizontal>> TE_MOTOR_H = BLOCK_ENTITIES.register("te_motor_h", () -> BlockEntityType.Builder.of(MotorTileEntityHorizontal::new, BLOCK_MOTOR_X.get()).build(null));
    public static final RegistryObject<BlockEntityType<MotorTileEntityVertical>> TE_MOTOR_V = BLOCK_ENTITIES.register("te_motor_v", () -> BlockEntityType.Builder.of(MotorTileEntityVertical::new, BLOCK_MOTOR_Y.get()).build(null));
    public static final RegistryObject<BlockEntityType<MotorTileEntityZDir>> TE_MOTOR_Z = BLOCK_ENTITIES.register("te_motor_z", () -> BlockEntityType.Builder.of(MotorTileEntityZDir::new, BLOCK_MOTOR_Z.get()).build(null));
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
