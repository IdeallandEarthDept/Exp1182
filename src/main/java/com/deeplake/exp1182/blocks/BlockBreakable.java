package com.deeplake.exp1182.blocks;

import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.util.AdvancementUtil;
import com.deeplake.exp1182.util.CommonDef;
import com.deeplake.exp1182.util.CommonFunctions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import org.jetbrains.annotations.NotNull;

public class BlockBreakable extends BaseBlockMJDS implements IBlockMJDS{
    public BlockBreakable(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState p_49862_) {
        super.destroy(levelAccessor, blockPos, p_49862_);
        levelAccessor.addParticle(ParticleTypes.EXPLOSION,
                blockPos.getX(),blockPos.getY(),blockPos.getZ(),
                0,0,0);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand p_60507_, @NotNull BlockHitResult result) {
        Direction direction = result.getDirection();
        switch (direction)
        {
            case DOWN:
                if (AdvancementUtil.hasAdvancement(player, AdvancementUtil.BALLOON))
                {
                    playerBreak(state, level, pos, player);
                }
                else {
                    CommonFunctions.SafeSendMsgToPlayer(player, "exp1182.msg.req_balloon");
                }
            break;
            case UP:
                CommonFunctions.SafeSendMsgToPlayer(player, "exp1182.msg.req_mine");
                break;
            case NORTH: case SOUTH:
            case WEST: case EAST:
                if (AdvancementUtil.hasAdvancement(player, AdvancementUtil.FOOD))
                {
                    playerBreak(state, level, pos, player);
                }
                else {
                    CommonFunctions.SafeSendMsgToPlayer(player, "exp1182.msg.req_food");
                }
            break;
            default :
                throw new IllegalStateException("Unexpected value: " + direction);

        }
//        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

        return InteractionResult.SUCCESS;
    }

    public static void playerBreak(BlockState state, Level level, BlockPos pos, Player player)
    {
        if (!level.isClientSide)
        {
            BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(level, pos, state, player);
            MinecraftForge.EVENT_BUS.post(event);
            if (!event.isCanceled())
            {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                state.getBlock().destroy(level, pos, state);
                AdvancementUtil.giveAdvancement(player, AdvancementUtil.BREAKABLE);
                level.playSound(null, pos, ModSounds.MONSTER_DEATH.get(), SoundSource.BLOCKS, 1f, 1f);
            }
        }
    }
}
