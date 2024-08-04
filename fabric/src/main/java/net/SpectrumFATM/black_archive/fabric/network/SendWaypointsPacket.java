package net.SpectrumFATM.black_archive.fabric.network;

import io.netty.buffer.Unpooled;
import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.SpectrumFATM.black_archive.fabric.screen.VortexScreen;

import java.util.ArrayList;
import java.util.List;

public class SendWaypointsPacket {
    public static final Identifier ID = new Identifier(BlackArchive.MOD_ID, "send_waypoints");

    public static void send(ServerPlayerEntity player, List<String> waypoints) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(waypoints.size());
        for (String waypoint : waypoints) {
            buf.writeString(waypoint);
        }
        ServerPlayNetworking.send(player, ID, buf);
    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(ID, (client, handler, buf, responseSender) -> {
            int size = buf.readInt();
            List<String> waypoints = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                waypoints.add(buf.readString(32767));
            }
            client.execute(() -> {
                if (client.currentScreen instanceof VortexScreen) {
                    ((VortexScreen) client.currentScreen).setWaypoints(waypoints);
                }
            });
        });
    }
}