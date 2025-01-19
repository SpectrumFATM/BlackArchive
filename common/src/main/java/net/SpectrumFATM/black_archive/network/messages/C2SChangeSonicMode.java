package net.SpectrumFATM.black_archive.network.messages;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.SpectrumFATM.black_archive.util.Platform;
import net.SpectrumFATM.black_archive.util.TARDISBindUtil;
import net.SpectrumFATM.black_archive.util.sonic.SonicEngine;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.common.items.ScrewdriverMode;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.registry.TRSoundRegistry;

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