package net.SpectrumFATM.black_archive.fabric;

import net.SpectrumFATM.black_archive.fabric.network.NetworkPackets;
import net.fabricmc.api.ClientModInitializer;

public class BlackArchiveClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        NetworkPackets.registerClientSidePackets();
    }
}
