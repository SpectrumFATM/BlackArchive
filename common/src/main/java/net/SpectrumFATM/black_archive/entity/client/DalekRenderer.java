package net.SpectrumFATM.black_archive.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.SpectrumFATM.black_archive.entity.custom.DalekEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DalekRenderer extends MobRenderer<DalekEntity, DalekModel<DalekEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("black_archive", "textures/entity/dalek.png");

    public DalekRenderer(EntityRendererProvider.Context context) {
        super(context, new DalekModel<>(context.bakeLayer(ModModelLayers.DALEK)), 0.6f);
    }

    @Override
    public ResourceLocation getTextureLocation(DalekEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(DalekEntity mobEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        matrixStack.pushPose();
        matrixStack.scale(0.65f, 0.65f, 0.65f);

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.popPose();
    }
}