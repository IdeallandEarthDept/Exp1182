package com.deeplake.exp1182.entities.mjds.projectiles;

import com.deeplake.exp1182.setup.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityMJDSStoneShower extends EntityMJDSBulletDeflect{
    public EntityMJDSStoneShower(EntityType<? extends EntityMJDSBulletPierece> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    public EntityMJDSStoneShower(Level p_36824_, double x, double y, double z, double vx, double vy, double vz) {
        super(p_36824_, x, y, z, vx, vy, vz);
    }

    public EntityMJDSStoneShower(Level p_36831_, LivingEntity p_36827_, double p_36829_, double p_36830_, double p_36828_) {
        super(p_36831_, p_36827_, p_36829_, p_36830_, p_36828_);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isNoGravity()) {
            Vec3 vec34 = this.getDeltaMovement();
            this.setDeltaMovement(vec34.x, vec34.y - (double)0.05F, vec34.z);
        }
    }

    static ItemStack stack = new ItemStack(ModItems.BULLET2.get());
    public ItemStack getItem() {
//       return new ItemStack(Items.FIRE_CHARGE);
        return stack;
    }
}
