package com.deeplake.exp1182.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.deeplake.exp1182.util.IDLNBT.*;
import static com.deeplake.exp1182.util.IDLNBTDef.*;

//on a server, strlen 65000 is ok, but 66000 will crash
public class IDLNBTUtil {
    public static CompoundTag getNBT(ItemStack stack)
    {
        CompoundTag nbt = stack.getTag();
        if (nbt == null)
        {
            nbt = new CompoundTag();
            stack.setTag(nbt);
        }
        return nbt;
    }

    public static CompoundTag getNBT(Entity entity) {
        CompoundTag nbt = entity.getPersistentData();
        return nbt;
    }


    public static CompoundTag getNBT(CompoundTag tag) {
        if(tag == null) {
            return new CompoundTag();
        }

        return tag;
    }

    //writeEntityToNBT
    //readEntityFromNBT

    @Nullable
    public static boolean StackHasKey(ItemStack stack, String key) {
        return !(stack.isEmpty() || !getNBT(stack).contains(key));
    }

    //Boolean
    public static boolean SetBoolean(ItemStack stack, String key, boolean value)
    {
        CompoundTag nbt = getNBT(stack);
        nbt.putBoolean(key, value);
        return true;
    }

    public static boolean GetBoolean(ItemStack stack, String key, boolean defaultVal)
    {
        if (StackHasKey(stack, key))
        {
            CompoundTag nbt = getNBT(stack);
            return nbt.getBoolean(key);
        }
        else
        {
            return defaultVal;
        }
    }

    public static boolean GetBoolean(ItemStack stack, String key)
    {
        if (StackHasKey(stack, key))
        {
            CompoundTag nbt = getNBT(stack);
            return nbt.getBoolean(key);
        }
        else
        {
            return false;
        }
    }
    //get with default val
    public static boolean GetBooleanDF(ItemStack stack, String key, boolean defaultVal)
    {
        if (StackHasKey(stack, key))
        {
            CompoundTag nbt = getNBT(stack);
            return nbt.getBoolean(key);
        }
        else
        {
            return defaultVal;
        }
    }

    //Double
    public static boolean SetDouble(ItemStack stack, String key, double value)
    {
        CompoundTag nbt = getNBT(stack);
        nbt.putDouble(key, value);
        return true;
    }

    public static double GetDouble(ItemStack stack, String key, double defaultVal)
    {
        if (StackHasKey(stack, key))
        {
            CompoundTag nbt = getNBT(stack);
            return nbt.getDouble(key);
        }
        else
        {
            return defaultVal;
        }
    }

    //Integer
    public static boolean SetLong(ItemStack stack, String key, long value)
    {
        CompoundTag nbt = getNBT(stack);
        nbt.putLong(key, value);
        return true;
    }
    public static boolean SetInt(ItemStack stack, String key, int value)
    {
        CompoundTag nbt = getNBT(stack);
        nbt.putInt(key, value);
        return true;
    }
    public static boolean SetIntOptimized(ItemStack stack, String key, int value)
    {
        CompoundTag nbt = getNBT(stack);
        if (nbt.getInt(key) != value)
        {
            nbt.putInt(key, value);
        }
        return true;
    }
    public static boolean SetInt(Entity entity, String key, int value)
    {
        CompoundTag nbt = getNBT(entity);
        nbt.putInt(key, value);
        return true;
    }
    public static boolean SetIntAuto(Entity entity, String key, int value)
    {
        if (entity instanceof Player)
        {
            setPlayerIdeallandTagSafe((Player) entity, key, value);
            return true;
        }
        CompoundTag nbt = getNBT(entity);
        nbt.putInt(key, value);
        return true;
    }

    public static boolean AddIntAuto(Entity entity, String key, int value)
    {
        int oldVal = GetIntAuto(entity, key, 0);
        SetIntAuto(entity, key, value + oldVal);
        return true;
    }

    public static int GetInt(Entity entity, String key, int defaultVal)
    {
        if (EntityHasKey(entity, key))
        {
            CompoundTag nbt = getNBT(entity);
            return nbt.getInt(key);
        }
        else
        {
            return defaultVal;
        }
    }

    public static int GetIntAuto(Entity entity, String key, int defaultVal)
    {
        if (entity instanceof Player)
        {
            return getPlayerIdeallandIntSafe((Player) entity, key);
        }

        if (EntityHasKey(entity, key))
        {
            CompoundTag nbt = getNBT(entity);
            return nbt.getInt(key);
        }
        else
        {
            return defaultVal;
        }
    }

    public static int GetInt(ItemStack stack, String key, int defaultVal)
    {
        if (StackHasKey(stack, key))
        {
            CompoundTag nbt = getNBT(stack);
            return nbt.getInt(key);
        }
        else
        {
            return defaultVal;
        }
    }

    public static long GetLong(ItemStack stack, String key, int defaultVal)
    {
        if (StackHasKey(stack, key))
        {
            CompoundTag nbt = getNBT(stack);
            return nbt.getLong(key);
        }
        else
        {
            return defaultVal;
        }
    }


