package com.deeplake.exp1182.util;

import com.deeplake.exp1182.blocks.BaseBlockMJDS;
import com.deeplake.exp1182.blocks.IBlockMJDS;
import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.setup.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import static com.deeplake.exp1182.events.EventsJumpHelper.getBlockPosBelowThatAffectsMyMovement;
import static com.deeplake.exp1182.util.IDLNBTDef.NO_REVIVE;

public class DesignUtil {

//    public static boolean isNearBoss(Player entity) {
//        //should improve. not good
//
//        if (entity.getServer() != null)
//        {
//            Collection<CustomServerBossInfo> collection = entity.getServer().getCustomBossEvents().getEvents();
////            for (CustomServerBossInfo info: collection
////                 ) {
////                if (info.shouldPlayBossMusic())
////            } player list is private, darn
//
//            return  !collection.isEmpty();
//        }
//        return false;
//    }

    public static boolean isInMJDS(Entity entity)
    {
        Block block = entity.level.getBlockState(getBlockPosBelowThatAffectsMyMovement(entity)).getBlock();
        return block instanceof BaseBlockMJDS;// || block instanceof LadderBlockMJDS || block instanceof BlockWallGlass;
    }

//    public static boolean isCreatureMJDS(LivingEntity entity)
//    {
//        if (entity instanceof AbstractSkeletonEntity)//including wither, stray and normal
//        {
//            return entity.getMainHandItem().getItem() instanceof SwordItem
//                    || entity.getOffhandItem().getItem() instanceof ShieldItem;
//        }
//        else return entity instanceof IMjdsMonster || entity instanceof Witch;
//    }

    public static boolean canRevive(Entity entity)
    {
        return entity != null && IDLNBTUtil.GetInt(entity, NO_REVIVE, 0) <= 0;
    }

    public static boolean isWithOffsetMJDS(Level world, BlockPos pos, Entity entity)
    {
        //todo
//        return false;
        return world.getBlockState(getBlockPosBelowThatAffectsMyMovement(entity).offset(pos)).getBlock() instanceof IBlockMJDS;
    }

    public static void applyMajou(Entity entity) {
        Level level = entity.getLevel();
        if (entity instanceof LivingEntity living)
        {
            if (!level.isClientSide) {
                majouBuff(living);
            } else {
                if (entity == Minecraft.getInstance().player)
                {
                    MusicManager musicManager = Minecraft.getInstance().getMusicManager();
                    if (!musicManager.isPlayingMusic(ModSounds.MUSIC_DUNGEON)) {
                        musicManager.stopPlaying();
                        musicManager.startPlaying(ModSounds.MUSIC_DUNGEON);
                    }
                }
            }
        }
    }

    public static void majouBuff(LivingEntity living) {
        living.addEffect(new MobEffectInstance(ModEffects.INSIDE_MAJOU.get(),
                60, 0, true, false, true, null));
    }
}
