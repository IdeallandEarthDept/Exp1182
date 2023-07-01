package com.deeplake.exp1182.items;

import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.util.IDLNBTDef;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class ItemTeleportBack extends ItemTeleport{
    public ItemTeleportBack(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    Vec3 getTarget(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        float x = tag.getFloat(IDLNBTDef.ANCHOR_X);
        float y = tag.getFloat(IDLNBTDef.ANCHOR_Y);
        float z = tag.getFloat(IDLNBTDef.ANCHOR_Z);
        return new Vec3(x,y,z);
    }

    @Override
    boolean giveReturnTicket() {
        return false;
    }

    @Override
    public void playsound(Player player)
    {
        player.playSound(ModSounds.PAUSE, 1f, 1f);
    }
}
