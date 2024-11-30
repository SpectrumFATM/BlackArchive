package net.SpectrumFATM.black_archive.entity.client;

import net.SpectrumFATM.black_archive.entity.custom.DalekEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DalekRenderer extends MobEntityRenderer<DalekEntity, DalekModel<DalekEntity>> {
    private static final Identifier TEXTURE = new Identifier("black_archive", "textures/entity/dalek.png");

    public DalekRenderer(EntityRendererFactory.Context context) {
        super(context, new DalekModel<>(context.getPart(ModModelLayers.DALEK)), 0.6f);
    }

    @Override
    public Identifier getTexture(DalekEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(DalekEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(0.65f, 0.65f, 0.65f);

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }
}