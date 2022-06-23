package com.deeplake.exp1182.blocks.motor;

import com.deeplake.exp1182.blocks.BaseBlockMJDS;
import com.deeplake.exp1182.blocks.IBlockMJDS;
import com.deeplake.exp1182.blocks.tileentity.MotorBlockEntityBase;
import com.deeplake.exp1182.blocks.tileentity.TileEntitySpawnBoss;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BaseBlockMotor extends BaseBlockMJDS implements EntityBlock, IBlockMJDS {

    public BaseBlockMotor(Properties p_49795_) {
        super(p_49795_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }
        return (lvl, pos, blockState, t) -> {
            if (t instanceof MotorBlockEntityBase tile) {
                MotorBlockEntityBase.serverTick(tile);
            }
        };
    }
}
