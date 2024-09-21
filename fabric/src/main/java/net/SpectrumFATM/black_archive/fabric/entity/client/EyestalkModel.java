package net.SpectrumFATM.black_archive.fabric.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;

public class EyestalkModel extends EntityModel<PlayerEntity> {
    private final ModelPart head;

    public EyestalkModel(ModelPart root) {
        this.head = root.getChild("head");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        // Define the head part
        ModelPartData head = modelPartData.addChild("head",
                ModelPartBuilder.create()
                        .uv(32, 32).cuboid(-0.5F, -7.0F, -8.0F, 1.0F, 1.0F, 5.0F, new Dilation(0.0F))
                        .uv(0, 0).cuboid(-1.5F, -8.0F, -8.0F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F))
                        .uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(PlayerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Set head angles based on player's movement
        this.head.yaw = netHeadYaw * 0.017453292F;  // Convert degrees to radians
        this.head.pitch = headPitch * 0.017453292F;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}