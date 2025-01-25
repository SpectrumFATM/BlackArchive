package net.SpectrumFATM.black_archive.network.messages.sonic;

import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.SpectrumFATM.black_archive.util.SonicEngine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.common.items.ScrewdriverMode;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;

public class C2SSonicMode extends MessageC2S {

    public C2SSonicMode() {
    }

    public C2SSonicMode(FriendlyByteBuf buf) {
    }

    @Override
    public MessageType getType() {
        return BlackArchiveNetworkHandler.SONIC_CHANGE;
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    @Override
    public void handle(MessageContext messageContext) {
        ServerPlayer player = messageContext.getPlayer();
        ItemStack heldItem = player.getMainHandItem();

        if (heldItem.getItem() instanceof ScrewdriverItem sonic) {
            ScrewdriverMode newMode = sonic.isScrewdriverMode(heldItem, ScrewdriverMode.ENABLED) ? ScrewdriverMode.DISABLED : ScrewdriverMode.ENABLED;
            sonic.setScrewdriverMode(player, heldItem, newMode, player.getOnPos(), player.serverLevel());
        }
    }
}