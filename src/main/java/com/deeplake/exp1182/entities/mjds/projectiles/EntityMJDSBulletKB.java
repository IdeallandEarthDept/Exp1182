package com.deeplake.exp1182.entities.mjds.projectiles;

import com.deeplake.exp1182.setup.ModEntities;
import com.deeplake.exp1182.setup.ModItems;
import com.deeplake.exp1182.util.AdvancementUtil;
import com.deeplake.exp1182.util.EntityUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityMJDSBulletKB extends EntityMJDSBulletBase{
    public EntityMJDSBulletKB(EntityType<? extends EntityMJDSBulletBase> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    public EntityMJDSBulletKB(Level p_36824_, double x, double y, double z, double vx, double vy, double vz) {
        super(ModEntities.BULLET_KB.get(), p_36824_, x, y, z, vx, vy, vz);
    }

    public EntityMJDSBulletKB(Level p_36831_, LivingEntity p_36827_, double x, double y, double z) {
        super(ModEntities.BULLET_KB.get(), p_36831_, p_36827_, x, y, z);
    }

    @Override
    public void init() {
        skipBlock = false;
        super.init();
    }

    @Override
    public void onHitEntityExtra(Entity entity) {
        super.onHitEntityExtra(entity);
        if (entity instanceof LivingEntity living)
        {
            EntityUtil.simpleKnockBack(3.5f, this, living);
        }
    }

    static ItemStack stack2 = new ItemStack(ModItems.BULLET2.get());
    @Override
    public ItemStack getItem() {
        return stack2;
    }

    @Override
    public void tick() {
//        super.tick();
        normalTick();
//        arrowTick();
//        Main.Log("UUID=%s,speed=%s,pos=%s",stringUUID.substring(0,4), getDeltaMovement(), position());
    }

    private void normalTick() {
        if (!this.isNoGravity()) {
            Entity entity = this.getOwner();
            if (this.level().isClientSide || (entity == null || !entity.isRemoved()) && this.level().hasChunkAt(this.blockPosition())) {
//                super.tick();
                if (this.shouldBurn()) {
                    this.setSecondsOnFire(1);
                }

                HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
                if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                    this.onHit(hitresult);
                }

                this.checkInsideBlocks();
//                this.setDeltaMovement(this.getDeltaMovement().add(0, -0.05, 0));
                Vec3 vec3 = this.getDeltaMovement();

                double d0 = this.getX() + vec3.x;
                double d1 = this.getY() + vec3.y;
                double d2 = this.getZ() + vec3.z;
                ProjectileUtil.rotateTowardsMovement(this, 0.2F);

                this.level().addParticle(this.getTrailParticle(), d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
                this.setPos(d0, d1, d2);
            } else {
                this.discard();
            }

            if (!level().isClientSide)
            {
                if (tickCount >= maxTicks) {
                    this.discard();
                }
            }
        }
    }

    boolean isSuccessfulHit(Entity entity, LivingEntity entity1) {
        if (entity instanceof Player player)
        {
            if (!AdvancementUtil.hasAdvancement(player, AdvancementUtil.SHIELD_BRONZE))
            {
                //no matter success or not
                player.getCooldowns().addCooldown(Items.SHIELD, 100);
            }
        }

        return entity.hurt(level().damageSources().mobProjectile(this, entity1), 5.0F);
    }
}
