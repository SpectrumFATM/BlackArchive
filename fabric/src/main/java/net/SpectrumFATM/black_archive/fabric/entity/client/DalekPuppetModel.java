package net.SpectrumFATM.black_archive.fabric.entity.client;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;

public class DalekPuppetModel<T extends LivingEntity> extends BipedEntityModel<T> {

    private final ModelPart eyestalk = EyestalkModel.getTexturedModelData().createModel();

    public DalekPuppetModel(ModelPart root) {
        super(root); // Use the default player model (false = no slim arms)
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
        super.setAngles(entity, limbAngle, limbDistance, customAngle, headYaw, headPitch);
        // You can modify the angles of your custom parts here (if necessary).
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        super.render(matrixStack, vertexConsumer, light, overlay, red, green, blue, alpha);
        
        // Render the eyestalk with its custom positioning
        this.eyestalk.copyTransform(this.head); // Attach to the head
        this.eyestalk.render(matrixStack, vertexConsumer, light, overlay);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = PlayerEntityModel.getTexturedModelData(Dilation.NONE, false);
        return TexturedModelData.of(data, 64, 64);
    }
}
