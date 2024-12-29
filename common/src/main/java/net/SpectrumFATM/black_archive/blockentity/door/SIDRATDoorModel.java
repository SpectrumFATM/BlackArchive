package net.SpectrumFATM.black_archive.blockentity.door;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import org.joml.Vector3f;
import whocraft.tardis_refined.client.model.blockentity.door.interior.ShellDoorModel;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModel;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;


public class SIDRATDoorModel extends ShellDoorModel {
	private final ModelPart pillars;
	private final ModelPart main;
	private final ModelPart door;
	private final ModelPart portal;
	private final ModelPart root;

	public SIDRATDoorModel(ModelPart root) {
		this.root = root;
		this.pillars = root.getChild("pillars");
		this.main = root.getChild("main");
		this.door = root.getChild("door");
		this.portal = root.getChild("portal");
	}


	@Override
	public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		pillars.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		door.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void renderFrame(GlobalDoorBlockEntity doorBlockEntity, boolean open, boolean isBaseModel, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.pillars.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}


	@Override
	public void renderPortalMask(GlobalDoorBlockEntity globalDoorBlockEntity, boolean b, boolean b1, PoseStack matrixStack, VertexConsumer vertexConsumer, int i, int i1, float v, float v1, float v2, float v3) {
		this.portal.render(matrixStack, vertexConsumer, i, i1, v, v1, v2, v3);
	}

	@Override
	public void setDoorPosition(boolean open) {
		this.door.skipDraw= open;
	}
}