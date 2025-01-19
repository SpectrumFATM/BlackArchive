package net.SpectrumFATM.black_archive.network;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.network.messages.*;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.NetworkManager;


public class BlackArchiveNetworkHandler {

    public static final NetworkManager NETWORK = NetworkManager.create(new ResourceLocation(BlackArchive.MOD_ID, "channel"));

    public static MessageType WAYPOINT_SAVE, WAYPOINT_DELETE, VM_TELEPORT, REMOTE_PACKET, FETCH_DIMENSIONS, OPEN_VORTEX_SCREEN, SONIC_SETTING, SONIC_CHANGE, SONIC_LOCK, SONIC_LOCATION;

    public static void registerPackets() {
        WAYPOINT_SAVE = NETWORK.registerC2S("waypoint_save", C2SWaypointSaveMessage::new);
        WAYPOINT_DELETE = NETWORK.registerC2S("waypoint_delete", C2SWaypointDeleteMessage::new);
        VM_TELEPORT = NETWORK.registerC2S("vm_teleport", C2STeleportMessage::new);
        REMOTE_PACKET = NETWORK.registerC2S("remote", C2SRemoteMessage::new);
        FETCH_DIMENSIONS = NETWORK.registerC2S("fetch_dimensions", C2SFetchDimensions::new);
        OPEN_VORTEX_SCREEN = NETWORK.registerS2C("open_vortex_screen", S2COpenVortexScreenMessage::new);
        SONIC_CHANGE = NETWORK.registerC2S("sonic_change", C2SSonicMode::new);
        SONIC_LOCK = NETWORK.registerC2S("sonic_lock", C2SLockFunction::new);
        SONIC_LOCATION = NETWORK.registerC2S("sonic_location", C2SSetLocation::new);
        SONIC_SETTING = NETWORK.registerC2S("sonic_setting", C2SChangeSonicMode::new);
    }
}
