package com.deeplake.exp1182.events;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.util.AdvancementUtil;
import com.deeplake.exp1182.util.DesignUtil;
import com.deeplake.exp1182.util.IDLNBTDef;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class EventsBirthHelper {

    public static ItemStack makeBannerShield(ItemStack shieldStack, ItemStack bannerStack)
    {
        CompoundTag CompoundTag = bannerStack.getTagElement(IDLNBTDef.BLOCK_ENTITY_TAG);
        CompoundTag CompoundTag1 = CompoundTag == null ? new CompoundTag() : CompoundTag.copy();
        CompoundTag1.putInt(IDLNBTDef.BASE, ((BannerItem)bannerStack.getItem()).getColor().getId());
        shieldStack.addTagElement(IDLNBTDef.BLOCK_ENTITY_TAG, CompoundTag1);
        return shieldStack;
    }

    @SubscribeEvent
    public static void onBirth(MobSpawnEvent.FinalizeSpawn event)
    {
        Level Level = event.getEntity().level();
        if (Level.isClientSide)
        {
            return;
        }

        LivingEntity livingEntity = event.getEntity();
        boolean inMJDS = DesignUtil.isInMJDS(livingEntity);
        //Skeletons -> Skeleton Soldier
        if (livingEntity instanceof Skeleton)
        {
            if (inMJDS || livingEntity.getRandom().nextFloat() < 0.1f)
            {
                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
                livingEntity.setItemInHand(InteractionHand.OFF_HAND, makeBannerShield(new ItemStack(Items.SHIELD), new ItemStack(Items.WHITE_BANNER)));
            }
        }
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        AdvancementUtil.giveAdvancement(event.getEntity(), "root");
    }
}
