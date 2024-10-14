package net.SpectrumFATM.black_archive.fabric.network;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.item.ModItems;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.util.DimensionUtil;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.TRUpgrades;

import java.util.ArrayList;
import java.util.Optional;

public class RemotePacket {
    public static final Identifier ID = new Identifier(BlackArchive.MOD_ID, "remote_packet");
    private final BlockPos pos;

    public RemotePacket(BlockPos pos) {
        this.pos = pos;
    }

    public static void send(BlockPos pos) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        ClientPlayNetworking.send(ID, buf);
    }

    public static RemotePacket decode(PacketByteBuf buf) {
        return new RemotePacket(buf.readBlockPos());
    }

    public void encode(PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            RemotePacket packet = RemotePacket.decode(buf);
            server.execute(() -> handle(player ,packet.pos));
        });
    }

    private static ActionResult handle(ServerPlayerEntity player, BlockPos pos) {
        BlockPos blockPos = pos;
        RegistryKey currentDimension = player.getServerWorld().getRegistryKey();
        ItemStack hand = player.getMainHandStack();

        if (hand.getItem() != ModItems.REMOTE) {
            hand = player.getOffHandStack();
        }

        if (!currentDimension.getValue().toString().startsWith("tardis_refined:")) {
            if (hand.getItem() instanceof KeyItem) {
                ArrayList<RegistryKey<World>> keyChain = KeyItem.getKeychain(hand);
                if (!keyChain.isEmpty()) {
                    RegistryKey<World> dimension = KeyItem.getKeychain(hand).get(0);
                    if (player.getServerWorld().isAir(blockPos.up()) && DimensionUtil.isAllowedDimension(player.getWorld().getRegistryKey())) {
                        ServerWorld tardisLevel = Platform.getServer().getWorld(dimension);
                        Optional<TardisLevelOperator> operatorOptional = TardisLevelOperator.get(tardisLevel);
                        if (operatorOptional.isEmpty()) {
                            return ActionResult.PASS;
                        }

                        TardisLevelOperator operator = operatorOptional.get();
                        TardisPilotingManager pilotManager = operator.getPilotingManager();
                        UpgradeHandler upgradeHandler = operator.getUpgradeHandler();
                        if (TRUpgrades.LANDING_PAD.get().isUnlocked(upgradeHandler) && pilotManager.beginFlight(true,null) && !pilotManager.isOnCooldown()) {
                            pilotManager.setTargetLocation(new TardisNavLocation(blockPos.up(), player.getHorizontalFacing().getOpposite(), player.getServerWorld()));
                            player.getServerWorld().playSound(null, blockPos, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            PlayerUtil.sendMessage(player, Text.translatable(ModMessages.TARDIS_IS_ON_THE_WAY), true);
                        }
                    }
                }
            }
        }
        return null;
    }
}