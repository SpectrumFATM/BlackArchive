package net.SpectrumFATM.black_archive.network.messages;

import net.SpectrumFATM.black_archive.item.ModItems;
import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.SpectrumFATM.black_archive.tardis.upgrades.ModUpgrades;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

import java.util.Optional;

public class C2SRemoteMessage extends MessageC2S {

    private final BlockPos pos;

    public C2SRemoteMessage(BlockPos pos) {
        this.pos = pos;
    }

    public C2SRemoteMessage(PacketByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    @Override
    public MessageType getType() {
        return BlackArchiveNetworkHandler.REMOTE_PACKET;
    }

    public void toBytes(PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public void handle(MessageContext messageContext) {
        ServerPlayerEntity player = messageContext.getPlayer();
        ItemStack heldItem = getHeldRemoteItem(player);

        if (heldItem.isEmpty()) return;

        RegistryKey<World> currentDimension = player.getWorld().getRegistryKey();

        // Validate dimension
        if (currentDimension.getValue().toString().startsWith("tardis_refined:")) return;

        // Validate keychain
        if (!(heldItem.getItem() instanceof KeyItem keyItem)) return;
        var keyChain = keyItem.getKeychain(heldItem);
        if (keyChain.isEmpty()) return;

        // Validate air block and dimension
        if (!player.getServerWorld().isAir(pos.up()) || !DimensionUtil.isAllowedDimension(currentDimension)) return;

        RegistryKey<World> tardisDimension = keyChain.get(0);
        ServerWorld tardisWorld = player.getServer().getWorld(tardisDimension);
        if (tardisWorld == null) return;

        // Validate TARDIS capabilities
        Optional<TardisLevelOperator> operatorOptional = TardisLevelOperator.get(tardisWorld);
        if (operatorOptional.isEmpty()) return;

        TardisLevelOperator operator = operatorOptional.get();
        TardisPilotingManager pilotingManager = operator.getPilotingManager();
        UpgradeHandler upgradeHandler = operator.getUpgradeHandler();

        // Validate upgrades and piloting state
        if (!ModUpgrades.REMOTE_UPGRADE.get().isUnlocked(upgradeHandler)
                || pilotingManager.isInRecovery()
                || pilotingManager.isHandbrakeOn()
                || !pilotingManager.beginFlight(true, null)) {
            return;
        }

        // Set TARDIS target location
        pilotingManager.setTargetLocation(new TardisNavLocation(pos.up(), player.getHorizontalFacing().getOpposite(), player.getServerWorld()));

        // Play sound and notify player
        player.getWorld().playSound(null, pos, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
        PlayerUtil.sendMessage(player, Text.translatable(ModMessages.TARDIS_IS_ON_THE_WAY), true);
    }

    private ItemStack getHeldRemoteItem(ServerPlayerEntity player) {
        ItemStack mainHand = player.getMainHandStack();
        if (mainHand.getItem() == ModItems.REMOTE.get()) return mainHand;

        ItemStack offHand = player.getOffHandStack();
        return offHand.getItem() == ModItems.REMOTE.get() ? offHand : ItemStack.EMPTY;
    }
}
