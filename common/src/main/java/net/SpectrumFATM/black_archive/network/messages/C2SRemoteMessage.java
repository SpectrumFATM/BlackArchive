package net.SpectrumFATM.black_archive.network.messages;

import net.SpectrumFATM.black_archive.item.ModItems;
import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.SpectrumFATM.black_archive.tardis.upgrades.ModUpgrades;
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
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.util.DimensionUtil;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.Optional;

public class C2SRemoteMessage extends MessageC2S {

    private final BlockPos pos;
    private final String levelName;

    public C2SRemoteMessage(BlockPos pos, String levelName) {
        this.pos = pos;
        this.levelName = levelName;
    }

    public C2SRemoteMessage(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.levelName = buf.readUtf();
    }

    @Override
    public MessageType getType() {
        return BlackArchiveNetworkHandler.REMOTE_PACKET;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeUtf(levelName);
    }

    @Override
    public void handle(MessageContext messageContext) {
        ServerPlayer player = messageContext.getPlayer();
        ItemStack heldItem = getHeldRemoteItem(player);

        if (heldItem.isEmpty()) {
            return;
        }

        ResourceKey<Level> currentDimension = player.level().dimension();

        if (!DimensionUtil.isAllowedDimension(currentDimension)) {
            return;
        }

        if (!player.serverLevel().isEmptyBlock(pos.above())) {
            return;
        }

        ResourceLocation dimensionId = new ResourceLocation(levelName);
        ResourceKey<Level> tardisDimension = ResourceKey.create(Registries.DIMENSION, dimensionId);
        ServerLevel tardisWorld = player.getServer().getLevel(tardisDimension);

        if (tardisWorld == null) {
            return;
        }

        Optional<TardisLevelOperator> operatorOptional = TardisLevelOperator.get(tardisWorld);
        if (operatorOptional.isEmpty()) {
            return;
        }

        TardisLevelOperator operator = operatorOptional.get();
        TardisPilotingManager pilotingManager = operator.getPilotingManager();
        UpgradeHandler upgradeHandler = operator.getUpgradeHandler();

        if (!ModUpgrades.REMOTE_UPGRADE.get().isUnlocked(upgradeHandler)
                || pilotingManager.isInRecovery()
                || pilotingManager.isHandbrakeOn()
                || pilotingManager.isInFlight()) {
            return;
        }

        pilotingManager.setTargetLocation(new TardisNavLocation(pos.above(), player.getDirection().getOpposite(), player.serverLevel()));
        pilotingManager.beginFlight(true, null);

        player.level().playSound(null, pos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1.0F, 1.0F);
        PlayerUtil.sendMessage(player, Component.translatable(ModMessages.TARDIS_IS_ON_THE_WAY), true);
    }

    private ItemStack getHeldRemoteItem(ServerPlayer player) {
        ItemStack mainHand = player.getMainHandItem();
        if (mainHand.getItem() == ModItems.REMOTE.get()) return mainHand;

        ItemStack offHand = player.getOffhandItem();
        return offHand.getItem() == ModItems.REMOTE.get() ? offHand : ItemStack.EMPTY;
    }
}