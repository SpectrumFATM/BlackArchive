package net.SpectrumFATM.black_archive.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.SpectrumFATM.black_archive.entity.custom.TimeFissureEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class TimeFissureRenderer extends EntityRenderer<TimeFissureEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("black_archive", "textures/entity/time_fissure.png");
    private final TimeFissureModel<TimeFissureEntity> model;

    public TimeFissureRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new TimeFissureModel<>(context.bakeLayer(ModModelLayers.TIME_FISSURE));
    }

    @Override
    public ResourceLocation getTextureLocation(TimeFissureEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(TimeFissureEntity entity, float yaw, float tickDelta, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int light) {
        matrixStack.pushPose();

        // Apply scaling or transformation if desired
        matrixStack.scale(1f, 1f, 1f);
        matrixStack.translate(0f, 0.75f, 0f);

        //Always face player
        matrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F));

        // Get the vertex consumer with the appropriate layer
        var vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entityTranslucent(TEXTURE));

        // Render the model with the transformed matrix stack
        model.renderToBuffer(matrixStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

        matrixStack.popPose();
        super.render(entity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
    }
}