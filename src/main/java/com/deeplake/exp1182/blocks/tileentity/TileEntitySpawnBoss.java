package com.deeplake.exp1182.blocks.tileentity;

import com.deeplake.exp1182.blocks.demo.WorldBossConfig;
import com.deeplake.exp1182.design.WorldBossSpawner;
import com.deeplake.exp1182.entities.EntityWorldBoss;
import com.deeplake.exp1182.setup.ModBlocks;
import com.deeplake.exp1182.setup.ModEntities;
import com.deeplake.exp1182.util.CommonFunctions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import static net.minecraft.world.entity.EntitySelector.LIVING_ENTITY_STILL_ALIVE;

public class TileEntitySpawnBoss extends BlockEntity {
    public TileEntitySpawnBoss(BlockPos pos, BlockState state) {
        super(ModBlocks.TE_SPAWN_BOSS.get(), pos, state);
    }


    //     Called by the block ticker
    public void tickServer() {
        if (WorldBossConfig.ENABLE_BOSS_SPAWN.get() &&  WorldBossSpawner.checkSpawn())
        {
            WorldBossSpawner.executeSpawn();

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


//            player.sendMessage(Component.translatable(NOTIFY_NEXT, WorldBossSpawner.getNextSpawnTimeString())
//                    .withStyle(ChatFormatting.YELLOW), Util.NIL_UUID);
        }
    }
}
