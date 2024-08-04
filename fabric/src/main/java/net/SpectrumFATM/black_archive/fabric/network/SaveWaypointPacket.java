package net.SpectrumFATM.black_archive.fabric.network;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class SaveWaypointPacket {
    public static final Identifier ID = new Identifier(BlackArchive.MOD_ID, "save_waypoint");

    public static void send(String name, double x, double y, double z, String dimension) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(name);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeString(dimension);
        ClientPlayNetworking.send(ID, buf);
    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            String name = buf.readString(32767);
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            String dimension = buf.readString(32767);

            server.execute(() -> {
                ItemStack heldItem = player.getMainHandStack();
                if (!heldItem.isEmpty()) {
                    NbtCompound nbt = heldItem.getOrCreateNbt();
                    NbtCompound waypointData = new NbtCompound();
                    waypointData.putDouble("x", x);
                    waypointData.putDouble("y", y);
                    waypointData.putDouble("z", z);
                    waypointData.putString("dimension", dimension);
                    nbt.put(name, waypointData);
                    heldItem.setNbt(nbt);
                    BlackArchive.LOGGER.info("Saved waypoint " + name + " at " + x + ", " + y + ", " + z + " in dimension " + dimension);
                }
            });
        });
    }
}