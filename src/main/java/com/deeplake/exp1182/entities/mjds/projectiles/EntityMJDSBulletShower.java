package com.deeplake.exp1182.entities.mjds.projectiles;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.entities.mjds.EntityMJDSStoneEmitter;
import com.deeplake.exp1182.setup.ModEntities;
import com.deeplake.exp1182.setup.ModItems;
import com.deeplake.exp1182.util.CommonDef;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityMJDSBulletShower extends EntityMJDSBulletBase{
    public EntityMJDSBulletShower(EntityType<? extends EntityMJDSBulletBase> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
        init();
    }

    public EntityMJDSBulletShower(Level p_36824_, double x, double y, double z, double vx, double vy, double vz) {
        super(ModEntities.BULLET_SHOWER.get(), p_36824_, x, y, z, vx, vy, vz);
        init();
    }

    public void init() {
        maxTicks = CommonDef.TICK_PER_SECOND * 3;
        super.init();
    }

    public EntityMJDSBulletShower(Level p_36831_, LivingEntity p_36827_, double p_36829_, double p_36830_, double p_36828_) {
        super(ModEntities.BULLET_SHOWER.get(), p_36831_, p_36827_, p_36829_, p_36830_, p_36828_);
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
            if (this.level.isClientSide || (entity == null || !entity.isRemoved()) && this.level.hasChunkAt(this.blockPosition())) {
//                super.tick();
                if (this.shouldBurn()) {
                    this.setSecondsOnFire(1);
                }

                HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
                if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                    this.onHit(hitresult);
                }

                this.checkInsideBlocks();
                this.setDeltaMovement(this.getDeltaMovement().add(0, -0.05, 0));
                Vec3 vec3 = this.getDeltaMovement();

                double d0 = this.getX() + vec3.x;
                double d1 = this.getY() + vec3.y;
                double d2 = this.getZ() + vec3.z;
                ProjectileUtil.rotateTowardsMovement(this, 0.2F);

                this.level.addParticle(this.getTrailParticle(), d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
                this.setPos(d0, d1, d2);
            } else {
                this.discard();
            }

            if (!level.isClientSide)
            {
                if (tickCount >= maxTicks) {
                    this.discard();
                }
            }
        }
    }

    private void arrowTick() {
        //ARROW
        Vec3 vec3 = this.getDeltaMovement();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = vec3.horizontalDistance();
            this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
            this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

        Vec3 vec32 = this.position();
        Vec3 vec33 = vec32.add(vec3);
        HitResult hitresult = this.level.clip(new ClipContext(vec32, vec33, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

        vec3 = this.getDeltaMovement();
        double d5 = vec3.x;
        double d6 = vec3.y;
        double d1 = vec3.z;

        double d7 = this.getX() + d5;
        double d2 = this.getY() + d6;
        double d3 = this.getZ() + d1;
        double d4 = vec3.horizontalDistance();

        //            this.setXRot((float)(Mth.atan2(d6, d4) * (double)(180F / (float)Math.PI)));
        //            this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
        //            this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
        float f = 0.99F;
        float f1 = 0.05F;
        if (this.isInWater()) {
            for(int j = 0; j < 4; ++j) {
                float f2 = 0.25F;
                this.level.addParticle(ParticleTypes.BUBBLE, d7 - d5 * 0.25D, d2 - d6 * 0.25D, d3 - d1 * 0.25D, d5, d6, d1);
            }

            f = 0.8f;
        }

        this.setDeltaMovement(vec3.scale((double)f));

        Vec3 vec34 = this.getDeltaMovement();
        this.setDeltaMovement(vec34.x, vec34.y - (double)0.05F, vec34.z);

        this.setPos(d7, d2, d3);
        this.checkInsideBlocks();
    }

    static ItemStack stack = new ItemStack(ModItems.BULLET3.get());
    public ItemStack getItem() {
//       return new ItemStack(Items.FIRE_CHARGE);
        return stack;
    }

    public Packet<?> getAddEntityPacket() {
        Entity entity = this.getOwner();
        int i = entity == null ? 0 : entity.getId();
        return new ClientboundAddEntityPacket(this.getId(), this.getUUID(), this.getX(), this.getY(), this.getZ(), this.getXRot(), this.getYRot(), this.getType(), i, new Vec3(this.xPower, this.yPower, this.zPower));
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket p_150128_) {
        super.recreateFromPacket(p_150128_);
        double d0 = p_150128_.getXa();
        double d1 = p_150128_.getYa();
        double d2 = p_150128_.getZa();
        double d3 = (d0 * d0 + d1 * d1 + d2 * d2);
        if (d3 != 0.0D) {
            this.xPower = d0;
            this.yPower = d1;
            this.zPower = d2;
        }
        setDeltaMovement(xPower, yPower, zPower);
    }

    protected boolean canHitEntity(Entity p_36842_) {
        return super.canHitEntity(p_36842_) && !(p_36842_ instanceof EntityMJDSStoneEmitter);
    }

    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.WHITE_ASH;
    }
}
