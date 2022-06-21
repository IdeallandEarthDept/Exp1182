package com.deeplake.exp1182.items;

import com.deeplake.exp1182.blocks.demo.WorldBossConfig;
import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.setup.ModItems;
import com.deeplake.exp1182.util.IDLNBT;
import com.deeplake.exp1182.util.IDLNBTDef;
import com.deeplake.exp1182.util.IDLNBTUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import static com.deeplake.exp1182.util.IDLNBTDef.UNSUBSCRIBE;

public class ItemTeleport extends Item implements INeedLogNBT{
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
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, level, entity, p_41407_, p_41408_);
        if (!level.isClientSide && entity instanceof Player player)
        {
            boolean subscribed = isSubscribed(player);

            boolean itemCurState = isSubscribed(stack);

            if (subscribed != itemCurState)
            {
                IDLNBTUtil.SetInt(stack, UNSUBSCRIBE, IDLNBT.getPlayerIdeallandIntSafe(player, UNSUBSCRIBE));
            }
        }

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!level.isClientSide)
        {
            if (player.isCrouching())
            {
                int oldValue = IDLNBT.getPlayerIdeallandIntSafe(player, UNSUBSCRIBE);
                IDLNBT.setPlayerIdeallandTagSafe(player, UNSUBSCRIBE, oldValue == 0 ? 1 : 0);
            }
            else {

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

                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
            }

            player.getCooldowns().addCooldown(this, 20);
            playsound(player);
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    @Override
    public boolean useOnRelease(ItemStack p_41464_) {

        return super.useOnRelease(p_41464_);
    }

    public static boolean isSubscribed(Player player)
    {
        return IDLNBT.getPlayerIdeallandIntSafe(player, UNSUBSCRIBE) == 0;
    }

    public static boolean isSubscribed(ItemStack stack)
    {
        return IDLNBTUtil.GetInt(stack, UNSUBSCRIBE) == 0;
    }

    public void playsound(Player player)
    {
        player.playSound(ModSounds.GAME_SELECT.get(), 1f, 1f);
    }

}
