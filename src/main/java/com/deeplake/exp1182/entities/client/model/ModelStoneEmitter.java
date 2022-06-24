package com.deeplake.exp1182.entities.client.model;// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.deeplake.exp1182.entities.mjds.EntityMJDSStoneEmitter;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class ModelStoneEmitter extends EntityModel<EntityMJDSStoneEmitter> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "entitystoneemitter"), "main");
	public static final String LID = "lid";
	private final ModelPart base;

	public ModelStoneEmitter(ModelPart root) {
		this.base = root.getChild("base");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(8, 45).addBox(-7.0F, -6.0F, -7.0F, 14.0F, 5.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-6.0F, -13.0F, -8.0F, 12.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 19).addBox(-7.0F, -15.0F, -7.0F, 5.0F, 9.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition right_r1 = base.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -5.0F, -0.5F, 12.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 7.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition lid = base.addOrReplaceChild(LID, CubeListBuilder.create(), PartPose.offset(5.7118F, -12.5268F, 0.0F));

		PartDefinition block1_r1 = lid.addOrReplaceChild("block1_r1", CubeListBuilder.create().texOffs(26, 0).addBox(0.5F, -4.5F, -7.0F, 5.0F, 9.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.2118F, 1.0268F, 0.0F, 0.0F, 0.0F, -0.7418F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(EntityMJDSStoneEmitter entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		base.getChild(LID).zRot = (float) Math.sin(limbSwing);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}