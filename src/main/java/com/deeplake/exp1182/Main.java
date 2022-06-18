package com.deeplake.exp1182;

import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.setup.ClientSetup;
import com.deeplake.exp1182.setup.ModConfig;
import com.deeplake.exp1182.setup.ModSetup;
import com.deeplake.exp1182.setup.Registration;
import com.deeplake.exp1182.util.CommonFunctions;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod(Main.MOD_ID)
public class Main {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "exp1182";
    private static final boolean SHOW_WARN = true;

    public Main() {

        // Register the deferred registry
        ModSetup.setup();
        Registration.init();
        ModConfig.register();

        // Register the setup method for modloading
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register 'ModSetup::init' to be called at mod setup time (server and client)
        modbus.addListener(ModSetup::init);
        // Register 'ClientSetup::init' to be called at mod setup time (client only)
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modbus.addListener(ClientSetup::init));
//        // Register the setup method for modloading
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::postInit);
//        // Register the enqueueIMC method for modloading
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
//        // Register the processIMC method for modloading
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
//
//        // Register ourselves for server and other game events we are interested in
//        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
//        LOGGER.info("HELLO FROM PREINIT");
//        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
    private void postInit(final FMLLoadCompleteEvent event)
    {
        // some preinit code
        Log("Post init begin.");

    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // Some example code to receive and process InterModComms from other mods
//        LOGGER.info("Got IMC {}", event.getIMCStream().
//                map(m->m.messageSupplier().get()).
//                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
//        LOGGER.info("HELLO from server starting");
    }

    //Logging
    public static void LogWarning(String str, Object...args)
    {
        if (SHOW_WARN)
        {
            LOGGER.warn(String.format(str, args));
        }
    }

    public static void LogWarning(String str)
    {
        if (SHOW_WARN)
        {
            LOGGER.warn(str);
        }
    }

    public static void Log(String str)
    {
        //if (ModConfig.GeneralConf.LOG_ON)
        {
            LOGGER.info(str);
        }
    }

    public static void Log(String str, Object...args)
    {
        //if (ModConfig.GeneralConf.LOG_ON)
        {
            LOGGER.info(String.format(str, args));
        }
    }
}
