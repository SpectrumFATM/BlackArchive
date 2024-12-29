package net.SpectrumFATM.black_archive.entity.features;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.effects.ModEffects;
import net.SpectrumFATM.black_archive.entity.client.EyestalkModel;
import net.SpectrumFATM.black_archive.entity.custom.DalekPuppetEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class DalekEyestalkFeatureRenderer<T extends LivingEntity> extends RenderLayer<T, HumanoidModel<T>> {
    private final EyestalkModel eyestalkModel;

    public DalekEyestalkFeatureRenderer(RenderLayerParent<T, HumanoidModel<T>> context) {
        super(context);
        this.eyestalkModel = new EyestalkModel(EyestalkModel.getTexturedModelData().bakeRoot());  // Initialize the eyestalk model
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float customAngle, float headYaw, float headPitch) {
        boolean shouldRenderEyestalk = false;

        // Check if the entity is a player or a Dalek slave
        if (entity instanceof Player player) {
            // Render if the player has the Dalek Nanocloud effect
            shouldRenderEyestalk = player.hasEffect(ModEffects.DALEK_NANOCLOUD.get()) && player.getHealth() <= 10.0F;
        } else if (entity instanceof DalekPuppetEntity dalekSlave) {
            // Render if the entity is a Dalek slave
            shouldRenderEyestalk = true;
        }

        if (shouldRenderEyestalk) {
            // Rotate the eyestalk with the entity's head
            this.getParentModel().head.translateAndRotate(matrices);

            // Render the eyestalk
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderType.entityCutout(new ResourceLocation(BlackArchive.MOD_ID, "textures/entity/eyestalk.png")));
            eyestalkModel.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
