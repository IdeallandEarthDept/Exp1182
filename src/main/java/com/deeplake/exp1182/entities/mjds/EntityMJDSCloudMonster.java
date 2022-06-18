package com.deeplake.exp1182.entities.mjds;

import com.deeplake.exp1182.blocks.IBlockMJDS;
import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.setup.ModEntities;
import com.deeplake.exp1182.util.CommonDef;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;

public class EntityMJDSCloudMonster extends Monster {

    static float BULLET_SPEED = 3f / CommonDef.TICK_PER_SECOND;
    int counter = 0;
    static final int MAX_COUNTER = CommonDef.TICK_PER_SECOND * 5;
    static final HashSet<Integer> ATTCK_SEQUENCE = new HashSet<>();

    static
    {
        ATTCK_SEQUENCE.add(CommonDef.TICK_PER_SECOND * 2);
        ATTCK_SEQUENCE.add(CommonDef.TICK_PER_SECOND * 3);
        ATTCK_SEQUENCE.add(CommonDef.TICK_PER_SECOND * 4);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.MOVEMENT_SPEED, (double)0.01F).add(Attributes.ATTACK_DAMAGE, 7.0D).add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    public EntityMJDSCloudMonster(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        setGlowingTag(true);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        //can attack wha it cant see
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, false, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    protected void customServerAiStep() {
        LivingEntity target = getTarget();
        if (target != null && target.isAlive())
        {
            if (counter < MAX_COUNTER)
            {
                if (ATTCK_SEQUENCE.contains(counter))
                {
                    Vec3 dir = (target.getEyePosition().subtract(getEyePosition())).normalize().scale(BULLET_SPEED);
//                    EntityMJDSBulletPierece bulletPierece =
//                            new EntityMJDSBulletPierece(
//                                    level,
//                                    this.getX(),
//                                    this.getEyeY(),
//                                    this.getZ(),
//                                    dir.x,
//                                    dir.y,
//                                    dir.z);

                    float accel = BULLET_SPEED;
                    float yawInRad = (float) (Math.toRadians(45));
                    EntityMJDSBulletPierece bulletPierece =
                            new EntityMJDSBulletPierece(
                            level,
                            this.getX(),
                            this.getY()+0.03f,
                            this.getZ(),
                            accel * Math.cos(yawInRad),
                            0,
                            accel * Math.sin(yawInRad));

                    bulletPierece.setOwner(this);
                    level.addFreshEntity(bulletPierece);
                    playSound(ModSounds.MONSTER_SHOOT_1.get(), 2f, 1f);
                }
                counter++;
            }
            else {
                counter = 0;
                if (random.nextBoolean())
                {
                    teleportTowards(target);
                }
                else {
                    teleport();
                }

            }
        }
        else {
            counter = 0;
        }

        super.customServerAiStep();
    }

    protected boolean teleport() {
        if (!this.level.isClientSide() && this.isAlive()) {
            double d0 = this.getX() + (this.random.nextDouble() - 0.5D) * 64.0D;
            double d1 = this.getY() + (double)(this.random.nextInt(64) - 32);
            double d2 = this.getZ() + (this.random.nextDouble() - 0.5D) * 64.0D;
            return this.teleport(d0, d1, d2);
        } else {
            return false;
        }
    }

    boolean teleportTowards(Entity p_32501_) {
        Vec3 vec3 = new Vec3(this.getX() - p_32501_.getX(), this.getY(0.5D) - p_32501_.getEyeY(), this.getZ() - p_32501_.getZ());
        vec3 = vec3.normalize();
        double d0 = 16.0D;
        double d1 = this.getX() + (this.random.nextDouble() - 0.5D) * 8.0D - vec3.x * 16.0D;
        double d2 = this.getY() + (double)(this.random.nextInt(16) - 8) - vec3.y * 16.0D;
        double d3 = this.getZ() + (this.random.nextDouble() - 0.5D) * 8.0D - vec3.z * 16.0D;
        return this.teleport(d1, d2, d3);
    }

    //just like enderman, except that it only lands on MJDS blocks.
    private boolean teleport(double p_32544_, double p_32545_, double p_32546_) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(p_32544_, p_32545_, p_32546_);

        while(blockpos$mutableblockpos.getY() > this.level.getMinBuildHeight() && !this.level.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMotion()) {
            blockpos$mutableblockpos.move(Direction.DOWN);
        }

        BlockState blockstate = this.level.getBlockState(blockpos$mutableblockpos);
        boolean flag = blockstate.getMaterial().blocksMotion() && blockstate.getBlock() instanceof IBlockMJDS;
        boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
        if (flag && !flag1) {
            net.minecraftforge.event.entity.EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory.onEnderTeleport(this, p_32544_, p_32545_, p_32546_);
            if (event.isCanceled()) return false;
            boolean flag2 = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag2 && !this.isSilent()) {
                this.level.playSound((Player)null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
                this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }

            return flag2;
        } else {
            return false;
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33579_) {
        return ModSounds.MONSTER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MONSTER_DEATH.get();
    }
}
