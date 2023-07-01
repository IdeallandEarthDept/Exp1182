package com.deeplake.exp1182.entities;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.entities.mjds.EntityRevivalMist;
import com.deeplake.exp1182.entities.mjds.projectiles.EntityMJDSBulletKB;
import com.deeplake.exp1182.entities.mjds.projectiles.EntityMJDSBulletPierece;
import com.deeplake.exp1182.util.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.*;

import static net.minecraft.world.entity.EntitySelector.LIVING_ENTITY_STILL_ALIVE;

public class EntityWorldBoss extends Monster {
    static final float MAX_HP = 1024;

    //AI StateMachine
    int curStateTick = 0;//how many ticks this state have lasted
    int maxStateTick = CommonDef.TICK_PER_SECOND * 7;//how many ticks a state may last
    int preWarmTicks = CommonDef.TICK_PER_SECOND * 2;

    enum BossState {
        NONE,
        BARRAGE_1,//NSEW bullet
        BARRAGE_2,//diagonal NSEW bullet
        BARRAGE_3,//8-dir bullet
        BARRAGE_4,//Gatling bullet
        BARRAGE_ANTI_AIR,//
        //todo: more
    }

    BossState curState = BossState.NONE;

    boolean forceKilled = false;

    public EntityWorldBoss(EntityType<? extends Monster> entityType, Level worldIn) {
        super(entityType, worldIn);
    }


    public static AttributeSupplier.Builder prepareAttributes() {
        return EntityUtil.getAttrBuilder(1, MAX_HP, 32, 0.2,1,1);
    }


    final int MIN_ATTACK_TICK = 3;

    //seconds
    float timeExpected(int playerCount) {
        return 20f + playerCount;
    }

    float getExpectedDPS(int playerCount) {
        return MAX_HP / (playerCount * timeExpected(playerCount));
    }

    //"K", single hit is K * DPS
    float singleHitDamageFactor = 1;
    //The more, the steeper damage curve
    float steepFactor = 5;

    //Damage Adjust
    float convertDamage(float raw) {
        int playerCount = level.players().size();
        float maxDamage = singleHitDamageFactor * getExpectedDPS(playerCount);
        return maxDamage - (steepFactor * maxDamage) / (raw + steepFactor);
    }

    HashMap<UUID, Integer> invulnerable = new HashMap<>();

    //Update
    @Override
    public void aiStep() {
        super.aiStep();
        handleInvincibleTick();
        if (!level.isClientSide) {
            handleStateMachine();
        }
    }

    private void handleInvincibleTick() {
        for (UUID uuid : invulnerable.keySet()) {
            int cur = invulnerable.get(uuid);
            if (invulnerable.get(uuid) > 0) {
                invulnerable.put(uuid, cur - 1);
            }
        }
    }

    void handleStateMachine() {
        if (curStateTick < preWarmTicks) {
            //do nothing, prewarm state
        } else {
            stateTick();
        }

        curStateTick++;
        if (curStateTick >= maxStateTick) {
            nextState();
            curStateTick = 0;
            prewarmBegin();
        }
    }

    void prewarmBegin() {

    }

    void stateTick() {
//        if (!level.isClientSide) {
//            Main.Log("State = %s", curState);
//        }

        updateTarget();
//        curState = BossState.BARRAGE_ANTI_AIR;

        switch (curState) {
            case NONE -> {
            }
            case BARRAGE_1 -> {
                for (float angle = 0; angle < 360f; angle += 90f)
                {
                    shootFireball(angle);
                }
            }
            case BARRAGE_2 -> {
                for (float angle = 45f; angle < 360f; angle += 90f)
                {
                    shootFireball(angle);
                }
            }
            case BARRAGE_3 -> {
                for (float angle = 0; angle < 360f; angle += 45f)
                {
                    shootFireball(angle);
                }
            }
            case BARRAGE_4 -> {
                shootFireballAimed(0);
            }
            case BARRAGE_ANTI_AIR -> {

                if (!level.isClientSide) {
                    boolean hasTarget = targetList.size() > 0;
                    if (hasTarget) {
                        if (level.getGameTime() % 10 == 0) {
                            for (Entity target : targetList)
                            {
                                if (target instanceof Player player)
                                {
                                    if (player.isSpectator())
                                    {
                                        continue;
                                    }
                                }

                                if (!target.isAlive())
                                {
                                    continue;
                                }

                                Vec3 dirRaw =(target.getEyePosition().subtract(getEyePosition())).normalize();
                                Vec3 dir = dirRaw.scale(getShootAccel());


                                Entity entity;
                                if (target.getEyePosition().y - getEyePosition().y > 2)
                                {
                                    entity =
                                            new EntityMJDSBulletPierece(
                                                    level,
                                                    this.getX()+dir.x * 0.5f,
                                                    this.getEyeY()+dir.y * 0.5f,
                                                    this.getZ()+dir.z * 0.5f,
                                                    dir.x,
                                                    dir.y,
                                                    dir.z);
                                }
                                else {
                                    entity =
                                            new EntityMJDSBulletKB(
                                                    level,
                                                    this.getX() + dirRaw.x,
                                                    this.getEyeY() + dirRaw.y,
                                                    this.getZ() + dirRaw.z,
                                                    dirRaw.x * 0.5f,
                                                    dirRaw.y * 0.5f,
                                                    dirRaw.z * 0.5f);
                                }


                                level.addFreshEntity(entity);
                            }

                            playSound(ModSounds.MONSTER_SHOOT_1.get(), 2f, 1f);
                        }
                    }
                    else {
                        for (float angle = 0; angle < 360f; angle += 10f)
                        {
                            shootFireballFloor(angle);
                        }
                    }
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + curState);
        }

        float hpRatio = getHealth() / getMaxHealth();
        if (revivalSkillLeft > 0 && hpRatio <= 0.5f)
        {
            revivalSkillLeft--;
            reviveNearbyMinions();
        }
        if (hpRatio <= 0.6f)
        {
             setRemainingFireTicks(20);
        }
    }

    //Skill: revive nearby minions
    void reviveNearbyMinions()
    {
        if (!level.isClientSide)
        {
            List<Entity> entityList = EntityUtil.getEntitiesWithinAABB(level,
                    getEyePosition(), 16f, EntityUtil.NON_SPEC);
            for (Entity entity:
                    entityList) {

                if (entity instanceof EntityRevivalMist mist)
                {
                    LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level);
                    if (lightningbolt != null)
                    {
                        lightningbolt.moveTo(mist.getEyePosition());
                        lightningbolt.setVisualOnly(true);
                        level.addFreshEntity(lightningbolt);
                    }
                    mist.reviveAndSuicide();
                }
            }
        }
    }


