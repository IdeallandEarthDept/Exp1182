package com.deeplake.exp1182.entities;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.util.CommonDef;
import com.deeplake.exp1182.util.CommonFunctions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.*;

import static net.minecraft.world.entity.EntitySelector.LIVING_ENTITY_STILL_ALIVE;

public class EntityWorldBoss extends Monster {
    static final float MAX_HP = 1024;

    //AI StateMachine
    int curStateTick = 0;//how many ticks this state have lasted
    int maxStateTick = CommonDef.TICK_PER_SECOND * 6;//how many ticks a state may last
    int preWarmTicks = CommonDef.TICK_PER_SECOND * 1;

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
        return Monster.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 1.0)
                .add(Attributes.MAX_HEALTH, MAX_HP)
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

    final int MIN_ATTACK_TICK = 3;

    //seconds
    float timeExpected(int playerCount) {
        return 60f;
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
        if (!level.isClientSide) {
            Main.Log("State = %s", curState);
        }

        updateTarget();

        shootFireballAimed(0);

        switch (curState) {
            case NONE -> {
            }
            case BARRAGE_1 -> {
                shootFireball(0);
            }
            case BARRAGE_2 -> {
                shootFireball(90);
            }
            case BARRAGE_3 -> {
                shootFireball(180);
            }
            case BARRAGE_4 -> {
                shootFireball(270);
            }
            case BARRAGE_ANTI_AIR -> {
                shootFireball(90);
                shootFireball(270);
            }
            default -> throw new IllegalStateException("Unexpected value: " + curState);
        }
    }

    float shootAccel = 0.1f;

    public float getShootAccel() {
        return 0.1f;
    }

    //yaw is in degrees
    void shootFireball(float yaw) {
        if (level.getGameTime() % 7 != 0) {
            return;
        }

        float accel = getShootAccel();
        float yawInRad = (float) (Math.toRadians(yaw));
        SmallFireball fireball = new SmallFireball(level,
                this,
                accel * Math.cos(yawInRad),
                0,
                accel * Math.sin(yawInRad));
        level.addFreshEntity(fireball);
    }

    List<Entity> targetList = new ArrayList<>();
    static float RANGE = 64f;
    void updateTarget()
    {
        targetList = level.getEntities(this, CommonFunctions.ServerAABB(getEyePosition().add(0,RANGE,0), RANGE), LIVING_ENTITY_STILL_ALIVE);
    }

    void shootFireballAimed(float error) {
        if (level.getGameTime() % 7 != 0) {
            return;
        }

        if (targetList.size() > 0)
        {
            Entity target = targetList.get(0);
            Vec3 dir = target.position().subtract(position()).normalize();

            float accel = getShootAccel();

            LargeFireball fireball = new LargeFireball(level,
                    this,
                    accel * dir.x,
                    accel * dir.y,
                    accel * dir.z, 2);
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

        Entity entity = damageSource.getEntity();
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

    //Boss Bar
    private final ServerBossEvent bossEvent =
            (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(),
                    BossEvent.BossBarColor.RED,
                    BossEvent.BossBarOverlay.PROGRESS))
                    .setDarkenScreen(true)
                    .setPlayBossMusic(true)
                    .setCreateWorldFog(true);

    void tickBossBar() {
        this.bossEvent.setProgress(getHealth() / getMaxHealth() % (0.1f) * 10);
        this.bossEvent.setColor(BossEvent.BossBarColor.RED);
        Main.Log("HP = %s", getHealth());
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
}
