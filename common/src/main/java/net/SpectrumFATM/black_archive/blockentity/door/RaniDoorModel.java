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
//	private final ModelPart portal;

	public RaniDoorModel(ModelPart root) {
		this.root = root;
		this.lights = root.getChild("lights");
		this.door = root.getChild("door");
		this.main = root.getChild("main");
	//	this.portal = root.getChild("portal");
	}

	@Override
	public void renderFrame(GlobalDoorBlockEntity doorBlockEntity, boolean open, boolean isBaseModel, MatrixStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.lights.render(poseStack, vertexConsumer, packedLight, packedOverlay);
		this.main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setDoorPosition(boolean open) {
		this.door.yaw = open ? 90.0F : 0.0F;
	}


	@Override
	public void renderPortalMask(GlobalDoorBlockEntity doorBlockEntity, boolean open, boolean isBaseModel, MatrixStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
	//	this.portal.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}