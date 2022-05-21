package com.deeplake.exp1182.items;

import com.deeplake.exp1182.desgin.WorldBossSpawner;
import com.deeplake.exp1182.entities.EntityWorldBoss;
import com.deeplake.exp1182.setup.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Calendar;

import static com.deeplake.exp1182.util.MessageDef.NOTIFY_NEXT;

public class ItemDebug extends Item {
    public ItemDebug(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void inventoryTick(ItemStack p_41404_, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        if (entity instanceof Player player)
        {
            if (!player.isCreative())
            {
                return;
            }

            if (WorldBossSpawner.checkSpawn())
            {
                Vec3 pos = entity.position().add(entity.getForward().scale(10));

                EntityWorldBoss boss = new EntityWorldBoss(ModEntities.WORLD_BOSS.get(), level);
                boss.setPos(pos);
                level.addFreshEntity(boss);

                WorldBossSpawner.executeSpawn();
                player.sendMessage(new TranslatableComponent(NOTIFY_NEXT, WorldBossSpawner.getNextSpawnTimeString())
                        .withStyle(ChatFormatting.YELLOW), Util.NIL_UUID);
            }
        }

        super.inventoryTick(p_41404_, level, entity, p_41407_, p_41408_);
    }

}
