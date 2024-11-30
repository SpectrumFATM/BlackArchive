package net.SpectrumFATM.black_archive.network;

import dev.architectury.networking.NetworkManager;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.renderer.TardisWarningRenderer;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class TardisWarningPacket {
    public static final Identifier ID = new Identifier(BlackArchive.MOD_ID, "tardis_warning_flash");

    public TardisWarningPacket() {
        // No data required for this packet
    }

    public TardisWarningPacket(PacketByteBuf buf) {
        // No data to decode
    }

    public void encode(PacketByteBuf buf) {
        // No data to encode
    }

    public static void sendToClient(ServerPlayerEntity player) {
        PacketByteBuf buf = new PacketByteBuf(io.netty.buffer.Unpooled.buffer());
        new TardisWarningPacket().encode(buf);
        NetworkManager.sendToPlayer(player, ID, buf);
    }

    public static void registerClientReceiver() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ID, (buf, context) -> {
            TardisWarningPacket packet = new TardisWarningPacket(buf);
            context.queue(() -> TardisWarningRenderer.triggerRedFlash());
        });
    }
}
