package net.SpectrumFATM.black_archive.entity.client;

import net.SpectrumFATM.black_archive.entity.custom.WeepingAngelEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector3f;

public class WeepingAngelModel<T extends MobEntity> extends EntityModel<T> {
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
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(36, 30).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-5.0F, -9.0F, -5.0F, 10.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(40, 0).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leftArm = modelPartData.addChild("leftArm", ModelPartBuilder.create().uv(32, 46).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

		ModelPartData rightArm = modelPartData.addChild("rightArm", ModelPartBuilder.create().uv(48, 46).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 2.0F, 0.0F));

		ModelPartData legs = modelPartData.addChild("legs", ModelPartBuilder.create().uv(0, 30).cuboid(-4.0F, 0.0F, -3.0F, 12.0F, 10.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 20).cuboid(-5.0F, 10.0F, -4.0F, 14.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

		ModelPartData wings = modelPartData.addChild("wings", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 30.0F, 0.0F));

		ModelPartData cube_r1 = wings.addChild("cube_r1", ModelPartBuilder.create().uv(16, 46).cuboid(1.0F, -8.0F, -7.0F, 0.0F, 14.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-6.0F, -22.0F, 7.0F, 0.0F, -0.6109F, 0.0F));

		ModelPartData cube_r2 = wings.addChild("cube_r2", ModelPartBuilder.create().uv(0, 46).cuboid(-1.0F, -8.0F, -7.0F, 0.0F, 14.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, -22.0F, 7.0F, 0.0F, 0.6109F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leftArm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rightArm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		legs.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		wings.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		if (entity instanceof WeepingAngelEntity weepingAngelEntity) {
			if (weepingAngelEntity.getStatuePose() == "default") {
				this.head.setAngles((float) -Math.toRadians(-12.5D), 0.0F, 0.0F);
				this.leftArm.setAngles((float) -Math.toRadians(112.5D), (float) -Math.toRadians(22.5D), 0.0F);
				this.rightArm.setAngles((float) -Math.toRadians(112.5D), (float) -Math.toRadians(-22.5D), 0.0F);
			} else {
				this.head.setAngles((float) -Math.toRadians(-12.5D), 0.0F, 0.0F);
				this.leftArm.setAngles((float) -Math.toRadians(90.0D), 0.0F, 0.0F);
				this.rightArm.setAngles((float) -Math.toRadians(90.0D), 0.0F, 0.0F);
			}
		}
	}
}