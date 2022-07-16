package com.deeplake.exp1182.util;

import com.deeplake.exp1182.entities.mjds.IMjdsMonster;
import com.google.common.base.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class EntityUtil {
//    public static final ITag.INamedTag<EntityType<?>> BOSS = bind("backtones:bosses");

//    public static boolean isBoss(LivingEntity creature)
//    {
//        //EntityTypeTags
//        return creature.getType().is(BOSS);
//    }

    public static List<Entity> getEntitiesWithinAABB(Level world, AABB aabb)
    {
        return world.getEntities(null, aabb);
    }

    public static List<Entity> getEntitiesWithinAABB(Level world, AABB aabb,@NotNull Predicate<? super Entity> filter)
    {
        return world.getEntities((Entity) null, aabb, filter);
    }

    public static List<Entity> getEntitiesWithinAABB(Level world, Vec3 center, double range, @NotNull Predicate<? super Entity> filter)
    {
        return world.getEntities((Entity) null, getServerAABB(center, range), filter);
    }

    public static <T extends Entity> List<T> getEntitiesWithinAABB(Level world, EntityType<T> clazz, AABB aabb, @NotNull Predicate<? super T > filter)
    {
        return world.getEntities(clazz, aabb, filter);
    }

    public static <T extends Entity> List<T> getEntitiesWithinAABB(Level world, @NotNull EntityType<T> clazz, Vec3 center, double range, @NotNull Predicate <? super T > filter)
    {
        return world.getEntities(clazz, getServerAABB(center, range), filter);
    }

    @NotNull
    public static AABB getServerAABB(Vec3 center, double range) {
        return CommonFunctions.ServerAABB(center.add(new Vec3(-range, -range, -range)), center.add(new Vec3(range, range, range)));
    }

    public static <T extends Entity> List<T> getEntitiesWithinAABBignoreY(Level world, EntityType<T> clazz, Vec3 center, double range, @Nullable Predicate <? super T > filter)
    {
        return world.getEntities(clazz, CommonFunctions.ServerAABBignoreY(center, (float) range), filter);
    }

    public static final Predicate<Entity> ALL = new Predicate<Entity>()
    {
        public boolean apply(@Nullable Entity entity)
        {
            return entity != null;
        }
    };

    public static final Predicate<Entity> IS_MJDS = new Predicate<Entity>()
    {
        public boolean apply(@Nullable Entity entity)
        {
            return entity instanceof IMjdsMonster;
        }
    };

    //not null and not a spec player.
    public static final Predicate<Entity> NON_SPEC = new Predicate<Entity>()
    {
        public boolean apply(@Nullable Entity entity)
        {
            return entity != null && !entity.isSpectator();
        }
    };

    public static final Predicate<Entity> UNDER_SKY = new Predicate<Entity>()
    {
        public boolean apply(@Nullable Entity entity)
        {
            return entity != null && entity.level.canSeeSky(new BlockPos(entity.getX(), entity.getY() + (double)entity.getEyeHeight(), entity.getZ()));
        }
    };

    public static boolean isSunlit(Entity entity)
    {
        float f = entity.getBrightness();
        return  f > 0.5F && UNDER_SKY.apply(entity);
    }

    public static boolean isMoonlit(Entity entity)
    {
        if (entity == null)
        {
            return false;
        }

        int tickInDay = (int) (entity.level.getGameTime() % 24000);
        if (tickInDay > 167 && tickInDay < 11834)
        {
            return false;
        }
        return UNDER_SKY.apply(entity);
    }
//
//    public static final Predicate<LivingEntity> LIVING = new Predicate<LivingEntity>()
//    {
//        public boolean apply(@Nullable LivingEntity entity)
//        {
//            return entity != null && !entity.isEntityUndead();
//        }
//    };
//
//    public static final Predicate<LivingEntity> UNDEAD = new Predicate<LivingEntity>()
//    {
//        public boolean apply(@Nullable LivingEntity entity)
//        {
//            return entity != null && entity.isEntityUndead();
//        }
//    };
//
//
//    public static final Predicate<LivingEntity> LIVING_HIGHER = new Predicate<LivingEntity>()
//    {
//        public boolean apply(@Nullable LivingEntity entity)
//        {
//            return entity != null && !entity.isEntityUndead() && !(entity instanceof EntityAnimal);
//        }
//    };
//
//    public static final Predicate<LivingEntity> USING_MODDED = new Predicate<LivingEntity>()
//    {
//        public boolean apply(@Nullable LivingEntity entity)
//        {
//            if (entity == null)
//            {
//                return false;
//            }
//
//            for (EntityEquipmentSlot slot:
//                    EntityEquipmentSlot.values()) {
//                ItemStack stack = entity.getItemStackFromSlot(slot);
//                if (stack.isEmpty())
//                {
//                    continue;
//                }
//
//                ResourceLocation regName = stack.getItem().getRegistryName();
//                if (!regName.getResourceDomain().equals(CommonDef.MINECRAFT))
//                {
//                    return true;
//                }
//            }
//
//            return false;
//        }
//    };

    //Note: this returns -1 if no buff.
    public static int getBuffLevel(LivingEntity livingBase, MobEffect potion) {
        if (livingBase == null || potion == null) {
//            Idealland.LogWarning(TRYING_TO_APPLY_ILLEGAL_POTION);
            return -1;
        }
        MobEffectInstance effect = livingBase.getEffect(potion);
        if (effect == null) {
            return -1;
        } else {
            return effect.getAmplifier();
        }
    }

    public static void simpleKnockBack(float power, Entity source, LivingEntity target)
    {
        if ((source.getX() - target.getX()) == 0 && (source.getZ() - target.getZ()) == 0) {
            //prevent div 0
            target.knockback(power, 1, 0);
        } else {
            target.knockback(power, (source.getX() - target.getX()), (source.getZ() - target.getZ()));
        }
    }

    @NotNull
    public static AttributeSupplier.Builder getAttrBuilder(double atk, double hpMax, double sight, double speed, double kb, double kb_r) {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, atk)
                .add(Attributes.MAX_HEALTH, hpMax)
                .add(Attributes.FOLLOW_RANGE, sight)
                .add(Attributes.MOVEMENT_SPEED, speed)
                .add(Attributes.ATTACK_KNOCKBACK, kb)
                .add(Attributes.KNOCKBACK_RESISTANCE, kb_r);
    }

    static final float STANDARD_ATK = 4.0f;

    @NotNull
    public static AttributeSupplier.Builder getAttrBuilder(double atk, double hits, double kb_r) {
        return getAttrBuilder(atk, hits * STANDARD_ATK, 32, 0.3, 0.3, kb_r);
    }
}
