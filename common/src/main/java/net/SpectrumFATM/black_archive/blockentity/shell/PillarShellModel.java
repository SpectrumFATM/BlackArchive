package net.SpectrumFATM.black_archive.blockentity.shell;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModel;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;

public class PillarShellModel extends ShellModel {
	private final ModelPart root;
	private final ModelPart door;
	private final ModelPart main;
	public PillarShellModel(ModelPart root) {
        super(root);
		this.root = root;
        this.door = root.getChild("door");
		this.main = root.getChild("main");
	}


	@Override
	public ModelPart getPart() {
		return this.root;
	}

	@Override
	public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void setDoorPosition(boolean open) {
		this.door.yaw = open ? 1.75F : 0.0F;
	}

	@Override
	public void renderShell(GlobalShellBlockEntity entity, boolean open, boolean isBaseModel, MatrixStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.handleAllAnimations(entity, this.getPart(), isBaseModel, open, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, this.getCurrentAlpha());
		this.main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, this.getCurrentAlpha());
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		super.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.door.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.main.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}