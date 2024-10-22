package net.SpectrumFATM.black_archive.fabric.network;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.renderer.TardisWarningRenderer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class TardisWarningPacket {
    public static final Identifier ID = new Identifier(BlackArchive.MOD_ID, "tardis_warning_flash");

    public static void sendToClient(ServerPlayerEntity player) {
        PacketByteBuf buf = PacketByteBufs.create();
        ServerPlayNetworking.send(player, ID, buf);
    }

    public static void registerClientReceiver() {
        ClientPlayNetworking.registerGlobalReceiver(ID, (client, handler, buf, responseSender) -> {
            client.execute(() -> {
                TardisWarningRenderer.triggerRedFlash();
            });
        });
    }
}
