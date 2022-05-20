package com.deeplake.exp1182.items;

import com.deeplake.exp1182.blocks.demo.WorldBossConfig;
import com.deeplake.exp1182.setup.ModItems;
import com.deeplake.exp1182.util.IDLNBTDef;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ItemTeleport extends Item {
    public ItemTeleport(Properties p_41383_) {
        super(p_41383_);
    }

    Vec3 getTarget(ItemStack stack)
    {
//        return new Vec3(0,200,0);
        return new Vec3(WorldBossConfig.X.get(),
                WorldBossConfig.Y.get(),
                WorldBossConfig.Z.get());
    }

    boolean giveReturnTicket()
    {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (giveReturnTicket())
        {
            ItemStack backStack = new ItemStack(ModItems.TP_BACK.get());
            CompoundTag tag = backStack.getOrCreateTag();
            tag.putFloat(IDLNBTDef.ANCHOR_X, (float) player.getX());
            tag.putFloat(IDLNBTDef.ANCHOR_Y, (float) player.getY());
            tag.putFloat(IDLNBTDef.ANCHOR_Z, (float) player.getZ());
            player.addItem(backStack);
        }

        Vec3 target = getTarget(itemstack);
        player.teleportTo(target.x, target.y, target.z);
        player.getCooldowns().addCooldown(this, 20);

        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }


        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    @Override
    public boolean useOnRelease(ItemStack p_41464_) {

        return super.useOnRelease(p_41464_);
    }
}
