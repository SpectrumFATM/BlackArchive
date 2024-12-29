package net.SpectrumFATM.black_archive.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.SpectrumFATM.black_archive.entity.custom.CybermanEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CybermanModel<T extends CybermanEntity> extends HumanoidModel<T> {

	private boolean isFiring = false;

	// Constructor that initializes the model using the parent BipedEntityModel
	public CybermanModel(ModelPart root) {
		super(root);
		isFiring = false;
	}

	// Method to define the model's texture and structure, using the biped structure
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();

		// Defining the head part, including any additional parts like the helmet or ears
		PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
						.texOffs(24, 16).addBox(-4.0F, -7.0F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.5F))
						.texOffs(24, 0).addBox(-5.0F, -9.0F, -1.0F, 10.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
						.texOffs(40, 25).addBox(-1.0F, -8.0F, -4.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.5F)),
				PartPose.offset(0.0F, 0.0F, 0.0F)
		);

		// You still need to define the hat part to avoid crashes, but you can keep it simple with no UV map
		modelPartData.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		// Defining the body part
		modelPartData.addOrReplaceChild("body", CubeListBuilder.create()
						.texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
						.texOffs(33, 56).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 5.0F, 3.0F, new CubeDeformation(0.5F)),
				PartPose.offset(0.0F, 0.0F, 0.0F)
		);

		// Defining the left and right arms
		modelPartData.addOrReplaceChild("left_arm", CubeListBuilder.create()
						.texOffs(16, 41).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(5.0F, 2.0F, 0.0F)
		);

		modelPartData.addOrReplaceChild("right_arm", CubeListBuilder.create()
						.texOffs(36, 37).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-5.0F, 2.0F, 0.0F)
		);

		// Defining the left and right legs
		modelPartData.addOrReplaceChild("left_leg", CubeListBuilder.create()
						.texOffs(0, 32).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(1.9F, 12.0F, 0.0F)
		);

		modelPartData.addOrReplaceChild("right_leg", CubeListBuilder.create()
						.texOffs(24, 25).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-1.9F, 12.0F, 0.0F)
		);

		return LayerDefinition.create(modelData, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		super.renderToBuffer(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(T livingEntity, float f, float g, float h, float i, float j) {
		super.setupAnim(livingEntity, f, g, h, i, j);

		if (isFiring) {
			// Set the arm angles for the firing animation
			this.rightArm.xRot = -1.5F;
		}
	}

	public void setFiring(boolean firing) {
		isFiring = firing;
	}
}
