package net.SpectrumFATM.black_archive.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.SpectrumFATM.black_archive.entity.custom.CybermanEntity;
import net.SpectrumFATM.black_archive.entity.custom.WeepingAngelEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WeepingAngelRenderer extends MobRenderer<WeepingAngelEntity, WeepingAngelModel<WeepingAngelEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("black_archive", "textures/entity/weeping_angel.png");

    public WeepingAngelRenderer(EntityRendererProvider.Context context) {
        super(context, new WeepingAngelModel<>(context.bakeLayer(ModModelLayers.ANGEL)), 0.6f);
    }

    @Override
    public ResourceLocation getTextureLocation(WeepingAngelEntity entity) {
        return TEXTURE;
    }


    @Override
    public void render(WeepingAngelEntity mobEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        matrixStack.pushPose();
        matrixStack.scale(1.01F, 1.01F, 1.01F);

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);

        matrixStack.popPose();
    }
}