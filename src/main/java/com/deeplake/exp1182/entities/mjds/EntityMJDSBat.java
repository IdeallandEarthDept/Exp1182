package com.deeplake.exp1182.entities.mjds;


import com.deeplake.exp1182.entities.mjds.ai.MobAttackGoal;
import com.deeplake.exp1182.setup.ModEntities;
import com.deeplake.exp1182.util.DesignUtil;
import com.deeplake.exp1182.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static com.deeplake.exp1182.util.IDLNBTDef.SPAWN_POINT;

public class EntityMJDSBat extends Bat implements IMjdsMonster  {
    public BlockPos spawnPoint;

    public EntityMJDSBat(EntityType<? extends Bat> p_i50290_1_, Level p_i50290_2_) {
        super(p_i50290_1_, p_i50290_2_);
        this.xpReward = 5;
    }

    @Override
    public void onRemovedFromWorld() {
        super.onRemovedFromWorld();
        if (!level.isClientSide && DesignUtil.canRevive(this))
        {
            //IdlFramework.Log("That is not dead which can eternal lie...");
            EntityRevivalMist mist = new EntityRevivalMist(ModEntities.REVIVE_MIST.get(), level);
            mist.setWith(this);
            mist.setPos(spawnPoint.getX()+0.5f, spawnPoint.getY()+1f, spawnPoint.getZ()+0.5f);
            level.addFreshEntity(mist);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        spawnPoint = NbtUtils.readBlockPos(nbt.getCompound(SPAWN_POINT));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.put(SPAWN_POINT,  NbtUtils.writeBlockPos(spawnPoint));
    }

    //AI
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.goalSelector.addGoal(2, new MobAttackGoal(this, 1.0, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    //from SlimeEntity
    public void push(Entity p_70108_1_) {
        super.push(p_70108_1_);
        if (p_70108_1_ instanceof IronGolem && this.isDealsDamage()) {
            this.dealDamage((LivingEntity)p_70108_1_);
        }
    }

    public void playerTouch(Player p_70100_1_) {
        if (this.isDealsDamage()) {
            this.dealDamage(p_70100_1_);
        }
    }

    protected boolean isDealsDamage() {
        return this.isEffectiveAi();
    }

    protected void dealDamage(LivingEntity p_175451_1_) {
        if (this.isAlive()) {
            if (this.distanceToSqr(p_175451_1_) < 0.6D && this.hasLineOfSight(p_175451_1_) && p_175451_1_.hurt(DamageSource.mobAttack(this), this.getAttackDamage())) {
                this.playSound(SoundEvents.BAT_TAKEOFF, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.doEnchantDamageEffects(this, p_175451_1_);
            }
        }
    }

    protected float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    public BlockPos getRespawn() {
        return spawnPoint;
    }

    //MonsterEntity
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.HOSTILE_SWIM;
    }

    protected SoundEvent getSwimSplashSound() {
        return SoundEvents.HOSTILE_SPLASH;
    }

    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        if (this.isInvulnerableTo(p_70097_1_)) {
            return false;
        } else if (p_70097_1_ == DamageSource.CRAMMING || p_70097_1_ == DamageSource.IN_WALL) {
            return false;
        }
        return super.hurt(p_70097_1_, p_70097_2_);
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.HOSTILE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.HOSTILE_DEATH;
    }

    protected boolean shouldDropExperience() {
        return true;
    }

    protected boolean shouldDropLoot() {
        return true;
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return EntityUtil.getAttrBuilder(5, 1, 0.5f);
    }

}