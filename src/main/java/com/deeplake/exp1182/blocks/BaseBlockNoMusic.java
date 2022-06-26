package com.deeplake.exp1182.blocks;

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

public class BaseBlockNoMusic extends BaseBlockMJDS implements IBlockMJDS {
    public BaseBlockNoMusic(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void stepOn(Level level, BlockPos p_152432_, BlockState p_152433_, Entity entity) {
        //no music
    }
}
