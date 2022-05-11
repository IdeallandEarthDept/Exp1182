package com.deeplake.exp1182.util;

import net.minecraft.commands.arguments.CompoundTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class IDLNBT {
    //Handle those which need to be stored into players permanently
    //PlayerData
    //--PERSISTED_NBT_TAG
    //  --IDEALLAND
    //    --KILL_COUNT,etc

    public static CompoundTag getTagSafe(CompoundTag tag, String key) {
        if(tag == null) {
            return new CompoundTag();
        }

        return tag.getCompound(key);
    }

    public static CompoundTag getPlyrIdlTagSafe(Player player) {
        if(player == null) {
            return new CompoundTag();
        }

        CompoundTag playerData = player.getPersistentData();
        CompoundTag data = getTagSafe(playerData, Player.PERSISTED_NBT_TAG);
        CompoundTag idl_data = getTagSafe(data, IDEALLAND);

        return idl_data;
    }

    public static CompoundTag getPlayerIdeallandTagGroupSafe(Player player, String key) {
        return getPlyrIdlTagSafe(player).getCompound(key);
    }

    public static int[] getPlayerIdeallandIntArraySafe(Player player, String key) {
        return getPlyrIdlTagSafe(player).getIntArray(key);
    }

    public static int getPlayerIdeallandIntSafe(Player player, String key) {
        return getPlyrIdlTagSafe(player).getInt(key);
    }
    public static float getPlayerIdeallandFloatSafe(Player player, String key) {
        return getPlyrIdlTagSafe(player).getFloat(key);
    }
    public static double getPlayerIdeallandDoubleSafe(Player player, String key) {
        return getPlyrIdlTagSafe(player).getDouble(key);
    }
    public static boolean getPlayerIdeallandBoolSafe(Player player, String key) {
        return getPlyrIdlTagSafe(player).getBoolean(key);
    }
    public static String getPlayerIdeallandStrSafe(Player player, String key) {
        return getPlyrIdlTagSafe(player).getString(key);
    }
    public static BlockPos getPlayerIdeallandBlockPosSafe(Player player, String key) {
        if (player == null)
        {
            return BlockPos.ZERO;
        }

        INBT inbt = getPlyrIdlTagSafe(player).get(key);
        if (inbt instanceof CompoundTag)
        {
            return NBTUtil.readBlockPos((CompoundTag) inbt);
        }
        return BlockPos.ZERO;
    }

    public static void setPlayerIdeallandTagSafe(Player player, String key, int value) {
        if (player == null)
        {
            return;
        }

        CompoundTag playerData = player.getPersistentData();
        CompoundTag data = getTagSafe(playerData, Player.PERSISTED_NBT_TAG);
        CompoundTag idl_data = getPlyrIdlTagSafe(player);

        idl_data.putInt(key, value);

        data.put(IDEALLAND, idl_data);
        playerData.put(Player.PERSISTED_NBT_TAG, data);
    }

    public static void setPlayerIdeallandTagSafe(Player player, String key, int[] value) {
        if (player == null)
        {
            return;
        }

        CompoundTag playerData = player.getPersistentData();
        CompoundTag data = getTagSafe(playerData, Player.PERSISTED_NBT_TAG);
        CompoundTag idl_data = getPlyrIdlTagSafe(player);

        idl_data.putIntArray(key, value);

        data.put(IDEALLAND, idl_data);
        playerData.put(Player.PERSISTED_NBT_TAG, data);
    }

    public static void setPlayerIdeallandTagSafe(Player player, String key, double value) {
        if (player == null)
        {
            return;
        }

        CompoundTag playerData = player.getPersistentData();
        CompoundTag data = getTagSafe(playerData, Player.PERSISTED_NBT_TAG);
        CompoundTag idl_data = getPlyrIdlTagSafe(player);

        idl_data.putDouble(key, value);

        data.put(IDEALLAND, idl_data);
        playerData.put(Player.PERSISTED_NBT_TAG, data);
    }

    public static void setPlayerIdeallandTagSafe(Player player, String key, boolean value) {
        if (player == null)
        {
            return;
        }

        CompoundTag playerData = player.getPersistentData();
        CompoundTag data = getTagSafe(playerData, Player.PERSISTED_NBT_TAG);
        CompoundTag idl_data = getPlyrIdlTagSafe(player);

        idl_data.putBoolean(key, value);

        data.put(IDEALLAND, idl_data);
        playerData.put(Player.PERSISTED_NBT_TAG, data);
    }

    public static void setPlayerIdeallandTagSafe(Player player, String key, String value) {
        if (player == null)
        {
            return;
        }

        CompoundTag playerData = player.getPersistentData();
        CompoundTag data = getTagSafe(playerData, Player.PERSISTED_NBT_TAG);
        CompoundTag idl_data = getPlyrIdlTagSafe(player);

        idl_data.putString(key, value);

        data.put(IDEALLAND, idl_data);
        playerData.put(Player.PERSISTED_NBT_TAG, data);
    }

    public static void setPlayerIdeallandTagSafe(Player player, String key, BlockPos value) {
        CompoundTag playerData = player.getPersistentData();
        CompoundTag data = getTagSafe(playerData, Player.PERSISTED_NBT_TAG);
        CompoundTag idl_data = getPlyrIdlTagSafe(player);

        idl_data.put(key, NBTUtil.writeBlockPos(value));

        data.put(IDEALLAND, idl_data);
        playerData.put(Player.PERSISTED_NBT_TAG, data);
    }
}
