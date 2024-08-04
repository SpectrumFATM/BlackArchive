package net.SpectrumFATM.black_archive.fabric.network;

public class NetworkPackets {

    public static void registerPackets() {
        VortexTeleportPacket.register();
        DimensionRequestPacket.register();
        DimensionResponsePacket.register();
        SaveWaypointPacket.register();
        SendWaypointsPacket.register();
        RequestWaypointsPacket.register();
        DeleteWaypointPacket.register();
    }
}
