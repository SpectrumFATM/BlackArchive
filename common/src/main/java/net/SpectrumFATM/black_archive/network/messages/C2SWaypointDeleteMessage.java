package net.SpectrumFATM.black_archive.network.messages;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.item.custom.VortexManipulatorItem;
import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;

public class C2SWaypointDeleteMessage extends MessageC2S {

    private final String name;

    public C2SWaypointDeleteMessage(String name) {
        this.name = name;
    }

    public C2SWaypointDeleteMessage(FriendlyByteBuf buf) {
        this.name = buf.readUtf();
    }

    @Override
    public MessageType getType() {
        return BlackArchiveNetworkHandler.WAYPOINT_DELETE;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(name);
    }

    @Override
    public void handle(MessageContext messageContext) {
        ItemStack heldItem = messageContext.getPlayer().getMainHandItem();

        if (!heldItem.isEmpty() && heldItem.hasTag()) {
            CompoundTag nbt = heldItem.getTag();
            if (nbt != null && nbt.contains(name)) {
                nbt.remove(name);
                heldItem.setTag(nbt);
            }
        }
    }
}
