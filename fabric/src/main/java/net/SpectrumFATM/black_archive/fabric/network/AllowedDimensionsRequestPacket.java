package net.SpectrumFATM.black_archive.fabric.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import whocraft.tardis_refined.common.util.DimensionUtil;

import java.util.List;
import java.util.stream.Collectors;

public class AllowedDimensionsRequestPacket {
    public static final Identifier ID = new Identifier(BlackArchive.MOD_ID, "allowed_dimensions_request");

    public static void send() {
        PacketByteBuf buf = PacketByteBufs.create();
        ClientPlayNetworking.send(ID, buf);
    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                List<String> allowedDimensions = DimensionUtil.getAllowedDimensions(server).stream()
                        .map(RegistryKey::getValue)
                        .map(Identifier::toString)
                        .toList();

                PacketByteBuf responseBuf = PacketByteBufs.create();
                responseBuf.writeInt(allowedDimensions.size());
                for (String dimension : allowedDimensions) {
                    responseBuf.writeString(dimension);
                }
                ServerPlayNetworking.send(player, AllowedDimensionsResponsePacket.ID, responseBuf);
            });
        });
    }
}