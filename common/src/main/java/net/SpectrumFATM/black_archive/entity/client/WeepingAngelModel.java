package net.SpectrumFATM.black_archive.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.SpectrumFATM.black_archive.entity.custom.WeepingAngelEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Mob;
import org.joml.Vector3f;

public class WeepingAngelModel<T extends Mob> extends EntityModel<T> {
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart legs;
	private final ModelPart wings;
	public WeepingAngelModel(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.leftArm = root.getChild("leftArm");
		this.rightArm = root.getChild("rightArm");
		this.legs = root.getChild("legs");
		this.wings = root.getChild("wings");
	}
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(36, 30).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(40, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leftArm = modelPartData.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(32, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition rightArm = modelPartData.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(48, 46).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition legs = modelPartData.addOrReplaceChild("legs", CubeListBuilder.create().texOffs(0, 30).addBox(-4.0F, 0.0F, -3.0F, 12.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 20).addBox(-5.0F, 10.0F, -4.0F, 14.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition wings = modelPartData.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0.0F, 30.0F, 0.0F));

		PartDefinition cube_r1 = wings.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 46).addBox(1.0F, -8.0F, -7.0F, 0.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -22.0F, 7.0F, 0.0F, -0.6109F, 0.0F));

		PartDefinition cube_r2 = wings.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 46).addBox(-1.0F, -8.0F, -7.0F, 0.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -22.0F, 7.0F, 0.0F, 0.6109F, 0.0F));
		return LayerDefinition.create(modelData, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leftArm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rightArm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		legs.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		wings.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		if (entity instanceof WeepingAngelEntity weepingAngelEntity) {
			if (weepingAngelEntity.getStatuePose() == "default") {
				this.head.setRotation((float) -Math.toRadians(-12.5D), 0.0F, 0.0F);
				this.leftArm.setRotation((float) -Math.toRadians(112.5D), (float) -Math.toRadians(22.5D), 0.0F);
				this.rightArm.setRotation((float) -Math.toRadians(112.5D), (float) -Math.toRadians(-22.5D), 0.0F);
			} else {
				this.head.setRotation((float) -Math.toRadians(-12.5D), 0.0F, 0.0F);
				this.leftArm.setRotation((float) -Math.toRadians(90.0D), 0.0F, 0.0F);
				this.rightArm.setRotation((float) -Math.toRadians(90.0D), 0.0F, 0.0F);
			}
		}
	}
}