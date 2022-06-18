package com.deeplake.exp1182.entities.client.model;

import com.deeplake.exp1182.Main;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public class HumanModel extends HumanoidModel<Mob> {
    public static final String BODY = "body";

    public static ModelLayerLocation NORMAL_LAYER = new ModelLayerLocation(new ResourceLocation(Main.MOD_ID, "skin_gargoyle"), BODY);

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = createMesh(CubeDeformation.NONE, 0.6f);
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public HumanModel(ModelPart part) {
        super(part);
    }
}
