package net.SpectrumFATM.black_archive.network;

import dev.architectury.networking.NetworkManager;
import net.SpectrumFATM.BlackArchive;
import net.minecraft.util.Identifier; // Yarn mapping for ResourceLocation


public class NetworkHandler {
    public static final Identifier WAYPOINT_SAVE_ID = new Identifier(BlackArchive.MOD_ID, "waypoint_save");
    public static final Identifier VM_TELEPORT = new Identifier(BlackArchive.MOD_ID, "vortexmanipulator_teleport");
    public static final Identifier TARDIS_WARN = new Identifier(BlackArchive.MOD_ID, "tardis_warning");

    public static void registerPackets() {
        // Register the packet for server-side handling
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, WAYPOINT_SAVE_ID, (buf, contextSupplier) -> {
            VMSavePacket packet = new VMSavePacket(buf);
            packet.handle(() -> contextSupplier);
        });

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, VM_TELEPORT, (buf, contextSupplier) -> {
            VMTeleportPacket packet = new VMTeleportPacket(buf);
            packet.handle(() -> contextSupplier);
        });

        NetworkManager.registerReceiver(NetworkManager.Side.S2C, TARDIS_WARN, (buf, contextSupplier) -> {
            TardisWarningPacket packet = new TardisWarningPacket(buf);
            packet.handle(() -> contextSupplier);
        });
    }
}
