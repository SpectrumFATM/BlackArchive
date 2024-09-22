package net.SpectrumFATM.black_archive.fabric.entity.client;

import net.SpectrumFATM.black_archive.fabric.entity.custom.DalekPuppetEntity;
import net.SpectrumFATM.black_archive.fabric.entity.features.DalekEyestalkFeatureRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DalekPuppetRenderer extends LivingEntityRenderer<DalekPuppetEntity, DalekPuppetModel<DalekPuppetEntity>> {

    public DalekPuppetRenderer(EntityRendererFactory.Context context) {
        super(context, new DalekPuppetModel<>(context.getPart(ModModelLayers.DALEK_SLAVE)), 0.5f);
        this.addFeature(new DalekEyestalkFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(DalekPuppetEntity entity) {
        return entity.getSkin();
    }

    @Override
    protected void renderLabelIfPresent(DalekPuppetEntity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (entity.shouldRenderName()) {
            super.renderLabelIfPresent(entity, text, matrices, vertexConsumers, light);
        }
    }
}