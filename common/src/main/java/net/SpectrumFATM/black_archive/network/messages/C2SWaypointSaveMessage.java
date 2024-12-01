package net.SpectrumFATM.black_archive.network.messages;

import net.SpectrumFATM.black_archive.item.custom.VortexManipulatorItem;
import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
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

    public C2SWaypointSaveMessage(PacketByteBuf buf) {
        this.name = buf.readString();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.dimension = buf.readString();
    }

    @Override
    public MessageType getType() {
        return BlackArchiveNetworkHandler.WAYPOINT_SAVE;
    }

    public void toBytes(PacketByteBuf buf) {
        buf.writeString(name);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeString(dimension);
    }

    @Override
    public void handle(MessageContext messageContext) {
            ServerPlayerEntity player = messageContext.getPlayer();
            ItemStack heldItem = player.getMainHandStack();
            if (!(heldItem.getItem() instanceof VortexManipulatorItem)) {
                heldItem = player.getOffHandStack();
            }

            if (!heldItem.isEmpty()) {
                NbtCompound nbt = heldItem.getOrCreateNbt();
                NbtCompound waypointData = new NbtCompound();
                waypointData.putDouble("x", x);
                waypointData.putDouble("y", y);
                waypointData.putDouble("z", z);
                waypointData.putString("dimension", dimension);
                nbt.put(name, waypointData);
                heldItem.setNbt(nbt);

                player.sendMessage(Text.of("Waypoint saved: " + nbt), false);
            }
        }

}
