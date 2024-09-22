package net.SpectrumFATM.black_archive.fabric.entity.features;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.entity.client.EyestalkModel;
import net.SpectrumFATM.black_archive.fabric.entity.custom.DalekPuppetEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class DalekEyestalkFeatureRenderer<T extends LivingEntity> extends FeatureRenderer<T, BipedEntityModel<T>> {
    private final EyestalkModel eyestalkModel;

    public DalekEyestalkFeatureRenderer(FeatureRendererContext<T, BipedEntityModel<T>> context) {
        super(context);
        this.eyestalkModel = new EyestalkModel(EyestalkModel.getTexturedModelData().createModel());  // Initialize the eyestalk model
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float customAngle, float headYaw, float headPitch) {
        boolean shouldRenderEyestalk = false;

        // Check if the entity is a player or a Dalek slave
        if (entity instanceof PlayerEntity player) {
            // Render if the player has the Dalek Nanocloud effect
            shouldRenderEyestalk = player.hasStatusEffect(BlackArchive.DALEK_NANOCLOUD) && player.getHealth() <= 10.0F;
        } else if (entity instanceof DalekPuppetEntity dalekSlave) {
            // Render if the entity is a Dalek slave
            shouldRenderEyestalk = true;
        }

        if (shouldRenderEyestalk) {
            // Rotate the eyestalk with the entity's head
            this.getContextModel().head.rotate(matrices);

            // Render the eyestalk
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(new Identifier(BlackArchive.MOD_ID, "textures/entity/eyestalk.png")));
            eyestalkModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
