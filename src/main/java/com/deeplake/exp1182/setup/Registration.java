package com.deeplake.exp1182.setup;

import com.deeplake.exp1182.client.ModSounds;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Registration {

//    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Main.MOD_ID);
//    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Main.MOD_ID);

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);
        ModBlocks.BLOCK_ENTITIES.register(bus);
//        CONTAINERS.register(bus);
        ModEntities.ENTITIES.register(bus);
        bind(Registries.SOUND_EVENT, ModSounds::init);

        ModEffects.EFFECTS.register(bus);
//        STRUCTURES.register(bus);
    }


//    public static final RegistryObject<StructureFeature<JigsawConfiguration>> THIEFDEN = STRUCTURES.register("thiefden", ThiefDenStructure::new);
//    public static final ResourceLocation RL_MYSTERIOUS_DIMENSION_SET = new ResourceLocation(Main.MOD_ID, "mysterious_dimension_structure_set");

//    public static final TagKey<Biome> HAS_PORTAL = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Main.MOD_ID, "has_structure/portal"));
//    public static final TagKey<Biome> HAS_THIEFDEN = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Main.MOD_ID, "has_structure/thiefden"));
//    public static final TagKey<StructureSet> MYSTERIOUS_DIMENSION_STRUCTURE_SET = TagKey.create(Registry.STRUCTURE_SET_REGISTRY, RL_MYSTERIOUS_DIMENSION_SET);
    private static <T> void bind(ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        FMLJavaModLoadingContext.get().getModEventBus().addListener((RegisterEvent event) -> {
            if (registry.equals(event.getRegistryKey())) {
                source.accept((t, rl) -> event.register(registry, rl, () -> t));
            }
        });
    }
}
