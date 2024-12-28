package net.SpectrumFATM.black_archive.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.SpectrumFATM.black_archive.entity.custom.CybermatEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CybermatRenderer extends MobRenderer<CybermatEntity, CybermatModel<CybermatEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("black_archive", "textures/entity/cybermat.png");

    public CybermatRenderer(EntityRendererProvider.Context context) {
        super(context, new CybermatModel<>(context.bakeLayer(ModModelLayers.CYBERMAT)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(CybermatEntity entity) {
        return TEXTURE;
    }


    @Override
    public void render(CybermatEntity mobEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        matrixStack.pushPose();
        matrixStack.scale(0.75f, 0.75f, 0.75f);

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.popPose();
    }
}