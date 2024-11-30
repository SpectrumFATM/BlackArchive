package net.SpectrumFATM.black_archive.network;

import dev.architectury.networking.NetworkManager;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.item.custom.VortexManipulatorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class VortexSaveWaypointPacket {
    public static final Identifier ID = new Identifier(BlackArchive.MOD_ID, "vortex_save_waypoint");

    private final String name;
    private final double x, y, z;
    private final String dimension;

    public VortexSaveWaypointPacket(String name, double x, double y, double z, String dimension) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
    }

    public VortexSaveWaypointPacket(PacketByteBuf buf) {
        this.name = buf.readString(32767);
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.dimension = buf.readString(32767);
    }

    public void encode(PacketByteBuf buf) {
        buf.writeString(name, 32767);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeString(dimension, 32767);
    }

    public static void send(String name, double x, double y, double z, String dimension) {
        PacketByteBuf buf = new PacketByteBuf(io.netty.buffer.Unpooled.buffer());
        new VortexSaveWaypointPacket(name, x, y, z, dimension).encode(buf);
        NetworkManager.sendToServer(ID, buf);
    }

    public static void register() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, ID, (buf, context) -> {
            VortexSaveWaypointPacket packet = new VortexSaveWaypointPacket(buf);
            context.queue(() -> packet.handle((ServerPlayerEntity) context.getPlayer()));
        });
    }

    // Handle method to process the packet data on the server
    public void handle(ServerPlayerEntity player) {
        ItemStack heldItem = player.getMainHandStack();

        // Check if the player is holding a VortexManipulatorItem
        if (!(heldItem.getItem() instanceof VortexManipulatorItem)) {
            heldItem = player.getOffHandStack();
        }

        if (!heldItem.isEmpty() && heldItem.getItem() instanceof VortexManipulatorItem) {
            NbtCompound nbt = heldItem.getOrCreateNbt();
            NbtCompound waypointData = new NbtCompound();

            // Store the waypoint data
            waypointData.putDouble("x", x);
            waypointData.putDouble("y", y);
            waypointData.putDouble("z", z);
            waypointData.putString("dimension", dimension);
            nbt.put(name, waypointData);

            // Update the item NBT
            heldItem.setNbt(nbt);
        }
    }
}