    public static int GetInt(ItemStack stack, String key)
    {
        return GetInt(stack, key, 0);
    }

    //String
    public static String GetString(ItemStack stack, String key, String defaultVal)
    {
        if (StackHasKey(stack, key))
        {
            CompoundTag nbt = getNBT(stack);
            return nbt.getString(key);
        }
        else
        {
            return defaultVal;
        }
    }

    public static boolean SetString(ItemStack stack, String key, String value)
    {
        CompoundTag nbt = getNBT(stack);
        nbt.putString(key, value);

        return true;
    }


    //entity
    @Nullable
    public static boolean EntityHasKey(Entity entity, String key)
    {
        return getNBT(entity).contains(key);
    }

    //Boolean
    public static boolean GetBoolean(Entity entity, String key, boolean defaultVal)
    {
        if (EntityHasKey(entity, key))
        {
            CompoundTag nbt = getNBT(entity);
            return nbt.getBoolean(key);
        }
        else
        {
            return defaultVal;
        }
    }

    public static boolean SetBoolean(Entity stack, String key, boolean value)
    {
        CompoundTag nbt = getNBT(stack);
        nbt.putBoolean(key, value);
        return true;
    }

    public static boolean SetString(Entity stack, String key, String value)
    {
        CompoundTag nbt = getNBT(stack);
        nbt.putString(key, value);
        return true;
    }

    public static int[] GetIntArray(ItemStack stack, String key)
    {
        if (StackHasKey(stack, key))
        {
            CompoundTag nbt = getNBT(stack);
            return nbt.getIntArray(key);
        }
        else
        {
            return new int[0];
        }
    }

    public static int[] GetIntArray(LivingEntity stack, String key)
    {
        if (EntityHasKey(stack, key))
        {
            CompoundTag nbt = getNBT(stack);
            return nbt.getIntArray(key);
        }
        else
        {
            return new int[0];
        }
    }

    public static void SetIntArray(ItemStack stack, String key, int[] array)
    {
        CompoundTag nbt = getNBT(stack);
        nbt.putIntArray(key, array);
    }

    public static void SetGuaEnhanceFree(ItemStack stack, int val)
    {
        SetInt(stack, GUA_FREE_SOCKET, val);
    }

    public static boolean GetIsLearned(Player player, int skillID)
    {
        int[] learnt = getPlayerIdeallandIntArraySafe(player, STARTER_KIT_VERSION_TAG);
        if (Arrays.binarySearch(learnt, skillID) >= 0)
        {
            return true;
        }
        return false;
    }

    public static void SetIsLearned(Player player, int skillID, boolean val)
    {
        int[] learnt = getPlayerIdeallandIntArraySafe(player, LEARNING_DONE);
        int oldIndex = Arrays.binarySearch(learnt, skillID);
        if (oldIndex >= 0)
        {
            if (val)
            {
                return;
            }else {
                //todo: remove it

            }
        }else {
            if (val)
            {
                ArrayList<Integer> list = new ArrayList<Integer>();
                for (int oldID:
                        learnt
                ) {
                    list.add(oldID);
                }
                list.add(skillID);
                Collections.sort(list);

                int[] newLearnt = list.stream().mapToInt(Integer::valueOf).toArray();
                setPlayerIdeallandTagSafe(player, LEARNING_DONE, newLearnt);
            }else {
                return;
            }
        }
    }
    //--------------------------------------------



    public static BlockPos getMarkedPos(ItemStack stack)
    {
        CompoundTag NBT = IDLNBTUtil.getNBT(stack);
        return new BlockPos(NBT.getDouble(ANCHOR_X), NBT.getDouble(ANCHOR_Y), NBT.getDouble(ANCHOR_Z));
    }

    public static BlockPos getMarkedPos2(ItemStack stack)
    {
        CompoundTag NBT = IDLNBTUtil.getNBT(stack);
        return new BlockPos(NBT.getDouble(ANCHOR_X_2), NBT.getDouble(ANCHOR_Y_2), NBT.getDouble(ANCHOR_Z_2));
    }

    public static void markPosToStack(ItemStack stack, BlockPos pos)
    {
        IDLNBTUtil.SetBoolean(stack, ANCHOR_READY, true);
        IDLNBTUtil.SetDouble(stack, ANCHOR_X, pos.getX());
        IDLNBTUtil.SetDouble(stack, ANCHOR_Y, pos.getY());
        IDLNBTUtil.SetDouble(stack, ANCHOR_Z, pos.getZ());
    }

    public static void markPosToStack2(ItemStack stack, BlockPos pos)
    {
        IDLNBTUtil.SetBoolean(stack, ANCHOR_READY_2, true);
        IDLNBTUtil.SetDouble(stack, ANCHOR_X_2, pos.getX());
        IDLNBTUtil.SetDouble(stack, ANCHOR_Y_2, pos.getY());
        IDLNBTUtil.SetDouble(stack, ANCHOR_Z_2, pos.getZ());
    }
}
