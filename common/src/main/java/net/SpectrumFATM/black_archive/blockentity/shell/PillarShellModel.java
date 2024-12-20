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

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(52, 0).cuboid(-1.0F, 29.0F, -1.0F, 16.0F, 2.0F, 16.0F, new Dilation(0.0F))
				.uv(52, 18).cuboid(-1.0F, -17.0F, -1.0F, 16.0F, 2.0F, 16.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(0.0F, -15.0F, 2.0F, 1.0F, 44.0F, 12.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(13.0F, -15.0F, 2.0F, 1.0F, 44.0F, 12.0F, new Dilation(0.0F))
				.uv(-2, 11).cuboid(1.0F, -15.0F, 13.0F, 12.0F, 44.0F, 1.0F, new Dilation(0.0F))
				.uv(40, 70).cuboid(0.0F, -15.0F, 0.0F, 14.0F, 12.0F, 12.0F, new Dilation(0.0F))
				.uv(0, 56).cuboid(0.0F, -15.0F, 0.0F, 14.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.0F, -7.0F, -7.0F));

		ModelPartData door = modelPartData.addChild("door", ModelPartBuilder.create().uv(52, 36).cuboid(0.0F, -32.0F, -2.0F, 14.0F, 32.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.0F, 22.0F, -5.0F));
		addMaterializationPart(modelPartData);
		return TexturedModelData.of(modelData, 128, 128);
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