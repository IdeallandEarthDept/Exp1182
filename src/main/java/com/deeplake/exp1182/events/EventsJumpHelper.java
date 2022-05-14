package com.deeplake.exp1182.events;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.util.DesignUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class EventsJumpHelper {

    @SubscribeEvent
    public static void onFall(LivingFallEvent event)
    {
        Level world = event.getEntity().level;
        if (!world.isClientSide)
        {
            LivingEntity livingEntity = event.getEntityLiving();

//            if (livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof EgoArmor)
//            {
//                //Popolon and Artemis boots prevents fall damage, even outside of MJDS
//                event.setDamageMultiplier(0f);
//                return;
//            }

            //takes no damage if near MJDS
            BlockPos[] posDeltaList = {
                    BlockPos.ZERO.east(),
                    BlockPos.ZERO.west(),
                    BlockPos.ZERO.south(),
                    BlockPos.ZERO.north(),
                    BlockPos.ZERO.east().north(),
                    BlockPos.ZERO.east().south(),
                    BlockPos.ZERO.west().north(),
                    BlockPos.ZERO.west().south(),
            };

            for (BlockPos pos: posDeltaList) {
                if (DesignUtil.isWithOffsetMJDS(world, pos, livingEntity))
                {
                    event.setDamageMultiplier(0f);
                    return;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event)
    {
        Level world = event.getEntity().level;
        if (world.isClientSide)//Yep. Client for players
        {
            LivingEntity livingEntity = event.getEntityLiving();
            if (!livingEntity.isOnGround())
            {
                return;
            }

            float jumpFactorMax = world.getBlockState(livingEntity.blockPosition()).getBlock().getJumpFactor();
            float jumpFactorMaxInit = jumpFactorMax;

            BlockPos[] posDeltaList = {
                    BlockPos.ZERO,
                    BlockPos.ZERO.east(),
                    BlockPos.ZERO.west(),
                    BlockPos.ZERO.south(),
                    BlockPos.ZERO.north(),
                    BlockPos.ZERO.east().north(),
                    BlockPos.ZERO.east().south(),
                    BlockPos.ZERO.west().north(),
                    BlockPos.ZERO.west().south(),
            };

            for (BlockPos pos: posDeltaList) {
                float max = getFactor(world, pos, livingEntity);
                if (max > jumpFactorMax)
                {
                    jumpFactorMax = max;
                }
            }

            //jumpFactorMax -= jumpFactorMaxInit;

//            if (livingEntity instanceof PlayerEntity)
//            {
//                Idealland.Log("d-jumpFactorMax = %s", jumpFactorMax);
//            }

            jumpFactorMax *= 0.42f;//const

            if (livingEntity.hasEffect(MobEffects.JUMP)) {
                jumpFactorMax += 0.1F * (float)(livingEntity.getEffect(MobEffects.JUMP).getAmplifier() + 1);
            }

            Vec3 v = livingEntity.getDeltaMovement();
            livingEntity.setDeltaMovement(v.x, jumpFactorMax, v.z);
        }
    }

    //copied from net.minecraft.entity.Entity
    public static BlockPos getBlockPosBelowThatAffectsMyMovement(Entity entity) {
        return new BlockPos(entity.position().x, entity.getBoundingBox().minY - 0.5000001D, entity.position().z);
    }

    static float getFactor(Level world, BlockPos pos, Entity entity)
    {
        float f = world.getBlockState(entity.blockPosition().offset(pos)).getBlock().getJumpFactor();
        float f1 = world.getBlockState(getBlockPosBelowThatAffectsMyMovement(entity).offset(pos)).getBlock().getJumpFactor();
        return f > f1 ? f : f1;
    }
}

