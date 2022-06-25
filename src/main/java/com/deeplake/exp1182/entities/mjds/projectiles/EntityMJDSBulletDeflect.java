package com.deeplake.exp1182.entities.mjds.projectiles;

import com.deeplake.exp1182.setup.ModEntities;
import com.deeplake.exp1182.setup.ModItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

//deflect on attack
public class EntityMJDSBulletDeflect extends EntityMJDSBulletBase {

    public EntityMJDSBulletDeflect(EntityType<? extends EntityMJDSBulletBase> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    public EntityMJDSBulletDeflect(Level p_36824_, double x, double y, double z, double vx, double vy, double vz) {
        super(ModEntities.BULLET2.get(), p_36824_, x, y, z, vx, vy, vz);
    }

    public EntityMJDSBulletDeflect(Level p_36831_, LivingEntity p_36827_, double p_36829_, double p_36830_, double p_36828_) {
        super(ModEntities.BULLET2.get(), p_36831_, p_36827_, p_36829_, p_36830_, p_36828_);
    }

    public boolean hurt(DamageSource p_36839_, float p_36840_) {
        return super.hurt(p_36839_, p_36840_);
    }

    static ItemStack stack = new ItemStack(ModItems.BULLET2.get());
    public ItemStack getItem() {
//       return new ItemStack(Items.FIRE_CHARGE);
        return stack;
    }

    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.WHITE_ASH;
    }
}
