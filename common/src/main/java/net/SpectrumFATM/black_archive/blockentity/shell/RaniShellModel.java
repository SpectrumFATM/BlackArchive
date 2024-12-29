package net.SpectrumFATM.black_archive.blockentity.shell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModel;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;

public class RaniShellModel extends ShellModel {
	private final ModelPart root;
	private final ModelPart side_lights;
	private final ModelPart door;
	private final ModelPart main;
	public RaniShellModel(ModelPart root) {
        super(root);
		this.root = root;
		this.door = root.getChild("door");
		this.side_lights = root.getChild("side_lights");
		this.main = root.getChild("main");
	}



	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void setDoorPosition(boolean open) {
		this.door.yRot = open ? 1.75F : 0.0F;
	}

	@Override
	public void renderShell(GlobalShellBlockEntity entity, boolean open, boolean isBaseModel, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.handleAllAnimations(entity, this.root(), isBaseModel, open, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, this.getCurrentAlpha());
		this.main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, this.getCurrentAlpha());
		this.side_lights.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, this.getCurrentAlpha());
	}

	@Override
	public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		super.renderToBuffer(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.door.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.main.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.side_lights.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}