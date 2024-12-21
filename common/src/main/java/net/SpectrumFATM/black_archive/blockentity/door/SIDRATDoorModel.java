package net.SpectrumFATM.black_archive.blockentity.door;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Vector3f;
import whocraft.tardis_refined.client.model.blockentity.door.interior.ShellDoorModel;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModel;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;


public class SIDRATDoorModel extends ShellDoorModel {
	private final ModelPart pillars;
	private final ModelPart main;
	private final ModelPart door;
	private final ModelPart root;
	public SIDRATDoorModel(ModelPart root) {
		this.root = root;
		this.pillars = root.getChild("pillars");
		this.main = root.getChild("main");
		this.door = root.getChild("door");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData pillars = modelPartData.addChild("pillars", ModelPartBuilder.create().uv(42, 48).cuboid(-9.0F, -33.0F, 1.0F, 1.0F, 0.0F, 3.0F, new Dilation(0.0F))
				.uv(40, 48).cuboid(-9.0F, -33.0F, 1.0F, 1.0F, 35.0F, 0.0F, new Dilation(0.0F))
				.uv(50, 0).cuboid(-26.0F, -33.0F, 1.0F, 1.0F, 0.0F, 3.0F, new Dilation(0.0F))
				.uv(48, 0).cuboid(-26.0F, -33.0F, 1.0F, 1.0F, 35.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(17.0F, 22.0F, 4.0F));

		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(32, 30).cuboid(-10.0F, -32.0F, 20.0F, 2.0F, 34.0F, 2.0F, new Dilation(0.0F))
				.uv(40, 0).cuboid(8.0F, -32.0F, 20.0F, 2.0F, 34.0F, 2.0F, new Dilation(0.0F))
				.uv(40, 36).cuboid(-8.0F, -32.0F, 20.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 30).cuboid(-8.0F, -30.0F, 21.0F, 16.0F, 32.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 22.0F, -14.0F));

		ModelPartData door = modelPartData.addChild("door", ModelPartBuilder.create().uv(40, 40).cuboid(-8.0F, 0.0F, 20.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(40, 44).cuboid(-8.0F, -30.0F, 20.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-8.0F, -28.0F, 20.0F, 16.0F, 28.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 22.0F, -14.0F));
		ShellModel.addMaterializationPart(modelPartData);
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		pillars.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		door.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void renderInteriorDoor(GlobalDoorBlockEntity doorBlockEntity, boolean open, boolean isBaseModel, MatrixStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.pillars.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setDoorPosition(boolean open) {
		this.door.pivotZ= open ? -12.0F : -14.0F;
	}
}