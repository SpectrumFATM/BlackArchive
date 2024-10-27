package net.SpectrumFATM.black_archive.fabric.entity.client;

import net.SpectrumFATM.black_archive.fabric.entity.custom.TimeFissureEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class TimeFissureRenderer extends EntityRenderer<TimeFissureEntity> {
    private static final Identifier TEXTURE = new Identifier("black_archive", "textures/entity/time_fissure.png");
    private final TimeFissureModel<TimeFissureEntity> model;

    public TimeFissureRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new TimeFissureModel<>(context.getPart(ModModelLayers.TIME_FISSURE));
    }

    @Override
    public Identifier getTexture(TimeFissureEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(TimeFissureEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        matrixStack.push();

        // Apply scaling or transformation if desired
        matrixStack.scale(1f, 1f, 1f);
        matrixStack.translate(0f, 0.75f, 0f);

        //Always face player
        matrixStack.multiply(this.dispatcher.getRotation());
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));

        // Get the vertex consumer with the appropriate layer
        var vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE));

        // Render the model with the transformed matrix stack
        model.render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);

        matrixStack.pop();
        super.render(entity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
    }
}