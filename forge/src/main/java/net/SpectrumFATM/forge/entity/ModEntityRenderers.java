package net.SpectrumFATM.forge.entity;

import net.SpectrumFATM.black_archive.blockentity.ModModels;
import net.SpectrumFATM.black_archive.blockentity.door.PillarDoorModel;
import net.SpectrumFATM.black_archive.blockentity.shell.PillarShellModel;
import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.entity.client.*;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModEntityRenderers {

    public static void registerRenderers(IEventBus modEventBus) {
        modEventBus.addListener(ModEntityRenderers::onRegisterRenderers);
        modEventBus.addListener(ModEntityRenderers::onRegisterModelLayers);
    }

    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Register entity renderers
        event.registerEntityRenderer(ModEntities.DALEK.get(), DalekRenderer::new);
        event.registerEntityRenderer(ModEntities.LASER.get(), LaserRenderer::new);
        event.registerEntityRenderer(ModEntities.DALEK_PUPPET.get(), DalekPuppetRenderer::new);
        event.registerEntityRenderer(ModEntities.CYBERMAN.get(), CybermanRenderer::new);
        event.registerEntityRenderer(ModEntities.CYBERMAT.get(), CybermatRenderer::new);
        event.registerEntityRenderer(ModEntities.TIME_FISSURE.get(), TimeFissureRenderer::new);
    }

    public static void onRegisterModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // Register model layer definitions
        event.registerLayerDefinition(ModModelLayers.DALEK, DalekModel::getTexturedModelData);
        event.registerLayerDefinition(ModModelLayers.DALEK_SLAVE, DalekPuppetModel::getTexturedModelData);
        event.registerLayerDefinition(ModModelLayers.CYBERMAN, CybermanModel::getTexturedModelData);
        event.registerLayerDefinition(ModModelLayers.CYBERMAT, CybermatModel::getTexturedModelData);
        event.registerLayerDefinition(ModModelLayers.TIME_FISSURE, TimeFissureModel::getTexturedModelData);
    }
}
