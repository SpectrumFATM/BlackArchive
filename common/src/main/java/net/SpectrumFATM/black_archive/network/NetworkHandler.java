package net.SpectrumFATM.black_archive.network;

import dev.architectury.networking.NetworkManager;
import net.SpectrumFATM.BlackArchive;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class NetworkHandler {
    public static final Identifier ALLOWED_DIMENSIONS_REQUEST = new Identifier(BlackArchive.MOD_ID, "allowed_dimensions_request");
    public static final Identifier ALLOWED_DIMENSIONS_RESPONSE = new Identifier(BlackArchive.MOD_ID, "allowed_dimensions_response");
    public static final Identifier DELETE_WAYPOINT = new Identifier(BlackArchive.MOD_ID, "delete_waypoint");
    public static final Identifier VORTEX_SAVE_WAYPOINT = new Identifier(BlackArchive.MOD_ID, "vortex_save_waypoint");
    public static final Identifier VORTEX_TELEPORT = new Identifier(BlackArchive.MOD_ID, "vortex_teleport");

    public static void register() {
        // Register AllowedDimensionsRequestPacket (Server receives from Client)
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, ALLOWED_DIMENSIONS_REQUEST, (buf, context) -> {
            AllowedDimensionsRequestPacket packet = new AllowedDimensionsRequestPacket(buf);
            context.queue(() -> packet.handle(buf, context));
        });

        // Register AllowedDimensionsResponsePacket (Client receives from Server)
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ALLOWED_DIMENSIONS_RESPONSE, (buf, context) -> {
            AllowedDimensionsResponsePacket packet = new AllowedDimensionsResponsePacket(buf);
            context.queue(packet::handle);
        });

        // Register DeleteWaypointPacket (Server receives from Client)
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, DELETE_WAYPOINT, (buf, context) -> {
            DeleteWaypointPacket packet = new DeleteWaypointPacket(buf);
            context.queue(() -> packet.handle((ServerPlayerEntity) context.getPlayer()));
        });

        // Register VortexSaveWaypointPacket (Server receives from Client)
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, VORTEX_SAVE_WAYPOINT, (buf, context) -> {
            VortexSaveWaypointPacket packet = new VortexSaveWaypointPacket(buf);
            context.queue(() -> packet.handle((ServerPlayerEntity) context.getPlayer()));
        });

        // Register VortexTeleportPacket (Server receives from Client)
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, VORTEX_TELEPORT, (buf, context) -> {
            VortexTeleportPacket packet = new VortexTeleportPacket(buf);
            context.queue(() -> packet.handle((ServerPlayerEntity) context.getPlayer()));
        });
    }
}
