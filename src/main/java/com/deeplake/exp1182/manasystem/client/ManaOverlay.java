package com.deeplake.exp1182.manasystem.client;

import com.deeplake.exp1182.manasystem.ManaConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.IIngameOverlay;

public class ManaOverlay {

    public static final IIngameOverlay HUD_MANA = (gui, poseStack, partialTicks, width, height) -> {
        String toDisplay = ClientManaData.getPlayerMana() + " / " + ClientManaData.getChunkMana();
        int x = ManaConfig.MANA_HUD_X.get();
        int y = ManaConfig.MANA_HUD_Y.get();

//        Player player = Minecraft.getInstance().player;

//        Attributes.ATTACK_DAMAGE.setSyncable(true);

        if (x >= 0 && y >= 0) {
//            gui.getFont().draw(poseStack, toDisplay, x, y, ManaConfig.MANA_HUD_COLOR.get());
//
//            gui.getFont().draw(poseStack, "Atk:" + player.getAttributeValue(Attributes.ATTACK_DAMAGE), x, y+20, ManaConfig.MANA_HUD_COLOR.get());
//            gui.getFont().draw(poseStack, "Def:" + player.getAttributeValue(Attributes.ARMOR), x, y+40, ManaConfig.MANA_HUD_COLOR.get());
//            gui.getFont().draw(poseStack, "Speed:" + player.getAttributeValue(Attributes.MOVEMENT_SPEED), x, y+60, ManaConfig.MANA_HUD_COLOR.get());
//

        }
    };
}
