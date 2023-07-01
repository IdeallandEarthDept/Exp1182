package com.deeplake.exp1182.events;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.setup.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class EventsSoundAssist {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDeath(LivingDeathEvent event)
    {
        Level world = event.getEntity().level();
        if (!event.isCanceled())
        {
            if (event.getEntity() instanceof Player player)
            {
                if (player.hasEffect(ModEffects.INSIDE_MAJOU.get()))
                {
                    SoundEvent soundEvent = ModSounds.PLAYER_DEATH;
                    world.playSound(null, player.getOnPos(), soundEvent, SoundSource.PLAYERS,1f, 1f);
                }
            }
        }

        //this even won't fire on client side.
        if (world.isClientSide)
        {
            MusicManager musicManager = Minecraft.getInstance().getMusicManager();
            if (musicManager.isPlayingMusic(ModSounds.MUSIC_DUNGEON)) {
                musicManager.stopPlaying();
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onHurt(LivingHurtEvent event)
    {
        Level world = event.getEntity().level();
        if (!event.isCanceled())
        {
            if (event.getEntity() instanceof Player player)
            {
                if (player.hasEffect(ModEffects.INSIDE_MAJOU.get()) && player.getHealth() > event.getAmount())
                {
                    SoundEvent soundEvent = player.getHealth() / player.getMaxHealth() > 0.33f ?
                            ModSounds.PLAYER_HURT:
                            ModSounds.LOW_HEALTH;
                    world.playSound(null, player.getOnPos(), soundEvent, SoundSource.PLAYERS,1f, 1f);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPickup(PlayerEvent.ItemPickupEvent event) {
        Level world = event.getEntity().level();
        Player player = event.getEntity();
        if (!event.isCanceled()) {
            Entity owner = event.getOriginalEntity().getOwner();
            if (owner == null)
            {
                return;
            }
            boolean samePerson = owner.getUUID() == player.getUUID();

            if (player.hasEffect(ModEffects.INSIDE_MAJOU.get()) && !samePerson)
            {
                world.playSound(null, player.getOnPos(), ModSounds.PICKUP, SoundSource.PLAYERS,1f, 1f);
            }
        }
    }
}
