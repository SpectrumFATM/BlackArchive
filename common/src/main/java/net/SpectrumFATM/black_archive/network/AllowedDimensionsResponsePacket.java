package net.SpectrumFATM.black_archive.network;

import net.SpectrumFATM.BlackArchive;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.SpectrumFATM.black_archive.screen.VortexScreen;

import java.util.ArrayList;
import java.util.List;

public class AllowedDimensionsResponsePacket {
    private final List<String> allowedDimensions;

    public AllowedDimensionsResponsePacket(List<String> allowedDimensions) {
        this.allowedDimensions = allowedDimensions;
    }

    public AllowedDimensionsResponsePacket(PacketByteBuf buf) {
        int size = buf.readInt();
        this.allowedDimensions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            this.allowedDimensions.add(buf.readString(32767)); // Read each dimension as a string
        }
    }

    public void encode(PacketByteBuf buf) {
        buf.writeInt(allowedDimensions.size());
        for (String dimension : allowedDimensions) {
            buf.writeString(dimension, 32767);
        }
    }

    public void handle() {
        MinecraftClient client = MinecraftClient.getInstance();
        client.execute(() -> {
            // Log the received dimensions
            BlackArchive.LOGGER.info("Allowed dimensions: " + allowedDimensions);

            // Update the VortexScreen if it's active
            if (client.currentScreen instanceof VortexScreen vortexScreen) {
                vortexScreen.setDimensions(allowedDimensions);
            }
        });
    }
}
