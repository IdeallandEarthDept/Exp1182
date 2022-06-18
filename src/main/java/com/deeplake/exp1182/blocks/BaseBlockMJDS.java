package com.deeplake.exp1182.blocks;

import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.setup.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
        applyMajou(level, entity);
    }

    @Override
    public void fallOn(Level p_152426_, BlockState p_152427_, BlockPos p_152428_, Entity p_152429_, float p_152430_) {
        applyMajou(p_152426_, p_152429_);
        p_152429_.playSound(ModSounds.FALL.get(), 1f, 1f);
    }

    private void applyMajou(Level level, Entity entity) {
        if (entity instanceof LivingEntity living)
        {
            if (!level.isClientSide) {
                majouBuff(living);
            } else {
                if (entity == Minecraft.getInstance().player)
                {
                    MusicManager musicManager = Minecraft.getInstance().getMusicManager();
                    if (!musicManager.isPlayingMusic(ModSounds.MUSIC_DUNGEON)) {
                        musicManager.startPlaying(ModSounds.MUSIC_DUNGEON);
                    }
                }
            }
        }
    }

    private void majouBuff(LivingEntity living) {
        living.addEffect(new MobEffectInstance(ModEffects.INSIDE_MAJOU.get(),
                60, 0, true, false, true, null));
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return MJDS_BLOCKS;
    }
}
