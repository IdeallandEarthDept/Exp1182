package com.deeplake.exp1182.blocks.tileentity;


import com.deeplake.exp1182.setup.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.state.BlockState;

public class MotorTileEntityZDir extends MotorBlockEntityBase {
    public MotorTileEntityZDir(BlockPos pos, BlockState state) {
        super(ModBlocks.TE_MOTOR_Z.get(), pos, state);
    }

    public Vec3i getOffset()
    {
        if (isPositiveDirection)
        {
            return new Vec3i(0, 0, 1);
        }
        else {
            return new Vec3i(0, 0, -1);
        }
    }
}
