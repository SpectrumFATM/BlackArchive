package net.SpectrumFATM.black_archive.fabric.network;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.stream.Collectors;

public class DimensionRequestPacket {
    public static final Identifier ID = new Identifier(BlackArchive.MOD_ID, "dimension_request");

    public static void send() {
        PacketByteBuf buf = PacketByteBufs.create();
        ClientPlayNetworking.send(ID, buf);
    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                // Handle the request and send the dimension data back to the client
                List<String> dimensions = server.getWorldRegistryKeys().stream()
                        .map(RegistryKey::getValue)
                        .map(Identifier::toString)
                        .filter(id -> !id.startsWith("tardis_refined"))
                        .collect(Collectors.toList());

                PacketByteBuf responseBuf = PacketByteBufs.create();
                responseBuf.writeInt(dimensions.size());
                for (String dimension : dimensions) {
                    responseBuf.writeString(dimension);
                }
                ServerPlayNetworking.send(player, DimensionResponsePacket.ID, responseBuf);
            });
        });
    }
}