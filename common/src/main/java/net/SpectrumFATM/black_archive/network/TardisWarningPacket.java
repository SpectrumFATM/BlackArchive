package net.SpectrumFATM.black_archive.network;

import dev.architectury.networking.NetworkManager;
import net.SpectrumFATM.black_archive.renderer.TardisWarningRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class TardisWarningPacket {
    // Constructor for creating the packet
    public TardisWarningPacket() {
    }

    // Constructor for reading data from the buffer
    public TardisWarningPacket(PacketByteBuf buf) {
    }

    // Method to write data to the buffer
    public void toBytes(PacketByteBuf buf) {
    }

    // Client-side handling of the packet
    public void handle(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> {
            TardisWarningRenderer.triggerRedFlash();
        });
    }
}
