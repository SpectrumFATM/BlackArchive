package net.SpectrumFATM.black_archive.fabric;

import net.SpectrumFATM.black_archive.fabric.entity.ModEntities;
import net.SpectrumFATM.black_archive.fabric.entity.client.DalekModel;
import net.SpectrumFATM.black_archive.fabric.entity.client.DalekRenderer;
import net.SpectrumFATM.black_archive.fabric.entity.client.ModModelLayers;
import net.SpectrumFATM.black_archive.fabric.network.NetworkPackets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class BlackArchiveClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        NetworkPackets.registerClientSidePackets();
        EntityRendererRegistry.register(ModEntities.DALEK, DalekRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.DALEK, DalekModel::getTexturedModelData);
    }
}
