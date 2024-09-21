package net.SpectrumFATM.black_archive.fabric.entity.features;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.entity.client.EyestalkModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class DalekEyestalkFeatureRenderer extends FeatureRenderer<PlayerEntity, PlayerEntityModel<PlayerEntity>> {
    private final EyestalkModel eyestalkModel;

    public DalekEyestalkFeatureRenderer(FeatureRendererContext<PlayerEntity, PlayerEntityModel<PlayerEntity>> context) {
        super(context);
        this.eyestalkModel = new EyestalkModel(EyestalkModel.getTexturedModelData().createModel());  // Initialize the eyestalk model
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float customAngle, float headYaw, float headPitch) {
        // Only render the eyestalk if the player has the Dalek Nanocloud effect
        if (player.hasStatusEffect(BlackArchive.DALEK_NANOCLOUD)) {
            // Rotate with the player's head
            this.getContextModel().head.rotate(matrices);

            // Render the eyestalk
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(new Identifier(BlackArchive.MOD_ID, "textures/entity/eyestalk.png")));
            eyestalkModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
