package net.SpectrumFATM.black_archive.network;

import dev.architectury.networking.NetworkManager;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.item.custom.VortexManipulatorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class VMSavePacket {

    private final String name;
    private final double x;
    private final double y;
    private final double z;
    private final String dimension;

    public VMSavePacket(String name, double x, double y, double z, String dimension) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
    }

    public VMSavePacket(PacketByteBuf buf) {
        this.name = buf.readString();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.dimension = buf.readString();
    }

    public void toBytes(PacketByteBuf buf) {
        buf.writeString(name);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeString(dimension);
    }


    public void handle(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> {
            ServerPlayerEntity player = (ServerPlayerEntity) contextSupplier.get().getPlayer();
            ItemStack heldItem = player.getMainHandStack();
            if (!(heldItem.getItem() instanceof VortexManipulatorItem)) {
                heldItem = player.getOffHandStack();
            }

            if (!heldItem.isEmpty()) {
                NbtCompound nbt = heldItem.getOrCreateNbt();
                NbtCompound waypointData = new NbtCompound();
                waypointData.putDouble("x", x);
                waypointData.putDouble("y", y);
                waypointData.putDouble("z", z);
                waypointData.putString("dimension", dimension);
                nbt.put(name, waypointData);
                heldItem.setNbt(nbt);

                player.sendMessage(Text.of("Waypoint saved: " + nbt), false);
            }
        });
    }
}
