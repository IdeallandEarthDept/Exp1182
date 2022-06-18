package com.deeplake.exp1182.client;

import com.deeplake.exp1182.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Main.MOD_ID);

    public static final RegistryObject<SoundEvent> SOUND_MUSIC_DUNGEON = register("music_dungeon");
    public static final RegistryObject<SoundEvent> JUMP = register("jump");
    public static final RegistryObject<SoundEvent> MONSTER_HURT = register("monster_hurt");
    public static final RegistryObject<SoundEvent> MONSTER_DEATH = register("monster_death");
    public static final RegistryObject<SoundEvent> MONSTER_SHOOT_1 = register("monster_shoot_1");
    public static final RegistryObject<SoundEvent> GAME_SELECT = register("game_select");
    public static final RegistryObject<SoundEvent> PICKUP = register("obtain_drop");
    public static final RegistryObject<SoundEvent> FALL = register("fall");
    public static final RegistryObject<SoundEvent> CUT = register("cut");

    public static Music MUSIC_DUNGEON;
    public static ForgeSoundType MJDS_BLOCKS =
            new ForgeSoundType(1.0F, 1.0F,
                    MONSTER_DEATH,SoundEvents.NETHER_BRICKS_STEP.delegate,CUT,CUT,FALL);
//    public Music(SoundEvent p_11627_, int p_11628_, int p_11629_, boolean p_11630_) {
//        this.event = p_11627_;
//        this.minDelay = p_11628_;
//        this.maxDelay = p_11629_;
//        this.replaceCurrentMusic = p_11630_;
//    }
    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, name)));
    }

    public static void initMusic()
    {
        MUSIC_DUNGEON = new Music(SOUND_MUSIC_DUNGEON.get(), 1, 1, true);
    }

}
