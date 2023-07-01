package com.deeplake.exp1182.blocks.mechanics;


import com.deeplake.exp1182.blocks.BaseBlockMJDS;
import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.util.CommonFunctions;
import com.deeplake.exp1182.util.EgoUtil;
import com.deeplake.exp1182.util.MJDSDefine;
import com.deeplake.exp1182.util.MessageDef;
import com.mojang.math.Vector3d;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import static com.deeplake.exp1182.util.MJDSDefine.EnumEgo.POPLON;

public class BaseBlockEgoDoor extends BaseBlockMJDS {

    MJDSDefine.EnumEgo egoReq = MJDSDefine.EnumEgo.POPLON;

    public BaseBlockEgoDoor(MJDSDefine.EnumEgo egoReq) {
        super(Properties.of().isSuffocating((p_235445_0_, p_235445_1_, p_235445_2_) -> false));
        this.egoReq = egoReq;
    }

    static double symmteric(double val, double axis)
    {
        return axis+axis-val;
    }

    float maxDistSqr = 2f*2f;
    float disturbanceY = 0.3f;//make the player fall, so they feel the teleport

    public boolean checkEgo(Player playerEntity)
    {
        return EgoUtil.getEgo(playerEntity) == egoReq;
    }

    Style RED = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.RED));

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player playerEntity, InteractionHand hand, BlockHitResult blockRayTraceResult) {

//        if (!world.isClientSide) {
//            world.playSound(null, pos, ModSounds.ERROR.get(), SoundSource.BLOCKS, 1f, 1f);
//        }

        if (checkEgo(playerEntity))
        {
            if (!world.isClientSide) {
//                if (failCheckDist(world, pos, playerEntity))
//                {
////                    world.playSound(null, pos, ModSounds.ERROR.get(), SoundSource.BLOCKS, 1f, 1f);
//                    return InteractionResult.FAIL;
//                }
                Vec3 thisPos = new Vec3(pos.getX()+0.5f, pos.getY(), pos.getZ()+0.5f);

                Vec3 delta = playerEntity.getPosition(0f).subtract(thisPos).scale(-1);
                if (Math.abs(delta.x) < 0.4f)
                {
                    delta = new Vec3(0, delta.y, delta.z);
                }

                if (Math.abs(delta.z) < 0.4f)
                {
                    delta = new Vec3(delta.x, delta.y, 0);
                }

                playerEntity.teleportTo(thisPos.x + Math.signum(delta.x),
                        playerEntity.getY()+disturbanceY,
                        thisPos.z + Math.signum(delta.z))
                        ;
                playerEntity.level.levelEvent(playerEntity, 1013, pos, 0);
                world.playSound(null, pos, ModSounds.DOOR_COMBINED.get(), SoundSource.BLOCKS, 1f, 1f);
            }

            return InteractionResult.SUCCESS;
        }
        else {
            if (!world.isClientSide)
            {
                errorMessage(playerEntity);
                world.playSound(null, pos, ModSounds.ERROR.get(), SoundSource.BLOCKS, 1f, 1f);
            }
            return InteractionResult.FAIL;
        }
    }

    @Nullable
    private boolean failCheckDist(Level world, BlockPos pos, Player playerEntity) {
        if (playerEntity.distanceToSqr(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f) >= maxDistSqr)
        {
            if (!world.isClientSide) {
                CommonFunctions.SafeSendMsgToPlayer(
                        RED,
                        playerEntity,
                        MessageDef.EGO_DOOR_CLOSER);
            }
            return true;
        }
        return false;
    }

    protected void errorMessage(Player playerEntity) {
        if (egoReq == MJDSDefine.EnumEgo.POPLON)
        {
            CommonFunctions.SafeSendMsgToPlayer(RED, playerEntity, MessageDef.REQ_POPOLON);
        }
        else {
            CommonFunctions.SafeSendMsgToPlayer(RED, playerEntity, MessageDef.REQ_APHRODITE);
        }
    }
}
