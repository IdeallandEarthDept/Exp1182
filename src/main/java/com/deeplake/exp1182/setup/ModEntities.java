package com.deeplake.exp1182.setup;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.entities.EntityWorldBoss;
import com.deeplake.exp1182.entities.mjds.*;
import com.deeplake.exp1182.entities.mjds.projectiles.EntityMJDSBulletDeflect;
import com.deeplake.exp1182.entities.mjds.projectiles.EntityMJDSBulletPierece;
import com.deeplake.exp1182.entities.mjds.projectiles.EntityMJDSBulletShower;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MOD_ID);

    public static final String NAME_WORLD_BOSS = "world_boss";
    public static final RegistryObject<EntityType<EntityWorldBoss>> WORLD_BOSS =
            getEntityTypeRegistryObject(NAME_WORLD_BOSS, EntityWorldBoss::new, 0.6f, 1.95f, MobCategory.MONSTER);

        static final String NAME_REVIVE_MIST = "revival_mist";
        public static final RegistryObject<EntityType<EntityRevivalMist>> REVIVE_MIST =
                getEntityTypeRegistryObject(NAME_REVIVE_MIST, EntityRevivalMist::new, 0.6f, 1.8f, MobCategory.MISC);

    static final String NAME_SKELETON = "mjds_skeleton";
    public static final RegistryObject<EntityType<EntityMJDSSkeleton>> MJDS_SKELETON =
             getEntityTypeRegistryObject(NAME_SKELETON, EntityMJDSSkeleton::new, 0.6f, 1.8f, MobCategory.MONSTER);

    static final String NAME_BLAZE = "mjds_blaze";
    public static final RegistryObject<EntityType<EntityMJDSBlaze>> MJDS_BLAZE = getEntityTypeRegistryObjectFireImmune(NAME_BLAZE, EntityMJDSBlaze::new, 0.6f, 1.8f, MobCategory.MONSTER);

    static final String NAME_BULLET1 = "bullet1";
    static final String NAME_BULLET2 = "bullet2";
    static final String NAME_BULLET3 = "bullet3";
    public static final RegistryObject<EntityType<EntityMJDSBulletPierece>> BULLET1 = getEntityTypeRegistryObjectFireImmune(NAME_BULLET1, EntityMJDSBulletPierece::new, 1.0f, 1.0f, MobCategory.MISC);
    public static final RegistryObject<EntityType<EntityMJDSBulletDeflect>> BULLET2 = getEntityTypeRegistryObjectFireImmune(NAME_BULLET2, EntityMJDSBulletDeflect::new, 1.0f, 1.0f, MobCategory.MISC);
    public static final RegistryObject<EntityType<EntityMJDSBulletShower>> BULLET_SHOWER = getEntityTypeRegistryObjectFireImmune(NAME_BULLET3, EntityMJDSBulletShower::new, 1.0f, 1.0f, MobCategory.MISC);
    public static final RegistryObject<EntityType<EntityMJDSCloudMonster>> CLOUD_MONSTER = getEntityTypeRegistryObjectFireImmune("cloud_mob", EntityMJDSCloudMonster::new, 0.6f, 1.8f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<EntityMJDSStoneEmitter>> SHOWER_MONSTER = getEntityTypeRegistryObjectFireImmune("shower_mob", EntityMJDSStoneEmitter::new, 1.0f, 1.0f, MobCategory.MONSTER);


    private static <T extends Entity> RegistryObject<EntityType<T>> getEntityTypeRegistryObject(String name, EntityType.EntityFactory<T> factory, float sizeXZ, float sizeY, MobCategory category) {
        return ENTITIES.register(name, () -> getBuilder(factory, sizeXZ, sizeY, category)
                .build(name));
    }

    @NotNull
    private static <T extends Entity> EntityType.Builder<T> getBuilder(EntityType.EntityFactory<T> factory, float sizeXZ, float sizeY, MobCategory category) {
        return EntityType.Builder.of(factory, category)
                .sized(sizeXZ, sizeY)
                .clientTrackingRange(8)
                .setShouldReceiveVelocityUpdates(false);
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> getEntityTypeRegistryObjectFireImmune(String name, EntityType.EntityFactory<T> factory, float sizeXZ, float sizeY, MobCategory category) {
        return ENTITIES.register(name, () -> getBuilder(factory, sizeXZ, sizeY, category).fireImmune()
                .build(name));
    }



    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(ModEntities.WORLD_BOSS.get(), EntityWorldBoss.prepareAttributes().build());
//        event.put(ModEntities.WORLD_BOSS.get(), EntityWorldBoss.prepareAttributes().build());
        event.put(ModEntities.MJDS_SKELETON.get(), EntityMJDSSkeleton.prepareAttributes().build());
        event.put(ModEntities.MJDS_BLAZE.get(), EntityMJDSBlaze.prepareAttributes().build());
        event.put(ModEntities.CLOUD_MONSTER.get(), EntityMJDSCloudMonster.prepareAttributes().build());
        event.put(ModEntities.SHOWER_MONSTER.get(), EntityMJDSStoneEmitter.prepareAttributes().build());
    }

}
