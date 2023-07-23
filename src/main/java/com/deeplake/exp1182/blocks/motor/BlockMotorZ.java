package com.deeplake.exp1182.blocks.motor;

import com.deeplake.exp1182.blocks.tileentity.MotorTileEntityZDir;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockMotorZ extends BaseBlockMotor{
    public BlockMotorZ(Properties p_49795_) {
        super(p_49795_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MotorTileEntityZDir(pos, state);
    }
}
