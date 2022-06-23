package com.deeplake.exp1182.blocks.motor;

import com.deeplake.exp1182.blocks.BaseBlockMJDS;
import com.deeplake.exp1182.blocks.IBlockMJDS;
import com.deeplake.exp1182.blocks.tileentity.MotorBlockEntityBase;
import com.deeplake.exp1182.blocks.tileentity.TileEntitySpawnBoss;
import com.deeplake.exp1182.util.CommonDef;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.piston.PistonMath;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

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
                if (tile.getLevel() != null) {
                    progress = (float) (tile.getLevel().getGameTime() % CommonDef.TICK_PER_SECOND) / CommonDef.TICK_PER_SECOND;
                }
            }
        };
    }


    float progress;

    //pistons are too complex.
//
//    private void moveCollidedEntities(Level p_155911_, BlockPos p_155912_, float p_155913_, PistonMovingBlockEntity this) {
//        Direction direction = this.getMovementDirection();
//        double d0 = (double)(p_155913_ - this.progress);
//        VoxelShape voxelshape = this.getCollisionRelatedBlockState().getCollisionShape(p_155911_, p_155912_);
//        if (!voxelshape.isEmpty()) {
//            AABB aabb = moveByPositionAndProgress(p_155912_, voxelshape.bounds(), this);
//            List<Entity> list = p_155911_.getEntities((Entity)null, PistonMath.getMovementArea(aabb, direction, d0).minmax(aabb));
//            if (!list.isEmpty()) {
//                List<AABB> list1 = voxelshape.toAabbs();
//                boolean flag = this.movedState.isSlimeBlock(); //TODO: is this patch really needed the logic of the original seems sound revisit later
//                Iterator iterator = list.iterator();
//
//                while(true) {
//                    Entity entity;
//                    while(true) {
//                        if (!iterator.hasNext()) {
//                            return;
//                        }
//
//                        entity = (Entity)iterator.next();
//                        if (entity.getPistonPushReaction() != PushReaction.IGNORE) {
//                            if (!flag) {
//                                break;
//                            }
//
//                            if (!(entity instanceof ServerPlayer)) {
//                                Vec3 vec3 = entity.getDeltaMovement();
//                                double d1 = vec3.x;
//                                double d2 = vec3.y;
//                                double d3 = vec3.z;
//                                switch(direction.getAxis()) {
//                                    case X:
//                                        d1 = (double)direction.getStepX();
//                                        break;
//                                    case Y:
//                                        d2 = (double)direction.getStepY();
//                                        break;
//                                    case Z:
//                                        d3 = (double)direction.getStepZ();
//                                }
//
//                                entity.setDeltaMovement(d1, d2, d3);
//                                break;
//                            }
//                        }
//                    }
//
//                    double d4 = 0.0D;
//
//                    for(AABB aabb2 : list1) {
//                        AABB aabb1 = PistonMath.getMovementArea(moveByPositionAndProgress(p_155912_, aabb2, this), direction, d0);
//                        AABB aabb3 = entity.getBoundingBox();
//                        if (aabb1.intersects(aabb3)) {
//                            d4 = Math.max(d4, getMovement(aabb1, direction, aabb3));
//                            if (d4 >= d0) {
//                                break;
//                            }
//                        }
//                    }
//
//                    if (!(d4 <= 0.0D)) {
//                        d4 = Math.min(d4, d0) + 0.01D;
//                        moveEntityByPiston(direction, entity, d4, direction);
//                        if (!this.extending && this.isSourcePiston) {
//                            fixEntityWithinPistonBase(p_155912_, entity, direction, d0);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private static void moveEntityByPiston(Direction p_60372_, Entity p_60373_, double p_60374_, Direction p_60375_) {
//        NOCLIP.set(p_60372_);
//        p_60373_.move(MoverType.PISTON, new Vec3(p_60374_ * (double)p_60375_.getStepX(), p_60374_ * (double)p_60375_.getStepY(), p_60374_ * (double)p_60375_.getStepZ()));
//        NOCLIP.set((Direction)null);
//    }
//
//    private static void moveStuckEntities(Level p_155932_, BlockPos p_155933_, float p_155934_, PistonMovingBlockEntity p_155935_) {
//        if (p_155935_.isStickyForEntities()) {
//            Direction direction = p_155935_.getMovementDirection();
//            if (direction.getAxis().isHorizontal()) {
//                double d0 = p_155935_.movedState.getCollisionShape(p_155932_, p_155933_).max(Direction.Axis.Y);
//                AABB aabb = moveByPositionAndProgress(p_155933_, new AABB(0.0D, d0, 0.0D, 1.0D, 1.5000000999999998D, 1.0D), p_155935_);
//                double d1 = (double)(p_155934_ - p_155935_.progress);
//
//                for(Entity entity : p_155932_.getEntities((Entity)null, aabb, (p_60384_) -> {
//                    return matchesStickyCritera(aabb, p_60384_);
//                })) {
//                    moveEntityByPiston(direction, entity, d1, direction);
//                }
//
//            }
//        }
//    }
//
//    private static AABB moveByPositionAndProgress(BlockPos p_155926_, AABB p_155927_, PistonMovingBlockEntity p_155928_) {
//        double d0 = (double)p_155928_.getExtendedProgress(p_155928_.progress);
//        return p_155927_.move((double)p_155926_.getX() + d0 * (double)p_155928_.direction.getStepX(), (double)p_155926_.getY() + d0 * (double)p_155928_.direction.getStepY(), (double)p_155926_.getZ() + d0 * (double)p_155928_.direction.getStepZ());
//    }
}
