package net.SpectrumFATM.forge;

import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.entity.client.*;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.common.MinecraftForge;

public class ModEntitiesForge {
    public static void registerPlatformRenderers() {
        // Register renderers through Forge's event bus
        IEventBus bus = MinecraftForge.EVENT_BUS;

        //bus.addListener(ModEntitiesForge::registerRenderers);
        //bus.addListener(ModEntitiesForge::registerModelLayers);
    }

    /*
    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.DALEK.get(), DalekRenderer::new);
        event.registerEntityRenderer(ModEntities.LASER.get(), LaserRenderer::new);
        event.registerEntityRenderer(ModEntities.DALEK_PUPPET.get(), DalekPuppetRenderer::new);
        event.registerEntityRenderer(ModEntities.CYBERMAN.get(), CybermanRenderer::new);
        event.registerEntityRenderer(ModEntities.CYBERMAT.get(), CybermatRenderer::new);
        event.registerEntityRenderer(ModEntities.TIME_FISSURE.get(), TimeFissureRenderer::new);
    }

    private static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.DALEK, DalekModel::getTexturedModelData);
        event.registerLayerDefinition(ModModelLayers.DALEK_SLAVE, DalekPuppetModel::getTexturedModelData);
        event.registerLayerDefinition(ModModelLayers.CYBERMAN, CybermanModel::getTexturedModelData);
        event.registerLayerDefinition(ModModelLayers.CYBERMAT, CybermatModel::getTexturedModelData);
        event.registerLayerDefinition(ModModelLayers.TIME_FISSURE, TimeFissureModel::getTexturedModelData);
    }

     */
}
