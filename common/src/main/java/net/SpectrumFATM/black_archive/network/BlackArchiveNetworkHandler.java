package net.SpectrumFATM.black_archive.network;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.network.messages.C2STeleportMessage;
import net.SpectrumFATM.black_archive.network.messages.C2SWaypointSaveMessage;
import net.SpectrumFATM.black_archive.network.messages.S2CTardisWarningMessage;
import net.minecraft.util.Identifier;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.NetworkManager;


public class BlackArchiveNetworkHandler {

    public static final NetworkManager NETWORK = NetworkManager.create(new Identifier(BlackArchive.MOD_ID, "channel"));


    public static MessageType WAYPOINT_SAVE, VM_TELEPORT, TARDIS_WARN;

    public static void registerPackets() {
        WAYPOINT_SAVE = NETWORK.registerC2S("waypoint_save", C2SWaypointSaveMessage::new);
        VM_TELEPORT = NETWORK.registerC2S("vm_teleport", C2STeleportMessage::new);
        TARDIS_WARN = NETWORK.registerS2C("tardis_warn", S2CTardisWarningMessage::new);

    }
}