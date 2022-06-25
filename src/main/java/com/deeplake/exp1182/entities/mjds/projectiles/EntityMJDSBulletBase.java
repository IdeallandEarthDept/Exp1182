package com.deeplake.exp1182.entities.mjds.projectiles;

import com.deeplake.exp1182.setup.ModEntities;
import com.deeplake.exp1182.setup.ModItems;
import com.deeplake.exp1182.util.CommonDef;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public abstract class EntityMJDSBulletBase extends AbstractHurtingProjectile implements ItemSupplier {
    int maxTicks = CommonDef.TICK_PER_SECOND * 2;
    public EntityMJDSBulletBase(EntityType<? extends EntityMJDSBulletBase> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
        setGlowingTag(true);
    }

    public EntityMJDSBulletBase(EntityType<? extends EntityMJDSBulletBase> type, Level p_36824_, double x, double y, double z, double vx, double vy, double vz) {
        super(type, x, y, z, vx, vy, vz, p_36824_);
        double d0 = vx * vx + vy * vy + vz * vz;
        if (d0 != 0.0D) {
            this.xPower = vx;
            this.yPower = vy;
            this.zPower = vz;
        }
        if (!level.isClientSide)
        {
            setGlowingTag(true);
            setDeltaMovement(vx, vy, vz);

        }else {
            setDeltaMovement(vx, vy, vz);
        }

        stack = new ItemStack(ModItems.BULLET1.get());
    }

    public EntityMJDSBulletBase(EntityType<? extends EntityMJDSBulletBase> type, Level p_36831_, LivingEntity p_36827_, double x, double y, double z) {
        super(type, p_36827_, z, x, y, p_36831_);
        setGlowingTag(true);
//        setDeltaMovement(x,y,z);
    }

    static final float VISIBLE_DIST = 64*64;

    public boolean shouldRenderAtSqrDistance(double p_36837_) {
        return true;
//        return p_36837_ < VISIBLE_DIST;
    }

    protected float getInertia() {
        return 1F;
    }

    protected void onHit(HitResult p_37406_) {
        super.onHit(p_37406_);
        HitResult.Type hitresult$type = p_37406_.getType();
        if (hitresult$type == HitResult.Type.ENTITY) {
            this.onHitEntity((EntityHitResult)p_37406_);
        } else if (hitresult$type == HitResult.Type.BLOCK) {
//            this.onHitBlock((BlockHitResult)p_37406_);
        }

        if (hitresult$type != HitResult.Type.MISS) {
            this.gameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide())
        {
            if (tickCount >= maxTicks)
            {
                this.discard();
            }
            else {

            }
        }
    }

    static ItemStack stack = new ItemStack(ModItems.BULLET1.get());
    public ItemStack getItem() {
//       return new ItemStack(Items.FIRE_CHARGE);
        return stack;
    }

    protected void onHitEntity(EntityHitResult p_37386_) {
        super.onHitEntity(p_37386_);
        if (!this.level.isClientSide) {
            Entity entity = p_37386_.getEntity();
            Entity entity1 = this.getOwner();
            if (entity1 == null || entity1 instanceof LivingEntity)
            {
                boolean flag = entity.hurt(DamageSource.indirectMobAttack(this, (LivingEntity) entity1), 5.0F);
                if (flag) {
                    this.doEnchantDamageEffects((LivingEntity) entity1, entity);
                }
            }

        }
    }

    public boolean hurt(DamageSource p_36839_, float p_36840_) {
        if (this.isInvulnerableTo(p_36839_)) {
            return false;
        } else {
            this.markHurt();
            Entity entity = p_36839_.getEntity();
            if (entity != null) {
                if (!this.level.isClientSide) {
                    Vec3 vec3 = entity.getLookAngle();
                    this.setDeltaMovement(vec3);
                    this.xPower = vec3.x * 0.1D;
                    this.yPower = vec3.y * 0.1D;
                    this.zPower = vec3.z * 0.1D;
                    this.setOwner(entity);
                }

                return true;
            } else {
                return false;
            }
        }
    }
}
