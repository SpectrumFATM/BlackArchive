package net.SpectrumFATM.black_archive.fabric.entity.client;

import net.SpectrumFATM.black_archive.fabric.entity.custom.CybermanEntity;
import net.SpectrumFATM.black_archive.fabric.entity.custom.DalekEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;

public class CybermanRenderer extends MobEntityRenderer<CybermanEntity, CybermanModel<CybermanEntity>> {
    private static final Identifier TEXTURE = new Identifier("black_archive", "textures/entity/cyberman.png");

    public CybermanRenderer(EntityRendererFactory.Context context) {
        super(context, new CybermanModel<>(context.getPart(ModModelLayers.CYBERMAN)), 0.6f);
    }

    @Override
    public Identifier getTexture(CybermanEntity entity) {
        return TEXTURE;
    }


    @Override
    public void render(CybermanEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(1.01F, 1.01F, 1.01F);

        CybermanModel<?> model = this.getModel();
        model.setFiring(mobEntity.isFiring());

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);

        matrixStack.pop();
    }
}