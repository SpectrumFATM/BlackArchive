package net.SpectrumFATM.black_archive.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.world.entity.LivingEntity;

public class DalekPuppetModel<T extends LivingEntity> extends HumanoidModel<T> {

    private final ModelPart eyestalk = EyestalkModel.getTexturedModelData().bakeRoot();

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
        this.jacket = root.getChild(PartNames.JACKET);
        this.leftSleeve = root.getChild("left_sleeve");
        this.rightSleeve = root.getChild("right_sleeve");
        this.leftPants = root.getChild("left_pants");
        this.rightPants = root.getChild("right_pants");
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
        super.setupAnim(entity, limbAngle, limbDistance, customAngle, headYaw, headPitch);

        // Apply transformations to sleeves and pants if needed
        this.ear.copyFrom(this.head);
        this.cloak.copyFrom(this.body);
        this.jacket.copyFrom(this.body);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        super.renderToBuffer(matrixStack, vertexConsumer, light, overlay, red, green, blue, alpha);

        // Render the eyestalk with its custom positioning
        this.eyestalk.copyFrom(this.head); // Attach to the head
        this.eyestalk.render(matrixStack, vertexConsumer, light, overlay);

        // Render the sleeves, pants, and jacket
        this.leftSleeve.render(matrixStack, vertexConsumer, light, overlay);
        this.rightSleeve.render(matrixStack, vertexConsumer, light, overlay);
        this.leftPants.render(matrixStack, vertexConsumer, light, overlay);
        this.rightPants.render(matrixStack, vertexConsumer, light, overlay);
        this.jacket.render(matrixStack, vertexConsumer, light, overlay); // Render the jacket
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition data = PlayerModel.createMesh(CubeDeformation.NONE, false);
        return LayerDefinition.create(data, 64, 64);
    }
}
