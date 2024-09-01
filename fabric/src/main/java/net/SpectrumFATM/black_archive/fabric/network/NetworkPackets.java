package net.SpectrumFATM.black_archive.fabric.network;

public class NetworkPackets {

    public static void registerPackets() {
        VortexTeleportPacket.register();
        AllowedDimensionsRequestPacket.register();
        DeleteWaypointPacket.register();
        RemotePacket.register();
    }

    public static void registerClientSidePackets() {
        AllowedDimensionsResponsePacket.registerClient();
    }
}
