package net.SpectrumFATM.black_archive.entity.client;

import net.SpectrumFATM.black_archive.entity.custom.CybermanEntity;
import net.SpectrumFATM.black_archive.entity.custom.WeepingAngelEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class WeepingAngelRenderer extends MobEntityRenderer<WeepingAngelEntity, WeepingAngelModel<WeepingAngelEntity>> {
    private static final Identifier TEXTURE = new Identifier("black_archive", "textures/entity/weeping_angel.png");

    public WeepingAngelRenderer(EntityRendererFactory.Context context) {
        super(context, new WeepingAngelModel<>(context.getPart(ModModelLayers.ANGEL)), 0.6f);
    }

    @Override
    public Identifier getTexture(WeepingAngelEntity entity) {
        return TEXTURE;
    }


    @Override
    public void render(WeepingAngelEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(1.01F, 1.01F, 1.01F);

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);

        matrixStack.pop();
    }
}