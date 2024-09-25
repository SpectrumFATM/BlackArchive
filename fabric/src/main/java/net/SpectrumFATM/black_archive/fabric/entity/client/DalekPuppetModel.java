package net.SpectrumFATM.black_archive.fabric.entity.client;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;

public class DalekPuppetModel<T extends LivingEntity> extends BipedEntityModel<T> {

    private final ModelPart eyestalk = EyestalkModel.getTexturedModelData().createModel();

    // Outer layer parts
    private final ModelPart ear;
    private final ModelPart cloak;
    private final ModelPart jacket;
    private final ModelPart leftSleeve;
    private final ModelPart rightSleeve;
    private final ModelPart leftPants;
    private final ModelPart rightPants;

    public DalekPuppetModel(ModelPart root) {
        super(root); // Use the default player model (false = no slim arms)

        // Initialize outer layer parts from the PlayerEntityModel
        this.ear = root.getChild("ear");
        this.cloak = root.getChild("cloak");
        this.jacket = root.getChild(EntityModelPartNames.JACKET);
        this.leftSleeve = root.getChild("left_sleeve");
        this.rightSleeve = root.getChild("right_sleeve");
        this.leftPants = root.getChild("left_pants");
        this.rightPants = root.getChild("right_pants");
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
        super.setAngles(entity, limbAngle, limbDistance, customAngle, headYaw, headPitch);

        // Apply transformations to sleeves and pants if needed
        this.ear.copyTransform(this.head);
        this.cloak.copyTransform(this.body);
        this.jacket.copyTransform(this.body);
        this.leftSleeve.copyTransform(this.leftArm);
        this.rightSleeve.copyTransform(this.rightArm);
        this.leftPants.copyTransform(this.leftLeg);
        this.rightPants.copyTransform(this.rightLeg);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        super.render(matrixStack, vertexConsumer, light, overlay, red, green, blue, alpha);

        // Render the eyestalk with its custom positioning
        this.eyestalk.copyTransform(this.head); // Attach to the head
        this.eyestalk.render(matrixStack, vertexConsumer, light, overlay);

        // Render the sleeves, pants, and jacket
        this.leftSleeve.render(matrixStack, vertexConsumer, light, overlay);
        this.rightSleeve.render(matrixStack, vertexConsumer, light, overlay);
        this.leftPants.render(matrixStack, vertexConsumer, light, overlay);
        this.rightPants.render(matrixStack, vertexConsumer, light, overlay);
        this.jacket.render(matrixStack, vertexConsumer, light, overlay); // Render the jacket
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = PlayerEntityModel.getTexturedModelData(Dilation.NONE, false);
        return TexturedModelData.of(data, 64, 64);
    }
}
