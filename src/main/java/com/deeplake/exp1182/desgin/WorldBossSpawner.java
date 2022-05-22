package com.deeplake.exp1182.desgin;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.items.ItemTeleport;
import com.deeplake.exp1182.setup.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.Calendar;
import java.util.List;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class WorldBossSpawner {

    //T = 1 min

    static boolean firstTick = true;
    static boolean shouldSpawn = false;
    static Calendar nextSpawnTime = Calendar.getInstance();

    public static boolean checkSpawn()
    {
        return shouldSpawn;
    }

    public static String getNextSpawnTimeString()
    {
        return String.format("%02d:%02d:%02d",
                nextSpawnTime.get(Calendar.HOUR),
                nextSpawnTime.get(Calendar.MINUTE),
                nextSpawnTime.get(Calendar.SECOND));
    }

    public static void executeSpawn()
    {
        shouldSpawn = false;
        updateSpawnTime();
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if (event.side == LogicalSide.SERVER && event.type == TickEvent.Type.WORLD && event.phase == TickEvent.Phase.START)
        {
            Level level = event.world;
            if (level.dimension() == Level.OVERWORLD)
            {

                if (firstTick)
                {
                    firstTick = false;
                    updateSpawnTime();
                    return;
                }

                if (Calendar.getInstance().after(nextSpawnTime))
                {
                    shouldSpawn = true;
                    List<? extends Player> list = level.players();

                    for (Player player :
                            list) {
                        if (ItemTeleport.isSubscribed(player))
                        {
                            player.addItem(new ItemStack(ModItems.TP_GO.get()));
                        }
                    }

                    updateSpawnTime();
                }
            }
        }
    }

    static void updateSpawnTime()
    {
        Calendar cur = Calendar.getInstance();
//        cur.set(Calendar.MINUTE, 0);
//        cur.set(Calendar.SECOND, 0);
//        cur.add(Calendar.HOUR, 1);

        cur.set(Calendar.SECOND, 0);
        cur.add(Calendar.MINUTE, 1);

        nextSpawnTime = cur;
        Main.Log("Spawntime = %s", nextSpawnTime);
    }

}
