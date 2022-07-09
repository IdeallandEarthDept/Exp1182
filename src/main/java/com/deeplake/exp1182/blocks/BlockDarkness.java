package com.deeplake.exp1182.blocks;

import com.deeplake.exp1182.util.AdvancementUtil;
import com.deeplake.exp1182.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class BlockDarkness extends BaseBlockMJDS{
    public BlockDarkness(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void animateTick(BlockState p_49888_, Level level, BlockPos pos, Random random) {
        super.animateTick(p_49888_, level, pos, random);
        if (random.nextInt() % 100 == 0)
        {
            level.addParticle(ParticleTypes.ASH, pos.getX(), pos.getY(), pos.getZ(), 0,0,0);
        }
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        super.entityInside(state, world, pos, entity);
        if (!world.isClientSide && entity instanceof Player player)
        {
            if (!AdvancementUtil.hasAdvancement(player, AdvancementUtil.CANDLE))
            {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,30, 0, true, false, true, null));
            }
        }
    }

    public VoxelShape getVisualShape(BlockState p_48735_, BlockGetter p_48736_, BlockPos p_48737_, CollisionContext p_48738_) {
        return Shapes.empty();
    }

    public float getShadeBrightness(BlockState p_48731_, BlockGetter p_48732_, BlockPos p_48733_) {
        return 1.0F;
    }

    public boolean propagatesSkylightDown(BlockState p_48740_, BlockGetter p_48741_, BlockPos p_48742_) {
        return true;
    }

    public boolean skipRendering(BlockState p_53972_, BlockState p_53973_, Direction p_53974_) {
        return p_53973_.is(this) ? true : super.skipRendering(p_53972_, p_53973_, p_53974_);
    }
}
