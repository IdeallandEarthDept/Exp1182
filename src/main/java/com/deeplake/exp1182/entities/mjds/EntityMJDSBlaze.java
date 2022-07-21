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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;

import static com.deeplake.exp1182.events.EventsBirthHelper.makeBannerShield;
import static com.deeplake.exp1182.util.IDLNBTDef.SPAWN_POINT;
import static net.minecraft.nbt.NbtUtils.readBlockPos;
import static net.minecraft.nbt.NbtUtils.writeBlockPos;

public class EntityMJDSBlaze extends Blaze implements IMjdsMonster{
    public BlockPos spawnPoint;
    public EntityMJDSBlaze(EntityType<? extends Blaze> p_33570_, Level p_33571_) {
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
        spawnPoint = blockPosition();
    }
    @Override
    public void checkDespawn() {
        super.checkDespawn();
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
    @Override
    public InteractionResult interactAt(Player playerEntity, Vec3 p_19981_, InteractionHand hand) {
        if (!playerEntity.level.isClientSide && playerEntity.isCreative())
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

    @Override
    public BlockPos getRespawn() {
        return spawnPoint;
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return EntityUtil.getAttrBuilder(6, 3, 0.8f);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_32146_, DifficultyInstance p_32147_, MobSpawnType p_32148_, @Nullable SpawnGroupData p_32149_, @Nullable CompoundTag p_32150_) {
        p_32149_ = super.finalizeSpawn(p_32146_, p_32147_, p_32148_, p_32149_, p_32150_);
        spawnPoint = blockPosition();
        return p_32149_;
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

        ItemStack itemStack;
        switch (random.nextInt(4))
        {
            case 0:
                itemStack = new ItemStack(Items.ARROW, 5);
                break;
            case 1:
                itemStack = new ItemStack(Items.BLAZE_ROD, 1);
                break;
            case 2:
                itemStack = new ItemStack(Items.POTION, 1);
                PotionUtils.setPotion(itemStack, Potions.HEALING);
                break;
            case 3:
                itemStack = new ItemStack(Items.BOW, 1);
                itemStack.enchant(Enchantments.FLAMING_ARROWS, 1);
                if (random.nextInt(100) == 0)
                {
                    itemStack.enchant(Enchantments.INFINITY_ARROWS, 1);
                }
                if (random.nextInt(10) == 0)
                {
                    itemStack.enchant(Enchantments.POWER_ARROWS, 2);
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
}
