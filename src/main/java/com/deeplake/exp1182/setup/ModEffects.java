package com.deeplake.exp1182.setup;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.effects.BaseEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Main.MOD_ID);

//    public static final RegistryObject<MobEffect> B_PURE_WATER = register("pure_water", MobEffectCategory.BENEFICIAL, 0xcccccc);
    public static final RegistryObject<MobEffect> INSIDE_MAJOU = register("inside_majou", MobEffectCategory.NEUTRAL, 0xcccccc);

    private static RegistryObject<MobEffect> register(String name, MobEffectCategory category, int color) {
        return EFFECTS.register(name, () -> new BaseEffect(category, color));
    }
}
