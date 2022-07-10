package com.deeplake.exp1182.events;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.entities.mjds.projectiles.EntityMJDSBulletBase;
import com.deeplake.exp1182.setup.ModEffects;
import com.deeplake.exp1182.util.AdvancementUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class EventsBulletHit {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onImpact(ProjectileImpactEvent event)
    {
        if (event.getRayTraceResult() instanceof EntityHitResult result && !event.isCanceled())
        {
            if (result.getEntity() instanceof Player player)
            {
                boolean hasAdv = AdvancementUtil.hasAdvancement(player, AdvancementUtil.SHIELD_BRONZE);
                if (hasAdv)
                {
                    Projectile projectile = event.getProjectile();
                    boolean isBlocking = player.isBlocking();

                    boolean isSPBullet = projectile instanceof EntityMJDSBulletBase;
                    boolean isInMajou = player.hasEffect(ModEffects.INSIDE_MAJOU.get());
                    boolean notAttacking = player.getAttackStrengthScale(0f) > 0.99f;
                    if (notAttacking && (isSPBullet || isInMajou))
                    {
                        projectile.setDeltaMovement(projectile.getDeltaMovement().scale(-1f));
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
