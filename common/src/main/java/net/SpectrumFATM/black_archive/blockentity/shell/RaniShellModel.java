package net.SpectrumFATM.black_archive.blockentity.shell;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
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
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData side_lights = modelPartData.addChild("side_lights", ModelPartBuilder.create().uv(48, 118).cuboid(21.0F, -42.0F, 0.0F, 2.0F, 42.0F, 20.0F, new Dilation(0.0F))
				.uv(92, 118).cuboid(-1.0F, -42.0F, 0.0F, 2.0F, 42.0F, 20.0F, new Dilation(0.0F)), ModelTransform.pivot(-11.0F, 21.0F, -9.5F));

		ModelPartData door = modelPartData.addChild("door", ModelPartBuilder.create().uv(144, 44).cuboid(0.0F, -42.0F, -1.0F, 16.0F, 42.0F, 0.0F, new Dilation(0.0F))
				.uv(136, 118).cuboid(0.0F, -42.0F, 0.0F, 16.0F, 42.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.0F, 21.0F, -9.5F));

		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 0).cuboid(-16.0F, -3.0F, -11.5F, 32.0F, 3.0F, 24.0F, new Dilation(0.0F))
				.uv(0, 27).cuboid(-16.0F, -48.0F, -11.5F, 32.0F, 3.0F, 24.0F, new Dilation(0.0F))
				.uv(0, 54).cuboid(-14.0F, -45.0F, -10.5F, 2.0F, 42.0F, 22.0F, new Dilation(0.0F))
				.uv(48, 54).cuboid(-10.0F, -45.0F, -10.5F, 2.0F, 42.0F, 22.0F, new Dilation(0.0F))
				.uv(114, 0).cuboid(-8.0F, -45.0F, 9.5F, 16.0F, 42.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 118).cuboid(12.0F, -45.0F, -10.5F, 2.0F, 42.0F, 22.0F, new Dilation(0.0F))
				.uv(96, 54).cuboid(8.0F, -45.0F, -10.5F, 2.0F, 42.0F, 22.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		addMaterializationPart(modelPartData);
		return TexturedModelData.of(modelData, 256, 256);
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
		this.side_lights.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, this.getCurrentAlpha());
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		super.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.door.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.main.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.side_lights.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}