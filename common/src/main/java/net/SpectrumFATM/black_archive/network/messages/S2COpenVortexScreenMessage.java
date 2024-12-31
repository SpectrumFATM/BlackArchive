package net.SpectrumFATM.black_archive.network.messages;

import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.SpectrumFATM.black_archive.screen.VortexScreen;
import net.SpectrumFATM.black_archive.util.ScreenUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.util.Platform;

import java.util.List;

public class S2COpenVortexScreenMessage extends MessageS2C {

    private final List<String> dimensions;

    public S2COpenVortexScreenMessage(List<String> dimensions) {
        this.dimensions = dimensions;
    }

    public S2COpenVortexScreenMessage(FriendlyByteBuf buf) {
        this.dimensions = buf.readList(FriendlyByteBuf::readUtf);
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeCollection(dimensions, FriendlyByteBuf::writeUtf);
    }

    @Override
    public void handle(MessageContext context) {
        ScreenUtil.openVortexScreen(dimensions);
    }

    @Override
    public MessageType getType() {
        return BlackArchiveNetworkHandler.OPEN_VORTEX_SCREEN;
    }
}