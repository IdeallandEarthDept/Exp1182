package com.deeplake.exp1182.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static com.deeplake.exp1182.util.IDLNBTDef.EGO_HP;
import static com.deeplake.exp1182.util.IDLNBTDef.MJDS_EGO;
import static com.deeplake.exp1182.util.MJDSDefine.EnumEgo.APHRODITE;
import static com.deeplake.exp1182.util.MJDSDefine.EnumEgo.POPLON;

public class EgoUtil {

    public static MJDSDefine.EnumEgo getEgo(Player Player)
    {
        return MJDSDefine.EnumEgo.fromInt(IDLNBT.getPlayerIdeallandIntSafe(Player, MJDS_EGO));
    }

//    public static void giveArmor(Player Player, MJDSDefine.EnumEgo state)
//    {
//        for (EquipmentSlot slot :
//                EquipmentSlot.values()) {
//            if (slot.getType() == EquipmentSlot.Type.ARMOR && isReplacable(Player.getItemBySlot(slot))){
//                Player.setItemSlot(slot, new ItemStack(getItemForEgoAndStack(slot, state)));
//            }
//        }
//    }

//    public static Item getItemForEgoAndStack(EquipmentSlot slot, MJDSDefine.EnumEgo state)
//    {
//        final int slotIndex = slot.getIndex();
//        if (slotIndex >= ItemRegistry.APHRODITE_ARMOR.length || slotIndex < 0)
//        {
//            return Items.AIR;
//        }
//        //inverted order in EquipmentSlot
//        if (APHRODITE == state){
//            return ItemRegistry.APHRODITE_ARMOR[3 - slotIndex].get();
//        } else {
//            return ItemRegistry.POPOLON_ARMOR[3 - slotIndex].get();
//        }
//    }
//
//    public static boolean isReplacable(ItemStack stack)
//    {
//        return stack.isEmpty() || stack.getItem() instanceof EgoArmor;
//    }

    //returns if the swap is successful
    public static boolean trySwapEgo(Player player)
    {
        //swap
        MJDSDefine.EnumEgo state = getEgo(player);
        MJDSDefine.EnumEgo otherState;
        if (POPLON==state)
        {
            otherState = APHRODITE;
        }
        else {//APHRODITE OR NONE
            otherState = POPLON;
            if (APHRODITE !=state)
            {
                //Initializes the HP of the other ego
                IDLNBT.setPlayerIdeallandTagSafe(player, EGO_HP, player.getMaxHealth());
            }
        }

        //play sound here?

        //swap HP
        double otherHP = IDLNBT.getPlayerIdeallandDoubleSafe(player, EGO_HP);
        IDLNBT.setPlayerIdeallandTagSafe(player, EGO_HP, player.getHealth());
        //The player probably won't be killed by this. How does one swap after they are dead?
        //In the original game, one will be forced to change ego after they die, however it won't work well with MC.
        //unless... the ego swaps when they revive? no. consider this: one get the girl killed on an island, far from
        //revive room.
        //also, what if both ego dies? This game has no game over outside extreme mode.
        //Maybe I should just make it an option, wether to enforce the death of another ego.
        player.setHealth((float) otherHP);

        //set armor
//        giveArmor(Player, otherState);

        //set state
        IDLNBT.setPlayerIdeallandTagSafe(player, MJDS_EGO, otherState.value);

        //send message
        CommonFunctions.SafeSendMsgToPlayer(
                Style.EMPTY.withColor(TextColor.fromLegacyFormat(otherState == POPLON ?
                        ChatFormatting.BLUE : ChatFormatting.LIGHT_PURPLE)),
                player,
                MessageDef.getSwapEgoMsgKey(otherState));

        return true;
    }
}
