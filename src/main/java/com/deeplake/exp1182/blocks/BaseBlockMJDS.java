package com.deeplake.exp1182.blocks;

import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.util.DesignUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import static com.deeplake.exp1182.client.ModSounds.MJDS_BLOCKS;

public class BaseBlockMJDS extends Block implements IBlockMJDS {
    public BaseBlockMJDS(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void stepOn(Level level, BlockPos p_152432_, BlockState p_152433_, Entity entity) {
        super.stepOn(level, p_152432_, p_152433_, entity);
        DesignUtil.applyMajou(entity);
    }

    @Override
    public void fallOn(Level p_152426_, BlockState p_152427_, BlockPos p_152428_, Entity p_152429_, float p_152430_) {
//        DesignUtil.applyMajou(p_152429_);
//        p_152429_.playSound(ModSounds.FALL.get(), 1f, 1f);
        super.fallOn(p_152426_, p_152427_, p_152428_, p_152429_, p_152430_);
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return MJDS_BLOCKS;
    }

    public static boolean neverDo(BlockState p_50779_, BlockGetter p_50780_, BlockPos p_50781_, EntityType<?> p_50782_) {
        return (boolean)false;
    }

    public static boolean neverDo(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }

    public static Boolean alwaysDo(BlockState p_50810_, BlockGetter p_50811_, BlockPos p_50812_, EntityType<?> p_50813_) {
        return (boolean)true;
    }
}
