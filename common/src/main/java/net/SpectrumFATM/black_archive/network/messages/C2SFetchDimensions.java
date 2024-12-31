package net.SpectrumFATM.black_archive.network.messages;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.SpectrumFATM.black_archive.tardis.upgrades.ModUpgrades;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.tardis.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.util.DimensionUtil;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class C2SFetchDimensions extends MessageC2S {

    public C2SFetchDimensions() {
    }

    public C2SFetchDimensions(FriendlyByteBuf buf) {
    }

    @Override
    public MessageType getType() {
        return BlackArchiveNetworkHandler.FETCH_DIMENSIONS;
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    @Override
    public void handle(MessageContext messageContext) {
        ServerPlayer player = messageContext.getPlayer();
        ItemStack heldItem = player.getUseItem();

        Set<ResourceKey<Level>> dimensions = DimensionUtil.getAllowedDimensions(player.getServer());

        List<String> dimensionIds = dimensions.stream().map(ResourceKey::location).map(ResourceLocation::toString).collect(Collectors.toList());

        BlackArchiveNetworkHandler.NETWORK.sendToPlayer(player, new S2COpenVortexScreenMessage(dimensionIds));

        S2COpenVortexScreenMessage message = new S2COpenVortexScreenMessage(dimensionIds);
        message.send(player);
    }
}
