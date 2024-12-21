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

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData pillars = modelPartData.addChild("pillars", ModelPartBuilder.create().uv(64, 116).cuboid(-9.0F, -39.0F, -13.0F, 1.0F, 0.0F, 18.0F, new Dilation(0.0F))
				.uv(118, 73).cuboid(-9.0F, -39.0F, -13.0F, 1.0F, 41.0F, 0.0F, new Dilation(0.0F))
				.uv(102, 116).cuboid(-26.0F, -39.0F, -13.0F, 1.0F, 0.0F, 18.0F, new Dilation(0.0F))
				.uv(120, 32).cuboid(-26.0F, -39.0F, 5.0F, 1.0F, 41.0F, 0.0F, new Dilation(0.0F))
				.uv(120, 73).cuboid(-9.0F, -39.0F, 5.0F, 1.0F, 41.0F, 0.0F, new Dilation(0.0F))
				.uv(118, 32).cuboid(-26.0F, -39.0F, -13.0F, 1.0F, 41.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(17.0F, 22.0F, 4.0F));

		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 58).cuboid(-15.0F, 0.0F, 8.0F, 30.0F, 2.0F, 12.0F, new Dilation(0.0F))
				.uv(0, 42).cuboid(-15.0F, -38.0F, 6.0F, 30.0F, 2.0F, 14.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-17.0F, -38.0F, 20.0F, 34.0F, 40.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 72).cuboid(15.0F, -38.0F, 6.0F, 2.0F, 40.0F, 14.0F, new Dilation(0.0F))
				.uv(32, 72).cuboid(-17.0F, -38.0F, 6.0F, 2.0F, 40.0F, 14.0F, new Dilation(0.0F))
				.uv(100, 32).cuboid(-15.0F, -37.0F, 6.0F, 7.0F, 39.0F, 2.0F, new Dilation(0.0F))
				.uv(100, 74).cuboid(8.0F, -37.0F, 6.0F, 7.0F, 39.0F, 2.0F, new Dilation(0.0F))
				.uv(64, 102).cuboid(-8.0F, -36.0F, 6.0F, 16.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 22.0F, -14.0F));

		ModelPartData door = modelPartData.addChild("door", ModelPartBuilder.create().uv(72, 0).cuboid(-8.0F, 0.0F, 6.0F, 16.0F, 2.0F, 14.0F, new Dilation(0.0F))
				.uv(72, 16).cuboid(-8.0F, -30.0F, 6.0F, 16.0F, 2.0F, 14.0F, new Dilation(0.0F))
				.uv(64, 72).cuboid(-8.0F, -28.0F, 6.0F, 16.0F, 28.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 22.0F, -14.0F));
		addMaterializationPart(modelPartData);
		return TexturedModelData.of(modelData, 256, 256);
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