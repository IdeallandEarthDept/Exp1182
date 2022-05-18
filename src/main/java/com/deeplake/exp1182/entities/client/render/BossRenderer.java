package com.deeplake.exp1182.entities.client.render;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.entities.EntityWorldBoss;
import com.deeplake.exp1182.entities.client.model.WorldBossModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class BossRenderer extends MobRenderer<EntityWorldBoss, WorldBossModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/entity/skin_gargoyle.png");

    public BossRenderer(EntityRendererProvider.Context context) {
        super(context, new WorldBossModel(context.bakeLayer(WorldBossModel.NORMAL_LAYER)), 1f);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(EntityWorldBoss entity) {
        return TEXTURE;
    }
}
