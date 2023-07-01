package com.deeplake.exp1182.blocks;

import net.minecraft.world.level.block.AbstractGlassBlock;


public class BlockWallGlass extends AbstractGlassBlock implements IBlockMJDS{
    public BlockWallGlass() {
        super(Properties.of()
                .strength(-1.0F, 3600000.0F)
                .noOcclusion()
                .isViewBlocking(BaseBlockMJDS::neverDo)
                .isValidSpawn(BaseBlockMJDS::neverDoET)
                .isRedstoneConductor(BaseBlockMJDS::neverDo)
                .isSuffocating(BaseBlockMJDS::neverDo)
        );
    }
}
