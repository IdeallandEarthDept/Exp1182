package com.deeplake.exp1182.blocks.tileentity;

import com.deeplake.exp1182.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import static com.deeplake.exp1182.util.CommonDef.TICK_PER_SECOND;

public class MotorBlockEntityBase extends BlockEntity {

    public MotorBlockEntityBase(BlockEntityType<?> p_154991_, BlockPos pos, BlockState state) {

        super(p_154991_, pos, state);
    }

    static final int MAX_DETECT = 3;
    static final int TICK_PER_MOVE = TICK_PER_SECOND;//todo: need check speed

    public boolean isPositiveDirection = true;

    public boolean isMovable(BlockPos pos)
    {
        if (level == null)
        {
            return false;
        }

        return pos.getY() < level.getMaxBuildHeight() && pos.getY() >= level.getMinBuildHeight() && level.getBlockState(pos).isAir();
    }

    public static void serverTick(Level p_155762_, BlockPos p_155763_, BlockState p_155764_, MotorBlockEntityBase p_155765_) {
        serverTick(p_155765_);
    }

    public static void serverTick(MotorBlockEntityBase te) {
        Level level = te.level;
        if (level != null && !level.isClientSide &&
                (level.getGameTime() % TICK_PER_MOVE == 0)) {
            //detect
            boolean isFree = true;
            BlockPos posPointer = te.getBlockPos();
            for (int i = 1; i <= MAX_DETECT; i++)
            {
                posPointer= posPointer.offset(te.getOffset());
                if (te.isMovable(posPointer))
                {
                    continue;
                }
                else {
                    //IdlFramework.Log("Failed : @%s, is %s", posPointer, level.getBlockState(posPointer));
                    isFree = false;
                    break;
                }
            }

            //IdlFramework.Log("@%s, isFree = %s", getBlockPos(), isFree);

            //move
            if (isFree)
            {
                BlockPos newPos = te.getBlockPos().offset(te.getOffset());
                level.setBlockAndUpdate(newPos, te.getBlockState());
                BlockEntity te2 = level.getBlockEntity(newPos);
                if (te2 instanceof MotorBlockEntityBase)
                {
                    ((MotorBlockEntityBase) te2).isPositiveDirection = te.isPositiveDirection;
                }
                level.setBlockAndUpdate(te.getBlockPos(), Blocks.AIR.defaultBlockState());
                List<Entity> entityList = EntityUtil.getEntitiesWithinAABB(level,
                        new Vec3(te.getBlockPos().getX() + 0.5f,
                                te.getBlockPos().getY() + 1f,
                                te.getBlockPos().getZ() + 0.5f), 0.5f, EntityUtil.NON_SPEC);

                //naturally falls down. Teleport in laggy servers will cause the play to fall through.
                if (te.getOffset().getY() >= 0)
                {
                    for (Entity living:
                            entityList) {

                        living.teleportTo(living.getX() + te.getOffset().getX(),
                                living.getY() + te.getOffset().getY(),
                                living.getZ() + te.getOffset().getZ());

                    }
                }

                te.setRemoved();
                //find entities
            }
            else {
//                level.playSound(null, te.getBlockPos().getX() + 0.5,
//                        te.getBlockPos().getY() + 0.5,
//                        te.getBlockPos().getZ() + 0.5,
//                        SoundEvents.PISTON_CONTRACT,
//                        SoundSource.BLOCKS,
//                        1f, 1f);
                te.isPositiveDirection = !te.isPositiveDirection;
            }
        }
    }

    public Vec3i getOffset()
    {
        if (isPositiveDirection)
        {
            return new Vec3i(1, 0, 0);
        }
        else {
            return new Vec3i(-1, 0, 0);
        }
    }


}
