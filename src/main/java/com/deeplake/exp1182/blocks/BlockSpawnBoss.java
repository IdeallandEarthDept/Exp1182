package com.deeplake.exp1182.blocks;

import com.deeplake.exp1182.blocks.tileentity.TileEntitySpawnBoss;
import com.deeplake.exp1182.desgin.WorldBossSpawner;
import com.deeplake.exp1182.entities.EntityWorldBoss;
import com.deeplake.exp1182.setup.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import static com.deeplake.exp1182.util.MessageDef.NOTIFY_NEXT;

public class BlockSpawnBoss extends Block implements EntityBlock {
    public BlockSpawnBoss(Properties p_49795_) {
        super(p_49795_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntitySpawnBoss(pos, state);
    }


    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }
        return (lvl, pos, blockState, t) -> {
            if (t instanceof TileEntitySpawnBoss tile) {
                tile.tickServer();
            }
        };
    }
}
