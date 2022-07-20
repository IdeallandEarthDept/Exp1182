package com.deeplake.exp1182.blocks;

import com.deeplake.exp1182.util.DesignUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BaseBlockNoAdv extends BaseBlockMJDS implements IBlockMJDS {
    public BaseBlockNoAdv(Properties p_49795_) {
        super(p_49795_);
    }

    //MalayP requested a version that keeps the jumping and fall protection, but skips the advancement checks.

    @Override
    public void stepOn(Level level, BlockPos p_152432_, BlockState p_152433_, Entity entity) {
//        super.stepOn(level, p_152432_, p_152433_, entity);
        DesignUtil.applyMajouNoAdv(entity);
    }
}
