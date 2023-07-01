package com.deeplake.exp1182.entities.mjds;

import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.setup.ModEntities;
import com.deeplake.exp1182.util.DesignUtil;
import com.deeplake.exp1182.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.deeplake.exp1182.events.EventsBirthHelper.makeBannerShield;
import static com.deeplake.exp1182.util.IDLNBTDef.SPAWN_POINT;
import static net.minecraft.nbt.NbtUtils.readBlockPos;
import static net.minecraft.nbt.NbtUtils.writeBlockPos;

public class EntityMJDSSkeleton extends Skeleton implements IMjdsMonster{
    public BlockPos spawnPoint;

    public EntityMJDSSkeleton(EntityType<? extends Skeleton> p_33570_, Level p_33571_) {
        super(p_33570_, p_33571_);
        for (EquipmentSlot slotType :
                EquipmentSlot.values()) {
            setDropChance(slotType, 0f);
        }
        setPersistenceRequired();
    }

    //do not burn under sun
    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

    protected void populateDefaultEquipmentSlots(DifficultyInstance p_180481_1_) {
        super.populateDefaultEquipmentSlots(p_180481_1_);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.setItemSlot(EquipmentSlot.OFFHAND, makeBannerShield(new ItemStack(Items.SHIELD), new ItemStack(Items.WHITE_BANNER)));

        spawnPoint = blockPosition();
    }

    @Override
    public void remove(RemovalReason p_146834_) {
        super.remove(p_146834_);
        if (p_146834_ == RemovalReason.KILLED || p_146834_ == RemovalReason.DISCARDED)
        {
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

//    @Override
//    public void onRemovedFromWorld() {
//        super.onRemovedFromWorld();
//        if (!level.isClientSide && DesignUtil.canRevive(this))
//        {
//            //IdlFramework.Log("That is not dead which can eternal lie...");
//            EntityRevivalMist mist = new EntityRevivalMist(ModEntities.REVIVE_MIST.get(), level);
//            mist.setWith(this);
//            mist.setPos(spawnPoint.getX()+0.5f, spawnPoint.getY()+1f, spawnPoint.getZ()+0.5f);
//            level.addFreshEntity(mist);
//        }
//    }

//    @Override
//    public void die(DamageSource p_70645_1_) {
//        super.die(p_70645_1_);
//    }


    @Override
    public InteractionResult interactAt(Player playerEntity, Vec3 p_19981_, InteractionHand hand) {
        if (!playerEntity.level().isClientSide && playerEntity.isCreative())
        {
            ItemStack stack = playerEntity.getItemInHand(hand);
            if (stack.getItem() instanceof ArmorItem)
            {
                ArmorItem armorItem = (ArmorItem) stack.getItem();
                setItemSlot(armorItem.getSlot(), stack.copy());
            }
            else if (stack.getItem() instanceof ShieldItem)
            {
                setItemSlot(EquipmentSlot.OFFHAND, stack.copy());
            }else if (stack.getItem() instanceof SwordItem)
            {
                setItemSlot(EquipmentSlot.MAINHAND, stack.copy());
            }
        }
        return super.interactAt(playerEntity, p_19981_, hand);
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

    //Deflect arrows from the front
    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event)
    {
        Entity bullet = event.getEntity();
        HitResult rayTraceResult = event.getRayTraceResult();
        if (rayTraceResult.getType().equals(HitResult.Type.ENTITY) && !bullet.level.isClientSide)
        {
            EntityHitResult result = (EntityHitResult) rayTraceResult;
            Entity hurtOne = result.getEntity();

            if (hurtOne instanceof EntityMJDSSkeleton)
            {
                //face on
                if (hurtOne.getViewVector(0f).dot(bullet.getViewVector(0)) < 0)
                {
                    event.setCanceled(true);
                    bullet.setDeltaMovement(bullet.getDeltaMovement().scale(-1));
                }
            }
        }

    }

    @Override
    public BlockPos getRespawn() {
        return spawnPoint;
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return EntityUtil.getAttrBuilder(3, 3, 1f);
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
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        invulnerableTime = 0;
        return super.hurt(p_21016_, p_21017_);
    }
}
