package net.SpectrumFATM.black_archive.entity.client;

import net.SpectrumFATM.black_archive.entity.custom.ChairEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ChairEntityRenderer extends EntityRenderer<ChairEntity> {

    public ChairEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ChairEntity entity) {
        return null;
    }

    @Override
    public boolean shouldRender(ChairEntity entity, Frustum frustum, double x, double y, double z) {
        return false;
    }
}