package net.SpectrumFATM.black_archive.fabric.entity.client;

import net.SpectrumFATM.black_archive.fabric.entity.custom.DalekEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class DalekModel<T extends DalekEntity> extends EntityModel<T> {
	private final ModelPart body;
	private final ModelPart head;
	public DalekModel(ModelPart root) {
		this.body = root.getChild("body");
		this.head = root.getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(57, 17).cuboid(-7.0F, -24.0F, -8.0F, 14.0F, 9.0F, 15.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-8.0F, -15.0F, -11.0F, 16.0F, 13.0F, 19.0F, new Dilation(0.0F))
				.uv(0, 32).cuboid(-9.0F, -2.0F, -12.0F, 18.0F, 2.0F, 21.0F, new Dilation(0.0F))
				.uv(0, 55).cuboid(-8.0F, -29.0F, -10.0F, 16.0F, 5.0F, 18.0F, new Dilation(0.0F))
				.uv(83, 90).cuboid(-7.0F, -28.0F, -19.0F, 2.0F, 2.0F, 9.0F, new Dilation(0.0F))
				.uv(0, 32).cuboid(-8.0F, -29.0F, -20.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F))
				.uv(96, 80).cuboid(5.0F, -28.0F, -20.0F, 2.0F, 2.0F, 10.0F, new Dilation(0.0F))
				.uv(0, 4).cuboid(6.0F, -29.0F, -19.0F, 0.0F, 4.0F, 9.0F, new Dilation(0.0F))
				.uv(53, 63).cuboid(-7.0F, -32.0F, -8.0F, 14.0F, 1.0F, 15.0F, new Dilation(0.0F))
				.uv(53, 63).cuboid(-7.0F, -35.0F, -8.0F, 14.0F, 1.0F, 15.0F, new Dilation(0.0F))
				.uv(0, 78).cuboid(-6.0F, -37.0F, -7.0F, 12.0F, 8.0F, 13.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData cube_r1 = body.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -2.0F, -4.5F, 0.0F, 4.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, -27.0F, -14.5F, 0.0F, 0.0F, 1.5708F));

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -3.0F, -17.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(63, 41).cuboid(-7.0F, 0.0F, -8.0F, 14.0F, 4.0F, 15.0F, new Dilation(0.0F))
				.uv(37, 78).cuboid(-1.0F, -2.0F, -15.0F, 2.0F, 2.0F, 9.0F, new Dilation(0.0F))
				.uv(50, 79).cuboid(-5.0F, -4.0F, -6.0F, 10.0F, 4.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -17.0F, 0.0F));

		ModelPartData cube_r2 = head.addChild("cube_r2", ModelPartBuilder.create().uv(10, 4).mirrored().cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(4.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

		ModelPartData cube_r3 = head.addChild("cube_r3", ModelPartBuilder.create().uv(10, 4).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.3491F));
		return TexturedModelData.of(modelData, 128, 128);
	}
	@Override
	public void setAngles(DalekEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yaw = netHeadYaw * ((float)Math.PI / 180F);
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}