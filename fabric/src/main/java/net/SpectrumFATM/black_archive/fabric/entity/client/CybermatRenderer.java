package net.SpectrumFATM.black_archive.fabric.entity.client;

import net.SpectrumFATM.black_archive.fabric.entity.custom.CybermanEntity;
import net.SpectrumFATM.black_archive.fabric.entity.custom.CybermatEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CybermatRenderer extends MobEntityRenderer<CybermatEntity, CybermatModel<CybermatEntity>> {
    private static final Identifier TEXTURE = new Identifier("black_archive", "textures/entity/cybermat.png");

    public CybermatRenderer(EntityRendererFactory.Context context) {
        super(context, new CybermatModel<>(context.getPart(ModModelLayers.CYBERMAT)), 0.4f);
    }

    @Override
    public Identifier getTexture(CybermatEntity entity) {
        return TEXTURE;
    }


    @Override
    public void render(CybermatEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(0.75f, 0.75f, 0.75f);

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }
}