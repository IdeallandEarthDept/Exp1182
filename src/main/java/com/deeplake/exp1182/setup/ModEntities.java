package com.deeplake.exp1182.setup;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.entities.EntityWorldBoss;
import com.deeplake.exp1182.entities.mjds.EntityMJDSSkeleton;
import com.deeplake.exp1182.entities.mjds.EntityRevivalMist;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MOD_ID);

    //    public static final RegistryObject<EntityType<ThiefEntity>> THIEF = ENTITIES.register("thief", () -> EntityType.Builder.of(ThiefEntity::new, MobCategory.CREATURE)
//            .sized(0.6f, 1.95f)
//            .clientTrackingRange(8)
//            .setShouldReceiveVelocityUpdates(false)
//            .build("thief"));

        public static final RegistryObject<EntityType<EntityWorldBoss>> WORLD_BOSS = ENTITIES.register("world_boss", () -> EntityType.Builder.of(EntityWorldBoss::new, MobCategory.MONSTER)
            .sized(0.6f, 1.95f)
            .clientTrackingRange(8)
            .setShouldReceiveVelocityUpdates(false)
            .build("world_boss"));

        static final String NAME_REVIVE_MIST = "revival_mist";
        public static final RegistryObject<EntityType<EntityRevivalMist>> REVIVE_MIST = ENTITIES.register(NAME_REVIVE_MIST, () -> EntityType.Builder.of(EntityRevivalMist::new, MobCategory.MISC)
            .sized(1f, 1f)
            .clientTrackingRange(8)
            .setShouldReceiveVelocityUpdates(false)
            .build(NAME_REVIVE_MIST));

    static final String NAME_SKELETON = "mjds_skeleton";
    public static final RegistryObject<EntityType<EntityMJDSSkeleton>> MJDS_SKELETON = ENTITIES.register(NAME_SKELETON, () -> EntityType.Builder.of(EntityMJDSSkeleton::new, MobCategory.MISC)
            .sized(1f, 1f)
            .clientTrackingRange(8)
            .setShouldReceiveVelocityUpdates(false)
            .build(NAME_SKELETON));
    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(ModEntities.WORLD_BOSS.get(), EntityWorldBoss.prepareAttributes().build());
//        event.put(ModEntities.WORLD_BOSS.get(), EntityWorldBoss.prepareAttributes().build());
        event.put(ModEntities.MJDS_SKELETON.get(), EntityWorldBoss.prepareAttributes().build());
    }

}
