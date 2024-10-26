package net.SpectrumFATM.black_archive.fabric.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import whocraft.tardis_refined.common.util.DimensionUtil;

import java.util.ArrayList;

public class AllowedDimensionsRequestPacket {
    public static final Identifier ID = new Identifier(BlackArchive.MOD_ID, "allowed_dimensions_request");

    public static void send() {
        PacketByteBuf buf = PacketByteBufs.create();
        ClientPlayNetworking.send(ID, buf);
    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {

                ArrayList<ServerWorld> filteredDimensions = new ArrayList<>();
                Iterable<ServerWorld> filteredLevels = server.getWorlds();
                filteredLevels.forEach((i) -> {
                    if (DimensionUtil.isAllowedDimension(i.getRegistryKey()) && !i.getRegistryKey().getValue().toString().equals("black_archive:time_vortex")) {
                        filteredDimensions.add(i);
                    }

                });

                PacketByteBuf responseBuf = PacketByteBufs.create();
                responseBuf.writeInt(filteredDimensions.size());
                for (ServerWorld dimension : filteredDimensions) {
                    responseBuf.writeString(dimension.getRegistryKey().getValue().toString());
                }
                ServerPlayNetworking.send(player, AllowedDimensionsResponsePacket.ID, responseBuf);
            });
        });
    }
}