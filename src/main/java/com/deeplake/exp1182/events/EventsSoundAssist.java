package com.deeplake.exp1182.events;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.setup.ModEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class EventsSoundAssist {

    @SubscribeEvent
    public static void onPickup(PlayerEvent.ItemPickupEvent event) {
        Level world = event.getEntity().level;
        Player player = event.getPlayer();
        if (world.isClientSide) {
            boolean samePerson = event.getOriginalEntity().getOwner() == player.getUUID();

            if (player.hasEffect(ModEffects.INSIDE_MAJOU.get()) && !samePerson)
            {
                player.playSound(ModSounds.PICKUP.get(), 1f, 1f);
            }

        }
    }
}
