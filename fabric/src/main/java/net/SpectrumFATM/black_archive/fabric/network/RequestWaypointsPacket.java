package net.SpectrumFATM.black_archive.fabric.network;

import io.netty.buffer.Unpooled;
import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.item.ModItems;
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
                ItemStack heldItem = player.getMainHandStack();

                if (heldItem.getItem() != ModItems.VORTEXMANIP) {
                    heldItem = player.getOffHandStack();
                    BlackArchive.LOGGER.info("Offhand item: " + heldItem);
                }

                if (!heldItem.isEmpty() && heldItem.hasNbt()) {
                    NbtCompound nbt = heldItem.getNbt();
                    if (nbt != null) {
                        List<String> waypoints = nbt.getKeys().stream().collect(Collectors.toList());
                        BlackArchive.LOGGER.info("Waypoints: " + waypoints);
                        SendWaypointsPacket.send(player, waypoints);
                    }
                }
            });
        });
    }
}