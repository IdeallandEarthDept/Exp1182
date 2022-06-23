package com.deeplake.exp1182.setup;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.manasystem.data.ManaEvents;
import com.deeplake.exp1182.worldgen.dimensions.Dimensions;
import com.deeplake.exp1182.worldgen.ores.Ores;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSetup {

    public static final String TAB_NAME = "tab1";

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(Main.MOD_ID + "." +TAB_NAME) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.FLAME_FLOOR.get());
        }
    };

    public static void setup() {
        IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addListener(Ores::onBiomeLoadingEvent);
        bus.addGenericListener(Entity.class, ManaEvents::onAttachCapabilitiesPlayer);
        bus.addListener(ManaEvents::onPlayerCloned);
        bus.addListener(ManaEvents::onRegisterCapabilities);
        bus.addListener(ManaEvents::onWorldTick);
    }

    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Ores.registerConfiguredFeatures();
            Dimensions.register();
        });
        Messages.register();
    }

}
