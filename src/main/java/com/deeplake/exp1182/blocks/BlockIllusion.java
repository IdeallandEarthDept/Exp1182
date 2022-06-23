package com.deeplake.exp1182.blocks;

import com.deeplake.exp1182.util.AdvancementUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import static net.minecraft.world.level.material.Material.*;

public class BlockIllusion extends BaseBlockMJDS  {
    public BlockIllusion(Properties p_49795_) {
        super(Properties.of(STONE)
                .strength(-1.0F, 3600000.0F)
                .noCollission());
    }

    @Override
    public void stepOn(Level p_176199_1_, BlockPos p_176199_2_, BlockState p_152433_, Entity entity) {
        super.stepOn(p_176199_1_, p_176199_2_, p_152433_, entity);
        if (entity instanceof Player)
        {
            Player playerEntity = (Player) entity;
            AdvancementUtil.giveAdvancement(playerEntity, AdvancementUtil.ACHV_WATCH_YOUR_STEP);
        }
    }
}