    //fireballs---------------

    float shootAccel = 0.1f;

    public float getShootAccel() {
        return shootAccel;
    }

    //yaw is in degrees
    void shootFireball(float yaw) {
        if (level.getGameTime() % 7 != 0) {
            return;
        }

        float accel = getShootAccel();
        float yawInRad = (float) (Math.toRadians(yaw));
        SmallFireball fireball = new SmallFireball(level,
                this.getX(),
                this.getEyeY(),
                this.getZ(),
                accel * Math.cos(yawInRad),
                0,
                accel * Math.sin(yawInRad));
        fireball.setOwner(this);
        level.addFreshEntity(fireball);
    }

    void shootFireballFloor(float yaw) {
        if (level.getGameTime() % 20 != 0) {
            return;
        }

        float accel = getShootAccel();
        float yawInRad = (float) (Math.toRadians(yaw));
        SmallFireball fireball = new SmallFireball(level,
                this.getX(),
                this.getY()+0.03f,
                this.getZ(),
                accel * Math.cos(yawInRad),
                0,
                accel * Math.sin(yawInRad));
        fireball.setOwner(this);
        level.addFreshEntity(fireball);
    }

    Entity mainTarget = null;
    List<Entity> targetList = new ArrayList<>();
    static float RANGE = 64f;
    void updateTarget()
    {
        if (checkTargetValid(mainTarget))
        {
            //ok, continue
        }
        else {
            //find next target
            List<Entity> entities = level.getEntities(this, CommonFunctions.ServerAABB(getEyePosition(), 4), LIVING_ENTITY_STILL_ALIVE);
            for (Entity entity:
                 entities) {
                if (checkTargetValid(entity))
                {
                    mainTarget = entity;
                    return;
                }
            }
            entities = level.getEntities(this, CommonFunctions.ServerAABB(getEyePosition(), RANGE), LIVING_ENTITY_STILL_ALIVE);
            for (Entity entity:
                    entities) {
                if (checkTargetValid(entity))
                {
                    mainTarget = entity;
                    return;
                }
            }
            mainTarget = null;
        }

    }

    static final float MAX_DIST = 32f * 32f;
    boolean checkTargetValid(Entity entity)
    {
        //todo: handle creative & spec
        if (entity == null)
        {
            return false;
        }
        else if (entity.isRemoved())
        {
            return false;
        }
        else if (entity == this) {
            return false;
        } else if (entity.distanceToSqr(this) > MAX_DIST)
        {
            return false;
        } else if (entity instanceof Player)
        {
            Player player = (Player) entity;
            if (player.isSpectator())
            {
                return false;
            }
            return true;
        } else if (entity instanceof AbstractIllager)
        {
            return  true;
        }
        return false;
    }


    void shootFireballAimed(float error) {
        if (level.getGameTime() % 7 != 0) {
//            Main.Log("time:%d", level.getGameTime());
            return;
        }

        if (mainTarget != null)
        {
            Entity target = mainTarget;
            Vec3 dir = target.position().subtract(position()).normalize();

            float accel = getShootAccel();

            Fireball fireball = new SmallFireball(level,
                    this.getX(),
                    this.getEyeY(),
                    this.getZ(),
                    accel * dir.x,
                    accel * dir.y,
                    accel * dir.z);
//            LargeFireball fireball = new LargeFireball(level,
//                    this,
//                    accel * dir.x,
//                    accel * dir.y,
//                    accel * dir.z, 2);
            fireball.setOwner(this);
            level.addFreshEntity(fireball);
        }
    }


