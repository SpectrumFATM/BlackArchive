package net.SpectrumFATM.black_archive.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.SpectrumFATM.black_archive.entity.custom.TimeFissureEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class TimeFissureModel<T extends TimeFissureEntity> extends EntityModel<T> {
	private final ModelPart front;
	private final ModelPart middle;
	private final ModelPart back;

	public TimeFissureModel(ModelPart root) {
		this.front = root.getChild("front");
		this.middle = root.getChild("middle");
		this.back = root.getChild("back");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition front = modelPartData.addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 32).addBox(-16.0F, -16.0F, -0.5F, 32.0F, 32.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition middle = modelPartData.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition back = modelPartData.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 64).addBox(-16.0F, -16.0F, 0.5F, 32.0F, 32.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));
		return LayerDefinition.create(modelData, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.front.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.middle.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.back.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}