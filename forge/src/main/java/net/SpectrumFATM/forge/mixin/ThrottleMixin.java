package net.SpectrumFATM.forge.mixin;

import net.SpectrumFATM.black_archive.util.SpaceTimeEventUtil;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.flight.ThrottleControl;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

@Mixin(ThrottleControl.class)
public class ThrottleMixin {

    @Inject(method = "onRightClick", at = @At("HEAD"), cancellable = true, remap = false)
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, PlayerEntity player, CallbackInfoReturnable ci) {
        SpaceTimeEventUtil.setComplexSpaceTimeEvent(player, true);
        return true;
    }
}
