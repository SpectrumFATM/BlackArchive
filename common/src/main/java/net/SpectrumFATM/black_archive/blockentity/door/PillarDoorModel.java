package net.SpectrumFATM.black_archive.blockentity.door;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import whocraft.tardis_refined.client.model.blockentity.door.interior.ShellDoorModel;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModel;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;

public class PillarDoorModel extends ShellDoorModel {
	private final ModelPart root;
	private final ModelPart main;
	private final ModelPart door;
	public PillarDoorModel(ModelPart root) {
		this.root = root;
		this.main = root.getChild("main");
		this.door = root.getChild("door");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 3).cuboid(0.0F, -15.0F, 13.0F, 14.0F, 32.0F, 0.0F, new Dilation(0.0F))
				.uv(0, 35).cuboid(-3.0F, -18.0F, 12.0F, 3.0F, 35.0F, 3.0F, new Dilation(0.0F))
				.uv(1, 35).cuboid(14.0F, -18.0F, 12.0F, 3.0F, 35.0F, 3.0F, new Dilation(0.0F))
				.uv(60, 0).cuboid(0.0F, -17.0F, 12.0F, 14.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.0F, 7.0F, -5.0F));

		ModelPartData door = modelPartData.addChild("door", ModelPartBuilder.create().uv(28, 1).cuboid(0.0F, -15.0F, -2.1F, 14.0F, 32.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.0F, 7.0F, 10.0F));
		ShellModel.addMaterializationPart(modelPartData);
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void renderInteriorDoor(GlobalDoorBlockEntity doorBlockEntity, boolean open, boolean isBaseModel, MatrixStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setDoorPosition(boolean open) {
		this.door.yaw = open ? -90.0F : 0.0F;
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}