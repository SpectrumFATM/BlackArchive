package net.SpectrumFATM.black_archive.item.custom;

import net.SpectrumFATM.black_archive.tardis.upgrades.ModUpgrades;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.util.DimensionUtil;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RemoteItem  extends KeyItem {
    public RemoteItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ServerWorld currentDimension = (ServerWorld) context.getWorld();
        ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();
        BlockPos blockPos = context.getBlockPos();

        if (!currentDimension.getRegistryKey().toString().startsWith("tardis_refined:")) {
            ArrayList<RegistryKey<World>> keyChain = KeyItem.getKeychain(player.getActiveItem());
            if (!keyChain.isEmpty()) {
                RegistryKey<World> dimension = KeyItem.getKeychain(player.getActiveItem()).get(0);
                if (player.getServerWorld().isAir(blockPos.up()) && DimensionUtil.isAllowedDimension(player.getWorld().getRegistryKey())) {
                    ServerWorld tardisLevel = Platform.getServer().getWorld(dimension);
                    Optional<TardisLevelOperator> operatorOptional = TardisLevelOperator.get(tardisLevel);
                    if (operatorOptional.isEmpty()) {
                        return ActionResult.PASS;
                    }

                    TardisLevelOperator operator = operatorOptional.get();
                    TardisPilotingManager pilotManager = operator.getPilotingManager();
                    UpgradeHandler upgradeHandler = operator.getUpgradeHandler();
                    if (ModUpgrades.REMOTE_UPGRADE.get().isUnlocked(upgradeHandler) && pilotManager.beginFlight(true, null) && !pilotManager.isOnCooldown() && !pilotManager.isHandbrakeOn() && DimensionUtil.isAllowedDimension(currentDimension.getRegistryKey())) {
                        pilotManager.setTargetLocation(new TardisNavLocation(blockPos.up(), player.getHorizontalFacing().getOpposite(), player.getServerWorld()));
                        player.getServerWorld().playSound(null, blockPos, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        PlayerUtil.sendMessage(player, Text.translatable(ModMessages.TARDIS_IS_ON_THE_WAY), true);
                    }
                }
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("item.remote.tooltip").formatted(Formatting.GOLD));
    }
}
