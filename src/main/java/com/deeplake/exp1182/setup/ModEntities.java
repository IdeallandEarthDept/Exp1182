package com.deeplake.exp1182.setup;

import com.deeplake.exp1182.Main;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MOD_ID);

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
//        event.put(ModEntities.THIEF.get(), ThiefEntity.prepareAttributes().build());
    }
//    public static final RegistryObject<EntityType<ThiefEntity>> THIEF = ENTITIES.register("thief", () -> EntityType.Builder.of(ThiefEntity::new, MobCategory.CREATURE)
//            .sized(0.6f, 1.95f)
//            .clientTrackingRange(8)
//            .setShouldReceiveVelocityUpdates(false)
//            .build("thief"));
}
