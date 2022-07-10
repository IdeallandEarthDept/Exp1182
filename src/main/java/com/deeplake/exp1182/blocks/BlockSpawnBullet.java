package com.deeplake.exp1182.blocks;

import com.deeplake.exp1182.blocks.tileentity.TileEntitySpawnBullet;
import com.deeplake.exp1182.blocks.tileentity.TileEntitySpawnBoss;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockSpawnBullet extends BaseBlockMJDS implements EntityBlock {
    public BlockSpawnBullet(Properties p_49795_) {
        super(p_49795_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntitySpawnBullet(pos, state);
    }


    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }
        return (lvl, pos, blockState, t) -> {
            if (t instanceof TileEntitySpawnBullet tile) {
                tile.tickServer();
            }
        };
    }
}
