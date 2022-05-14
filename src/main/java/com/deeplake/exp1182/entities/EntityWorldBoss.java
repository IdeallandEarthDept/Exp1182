package com.deeplake.exp1182.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.UUID;

public class EntityWorldBoss extends Monster {
    static final float MAX_HP = 1024;

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
    float timeExpected(int playerCount)
    {
        return 300f;
    }

    float getExpectedDPS(int playerCount)
    {
        return MAX_HP / (playerCount * timeExpected(playerCount));
    }

    //"K", single hit is K * DPS
    float singleHitDamageFactor = 1/3f;
    //The more, the steeper damage curve
    float steepFactor = 5;
    //Damage Adjust
    float convertDamage(float raw)
    {
        int playerCount = level.players().size();
        float maxDamage = singleHitDamageFactor * getExpectedDPS(playerCount);
        return maxDamage - (steepFactor * maxDamage) / (raw + steepFactor);
    }

    HashMap<UUID, Integer> invulnerable = new HashMap<>();

    @Override
    public void aiStep() {
        super.aiStep();
        for (UUID uuid : invulnerable.keySet())
        {
            int cur = invulnerable.get(uuid);
            if (invulnerable.get(uuid) > 0)
            {
                invulnerable.put(uuid, cur - 1);
            }
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float damage) {
        Entity entity = damageSource.getEntity();
        if (entity instanceof Player)
        {
            UUID uuid1 = entity.getUUID();
            if (uuid1 != null)
            {
                if (invulnerable.containsKey(uuid1) && invulnerable.get(uuid1) > 0)
                {
                    return false;
                }
                else
                {
                    invulnerable.put(uuid1, MIN_ATTACK_TICK);
                }
            }
        }

        boolean result = super.hurt(damageSource, convertDamage(damage));
        if (result)
        {
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

}
