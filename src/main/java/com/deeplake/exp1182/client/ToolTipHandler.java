package com.deeplake.exp1182.client;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.blocks.BlockAchvBox;
import com.deeplake.exp1182.items.INeedLogNBT;
import com.deeplake.exp1182.items.ItemTeleport;
import com.deeplake.exp1182.setup.ModItems;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT)
public class ToolTipHandler {
    public static final String KEY_SUBSCRIBED = Main.MOD_ID + ".desc.key_subscribed";
    public static final String KEY_UNSUBSCRIBED = Main.MOD_ID + ".desc.key_unsubscribed";
    public static final String DESC = ".desc";

    @SubscribeEvent
    public static void onDesc(ItemTooltipEvent event)
    {
        Player playerEntity = event.getPlayer();
        ItemStack stack = event.getItemStack();
        Item itemType = stack.getItem();
        if (itemType instanceof INeedLogNBT)
        {
            event.getToolTip().add(new TextComponent(event.getItemStack().getOrCreateTag().toString()));
        }

        final ResourceLocation registryName = itemType.getRegistryName();
        if (registryName != null
                && registryName.getNamespace().equals(Main.MOD_ID))
        {
            String key = itemType.getDescriptionId() + DESC;
            if (I18n.exists(key)) {
                event.getToolTip().add(new TranslatableComponent(key));
            }

            if (itemType instanceof ItemTeleport)
            {
                boolean state = ItemTeleport.isSubscribed(stack);
                String key2 = state ? KEY_SUBSCRIBED : KEY_UNSUBSCRIBED;
                event.getToolTip().add(new TranslatableComponent(key2));
            }

            if (itemType instanceof BlockItem blockItem)
            {
                if (blockItem.getBlock() instanceof BlockAchvBox blockAchvBox)
                {
                    event.getToolTip().add(new TranslatableComponent(String.format(blockAchvBox.getAchvName())));
                }
            }
        }
    }
}
