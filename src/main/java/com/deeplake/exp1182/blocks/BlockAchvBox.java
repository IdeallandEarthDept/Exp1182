package com.deeplake.exp1182.blocks;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.util.AdvancementUtil;
import com.deeplake.exp1182.util.CommonDef;
import com.deeplake.exp1182.util.CommonFunctions;
import com.deeplake.exp1182.util.MessageDef;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;
import java.util.function.Supplier;

public class BlockAchvBox extends BaseBlockMJDS {

    Supplier<Item> sellItemSupp;
    String achvName;

    String ACHV_FORMAT ="%s.advancements.%s.title";
    Style YELLOW = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.YELLOW));

    public BlockAchvBox(Supplier<Item> sellItemSupp, String achvName) {
        super(Properties.of(Material.GLASS).noOcclusion().isViewBlocking(BaseBlockMJDS::neverDo));
        this.sellItemSupp = sellItemSupp;
        this.achvName = achvName;
    }

    public boolean hasItem()
    {
        return sellItemSupp != null;
    }

    public boolean hasAchv()
    {
        return !achvName.isEmpty();
    }

    public String getStackName()
    {
        return hasItem() ? sellItemSupp.get().getDescriptionId() : CommonDef.EMPTY;
    }

    public String getAchvName()
    {
        return String.format(ACHV_FORMAT, Main.MOD_ID, achvName);
    }

    public ItemStack getAwardStack(BlockState state, Level world, BlockPos pos, Player Player, InteractionHand hand, BlockHitResult blockRayTraceResult) {
        return hasItem() ? new ItemStack(sellItemSupp.get(), 1) : ItemStack.EMPTY;
    }

    @Override
    public void animateTick(BlockState p_180655_1_, Level world, BlockPos pos, Random random) {
        super.animateTick(p_180655_1_, world, pos, random);
        if (world.isClientSide)
        {
            world.addParticle(ParticleTypes.HAPPY_VILLAGER,
                    pos.getX() + CommonFunctions.flunctate(0.5,1,random),
                    pos.getY() + CommonFunctions.flunctate(0.5,1,random),
                    pos.getZ() + CommonFunctions.flunctate(0.5,1,random),
                    0,0,0
            );
        }
    }

    //(onBlockActivated)
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player Player, InteractionHand hand, BlockHitResult blockRayTraceResult) {
        world.playSound(null, pos, ModSounds.PICKUP.get(), SoundSource.BLOCKS, 1f, 1f);
        if (world.isClientSide)
        {
            return InteractionResult.SUCCESS;
        }
        else {
            if (AdvancementUtil.hasAdvancement(Player, achvName))
            {
                CommonFunctions.SafeSendMsgToPlayer(YELLOW, Player, MessageDef.BOX_FAIL,
                        new TranslatableComponent(getAchvName()));
                world.playSound(null, pos, ModSounds.PICKUP.get(), SoundSource.BLOCKS, 1f, 1f);
                return InteractionResult.FAIL;
            }
            else {
                AdvancementUtil.giveAdvancement(Player, achvName);
                Player.addItem(getAwardStack(state, world, pos, Player, hand, blockRayTraceResult));
                world.playSound(null, pos, ModSounds.PICKUP.get(), SoundSource.BLOCKS, 1f, 1f);
                return InteractionResult.SUCCESS;
            }
        }
    }


}
