package net.SpectrumFATM.black_archive.network.messages;

import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.SpectrumFATM.black_archive.util.DimensionRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.core.registries.Registries;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;

import java.util.UUID;

public class C2SAddDimension extends MessageC2S {

    private final String dimensionName;

    public C2SAddDimension(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public C2SAddDimension(FriendlyByteBuf buf) {
        this.dimensionName = buf.readUtf();
    }

    @Override
    public MessageType getType() {
        return BlackArchiveNetworkHandler.ADD_DIM;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(dimensionName);
    }

    @Override
    public void handle(MessageContext messageContext) {
        ServerPlayer player = messageContext.getPlayer();
        UUID newUUID = UUID.randomUUID();
        ResourceLocation dimensionLocation = new ResourceLocation("black_archive", newUUID.toString());
        ResourceKey<Level> dimensionKey = ResourceKey.create(Registries.DIMENSION, dimensionLocation);

        if (player.getServer().getLevel(dimensionKey) == null) {
            DimensionRegistry.createDimension(player.getServer(), dimensionKey, player);
        }
    }
}