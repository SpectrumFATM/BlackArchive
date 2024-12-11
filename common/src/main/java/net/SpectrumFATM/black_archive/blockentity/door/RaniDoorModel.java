package net.SpectrumFATM.black_archive.blockentity.door;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import whocraft.tardis_refined.client.model.blockentity.door.interior.ShellDoorModel;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;

public class RaniDoorModel extends ShellDoorModel {
	private final ModelPart root;
	private final ModelPart lights;
	private final ModelPart door;
	private final ModelPart main;
	public RaniDoorModel(ModelPart root) {
		this.root = root;
		this.lights = root.getChild("lights");
		this.door = root.getChild("door");
		this.main = root.getChild("main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData lights = modelPartData.addChild("lights", ModelPartBuilder.create().uv(68, 64).cuboid(21.0F, -42.0F, 14.0F, 2.0F, 42.0F, 3.0F, new Dilation(0.0F))
		.uv(78, 64).cuboid(-1.0F, -42.0F, 14.0F, 2.0F, 42.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-11.0F, 21.0F, -9.5F));

		ModelPartData door = modelPartData.addChild("door", ModelPartBuilder.create().uv(36, 60).cuboid(-16.0F, -42.0F, -1.0F, 16.0F, 42.0F, 0.0F, new Dilation(0.0F))
		.uv(0, 18).cuboid(-16.0F, -42.0F, 0.0F, 16.0F, 42.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 21.0F, 4.5F));

		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 0).cuboid(-16.0F, -3.0F, 2.5F, 32.0F, 3.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 9).cuboid(-16.0F, -48.0F, 2.5F, 32.0F, 3.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 62).cuboid(-14.0F, -45.0F, 3.5F, 2.0F, 42.0F, 4.0F, new Dilation(0.0F))
		.uv(12, 62).cuboid(-10.0F, -45.0F, 3.5F, 2.0F, 42.0F, 4.0F, new Dilation(0.0F))
		.uv(36, 18).cuboid(-8.0F, -45.0F, 7.5F, 16.0F, 42.0F, 0.0F, new Dilation(0.0F))
		.uv(68, 18).cuboid(12.0F, -45.0F, 3.5F, 2.0F, 42.0F, 4.0F, new Dilation(0.0F))
		.uv(24, 62).cuboid(8.0F, -45.0F, 3.5F, 2.0F, 42.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void renderInteriorDoor(GlobalDoorBlockEntity doorBlockEntity, boolean open, boolean isBaseModel, MatrixStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.lights.render(poseStack, vertexConsumer, packedLight, packedOverlay);
		this.main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setDoorPosition(boolean open) {
		this.door.yaw = open ? 90.0F : 0.0F;
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}