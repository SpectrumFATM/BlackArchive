package net.SpectrumFATM.forge.mixin;

import net.SpectrumFATM.black_archive.entity.features.DalekEyestalkFeatureRenderer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerEntityRendererMixinForge extends LivingEntityRenderer<Player, PlayerModel<Player>> {

    public PlayerEntityRendererMixinForge(EntityRendererProvider.Context ctx, PlayerModel<Player> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    // Inject into the constructor of PlayerEntityRenderer
    @Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Z)V", at = @At("TAIL"))
    private void onInit(EntityRendererProvider.Context context, boolean slim, CallbackInfo info) {
        // Add the custom DalekEyestalkFeatureRenderer
        this.addLayer(new DalekEyestalkFeatureRenderer(this));
    }
}