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

public class EntityMJDSBulletPierece extends AbstractHurtingProjectile implements ItemSupplier {
    int maxTicks = CommonDef.TICK_PER_SECOND * 2;
    public EntityMJDSBulletPierece(EntityType<? extends EntityMJDSBulletPierece> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
        setGlowingTag(true);
    }

    public EntityMJDSBulletPierece(Level p_36824_, double x, double y, double z, double vx, double vy, double vz) {
        super(ModEntities.BULLET1.get(), x, y, z, vx, vy, vz, p_36824_);
        setGlowingTag(true);
        stack = new ItemStack(ModItems.BULLET1.get());
    }

    public EntityMJDSBulletPierece(Level p_36831_, LivingEntity p_36827_, double p_36829_, double p_36830_, double p_36828_) {
        super(ModEntities.BULLET1.get(), p_36827_, p_36828_, p_36829_, p_36830_, p_36831_);
        setGlowingTag(true);
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
            boolean flag = entity.hurt(DamageSource.indirectMagic(this, entity1), 5.0F);
            if (flag && entity1 instanceof LivingEntity) {
                this.doEnchantDamageEffects((LivingEntity)entity1, entity);
            }
        }
    }

    @Override
    public boolean hurt(DamageSource p_36839_, float p_36840_) {
        if (level.isClientSide)
        {
//            level.addParticle(ParticleTypes.HEART);
            level.addParticle(ParticleTypes.EXPLOSION, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0, 0, 0);
        }
        discard();
        return true;
    }
}
