package com.deeplake.exp1182.entities.mjds;

import com.deeplake.exp1182.blocks.IBlockMJDS;
import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.entities.mjds.projectiles.EntityMJDSBulletPierece;
import com.deeplake.exp1182.setup.ModEntities;
import com.deeplake.exp1182.util.CommonDef;
import com.deeplake.exp1182.util.DesignUtil;
import com.deeplake.exp1182.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.HashSet;

import static com.deeplake.exp1182.util.IDLNBTDef.SPAWN_POINT;
import static net.minecraft.nbt.NbtUtils.readBlockPos;
import static net.minecraft.nbt.NbtUtils.writeBlockPos;

public class EntityMJDSCloudMonster extends Monster implements IMjdsMonster {
    public BlockPos spawnPoint;
    static float BULLET_SPEED = 3f / CommonDef.TICK_PER_SECOND;
    int counter = 0;
    static final int MAX_COUNTER = CommonDef.TICK_PER_SECOND * 5;
    static final HashSet<Integer> ATTCK_SEQUENCE = new HashSet<>();

    static
    {
//        ATTCK_SEQUENCE.add(CommonDef.TICK_PER_SECOND * 2);
        ATTCK_SEQUENCE.add(CommonDef.TICK_PER_SECOND * 3);
//        ATTCK_SEQUENCE.add(CommonDef.TICK_PER_SECOND * 4);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.MOVEMENT_SPEED, (double)0.01F).add(Attributes.ATTACK_DAMAGE, 7.0D).add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    public EntityMJDSCloudMonster(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        setGlowingTag(true);
        for (EquipmentSlot slotType :
                EquipmentSlot.values()) {
            setDropChance(slotType, 0f);
        }
        setPersistenceRequired();
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
        if (!level().isClientSide) {
            boolean hasTarget = target != null && target.isAlive();
            setGlowingTag(hasTarget);
            if (hasTarget) {
                if (counter < MAX_COUNTER) {
                    if (ATTCK_SEQUENCE.contains(counter)) {
                        Vec3 dir = (target.getEyePosition().subtract(getEyePosition())).normalize().scale(BULLET_SPEED);

                        AbstractHurtingProjectile bulletPierece =
                                new EntityMJDSBulletPierece(
                                        level(),
                                        this.getX(),
                                        this.getEyeY(),
                                        this.getZ(),
                                        dir.x,
                                        dir.y,
                                        dir.z);
                        level().addFreshEntity(bulletPierece);
                        playSound(ModSounds.MONSTER_SHOOT_1, 2f, 1f);
                    }
                    counter++;
                } else {
                    counter = 0;
                    for (int i = 0; i < 100; i++) {
                        if (random.nextBoolean()) {
                            if (teleportTowards(target)) {
                                break;
                            }
                        } else {
                            if (teleport()) {
                                break;
                            }
                        }
                    }
                }
            } else {
                counter = 0;
            }
        } else {
            counter = 0;
        }

        super.aiStep();
    }

    protected boolean teleport() {
        if (!this.level().isClientSide() && this.isAlive()) {
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

        while(blockpos$mutableblockpos.getY() > this.level().getMinBuildHeight() && !this.level().getBlockState(blockpos$mutableblockpos).blocksMotion()) {
            blockpos$mutableblockpos.move(Direction.DOWN);
        }

        BlockState blockstate = this.level().getBlockState(blockpos$mutableblockpos);
        boolean flag = blockstate.blocksMotion() && blockstate.getBlock() instanceof IBlockMJDS;
        boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
        if (flag && !flag1) {
            net.minecraftforge.event.entity.EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory.onEnderTeleport(this, p_32544_, p_32545_, p_32546_);
            if (event.isCanceled()) return false;
            boolean flag2 = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag2 && !this.isSilent()) {
                this.level().playSound((Player)null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
                this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }

            return flag2;
        } else {
            return false;
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33579_) {
        return ModSounds.MONSTER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MONSTER_DEATH;
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
    public void remove(RemovalReason p_146834_) {
        super.remove(p_146834_);
        if (p_146834_ == RemovalReason.KILLED || p_146834_ == RemovalReason.DISCARDED)
        {
            if (!level().isClientSide && DesignUtil.canRevive(this))
            {
                //IdlFramework.Log("That is not dead which can eternal lie...");
                EntityRevivalMist mist = new EntityRevivalMist(ModEntities.REVIVE_MIST.get(), level());
                mist.setWith(this);
                mist.setPos(spawnPoint.getX()+0.5f, spawnPoint.getY()+1f, spawnPoint.getZ()+0.5f);
                level().addFreshEntity(mist);
            }
        }
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return EntityUtil.getAttrBuilder(6, 4, 1f);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource p_21385_, int p_21386_, boolean p_21387_) {
        super.dropCustomDeathLoot(p_21385_, p_21386_, p_21387_);

        ItemStack itemStack;
        switch (random.nextInt(4))
        {
            case 0:
                itemStack = new ItemStack(Items.ARROW, 5);
                break;
            case 1:
                itemStack = new ItemStack(Items.BREAD, 2);
                break;
            case 2:
                itemStack = new ItemStack(Items.POTION, 1);
                if (random.nextBoolean())
                {
                    PotionUtils.setPotion(itemStack, Potions.MUNDANE);
                }
                else {
                    PotionUtils.setPotion(itemStack, Potions.SWIFTNESS);
                }

                break;
            default:
                itemStack = null;
                break;
        }
        if (itemStack != null)
        {
            this.spawnAtLocation(itemStack);
        }
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        invulnerableTime = 0;
        return super.hurt(p_21016_, p_21017_);
    }
}
