package com.deeplake.exp1182.blocks.tileentity;

import com.deeplake.exp1182.desgin.WorldBossSpawner;
import com.deeplake.exp1182.entities.EntityWorldBoss;
import com.deeplake.exp1182.setup.ModBlocks;
import com.deeplake.exp1182.setup.ModEntities;
import com.deeplake.exp1182.util.CommonFunctions;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

import java.util.List;

import static com.deeplake.exp1182.util.MessageDef.NOTIFY_NEXT;
import static net.minecraft.world.entity.EntitySelector.LIVING_ENTITY_STILL_ALIVE;

public class TileEntitySpawnBoss extends BlockEntity {
    public TileEntitySpawnBoss(BlockPos pos, BlockState state) {
        super(ModBlocks.TE_SPAWN_BOSS.get(), pos, state);
    }


    //     Called by the block ticker
    public void tickServer() {
        if (WorldBossSpawner.checkSpawn())
        {
            Vec3 pos = new Vec3(getBlockPos().getX()+0.5f,
                    getBlockPos().getY()+2,
                    getBlockPos().getZ()+0.5f);

            List<EntityWorldBoss> entities =
                    level.getEntities(
                            ModEntities.WORLD_BOSS.get(),
                            CommonFunctions.ServerAABB(pos, 4),
                            LIVING_ENTITY_STILL_ALIVE);
            if (entities.size() > 0)
            {
                return;
            }


            EntityWorldBoss boss = new EntityWorldBoss(ModEntities.WORLD_BOSS.get(), level);
            boss.setPos(pos);
            level.addFreshEntity(boss);

            WorldBossSpawner.executeSpawn();
//            player.sendMessage(new TranslatableComponent(NOTIFY_NEXT, WorldBossSpawner.getNextSpawnTimeString())
//                    .withStyle(ChatFormatting.YELLOW), Util.NIL_UUID);
        }
    }
}
