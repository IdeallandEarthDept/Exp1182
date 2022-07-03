package com.deeplake.exp1182.util;

import com.deeplake.exp1182.Main;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class AdvancementUtil {

    public static final String ACHV_WATCH_YOUR_STEP = "watch_your_step";


    public static final String ACHV_ALTEREGO_APHRODITE = "alterego_aphrodite";
//    public static final String ACHV_ALTEREGO_APHRODITE = "alterego_aphrodite";
//    public static final String ACHV_ALTEREGO_APHRODITE = "alterego_aphrodite";
//    public static final String ACHV_ALTEREGO_APHRODITE = "alterego_aphrodite";
//    public static final String ACHV_ALTEREGO_APHRODITE = "alterego_aphrodite";
//    public static final String ACHV_ALTEREGO_APHRODITE = "alterego_aphrodite";
//    public static final String ACHV_ALTEREGO_APHRODITE = "alterego_aphrodite";
//    public static final String ACHV_ALTEREGO_APHRODITE = "alterego_aphrodite";


    public static final String ACHV_ROOT = "root";


    public static boolean giveAdvancement(Player player, String id)
    {
        if (player.level.isClientSide)
        {
            return false;
        }

        ServerPlayer playerMP = (ServerPlayer) player;
        Advancement advancement = findAdvancement(player.getServer(), id);
        if (advancement == null)
        {
            Main.LogWarning("failed to find an advancement:%s", id);
            return false;
        }

        return giveAdvancement(player, advancement);
    }

    public static boolean giveAdvancement(Player player, Advancement advancement)
    {
        if (player.level.isClientSide)
        {
            return false;
        }

        ServerPlayer playerMP = (ServerPlayer) player;
        if (advancement == null)
        {
            return false;
        }
        AdvancementProgress advancementprogress = playerMP.getAdvancements().getOrStartProgress(advancement);
        if (advancementprogress.isDone()) {
            return false;
        } else {
            for(String s : advancementprogress.getRemainingCriteria()) {
                playerMP.getAdvancements().award(advancement, s);
            }

            return true;
        }
    }

    public static boolean hasAdvancement(ServerPlayer p_192552_2_, String id)
    {
        Advancement adv = findAdvancement(p_192552_2_.server, id);
        if (adv == null)
        {
            return false;
        }
        else {
            return hasAdvancement(p_192552_2_, adv);
        }
    }
    public static boolean hasAdvancement(ServerPlayer p_192552_2_, Advancement p_192552_3_)
    {
        AdvancementProgress advancementprogress = p_192552_2_.getAdvancements().getOrStartProgress(p_192552_3_);
        return advancementprogress.isDone();
    }

    public static Advancement findAdvancement(MinecraftServer server, String id)
    {
        Advancement advancement = server.getAdvancements().getAdvancement(new ResourceLocation(Main.MOD_ID, id));
        if (advancement == null)
        {
            advancement = server.getAdvancements().getAdvancement(new ResourceLocation(id));//Also try vanilla
        }

        if (advancement == null)
        {
            Main.Log("Cannot find advancement:%s", id);
            return null;
        }
        else
        {
            return advancement;
        }
    }
}

