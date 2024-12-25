package net.SpectrumFATM.black_archive.blockentity.shell;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.joml.Vector3f;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModel;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;

public class SIDRATModel extends ShellModel {
	private final ModelPart pillars;
	private final ModelPart main;
	private final ModelPart door;
	private final ModelPart root;
	public SIDRATModel(ModelPart root) {
        super(root);
		this.root = root;
        this.pillars = root.getChild("pillars");
		this.main = root.getChild("main");
		this.door = root.getChild("door");
	}

	@Override
	public void setDoorPosition(boolean open) {
		this.door.translate(new Vector3f(0.0F, 0.0F, open ? -12.0F : 0.0F));
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		this.pillars.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		this.main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		this.door.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void renderShell(GlobalShellBlockEntity entity, boolean open, boolean isBaseModel, MatrixStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.handleAllAnimations(entity, this.getPart(), isBaseModel, open, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, this.getCurrentAlpha());
		this.door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, this.getCurrentAlpha());
		this.main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, this.getCurrentAlpha());
	}

	@Override
	public ModelPart getPart() {
		return root;
	}

	@Override
	public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}
}