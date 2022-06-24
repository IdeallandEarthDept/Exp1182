package com.deeplake.exp1182.entities.client.render;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.entities.EntityWorldBoss;
import com.deeplake.exp1182.entities.client.model.ModelStoneEmitter;
import com.deeplake.exp1182.entities.client.model.WorldBossModel;
import com.deeplake.exp1182.entities.mjds.EntityMJDSStoneEmitter;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;

import javax.annotation.Nonnull;

public class ShowerMonsterRenderer extends MobRenderer<EntityMJDSStoneEmitter, ModelStoneEmitter> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/entity/stone_emitter.png");

    public ShowerMonsterRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelStoneEmitter(context.bakeLayer(ModelStoneEmitter.LAYER_LOCATION)), 1f);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(EntityMJDSStoneEmitter entity) {
        return TEXTURE;
    }
}
