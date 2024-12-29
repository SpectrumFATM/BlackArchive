package net.SpectrumFATM.black_archive.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.SpectrumFATM.black_archive.entity.custom.DalekEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DalekModel<T extends DalekEntity> extends EntityModel<T> {
	private final ModelPart body;
	private final ModelPart head;
	public DalekModel(ModelPart root) {
		this.body = root.getChild("body");
		this.head = root.getChild("head");
	}
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(57, 17).addBox(-7.0F, -24.0F, -8.0F, 14.0F, 9.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-8.0F, -15.0F, -11.0F, 16.0F, 13.0F, 19.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-9.0F, -2.0F, -12.0F, 18.0F, 2.0F, 21.0F, new CubeDeformation(0.0F))
				.texOffs(0, 55).addBox(-8.0F, -29.0F, -10.0F, 16.0F, 5.0F, 18.0F, new CubeDeformation(0.0F))
				.texOffs(83, 90).addBox(-7.0F, -28.0F, -19.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-8.0F, -29.0F, -20.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(96, 80).addBox(5.0F, -28.0F, -20.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(6.0F, -29.0F, -19.0F, 0.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(53, 63).addBox(-7.0F, -32.0F, -8.0F, 14.0F, 1.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(53, 63).addBox(-7.0F, -35.0F, -8.0F, 14.0F, 1.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(0, 78).addBox(-6.0F, -37.0F, -7.0F, 12.0F, 8.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.0F, -4.5F, 0.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -27.0F, -14.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -17.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(63, 41).addBox(-7.0F, 0.0F, -8.0F, 14.0F, 4.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(37, 78).addBox(-1.0F, -2.0F, -15.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(50, 79).addBox(-5.0F, -4.0F, -6.0F, 10.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -17.0F, 0.0F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(10, 4).mirror().addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(10, 4).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.3491F));
		return LayerDefinition.create(modelData, 128, 128);
	}
	@Override
	public void setupAnim(DalekEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
	}
	@Override
	public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}