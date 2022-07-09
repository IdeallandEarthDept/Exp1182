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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import static com.deeplake.exp1182.util.MJDSDefine.EnumEgo.POPLON;

public class BaseBlockEgoDoor extends BaseBlockMJDS {

    MJDSDefine.EnumEgo egoReq = MJDSDefine.EnumEgo.POPLON;

    public BaseBlockEgoDoor(MJDSDefine.EnumEgo egoReq) {
        super(Properties.of(Material.STONE).isSuffocating((p_235445_0_, p_235445_1_, p_235445_2_) -> false));
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

        if (checkEgo(playerEntity))
        {
            if (!world.isClientSide) {
                if (failCheckDist(world, pos, playerEntity))
                {
                    return InteractionResult.FAIL;
                }
                Vector3d thisPos = new Vector3d(pos.getX()+0.5f, pos.getY(), pos.getZ()+0.5f);
                playerEntity.teleportTo(symmteric(playerEntity.getX(), thisPos.x),
                        playerEntity.getY()+disturbanceY,
                        symmteric(playerEntity.getZ(), thisPos.z));
                playerEntity.level.levelEvent(playerEntity, 1013, pos, 0);
            }
            playerEntity.playSound(ModSounds.DOOR_COMBINED.get(), 1f, 1f);
            return InteractionResult.SUCCESS;
        }
        else {
            if (!world.isClientSide)
            {
                errorMessage(playerEntity);
            }
            playerEntity.playSound(ModSounds.ERROR.get(), 1f, 1f);
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
