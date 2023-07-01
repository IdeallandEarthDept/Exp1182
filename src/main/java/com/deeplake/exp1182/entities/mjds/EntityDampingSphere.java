package com.deeplake.exp1182.entities.mjds;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.util.CommonFunctions;
import com.deeplake.exp1182.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.deeplake.exp1182.util.CommonDef.CHUNK_MASK;
import static com.deeplake.exp1182.util.CommonDef.CHUNK_SIZE;
import static com.deeplake.exp1182.util.EntityUtil.ALL;
import static com.deeplake.exp1182.util.IDLNBTDef.*;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class EntityDampingSphere extends Entity {

    float omegaX = 1f;
    float omegaY = 2f;
    float omegaZ = 1.5f;

    float thetaX = 0f;
    float thetaY = 0f;
    float thetaZ = (float) (Math.PI / 3f);

    public static class RecordChange{
        public BlockState state = Blocks.AIR.defaultBlockState();
        public BlockPos pos = BlockPos.ZERO;

        public RecordChange(BlockState state, BlockPos pos) {
            this.state = state;
            this.pos = pos;
        }
    }

    List<RecordChange> changes = new ArrayList<>();
    protected Logger logger = LogManager.getLogger();

    public EntityDampingSphere(EntityType<?> p_i48580_1_, Level p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
    }

    public boolean isInDistance(BlockPos pos)
    {
        return (pos.getX() | CHUNK_MASK) == ((int)getX() | CHUNK_MASK)
                && (pos.getZ() | CHUNK_MASK) == ((int)getZ() | CHUNK_MASK);
    }

    @SubscribeEvent
    public static void onBlockChange(BlockEvent.BreakEvent breakEvent)
    {
        Level level1 = breakEvent.getPlayer().level();
        //if (!breakEvent.getPlayer().isCreative())
        if (level1 != null)
        {
            BlockPos pos = breakEvent.getPos();
            AABB aabb = new AABB(
                    pos.getX() - 8,
                    -9999,
                    pos.getZ() - 8,

                    pos.getX() + 8,
                    9999,
                    pos.getZ() + 8
            );

            List<EntityDampingSphere> spheres = level1.getEntitiesOfClass(EntityDampingSphere.class, aabb);
            for (EntityDampingSphere sphere : spheres)
            {
//                if (sphere.isInDistance(pos))
                {
                    sphere.changes.add(new RecordChange(breakEvent.getState(), pos));
                }
            }

        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide)
        {
            if (CommonFunctions.isSecondTick(level()))
            {
                if (EntityUtil.getEntitiesWithinAABBignoreY(level(),
                        EntityType.PLAYER,
                        getEyePosition(0),
                        CHUNK_SIZE,
                        ALL).size() == 0)
                {
                    for (int i = 0; i <= 16; i++)
                    {
                        if (changes.size() > 0)
                        {
                            RecordChange change = changes.get(0);
                            level().setBlock(change.pos, change.state, 3);
                            changes.remove(change);
                        }
                        else {
                            break;
                        }
                    }
                }
            }
        }
        else {
            float x = (float) (getX() + Math.sin(thetaX));
            float y = (float) (getY() + Math.sin(thetaY));
            float z = (float) (getZ() + Math.sin(thetaZ));
            thetaX+=omegaX;
            thetaY+=omegaY;
            thetaZ+=omegaZ;

            level().addParticle(ParticleTypes.EFFECT, x, y, z, 0, 0, 0);
        }
    }

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.EntityPlaceEvent placeEvent)
    {
        //set block state won't trigger this, but player's regular action can
        Entity entity = placeEvent.getEntity();
        if (entity instanceof Player && ((Player) entity).isCreative())
        {
            return;
        }

        BlockPos pos = placeEvent.getPos();
        if (isInDistance(pos))
        {
            level().destroyBlock(pos, true);
            //placeEvent.setCanceled(true);//wont drop the block
            if (level().isClientSide)
            {
                level().addParticle(ParticleTypes.EXPLOSION, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 0, 0, 0);
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        //this.entityData.set(COUNTER, 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_70037_1_) {
        ListTag nbtRecordList = p_70037_1_.getList(CHANGE_RECORD, 10);

        for (Tag v: nbtRecordList)
        {
            try
            {
                CompoundTag compoundNBT = (CompoundTag) v;
                RecordChange recordChange = new RecordChange(
                        NbtUtils.readBlockState((CompoundTag) compoundNBT.get(BLOCK_STATE)),
                        NbtUtils.readBlockPos((CompoundTag) compoundNBT.get(BLOCK_POS))
                );
                changes.add(recordChange);
            }
            catch (Exception e)
            {
                Main.LogWarning("Cannot resolve NBT:", v.toString());
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_213281_1_) {
        ListTag nbtRecordList = new ListTag();

        for (RecordChange v: changes)
        {
            CompoundTag nbtRecord = new CompoundTag();
            nbtRecord.put(BLOCK_STATE, NbtUtils.writeBlockState(v.state));
            nbtRecord.put(BLOCK_POS, NbtUtils.writeBlockPos(v.pos));
            nbtRecordList.add(nbtRecord);
        }

        p_213281_1_.put(CHANGE_RECORD, nbtRecordList);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}

