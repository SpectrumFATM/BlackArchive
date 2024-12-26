package net.SpectrumFATM.black_archive.mixin;

import net.SpectrumFATM.black_archive.tardis.upgrades.ModUpgrades;
import net.SpectrumFATM.black_archive.world.dimension.ModDimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
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

    @Inject(method = "onRightClick(Lwhocraft/tardis_refined/common/capability/tardis/TardisLevelOperator;Lwhocraft/tardis_refined/common/tardis/themes/ConsoleTheme;Lwhocraft/tardis_refined/common/entity/ControlEntity;Lnet/minecraft/entity/player/PlayerEntity;)Z", at = @At("TAIL"), cancellable = true, remap = false)
    public void onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, PlayerEntity player, CallbackInfoReturnable ci) {
        if (player.isSneaking()) {
            if (ModUpgrades.TEMPORAL_ORBIT_UPGRADE.get().isUnlocked(operator.getUpgradeHandler())) {
                operator.getPilotingManager().setTargetLocation(new TardisNavLocation(operator.getPilotingManager().getTargetLocation().getPosition(), operator.getPilotingManager().getCurrentLocation().getDirection(), player.getServer().getWorld(ModDimensions.TIMEDIM_LEVEL_KEY)));
                player.sendMessage(Text.translatable("control.black_archive.temporal_orbit"), true);
            }
        }
    }

    @Inject(method = "onLeftClick(Lwhocraft/tardis_refined/common/capability/tardis/TardisLevelOperator;Lwhocraft/tardis_refined/common/tardis/themes/ConsoleTheme;Lwhocraft/tardis_refined/common/entity/ControlEntity;Lnet/minecraft/entity/player/PlayerEntity;)Z", at = @At("TAIL"), cancellable = true, remap = false)
    public void onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, PlayerEntity player, CallbackInfoReturnable ci) {
        this.onRightClick(operator, theme, controlEntity, player, ci);
    }
}
