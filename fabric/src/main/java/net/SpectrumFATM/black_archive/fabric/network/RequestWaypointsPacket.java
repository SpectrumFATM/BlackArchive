package net.SpectrumFATM.black_archive.fabric.network;

import io.netty.buffer.Unpooled;
import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.stream.Collectors;

public class RequestWaypointsPacket {
    public static final Identifier ID = new Identifier(BlackArchive.MOD_ID, "request_waypoints");

    public static void send() {
        ClientPlayNetworking.send(ID, new PacketByteBuf(Unpooled.buffer()));
    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                // Handle the request on the server side
                ItemStack heldItem = player.getMainHandStack();
                if (!heldItem.isEmpty() && heldItem.hasNbt()) {
                    NbtCompound nbt = heldItem.getNbt();
                    if (nbt != null) {
                        List<String> waypoints = nbt.getKeys().stream().collect(Collectors.toList());
                        SendWaypointsPacket.send(player, waypoints);
                    }
                }
            });
        });
    }
}