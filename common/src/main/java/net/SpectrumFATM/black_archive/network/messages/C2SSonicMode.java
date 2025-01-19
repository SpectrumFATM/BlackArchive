package net.SpectrumFATM.black_archive.network.messages;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.SpectrumFATM.black_archive.item.custom.SonicItem;
import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.SpectrumFATM.black_archive.tardis.upgrades.ModUpgrades;
import net.SpectrumFATM.black_archive.util.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.tardis.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.common.items.ScrewdriverMode;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.util.DimensionUtil;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.Optional;

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
        String mode = "";

        if (heldItem.getItem() instanceof ScrewdriverItem sonic) {
            ScrewdriverMode newMode = sonic.isScrewdriverMode(heldItem, ScrewdriverMode.ENABLED) ? ScrewdriverMode.DISABLED : ScrewdriverMode.ENABLED;
            sonic.setScrewdriverMode(player, heldItem, newMode, player.getOnPos(), player.serverLevel());
        }
    }
}