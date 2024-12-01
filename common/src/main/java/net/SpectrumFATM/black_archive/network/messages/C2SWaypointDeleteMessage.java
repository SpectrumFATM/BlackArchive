package net.SpectrumFATM.black_archive.network.messages;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.item.custom.VortexManipulatorItem;
import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;

public class C2SWaypointDeleteMessage extends MessageC2S {

    private final String name;

    public C2SWaypointDeleteMessage(String name) {
        this.name = name;
    }

    public C2SWaypointDeleteMessage(PacketByteBuf buf) {
        this.name = buf.readString();
    }

    @Override
    public MessageType getType() {
        return BlackArchiveNetworkHandler.WAYPOINT_DELETE;
    }

    public void toBytes(PacketByteBuf buf) {
        buf.writeString(name);
    }

    @Override
    public void handle(MessageContext messageContext) {
        ItemStack heldItem = messageContext.getPlayer().getMainHandStack();

        if (!heldItem.isEmpty() && heldItem.hasNbt()) {
            NbtCompound nbt = heldItem.getNbt();
            if (nbt != null && nbt.contains(name)) {
                nbt.remove(name);
                heldItem.setNbt(nbt);
            }
        }
    }
}
