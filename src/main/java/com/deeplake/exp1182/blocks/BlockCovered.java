package com.deeplake.exp1182.blocks;

import com.deeplake.exp1182.setup.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

public class BlockCovered extends BlockBreakable  {
    public RegistryObject<Block> aftermath = ModBlocks.FLAME_FLOOR;

    public BlockCovered(RegistryObject<Block> aftermath) {
        super(ModBlocks.BLOCK_PROP_MJDS);
        this.aftermath = aftermath;
    }

    public BlockCovered() {
        super(ModBlocks.BLOCK_PROP_MJDS);
    }

    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        super.destroy(levelAccessor, blockPos, blockState);
        levelAccessor.setBlock(blockPos, aftermath.get().defaultBlockState(), 0);
    }
}
