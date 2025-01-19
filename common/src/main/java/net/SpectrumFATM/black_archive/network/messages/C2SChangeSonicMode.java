package net.SpectrumFATM.black_archive.network.messages;

import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;

public class C2SChangeSonicMode extends MessageC2S {
    private String mode;
    private boolean shouldDisplayChangeMessage;

    public C2SChangeSonicMode(String mode, boolean shouldDisplayChangeMessage) {
        this.mode = mode;
        this.shouldDisplayChangeMessage = shouldDisplayChangeMessage;
    }

    public C2SChangeSonicMode(FriendlyByteBuf buf) {
        this.mode = buf.readUtf();
        this.shouldDisplayChangeMessage = buf.readBoolean();
    }

    @Override
    public MessageType getType() {
        return BlackArchiveNetworkHandler.SONIC_SETTING;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.mode);
        buf.writeBoolean(this.shouldDisplayChangeMessage);
    }

    @Override
    public void handle(MessageContext messageContext) {
        ItemStack itemStack = messageContext.getPlayer().getMainHandItem();

        setSonicSetting(itemStack, this.mode);
        sendSettingChangeMessage(this.mode, messageContext.getPlayer(), shouldDisplayChangeMessage);
    }

    private static void setSonicSetting(ItemStack stack, String levelName) {
        CompoundTag nbtData = stack.getOrCreateTag(); // Access item's NBT
        nbtData.putString("SonicSetting", levelName);
    }

    private static void sendSettingChangeMessage(String mode, Player player, boolean shouldDisplayChangeMessage) {
        if (shouldDisplayChangeMessage) {
            switch (mode) {
                case "location":
                    player.displayClientMessage(Component.translatable("item.sonic.locator"), true);
                    break;
                case "homing":
                    player.displayClientMessage(Component.translatable("item.sonic.homing"), true);
                    break;
                default:
                    player.displayClientMessage(Component.translatable("item.sonic.block"), true);
                    break;
            }
        }
    }
}