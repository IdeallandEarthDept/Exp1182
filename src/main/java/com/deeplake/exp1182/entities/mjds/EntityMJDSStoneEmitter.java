package com.deeplake.exp1182.entities.mjds;

import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.entities.mjds.projectiles.EntityMJDSBulletShower;
import com.deeplake.exp1182.setup.ModEntities;
import com.deeplake.exp1182.util.CommonDef;
import com.deeplake.exp1182.util.DesignUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

import static com.deeplake.exp1182.util.IDLNBTDef.SPAWN_POINT;
import static net.minecraft.nbt.NbtUtils.readBlockPos;
import static net.minecraft.nbt.NbtUtils.writeBlockPos;

public class EntityMJDSStoneEmitter extends Monster implements IMjdsMonster {
    public BlockPos spawnPoint;
    static float BULLET_SPEED = 3f / CommonDef.TICK_PER_SECOND;
    int counter = 0;
    static final int MAX_COUNTER = CommonDef.TICK_PER_SECOND * 5;

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.MOVEMENT_SPEED, (double)0.01F).add(Attributes.ATTACK_DAMAGE, 7.0D).add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    public EntityMJDSStoneEmitter(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        setGlowingTag(true);
        for (EquipmentSlot slotType :
                EquipmentSlot.values()) {
            setDropChance(slotType, 0f);
        }
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.MAX_HEALTH, 56)
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
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

    @Override
    public void aiStep() {
        LivingEntity target = getTarget();
        if (!level.isClientSide && target != null && target.isAlive())
        {
            if (counter < MAX_COUNTER)
            {
                if (counter < 20)
                {
                    double theta = Math.PI * 2 * random.nextDouble();
                    double alpha = Math.PI / 2 * random.nextDouble();
//                    Vec3 dir = (target.getEyePosition().subtract(getEyePosition())).normalize().scale(BULLET_SPEED);
                    Vec3 dir = new Vec3(
                            (Math.cos(theta)) * Math.cos(alpha),
                             Math.sin(alpha),
                            (Math.sin(theta))* Math.cos(alpha)
                    ).scale(BULLET_SPEED) ;
//                    Vec3 dir = (target.position().subtract(position())).normalize().scale(BULLET_SPEED);
//                    Vec3 dir = new Vec3(1f,1f,1f);

                    AbstractHurtingProjectile bulletPierece =
                            new EntityMJDSBulletShower(
                                    level,
                                    this.getX(),
                                    this.getEyeY(),
                                    this.getZ(),
                                    dir.x,
                                    dir.y,
                                    dir.z);
                    level.addFreshEntity(bulletPierece);
                    playSound(ModSounds.MONSTER_SHOOT_2.get(), 2f, 1f);
                }
                counter++;
            }
            else {
                counter = 0;
            }
        }
        else {
            counter = 0;
        }

        super.aiStep();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33579_) {
        return ModSounds.MONSTER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MONSTER_DEATH.get();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        spawnPoint = readBlockPos(nbt.getCompound(SPAWN_POINT));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.put(SPAWN_POINT, writeBlockPos(spawnPoint));
    }

    @Override
    public BlockPos getRespawn() {
        return spawnPoint;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_32146_, DifficultyInstance p_32147_, MobSpawnType p_32148_, @Nullable SpawnGroupData p_32149_, @Nullable CompoundTag p_32150_) {
        p_32149_ = super.finalizeSpawn(p_32146_, p_32147_, p_32148_, p_32149_, p_32150_);
        spawnPoint = blockPosition();
        return p_32149_;
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
}
