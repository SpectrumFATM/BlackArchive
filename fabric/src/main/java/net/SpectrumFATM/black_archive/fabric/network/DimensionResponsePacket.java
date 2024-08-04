package net.SpectrumFATM.black_archive.fabric.network;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.SpectrumFATM.black_archive.fabric.screen.VortexScreen;

import java.util.ArrayList;
import java.util.List;

public class DimensionResponsePacket {
    public static final Identifier ID = new Identifier(BlackArchive.MOD_ID, "dimension_response");

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(ID, (client, handler, buf, responseSender) -> {
            int size = buf.readInt();
            List<String> dimensions = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                dimensions.add(buf.readString(32767));
            }

            client.execute(() -> {
                // Update the VortexScreen with the received dimensions
                if (client.currentScreen instanceof VortexScreen) {
                    ((VortexScreen) client.currentScreen).setDimensions(dimensions);
                }
            });
        });
    }
}