package com.deeplake.exp1182.entities.mjds;

import com.deeplake.exp1182.setup.ModEntities;
import com.deeplake.exp1182.util.CommonDef;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class EntityMJDSBulletPierece extends AbstractHurtingProjectile implements ItemSupplier {
    int maxTicks = CommonDef.TICK_PER_SECOND * 2;
    public EntityMJDSBulletPierece(EntityType<? extends EntityMJDSBulletPierece> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
        setGlowingTag(true);
    }

    public EntityMJDSBulletPierece(Level p_36824_, double p_36819_, double p_36818_, double p_36821_, double p_36822_, double p_36823_, double p_36820_) {
        super(ModEntities.BULLET1.get(), p_36818_, p_36819_, p_36820_, p_36821_, p_36822_, p_36823_, p_36824_);
        setGlowingTag(true);
    }

    public EntityMJDSBulletPierece(Level p_36831_, LivingEntity p_36827_, double p_36829_, double p_36830_, double p_36828_) {
        super(ModEntities.BULLET1.get(), p_36827_, p_36828_, p_36829_, p_36830_, p_36831_);
        setGlowingTag(true);
    }

    static final float VISIBLE_DIST = 64*64;

    public boolean shouldRenderAtSqrDistance(double p_36837_) {
        return p_36837_ < VISIBLE_DIST;
    }

    protected float getInertia() {
        return 1F;
    }

    protected void onHit(HitResult p_37406_) {
        super.onHit(p_37406_);
        HitResult.Type hitresult$type = p_37406_.getType();
        if (!this.level.isClientSide) {
            if (hitresult$type != HitResult.Type.BLOCK)
            {
                this.discard();
            }
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

    static ItemStack stack = new ItemStack(Items.FIRE_CHARGE);
    public ItemStack getItem() {
        return stack;
    }
}
