package net.SpectrumFATM.black_archive.network.messages;

import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.SpectrumFATM.black_archive.renderer.TardisWarningRenderer;
import net.minecraft.network.PacketByteBuf;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;

public class S2CTardisWarningMessage extends MessageS2C {
    // Constructor for creating the packet
    public S2CTardisWarningMessage() {
    }

    @Override
    public MessageType getType() {
        return BlackArchiveNetworkHandler.TARDIS_WARN;
    }

    // Constructor for reading data from the buffer
    public S2CTardisWarningMessage(PacketByteBuf buf) {
    }

    // Method to write data to the buffer
    public void toBytes(PacketByteBuf buf) {
    }

    @Override
    public void handle(MessageContext messageContext) {
        TardisWarningRenderer.triggerRedFlash();
    }

}
