package com.deeplake.exp1182.blocks.mechanics;

import com.deeplake.exp1182.util.AdvancementUtil;
import com.deeplake.exp1182.util.CommonFunctions;
import com.deeplake.exp1182.util.MJDSDefine;
import com.deeplake.exp1182.util.MessageDef;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class BlockAchvDoor extends BaseBlockEgoDoor{
    final String achvKey;
    public BlockAchvDoor(String achvKey) {
        super(MJDSDefine.EnumEgo.NONE);
        this.achvKey = achvKey;
    }

    @Override
    public boolean checkEgo(Player playerEntity) {
        return AdvancementUtil.hasAdvancement(playerEntity, achvKey);
    }

    @Override
    protected void errorMessage(Player playerEntity) {
        CommonFunctions.SafeSendMsgToPlayer(RED, playerEntity, MessageDef.REQ_ACHV,
                Component.translatable(String.format("exp1182.advancements.%s.title", achvKey)));
    }
}
