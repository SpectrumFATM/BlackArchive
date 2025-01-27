package net.SpectrumFATM.black_archive.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.SpectrumFATM.black_archive.entity.custom.CybermanEntity;
import net.SpectrumFATM.black_archive.entity.custom.SilurianEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SilurianRenderer extends MobRenderer<SilurianEntity, SilurianModel<SilurianEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("black_archive", "textures/entity/silurian.png");

    public SilurianRenderer(EntityRendererProvider.Context context) {
        super(context, new SilurianModel<>(context.bakeLayer(ModModelLayers.SILURIAN)), 0.6f);
    }

    @Override
    public ResourceLocation getTextureLocation(SilurianEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(SilurianEntity mobEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}