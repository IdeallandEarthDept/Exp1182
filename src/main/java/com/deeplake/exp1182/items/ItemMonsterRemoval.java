package com.deeplake.exp1182.items;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.entities.mjds.IMjdsMonster;
import com.deeplake.exp1182.util.*;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.List;

import static com.deeplake.exp1182.util.EntityUtil.IS_MJDS;

public class ItemMonsterRemoval extends Item {
    public ItemMonsterRemoval(Properties p_41383_) {
        super(p_41383_);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onHit(LivingHurtEvent event) {
        Level world = event.getEntity().level();
        if (world.isClientSide) {
            return;
        }

        LivingEntity hurtOne = event.getEntity();

        if (event.getSource().getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();

            if (attacker.getMainHandItem().getItem() instanceof ItemMonsterRemoval &&
                    hurtOne instanceof IMjdsMonster)
            {
                exileEntity(hurtOne, attacker);
            }
        }
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerEntity, LivingEntity target, InteractionHand hand) {

        Level world = playerEntity.level();

        if (hand == InteractionHand.OFF_HAND || !playerEntity.isCreative())
        {
            return super.interactLivingEntity(stack, playerEntity, target, hand);
        }

        if (world.isClientSide) {
            return InteractionResult.sidedSuccess(true);
        }

        playerEntity.awardStat(Stats.ITEM_USED.get(this));
        CommonFunctions.activateCooldown(stack, playerEntity, CommonDef.TICK_PER_SECOND);

        List<Entity> list = EntityUtil.getEntitiesWithinAABB(world, playerEntity.position(), 3f, IS_MJDS);
        for (Entity monster : list)
        {
            exileEntity(monster, playerEntity);
        }

        return super.interactLivingEntity(stack, playerEntity, target, hand);
    }

    public static void exileEntity(Entity hurtOne, @Nullable Entity source)
    {
        IDLNBTUtil.SetInt(hurtOne, IDLNBTDef.NO_REVIVE, 1);
        if (source != null)
        {
            Main.Log("%s(%s) removed %s @%s permanently.", source.getName().getContents(), source.getStringUUID(), hurtOne.getName().getContents(), ((IMjdsMonster) hurtOne).getRespawn());
        }
        hurtOne.remove(Entity.RemovalReason.DISCARDED);
    }
}
