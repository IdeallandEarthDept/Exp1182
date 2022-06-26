package com.deeplake.exp1182.blocks;

import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Material;

public class BlockWallGlass extends AbstractGlassBlock implements IBlockMJDS{
    public BlockWallGlass() {
        super(Properties.of(Material.GLASS)
                .strength(-1.0F, 3600000.0F)
                .noOcclusion()
                .isViewBlocking(BaseBlockMJDS::neverDo)
                .isValidSpawn(BaseBlockMJDS::neverDo)
                .isRedstoneConductor(BaseBlockMJDS::neverDo)
                .isSuffocating(BaseBlockMJDS::neverDo)
        );
    }
}
