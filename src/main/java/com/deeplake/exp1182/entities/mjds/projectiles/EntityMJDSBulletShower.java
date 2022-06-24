package com.deeplake.exp1182.entities.mjds.projectiles;

import com.deeplake.exp1182.setup.ModEntities;
import com.deeplake.exp1182.setup.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityMJDSBulletShower extends EntityMJDSBulletBase{
    public EntityMJDSBulletShower(EntityType<? extends EntityMJDSBulletBase> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    public EntityMJDSBulletShower(Level p_36824_, double x, double y, double z, double vx, double vy, double vz) {
        super(ModEntities.BULLET_SHOWER.get(), p_36824_, x, y, z, vx, vy, vz);
    }

    public EntityMJDSBulletShower(Level p_36831_, LivingEntity p_36827_, double p_36829_, double p_36830_, double p_36828_) {
        super(ModEntities.BULLET_SHOWER.get(), p_36831_, p_36827_, p_36829_, p_36830_, p_36828_);
    }

    @Override
    public void tick() {
//        super.tick();
//        if (!this.isNoGravity()) {
        Entity entity = this.getOwner();
        if (this.level.isClientSide || (entity == null || !entity.isRemoved()) && this.level.hasChunkAt(this.blockPosition())) {
            super.tick();
            if (this.shouldBurn()) {
                this.setSecondsOnFire(1);
            }

            HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            this.checkInsideBlocks();
            this.setDeltaMovement(this.getDeltaMovement().add(0, -0.2, 0));
            Vec3 vec3 = this.getDeltaMovement();

            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;
            ProjectileUtil.rotateTowardsMovement(this, 0.2F);

            this.level.addParticle(this.getTrailParticle(), d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
            this.setPos(d0, d1, d2);
        } else {
            this.discard();

            if (!level.isClientSide()) {
                if (tickCount >= maxTicks) {
                    this.discard();
                }
            }
        }
    }

    static ItemStack stack = new ItemStack(ModItems.BULLET3.get());
    public ItemStack getItem() {
//       return new ItemStack(Items.FIRE_CHARGE);
        return stack;
    }

}
