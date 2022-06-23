package com.deeplake.exp1182.entities.mjds.projectiles;

import com.deeplake.exp1182.setup.ModItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

//deflect on attack
public class EntityMJDSBulletDeflect extends EntityMJDSBulletPierece {

    public EntityMJDSBulletDeflect(EntityType<? extends EntityMJDSBulletPierece> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    public EntityMJDSBulletDeflect(Level p_36824_, double x, double y, double z, double vx, double vy, double vz) {
        super(p_36824_, x, y, z, vx, vy, vz);
    }

    public EntityMJDSBulletDeflect(Level p_36831_, LivingEntity p_36827_, double p_36829_, double p_36830_, double p_36828_) {
        super(p_36831_, p_36827_, p_36829_, p_36830_, p_36828_);
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

    static ItemStack stack = new ItemStack(ModItems.BULLET2.get());
    public ItemStack getItem() {
//       return new ItemStack(Items.FIRE_CHARGE);
        return stack;
    }
}
