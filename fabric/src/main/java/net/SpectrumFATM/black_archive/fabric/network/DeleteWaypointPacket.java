package net.SpectrumFATM.black_archive.fabric.network;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class DeleteWaypointPacket {
    public static final Identifier ID = new Identifier(BlackArchive.MOD_ID, "delete_waypoint");

    public static void send(String name) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(name);
        ClientPlayNetworking.send(ID, buf);
    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            String name = buf.readString(32767);

            server.execute(() -> {
                ItemStack heldItem = player.getMainHandStack();
                if (!heldItem.isEmpty() && heldItem.hasNbt()) {
                    NbtCompound nbt = heldItem.getNbt();
                    if (nbt != null && nbt.contains(name)) {
                        nbt.remove(name);
                        heldItem.setNbt(nbt);
                    }
                }
            });
        });
    }
}