    void nextState() {
        //todo: prevent state repeat
        curState = BossState.values()[random.nextInt(BossState.values().length)];
    }


    @Override
    public void move(MoverType p_19973_, Vec3 velocity) {
        super.move(p_19973_, new Vec3(0, velocity.y, 0));
    }

    @Override
    public void teleportTo(double p_19887_, double p_19888_, double p_19889_) {
        //do nothing
        return;
    }

    @Override
    public void knockback(double p_147241_, double p_147242_, double p_147243_) {
        //do nothing
        return;
    }

    @Override
    public void kill() {
        forceKilled = true;
        super.kill();
    }

    @Override
    public boolean hurt(DamageSource damageSource, float damage) {
        if (forceKilled) {
            return super.hurt(damageSource, damage);
        }

        if (damageSource == DamageSource.ON_FIRE)
        {
            return false;
        }

        if (damageSource == DamageSource.IN_FIRE)
        {
            return false;
        }

        Entity entity = damageSource.getEntity();
        if (entity instanceof LivingEntity)
        {
            if (entity instanceof Player) {
                UUID uuid1 = entity.getUUID();
                if (uuid1 != null) {
                    if (invulnerable.containsKey(uuid1) && invulnerable.get(uuid1) > 0) {
                        return false;
                    } else {
                        invulnerable.put(uuid1, MIN_ATTACK_TICK);
                    }
                }
            }

            if (checkTargetValid(entity))
            {
                if (!targetList.contains(entity))
                {
                    targetList.add(entity);
                }
                if (mainTarget == null)
                {
                    mainTarget = entity;
                }
            }
        }


        boolean result = super.hurt(damageSource, convertDamage(damage));
        if (result) {
            invulnerableTime = 0;
        }
        return result;
    }

    //AI
    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    //-------------------------------
    //NBT
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
//        stealing = tag.getBoolean("Stealing");
    }

    @Override
    public boolean save(CompoundTag tag) {
//        tag.putBoolean("Stealing", stealing);
        return super.save(tag);
    }

    int hpBarDivider = 10;
    int revivalSkillLeft = 1;

    //Boss Bar
    private final ServerBossEvent bossEvent =
            (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(),
                    BossEvent.BossBarColor.RED,
                    BossEvent.BossBarOverlay.PROGRESS))
                    .setDarkenScreen(true)
                    .setPlayBossMusic(true)
                    .setCreateWorldFog(true);

    void tickBossBar() {
        float perBar = 1 / (float)hpBarDivider;
        float percent = getHealth() / getMaxHealth();
        this.bossEvent.setProgress(percent % (perBar) * hpBarDivider);
        this.bossEvent.setColor(BossEvent.BossBarColor.RED);
        this.bossEvent.setName(Component.translatable(this.getType().getDescription().getString() + String.format(" %d%%", (int)(percent * 100))));
//        Main.Log("HP = %s", getHealth());
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        tickBossBar();
    }

    public void startSeenByPlayer(ServerPlayer p_31483_) {
        super.startSeenByPlayer(p_31483_);
        this.bossEvent.addPlayer(p_31483_);
    }

    public void stopSeenByPlayer(ServerPlayer p_31488_) {
        super.stopSeenByPlayer(p_31488_);
        this.bossEvent.removePlayer(p_31488_);
    }

    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    public boolean canBreatheUnderwater() {
        return true;
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
    protected void dropCustomDeathLoot(DamageSource p_21385_, int p_21386_, boolean p_21387_) {
        super.dropCustomDeathLoot(p_21385_, p_21386_, p_21387_);
        int playerCount = level.players().size();
        List<Entity> entityList = EntityUtil.getEntitiesWithinAABB(level,
                getEyePosition(), 16f, EntityUtil.NON_SPEC);
        for (Entity entity:
                entityList) {

            if (entity instanceof Player player)
            {
                ItemStack itemStack;
                switch (random.nextInt(4))
                {
                    case 0:
                        itemStack = new ItemStack(Items.CHAINMAIL_HELMET, 1);
                        break;
                    case 1:
                        itemStack = new ItemStack(Items.CHAINMAIL_CHESTPLATE, 1);
                        break;
                    case 2:
                        itemStack = new ItemStack(Items.CHAINMAIL_LEGGINGS, 1);
                        break;
                    case 3:
                        itemStack = new ItemStack(Items.CHAINMAIL_BOOTS, 1);
                        break;
                    default:
                        itemStack = null;
                        break;
                }

                if (itemStack != null)
                {
                    EnchantmentHelper.enchantItem(random, itemStack, playerCount * 2 + 10, true);
                    //this.spawnAtLocation(itemStack);

                    CommonFunctions.SafeSendMsgToPlayer(player, MessageDef.BOSS_DROP, itemStack.getDisplayName());
                    player.addItem(itemStack);
                    AdvancementUtil.giveAdvancement(player, AdvancementUtil.ACHV_ROOT);
                    level.playSound(null, getOnPos(), ModSounds.PICKUP.get(), SoundSource.HOSTILE, 1f,1f);
                }

            }
        }
    }
}
