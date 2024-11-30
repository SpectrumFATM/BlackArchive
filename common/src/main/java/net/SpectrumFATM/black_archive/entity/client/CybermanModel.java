package net.SpectrumFATM.black_archive.entity.client;

import net.SpectrumFATM.black_archive.entity.custom.CybermanEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class CybermanModel<T extends CybermanEntity> extends BipedEntityModel<T> {

	private boolean isFiring = false;

	// Constructor that initializes the model using the parent BipedEntityModel
	public CybermanModel(ModelPart root) {
		super(root);
		isFiring = false;
	}

	// Method to define the model's texture and structure, using the biped structure
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		// Defining the head part, including any additional parts like the helmet or ears
		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create()
						.uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
						.uv(24, 16).cuboid(-4.0F, -7.0F, -2.0F, 8.0F, 5.0F, 4.0F, new Dilation(0.5F))
						.uv(24, 0).cuboid(-5.0F, -9.0F, -1.0F, 10.0F, 6.0F, 2.0F, new Dilation(0.0F))
						.uv(40, 25).cuboid(-1.0F, -8.0F, -4.0F, 2.0F, 2.0F, 7.0F, new Dilation(0.5F)),
				ModelTransform.pivot(0.0F, 0.0F, 0.0F)
		);

		// You still need to define the hat part to avoid crashes, but you can keep it simple with no UV map
		modelPartData.addChild("hat", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		// Defining the body part
		modelPartData.addChild("body", ModelPartBuilder.create()
						.uv(0, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F))
						.uv(33, 56).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 5.0F, 3.0F, new Dilation(0.5F)),
				ModelTransform.pivot(0.0F, 0.0F, 0.0F)
		);

		// Defining the left and right arms
		modelPartData.addChild("left_arm", ModelPartBuilder.create()
						.uv(16, 41).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)),
				ModelTransform.pivot(5.0F, 2.0F, 0.0F)
		);

		modelPartData.addChild("right_arm", ModelPartBuilder.create()
						.uv(36, 37).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)),
				ModelTransform.pivot(-5.0F, 2.0F, 0.0F)
		);

		// Defining the left and right legs
		modelPartData.addChild("left_leg", ModelPartBuilder.create()
						.uv(0, 32).cuboid(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)),
				ModelTransform.pivot(1.9F, 12.0F, 0.0F)
		);

		modelPartData.addChild("right_leg", ModelPartBuilder.create()
						.uv(24, 25).cuboid(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)),
				ModelTransform.pivot(-1.9F, 12.0F, 0.0F)
		);

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		super.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(T livingEntity, float f, float g, float h, float i, float j) {
		super.setAngles(livingEntity, f, g, h, i, j);

		if (isFiring) {
			// Set the arm angles for the firing animation
			this.rightArm.pitch = -1.5F;
		} else {
			// Set the arm angles for the idle animation
			this.rightArm.pitch = 0.0F;
		}
	}

	public void setFiring(boolean firing) {
		isFiring = firing;
	}
}
