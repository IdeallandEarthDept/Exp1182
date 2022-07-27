package com.deeplake.exp1182.entities.client.render;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.entities.EntityWorldBoss;
import com.deeplake.exp1182.entities.client.model.HumanModel;
import com.deeplake.exp1182.entities.client.model.WorldBossModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

import javax.annotation.Nonnull;

public class HumanoidRenderer extends MobRenderer<Mob, HumanModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/entity/cloud_mob.png");

    public HumanoidRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanModel(context.bakeLayer(HumanModel.NORMAL_LAYER)), 1f);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(Mob entity) {
        return TEXTURE;
    }
}
