package net.SpectrumFATM.black_archive.fabric.entity;

import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.entity.client.*;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ModEntityRenderers {
    public static void registerRenderers() {
        EntityRendererRegistry.register(ModEntities.DALEK.get(), DalekRenderer::new);
        EntityRendererRegistry.register(ModEntities.LASER.get(), LaserRenderer::new);
        EntityRendererRegistry.register(ModEntities.DALEK_PUPPET.get(), DalekPuppetRenderer::new);
        EntityRendererRegistry.register(ModEntities.CYBERMAN.get(), CybermanRenderer::new);
        EntityRendererRegistry.register(ModEntities.CYBERMAT.get(), CybermatRenderer::new);
        EntityRendererRegistry.register(ModEntities.TIME_FISSURE.get(), TimeFissureRenderer::new);
    }

    public static void registerModelLayers() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.DALEK, DalekModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.DALEK_SLAVE, DalekPuppetModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CYBERMAN, CybermanModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CYBERMAT, CybermatModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.TIME_FISSURE, TimeFissureModel::getTexturedModelData);
    }
}
