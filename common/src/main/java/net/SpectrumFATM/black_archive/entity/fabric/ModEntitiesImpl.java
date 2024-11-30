package net.SpectrumFATM.black_archive.entity.fabric;

import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.entity.client.*;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ModEntitiesImpl {
    public static void registerPlatformRenderers() {
        // Register entity renderers
        EntityRendererRegistry.register(ModEntities.DALEK.get(), DalekRenderer::new);
        EntityRendererRegistry.register(ModEntities.LASER.get(), LaserRenderer::new);
        EntityRendererRegistry.register(ModEntities.DALEK_PUPPET.get(), DalekPuppetRenderer::new);
        EntityRendererRegistry.register(ModEntities.CYBERMAN.get(), CybermanRenderer::new);
        EntityRendererRegistry.register(ModEntities.CYBERMAT.get(), CybermatRenderer::new);
        EntityRendererRegistry.register(ModEntities.TIME_FISSURE.get(), TimeFissureRenderer::new);

        // Register model layers
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.DALEK, DalekModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.DALEK_SLAVE, DalekPuppetModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CYBERMAN, CybermanModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CYBERMAT, CybermatModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.TIME_FISSURE, TimeFissureModel::getTexturedModelData);
    }
}
