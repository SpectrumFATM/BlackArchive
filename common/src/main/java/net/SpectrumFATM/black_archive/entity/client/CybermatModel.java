package net.SpectrumFATM.black_archive.entity.client;

import net.SpectrumFATM.black_archive.entity.custom.CybermatEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class CybermatModel<T extends CybermatEntity> extends EntityModel<T> {
	private final ModelPart body;
	public CybermatModel(ModelPart root) {
		this.body = root.getChild("body");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 8).cuboid(-3.0F, 0.0F, -7.0F, 6.0F, 3.0F, 4.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-4.0F, -1.0F, -5.0F, 8.0F, 4.0F, 4.0F, new Dilation(0.0F))
		.uv(0, 15).cuboid(-3.0F, 0.0F, -1.0F, 6.0F, 3.0F, 4.0F, new Dilation(0.0F))
		.uv(20, 8).cuboid(-2.0F, 1.0F, 3.0F, 4.0F, 2.0F, 3.0F, new Dilation(0.0F))
		.uv(20, 13).cuboid(-1.0F, 2.0F, 6.0F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F))
		.uv(20, 21).cuboid(-1.0F, 0.0F, 3.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(20, 17).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 21.0F, -1.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}
}