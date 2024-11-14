package net.SpectrumFATM.black_archive.fabric;

import net.SpectrumFATM.black_archive.fabric.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.fabric.entity.ModEntities;
import net.SpectrumFATM.black_archive.fabric.entity.features.BraceletFeatureRenderer;
import net.SpectrumFATM.black_archive.fabric.network.NetworkPackets;
import net.SpectrumFATM.black_archive.fabric.renderer.TardisWarningRenderer;
import net.SpectrumFATM.black_archive.fabric.renderer.VortexSkyRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class BlackArchiveClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        NetworkPackets.registerClientSidePackets();
        ModEntities.registerRenderers();
        TardisWarningRenderer.register();

        if (BlackArchiveConfig.CLIENT.shouldTimeVortexRender.get()) {
            DimensionRenderingRegistry.registerSkyRenderer(RegistryKey.of(RegistryKey.ofRegistry(new Identifier("minecraft", "dimension")), new Identifier(BlackArchive.MOD_ID, "time_vortex")), new VortexSkyRenderer());
        }

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof PlayerEntityRenderer) {
                registrationHelper.register(new BraceletFeatureRenderer((FeatureRendererContext<PlayerEntity, PlayerEntityModel<PlayerEntity>>) entityRenderer));
            }
        });
    }

}