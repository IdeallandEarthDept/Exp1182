package com.deeplake.exp1182.setup;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.client.ModSounds;
import com.deeplake.exp1182.entities.client.model.ModelStoneEmitter;
import com.deeplake.exp1182.entities.client.model.WorldBossModel;
import com.deeplake.exp1182.entities.client.render.BossRenderer;
import com.deeplake.exp1182.entities.client.render.HumanoidRenderer;
import com.deeplake.exp1182.entities.client.render.ShowerMonsterRenderer;
import com.deeplake.exp1182.entities.client.render.VoidRenderer;
import com.deeplake.exp1182.entities.demo.ThiefModel;
import com.deeplake.exp1182.manasystem.client.KeyBindings;
import com.deeplake.exp1182.manasystem.client.KeyInputHandler;
import com.deeplake.exp1182.manasystem.client.ManaOverlay;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.DragonFireballRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.minecraftforge.client.gui.ForgeIngameGui.HOTBAR_ELEMENT;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
//            MenuScreens.register(ModBlocks.POWERGEN_CONTAINER.get(), PowergenScreen::new);
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.SP_GLASS.get(), RenderType.translucent());
//            PowergenRenderer.register();

        });
        MinecraftForge.EVENT_BUS.addListener(KeyInputHandler::onKeyInput);
        KeyBindings.init();

        ModSounds.initMusic();
//        OverlayRegistry.registerOverlayAbove(HOTBAR_ELEMENT, "name", ManaOverlay.HUD_MANA);
    }

    @SubscribeEvent
    public static void onModelRegistryEvent(ModelRegistryEvent event) {
//        ModelLoaderRegistry.registerLoader(GeneratorModelLoader.GENERATOR_LOADER, new GeneratorModelLoader());
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
//        event.registerLayerDefinition(ThiefModel.THIEF_LAYER, ThiefModel::createBodyLayer);
        event.registerLayerDefinition(WorldBossModel.NORMAL_LAYER, WorldBossModel::createBodyLayer);
        event.registerLayerDefinition(ModelStoneEmitter.LAYER_LOCATION, ModelStoneEmitter::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.WORLD_BOSS.get(), BossRenderer::new);
        event.registerEntityRenderer(ModEntities.REVIVE_MIST.get(), VoidRenderer::new);
        event.registerEntityRenderer(ModEntities.MJDS_SKELETON.get(), SkeletonRenderer::new);
        event.registerEntityRenderer(ModEntities.MJDS_BLAZE.get(), BlazeRenderer::new);
        event.registerEntityRenderer(ModEntities.BULLET1.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.BULLET2.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.BULLET_SHOWER.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.CLOUD_MONSTER.get(), HumanoidRenderer::new);
        event.registerEntityRenderer(ModEntities.SHOWER_MONSTER.get(), ShowerMonsterRenderer::new);
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (!event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
            return;
        }
//        event.addSprite(PowergenRenderer.HALO);
    }
}
