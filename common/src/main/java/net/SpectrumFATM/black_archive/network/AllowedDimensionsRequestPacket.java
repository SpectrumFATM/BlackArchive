package net.SpectrumFATM.black_archive.network;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import dev.architectury.networking.NetworkManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraft.util.Identifier;
import io.netty.buffer.Unpooled;
import whocraft.tardis_refined.common.util.DimensionUtil;

import java.util.List;
import java.util.stream.Collectors;

public class AllowedDimensionsRequestPacket {

    public AllowedDimensionsRequestPacket() {
        // Default constructor
    }

    public AllowedDimensionsRequestPacket(PacketByteBuf buf) {
        // No data to read
    }

    public void encode(PacketByteBuf buf) {
        // No data to write
    }

    public static void handle(PacketByteBuf packetByteBuf, NetworkManager.PacketContext packetContext) {
        ServerPlayerEntity player = (ServerPlayerEntity) packetContext.getPlayer();
        MinecraftServer server = player.server;

        // Collect allowed dimensions
        List<String> allowedDimensions = server.getWorldRegistryKeys().stream()
                .filter(level -> DimensionUtil.isAllowedDimension(level) &&
                        !level.getValue().equals(new Identifier("black_archive:time_vortex")))
                .map(level -> level.getValue().toString())
                .collect(Collectors.toList());

        // Send response packet back to client
        AllowedDimensionsResponsePacket responsePacket = new AllowedDimensionsResponsePacket(allowedDimensions);
        NetworkManager.sendToPlayer(player, NetworkHandler.ALLOWED_DIMENSIONS_RESPONSE, packetByteBuf);
    }

    public static void register() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, NetworkHandler.ALLOWED_DIMENSIONS_REQUEST,
                (buf, context) -> {
                    AllowedDimensionsRequestPacket packet = new AllowedDimensionsRequestPacket(buf);
                    context.queue(() -> packet.handle(buf, context));
                });
    }

    public PacketByteBuf toBuffer() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        this.encode(buf);
        return buf;
    }

    public static void send() {
        // Create an empty buffer for this request
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        new AllowedDimensionsRequestPacket().encode(buf);

        // Send the packet to the server
        NetworkManager.sendToServer(NetworkHandler.ALLOWED_DIMENSIONS_REQUEST, buf);
    }
}