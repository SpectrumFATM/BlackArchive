package net.SpectrumFATM.black_archive.mixin;

import net.SpectrumFATM.black_archive.tardis.upgrades.ModUpgrades;
import net.SpectrumFATM.black_archive.world.dimension.ModDimensions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.control.flight.DimensionalControl;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

@Mixin(DimensionalControl.class)
public class DimensionalMixin {

    @Inject(method = "onRightClick(Lwhocraft/tardis_refined/common/capability/tardis/TardisLevelOperator;Lwhocraft/tardis_refined/common/tardis/themes/ConsoleTheme;Lwhocraft/tardis_refined/common/entity/ControlEntity;Lnet/minecraft/world/entity/player/Player;)Z", at = @At("TAIL"), cancellable = true)
    public void onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player, CallbackInfoReturnable<Boolean> ci) {
        if (player.isShiftKeyDown()) {
            if (ModUpgrades.TEMPORAL_ORBIT_UPGRADE.get().isUnlocked(operator.getUpgradeHandler())) {
                operator.getPilotingManager().setTargetLocation(new TardisNavLocation(operator.getPilotingManager().getTargetLocation().getPosition(), operator.getPilotingManager().getCurrentLocation().getDirection(), player.getServer().getLevel(ModDimensions.TIMEDIM_LEVEL_KEY)));
                player.displayClientMessage(Component.translatable("control.black_archive.temporal_orbit"), true);
            }
        }
    }

    @Inject(method = "onLeftClick(Lwhocraft/tardis_refined/common/capability/tardis/TardisLevelOperator;Lwhocraft/tardis_refined/common/tardis/themes/ConsoleTheme;Lwhocraft/tardis_refined/common/entity/ControlEntity;Lnet/minecraft/world/entity/player/Player;)Z", at = @At("TAIL"), cancellable = true)
    public void onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player, CallbackInfoReturnable<Boolean> ci) {
        this.onRightClick(operator, theme, controlEntity, player, ci);
    }
}