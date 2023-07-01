package com.deeplake.exp1182.entities.mjds;

import com.deeplake.exp1182.util.CommonDef;
import com.deeplake.exp1182.util.CommonFunctions;
import com.deeplake.exp1182.util.EntityUtil;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.UUID;

import static com.deeplake.exp1182.util.IDLNBTDef.SPAWNER_NBT;
import static com.deeplake.exp1182.util.IDLNBTDef.SPAWNER_TYPE;

public class EntityRevivalMist extends Entity {
    EntityType entityType;
    CompoundTag entityNBT = new CompoundTag();

    //That is not dead which can eternal lie
    //And with strange aeons even death may die

    public EntityRevivalMist(EntityType<?> p_i48580_1_, Level p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
    }

    public void setWith(LivingEntity livingEntity)
    {
        entityType = livingEntity.getType();
        livingEntity.saveWithoutId(entityNBT);
        //IdlFramework.Log("Rememebered: %s@%s\n%s",entityType.toString(), getEyePosition(0), entityNBT.toString());
    }

    @Override
    public void tick() {
        super.tick();
        //has a 3 sec cooldown
        if (!level().isClientSide && isAlive() && tickCount > CommonDef.TICK_PER_SECOND * 3)
        {
            //It will revive if it's outside the screen...
            if (EntityUtil.getEntitiesWithinAABB(level(), EntityType.PLAYER, getEyePosition(0),  32, EntityUtil.NON_SPEC).size() == 0)
            {
                //But will not revive when it's too far from players. Minecraft will despawn it, and thus keep cycling.
                if (EntityUtil.getEntitiesWithinAABB(level(), EntityType.PLAYER, getEyePosition(0),  64, EntityUtil.NON_SPEC).size() != 0)
                {
                    reviveAndSuicide();
                }
            }
        }
        else {
            level().addParticle(ParticleTypes.CLOUD, getX() + CommonFunctions.flunctate(0, 0.5f, random),
                    getY() + CommonFunctions.flunctate(0.5f, 0.5f, random),
                    getZ()+ CommonFunctions.flunctate(0, 0.5f, random),
                    0, 0, 0);
        }
    }

    public void reviveAndSuicide() {
        Entity entity = entityType.create(level());
        if (entity instanceof LivingEntity)
        {
            entity.load(entityNBT);
            entity.copyPosition(this);
            entity.setUUID(UUID.randomUUID());
            ((LivingEntity) entity).deathTime = 0;
            entity.revive();
            ((LivingEntity) entity).setHealth(((LivingEntity) entity).getMaxHealth());
            level().addFreshEntity(entity);
        }
        //IdlFramework.Log("...And with strange aeons even death may die. Recovered: %s@%s", entityType.toString(), getEyePosition(0));
        remove(RemovalReason.KILLED);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        Optional<EntityType<?>> entityType = EntityType.byString(nbt.getString(SPAWNER_TYPE));
        if (entityType.isPresent())
        {
            this.entityType = entityType.get();

            Tag rawNBT = nbt.get(SPAWNER_NBT);
            if (rawNBT != null)
            {
                try {
                    this.entityNBT = TagParser.parseTag(rawNBT.getAsString());
                }  catch (CommandSyntaxException e) {
                    e.printStackTrace();
                    this.entityNBT = new CompoundTag();
                }
            }else {
                this.entityNBT = new CompoundTag();
            }
        }
        else {
            remove(RemovalReason.KILLED);
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        if (entityType != null)
        {
            nbt.putString(SPAWNER_TYPE, ForgeRegistries.ENTITY_TYPES.getKey(entityType).toString());
        }

        nbt.putString(SPAWNER_NBT, entityNBT.toString());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
