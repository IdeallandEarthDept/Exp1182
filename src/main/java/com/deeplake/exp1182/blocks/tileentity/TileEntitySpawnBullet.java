package com.deeplake.exp1182.blocks.tileentity;

import com.deeplake.exp1182.entities.mjds.projectiles.EntityMJDSBulletKB;
import com.deeplake.exp1182.setup.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntitySpawnBullet extends BlockEntity {
    public TileEntitySpawnBullet(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlocks.TE_SPAWN_BULLET.get(), p_155229_, p_155230_);
    }

    public void tickServer() {
        if (level.isClientSide)
        {
            return;
        }

        long ticks = level.getGameTime();
        if (ticks % 20 == 0)
        {
            for (Direction direction : Direction.values()) {
                if (direction.getAxis() != Direction.Axis.Y)
                {
                    Vec3i dir = direction.getNormal();
                    BlockState state = level.getBlockState(getBlockPos().offset(dir));
                    if (!state.getMaterial().blocksMotion())
                    {
                        AbstractHurtingProjectile bulletPierece =
                                new EntityMJDSBulletKB(
                                        level,
                                        getBlockPos().getX()+0.5f+dir.getX(),
                                        getBlockPos().getY()+0.5f,
                                        getBlockPos().getZ()+0.5f+dir.getZ(),
                                        dir.getX(),
                                        0,
                                        dir.getZ());
                        level.addFreshEntity(bulletPierece);
                    }
                }
            }
        }
    }
}
