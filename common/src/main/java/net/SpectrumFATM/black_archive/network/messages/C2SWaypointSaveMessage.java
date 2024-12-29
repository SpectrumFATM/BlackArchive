package net.SpectrumFATM.black_archive.network.messages;

import net.SpectrumFATM.black_archive.item.custom.VortexManipulatorItem;
import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;

public class C2SWaypointSaveMessage extends MessageC2S {

    private final String name;
    private final double x;
    private final double y;
    private final double z;
    private final String dimension;

    public C2SWaypointSaveMessage(String name, double x, double y, double z, String dimension) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
    }

    public C2SWaypointSaveMessage(FriendlyByteBuf buf) {
        this.name = buf.readUtf();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.dimension = buf.readUtf();
    }

    @Override
    public MessageType getType() {
        return BlackArchiveNetworkHandler.WAYPOINT_SAVE;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(name);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeUtf(dimension);
    }

    @Override
    public void handle(MessageContext messageContext) {
            ServerPlayer player = messageContext.getPlayer();
            ItemStack heldItem = player.getMainHandItem();
            if (!(heldItem.getItem() instanceof VortexManipulatorItem)) {
                heldItem = player.getOffhandItem();
            }

            if (!heldItem.isEmpty()) {
                CompoundTag nbt = heldItem.getOrCreateTag();
                CompoundTag waypointData = new CompoundTag();
                waypointData.putDouble("x", x);
                waypointData.putDouble("y", y);
                waypointData.putDouble("z", z);
                waypointData.putString("dimension", dimension);
                nbt.put(name, waypointData);
                heldItem.setTag(nbt);
            }
        }

}
