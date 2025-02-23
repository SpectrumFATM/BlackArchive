package net.SpectrumFATM.black_archive.mixin;

import net.SpectrumFATM.black_archive.world.dimension.ModDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;

@Mixin(TardisPilotingManager.class)
public abstract class TardisPilotingMixin {

    @Shadow
    private final TardisLevelOperator operator;

    protected TardisPilotingMixin(TardisLevelOperator operator) {
        this.operator = operator;
    }

    @Inject(method = "findClosestValidPosition", at = @At("HEAD"), cancellable = true, remap = false)
    public void findClosestValidPosition(TardisNavLocation location, CallbackInfoReturnable<TardisNavLocation> ci) {
        if (location.getDimensionKey().location() == ModDimensions.SPACEDIM_LEVEL_KEY.location() || location.getDimensionKey().location() == ModDimensions.TIMEDIM_LEVEL_KEY.location()) {
            ci.setReturnValue(location);
        }
    }

    @Inject(method ="isInFlight", at = @At("HEAD"), remap = false, cancellable = true)
    public void isInFlight(CallbackInfoReturnable<Boolean> cir) {
        TardisPilotingManager pilotingManager = (TardisPilotingManager) (Object) this;
        if (pilotingManager.getCurrentLocation().getDimensionKey().location() == ModDimensions.TIMEDIM_LEVEL_KEY.location() || pilotingManager.getCurrentLocation().getDimensionKey().location() == ModDimensions.TIMEDIM_LEVEL_KEY.location()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method ="beginFlight", at = @At("HEAD"), remap = false, cancellable = true)
    private void stopFlightDanceBeforeFlight(boolean autoLand, CallbackInfoReturnable<Boolean> cir) {
        TardisPilotingManager pilotingManager = (TardisPilotingManager) (Object) this;
        if (this.operator.getFlightDanceManager().isDancing() && pilotingManager.getCurrentLocation().getDimensionKey().location() == ModDimensions.TIMEDIM_LEVEL_KEY.location() || pilotingManager.getCurrentLocation().getDimensionKey().location() == ModDimensions.TIMEDIM_LEVEL_KEY.location()) {
            this.operator.getFlightDanceManager().stopDancing();
        }
    }
}