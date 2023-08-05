package com.deeplake.exp1182.events;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.blocks.IBlockMJDS;
import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.setup.ModEffects;
import com.deeplake.exp1182.util.AdvancementUtil;
import com.deeplake.exp1182.util.CommonDef;
import com.deeplake.exp1182.util.DesignUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.deeplake.exp1182.util.DesignUtil.applyMajou;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class EventsJumpHelper {
    static BlockPos DOWN = new BlockPos(0,-1,0);
    static BlockPos[] posDeltaList = {
            BlockPos.ZERO,
            BlockPos.ZERO.east(),
            BlockPos.ZERO.west(),
            BlockPos.ZERO.south(),
            BlockPos.ZERO.north(),
            BlockPos.ZERO.east().north(),
            BlockPos.ZERO.east().south(),
            BlockPos.ZERO.west().north(),
            BlockPos.ZERO.west().south(),
            DOWN,
            DOWN.east(),
            DOWN.west(),
            DOWN.south(),
            DOWN.north(),
            DOWN.east().north(),
            DOWN.east().south(),
            DOWN.west().north(),
            DOWN.west().south(),
    };

    @SubscribeEvent
    public static void onFall(LivingFallEvent event)
    {
        Level world = event.getEntity().level();
        LivingEntity livingEntity = event.getEntity();

        //takes no damage if near MJDS
        for (BlockPos pos: posDeltaList) {
            if (DesignUtil.isWithOffsetMJDS(world, pos, livingEntity))
            {
                applyMajou(livingEntity);
                if (!world.isClientSide) {
                    boolean nullify = true;
                    if (livingEntity instanceof Player player)
                    {
                        MobEffectInstance effect = player.getEffect(ModEffects.INSIDE_MAJOU.get());
                        if (effect != null)
                        {
                            int amplifier = effect.getAmplifier();
                            nullify = AdvancementUtil.hasAdvancement(player, AdvancementUtil.FEATHER) ||
                                    ((amplifier & AdvancementUtil.FEATHER_BIT) > 0);
                        }
                    }

                    if (nullify)
                    {
                        event.setDamageMultiplier(0f);
                    }
                }
                else
                {
                    if (livingEntity instanceof Player)
                    {
                        livingEntity.playSound(ModSounds.FALL, 1f, 1f);
                    }
                }
                return;
            }
        }
    }

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event)
    {
        Level world = event.getEntity().level();
        LivingEntity livingEntity = event.getEntity();
        if (world.isClientSide)//Yep. Client for players
        {

            if (!livingEntity.onGround())
            {
                return;
            }

            boolean applyJump = true;

            if (livingEntity instanceof Player player)
            {
                applyJump = AdvancementUtil.checkAdvClient(player, AdvancementUtil.SHOES_BIT);
            }

            if (applyJump)
            {
                float jumpFactorMax = getFactor(world, BlockPos.ZERO, livingEntity);

                for (BlockPos pos: posDeltaList) {
                    float max = getFactor(world, pos, livingEntity);
                    if (max > jumpFactorMax)
                    {
                        jumpFactorMax = max;
                    }
                }

                jumpFactorMax *= 0.42f;//const

                if (livingEntity.hasEffect(MobEffects.JUMP)) {
                    jumpFactorMax += 0.1F * (float)(livingEntity.getEffect(MobEffects.JUMP).getAmplifier() + 1);
                }

                Vec3 v = livingEntity.getDeltaMovement();
                livingEntity.setDeltaMovement(v.x, jumpFactorMax, v.z);

                if (livingEntity instanceof Player player)
                {
                    player.playSound(ModSounds.JUMP, 4f, 1f);
                }
            }
        }
    }

    //copied from net.minecraft.entity.Entity
    public static BlockPos getBlockPosBelowThatAffectsMyMovement(Entity entity) {
        return new BlockPos((int) entity.position().x, (int) (entity.getBoundingBox().minY - 0.5000001D), (int) entity.position().z);
    }

    static float getFactor(Level world, BlockPos pos, Entity entity)
    {
        Block block = world.getBlockState(entity.blockPosition().offset(pos)).getBlock();
        Block block1 = world.getBlockState(getBlockPosBelowThatAffectsMyMovement(entity).offset(pos)).getBlock();
        if (block instanceof IBlockMJDS || block1 instanceof IBlockMJDS)
        {
            return CommonDef.JUMP_FACTOR_MJDS_RATIO;
        }

        float f = block.getJumpFactor();
        float f1 = block1.getJumpFactor();
        return f > f1 ? f : f1;
    }
}

