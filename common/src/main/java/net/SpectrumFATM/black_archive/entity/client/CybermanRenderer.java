package net.SpectrumFATM.black_archive.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.SpectrumFATM.black_archive.entity.custom.CybermanEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CybermanRenderer extends MobRenderer<CybermanEntity, CybermanModel<CybermanEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("black_archive", "textures/entity/cyberman.png");

    public CybermanRenderer(EntityRendererProvider.Context context) {
        super(context, new CybermanModel<>(context.bakeLayer(ModModelLayers.CYBERMAN)), 0.6f);
    }

    @Override
    public ResourceLocation getTextureLocation(CybermanEntity entity) {
        return TEXTURE;
    }


    @Override
    public void render(CybermanEntity mobEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        matrixStack.pushPose();
        matrixStack.scale(1.01F, 1.01F, 1.01F);

        CybermanModel<?> model = this.getModel();
        model.setFiring(mobEntity.isFiring());

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);

        matrixStack.popPose();
    }
}