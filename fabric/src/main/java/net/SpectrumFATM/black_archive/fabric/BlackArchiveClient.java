package net.SpectrumFATM.black_archive.fabric;

import net.SpectrumFATM.black_archive.blockentity.ModModels;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.entity.client.ModModelLayers;
import net.SpectrumFATM.black_archive.entity.features.BraceletFeatureRenderer;
import net.SpectrumFATM.black_archive.fabric.renderer.FabricSkyRenderer;
import net.SpectrumFATM.black_archive.fabric.entity.ModEntityRenderers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import whocraft.tardis_refined.api.event.TardisClientEvents;

public class BlackArchiveClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModModels.init();

        if (BlackArchiveConfig.CLIENT.shouldTimeVortexRender.get()) {
            FabricSkyRenderer.register();
        }

        ModEntityRenderers.registerRenderers();
        ModEntityRenderers.registerModelLayers();

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof PlayerRenderer) {
                registrationHelper.register(new BraceletFeatureRenderer((RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>) entityRenderer));
            }
        });
    }

}