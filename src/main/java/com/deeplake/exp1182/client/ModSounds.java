package com.deeplake.exp1182.client;

import com.deeplake.exp1182.Main;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class ModSounds {
//    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Main.MOD_ID);
    private static final List<SoundEvent> EVENTS = new ArrayList<>();
       public static final SoundEvent JUMP = makeSoundEvent("jump");
    public static final SoundEvent MONSTER_HURT = makeSoundEvent("monster_hurt");
    public static final SoundEvent MONSTER_DEATH = makeSoundEvent("monster_death");
    public static final SoundEvent MONSTER_SHOOT_1 = makeSoundEvent("monster_shoot_1");
    public static final SoundEvent MONSTER_SHOOT_2 = makeSoundEvent("monster_shoot_2");
    public static final SoundEvent GAME_SELECT = makeSoundEvent("game_select");
    public static final SoundEvent PICKUP = makeSoundEvent("obtain_drop");
    public static final SoundEvent FALL = makeSoundEvent("fall");
    public static final SoundEvent CUT = makeSoundEvent("cut");
    public static final SoundEvent PAUSE = makeSoundEvent("pause");
    public static final SoundEvent ERROR = makeSoundEvent("error");
    public static final SoundEvent DOOR_COMBINED = makeSoundEvent("door_combined");
    public static final SoundEvent LOW_HEALTH = makeSoundEvent("low_health");
    public static final SoundEvent PLAYER_HURT = makeSoundEvent("player_hurt");
    public static final SoundEvent ENTER_DOOR = makeSoundEvent("enter_door");
    public static final SoundEvent PLAYER_DEATH = makeSoundEvent("player_death");
    public static final SoundEvent IMMUNE_1 = makeSoundEvent("immune1");
    public static final SoundEvent DEFLECT = makeSoundEvent("deflect");

    public static final SoundEvent SOUND_MUSIC_DUNGEON = makeSoundEvent("music_dungeon");
//    public static final Holder.Reference<SoundEvent> SOUND_MUSIC_DUNGEON = registerForHolder(new ResourceLocation(Main.MOD_ID,"music_dungeon"));
    public static final RegistryObject<net.minecraftforge.client.event.sound.SoundEvent> TEST = null;

    public static Music MUSIC_DUNGEON;
    public static ForgeSoundType MJDS_BLOCKS =
            new ForgeSoundType(1.0F, 1.0F,
                    ()-> MONSTER_DEATH,
                    ()-> SoundEvents.NETHER_BRICKS_STEP,
                    ()-> CUT,
                    ()-> CUT,
                    ()-> FALL);
//    public Music(SoundEvent p_11627_, int p_11628_, int p_11629_, boolean p_11630_) {
//        this.event = p_11627_;
//        this.minDelay = p_11628_;
//        this.maxDelay = p_11629_;
//        this.replaceCurrentMusic = p_11630_;
//    }
//    private static SoundEvent makeSoundEvent(String name) {
//        return SOUNDS.makeSoundEvent(name, () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, name)));
//    }

    public static void initMusic()
    {
//        MUSIC_DUNGEON = new Music(SOUND_MUSIC_DUNGEON, 1, 1, true);
    }

    private static SoundEvent makeSoundEvent(String name) {
        SoundEvent event = SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MOD_ID,name));
        EVENTS.add(event);
        return event;
    }

    public static void init(BiConsumer<SoundEvent, ResourceLocation> r) {
        for (SoundEvent event : EVENTS) {
            r.accept(event, event.getLocation());
        }
    }

    public static Holder.Reference<SoundEvent> registerForHolder(ResourceLocation p_263362_) {
        return registerForHolder(p_263362_, p_263362_);
    }

    public static Holder.Reference<SoundEvent> registerForHolder(ResourceLocation p_263362_, ResourceLocation p_263424_) {
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, p_263362_,
                SoundEvent.createVariableRangeEvent(p_263424_));
    }
}
