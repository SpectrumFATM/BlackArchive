package net.SpectrumFATM.black_archive.fabric.entity.client;

import net.SpectrumFATM.black_archive.fabric.entity.custom.TimeFissureEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class TimeFissureModel<T extends TimeFissureEntity> extends EntityModel<T> {
	private final ModelPart front;
	private final ModelPart middle;
	private final ModelPart back;

	public TimeFissureModel(ModelPart root) {
		this.front = root.getChild("front");
		this.middle = root.getChild("middle");
		this.back = root.getChild("back");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData front = modelPartData.addChild("front", ModelPartBuilder.create().uv(0, 32).cuboid(-16.0F, -16.0F, -0.5F, 32.0F, 32.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 8.0F, 0.0F));

		ModelPartData middle = modelPartData.addChild("middle", ModelPartBuilder.create().uv(0, 0).cuboid(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 8.0F, 0.0F));

		ModelPartData back = modelPartData.addChild("back", ModelPartBuilder.create().uv(0, 64).cuboid(-16.0F, -16.0F, 0.5F, 32.0F, 32.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 8.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.front.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.middle.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.back.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}