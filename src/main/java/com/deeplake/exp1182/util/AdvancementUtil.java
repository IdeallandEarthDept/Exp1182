package com.deeplake.exp1182.util;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.setup.ModEffects;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class AdvancementUtil {

    public static final String ACHV_WATCH_YOUR_STEP = "watch_your_step";


    public static final String FEATHER = "feather";
    public static final String SHOES = "shoes";
    public static final String FOOD = "food";
    public static final String SHIELD_BRONZE = "shield_bronze";
    public static final String GREAT_KEY = "great_key";
    public static final String CANDLE = "candle";
    public static final String HOLY_WATER = "holy_water";
    public static final String BALLOON = "balloon";
    public static final String BREAKABLE = "breakable";
    public static final String TRIANGLE = "triangle";

    public static final int FEATHER_BIT = 1;
    public static final int SHOES_BIT = 2;
    public static final int TRIANGLE_BIT = 4;


    public static final String ACHV_ROOT = "root";


    public static boolean giveAdvancement(Player player, String id)
    {
        if (player.level().isClientSide)
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
        if (player.level().isClientSide)
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

    public static boolean hasAdvancement(Player player, String id)
    {
        if (!player.level().isClientSide)
        {
            return hasAdvancement((ServerPlayer)player, id);
        }
        return false;
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

    public static boolean hasAdvancementClient(String name)
    {
//        try
        {
            ClientAdvancements advancements = null;
            if (Minecraft.getInstance().player != null) {
                advancements = Minecraft.getInstance().player.connection.getAdvancements();

                AdvancementList list = advancements.getAdvancements();
                if (list != null)
                {
                    Advancement advancement = list.get(new ResourceLocation(Main.MOD_ID, name));
                    Main.Log("checking");
//                    AdvancementProgress advancementprogress = advancement.getValue();
//                    AdvancementProgress advancementprogress = list.get(new ResourceLocation(Main.MOD_ID, name));
                }
            }
            return true;
        }
//        catch (ClassNotFoundException e)
//        {
//            Main.Log("called client function hasAdvancementClient from server side");
//            return false;
//        }
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

    public static boolean checkAdvClient(Player player, int bit)
    {
        int level = EntityUtil.getBuffLevel(player, ModEffects.INSIDE_MAJOU.get());
        if (level > 0)
        {
            return (level & bit) > 0;
        }
        else {
            return false;
        }
    }
}

