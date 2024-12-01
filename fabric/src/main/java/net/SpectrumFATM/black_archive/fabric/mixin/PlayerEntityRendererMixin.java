package net.SpectrumFATM.black_archive.fabric.mixin;

import net.SpectrumFATM.black_archive.entity.features.DalekEyestalkFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<PlayerEntity, PlayerEntityModel<PlayerEntity>> {

    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<PlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    // Inject into the constructor of PlayerEntityRenderer
    @Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Z)V", at = @At("TAIL"))
    private void onInit(EntityRendererFactory.Context context, boolean slim, CallbackInfo info) {
        // Add the custom DalekEyestalkFeatureRenderer
        this.addFeature(new DalekEyestalkFeatureRenderer(this));
    }
}
