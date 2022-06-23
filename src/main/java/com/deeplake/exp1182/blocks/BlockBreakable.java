package com.deeplake.exp1182.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class BlockBreakable extends BaseBlockMJDS implements IBlockMJDS{
    public BlockBreakable(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState p_49862_) {
        super.destroy(levelAccessor, blockPos, p_49862_);
        levelAccessor.addParticle(ParticleTypes.EXPLOSION,
                blockPos.getX(),blockPos.getY(),blockPos.getZ(),
                0,0,0);
    }
}
