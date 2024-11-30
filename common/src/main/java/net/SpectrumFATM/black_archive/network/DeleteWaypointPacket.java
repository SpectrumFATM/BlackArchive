package net.SpectrumFATM.black_archive.network;

import dev.architectury.networking.NetworkManager;
import net.SpectrumFATM.BlackArchive;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class DeleteWaypointPacket {
    public static final Identifier ID = new Identifier(BlackArchive.MOD_ID, "delete_waypoint");

    private final String waypointName;

    // Constructor for sending the packet
    public DeleteWaypointPacket(String waypointName) {
        this.waypointName = waypointName;
    }

    // Constructor for decoding the packet
    public DeleteWaypointPacket(PacketByteBuf buf) {
        this.waypointName = buf.readString(32767);
    }

    // Encodes the packet data into the buffer
    public void encode(PacketByteBuf buf) {
        buf.writeString(waypointName, 32767);
    }

    // Handles the packet when received on the server
    public void handle(ServerPlayerEntity player) {
        player.server.execute(() -> {
            ItemStack heldItem = player.getMainHandStack();
            if (!heldItem.isEmpty() && heldItem.hasNbt()) {
                NbtCompound tag = heldItem.getNbt();
                if (tag != null && tag.contains(waypointName)) {
                    tag.remove(waypointName);
                    heldItem.setNbt(tag);
                }
            }
        });
    }

    // Sends the packet from the client to the server
    public static void send(String name) {
        PacketByteBuf buf = new PacketByteBuf(io.netty.buffer.Unpooled.buffer());
        new DeleteWaypointPacket(name).encode(buf);
        NetworkManager.sendToServer(ID, buf);
    }

    // Registers the packet on the server side
    public static void register() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, ID, (buf, context) -> {
            DeleteWaypointPacket packet = new DeleteWaypointPacket(buf);
            context.queue(() -> packet.handle((ServerPlayerEntity) context.getPlayer()));
        });
    }
}