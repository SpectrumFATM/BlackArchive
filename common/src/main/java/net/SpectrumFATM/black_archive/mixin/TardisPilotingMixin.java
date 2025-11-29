package net.SpectrumFATM.black_archive.mixin;

import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.ad_astra_compat.util.AATools;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.world.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;

import static com.ibm.icu.impl.SortedSetRelation.B;

@Mixin(TardisPilotingManager.class)
public abstract class TardisPilotingMixin {

    private boolean oxygenShell;

    @Shadow
    private final TardisLevelOperator operator;

    @Shadow private TardisNavLocation currentLocation;

    @Shadow public abstract TardisNavLocation getTargetLocation();

    @Shadow public abstract TardisNavLocation getCurrentLocation();

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

    @Inject(method = "endFlight", at = @At("HEAD"), remap = false, cancellable = false)
    private void endFlight(boolean forceFlightEnd, boolean isCrashing, CallbackInfoReturnable<Boolean> cir) {
        TardisNavLocation tardisNavLocation = getCurrentLocation();
        int radius = BlackArchiveConfig.COMMON.tardisLifeSupportRange.get();
        try {
            if (!OxygenApi.API.hasOxygen(tardisNavLocation.getLevel())) {
                oxygenShell = true;
                OxygenApi.API.setOxygen(tardisNavLocation.getLevel(), AATools.getPositionsInRadius(tardisNavLocation.getPosition(), radius, radius), true);
                TemperatureApi.API.setTemperature(tardisNavLocation.getLevel(), AATools.getPositionsInRadius(tardisNavLocation.getPosition(), radius, radius), (short)22);
            }
        } catch(Exception e) {
            BlackArchive.LOGGER.error("Could not initiate Tardis life support systems: " + e.getMessage());
        }
    }

    @Inject(method = "beginFlight", at = @At("HEAD"), remap = false, cancellable = true)
    private void beginFlight(boolean autoLand, CallbackInfoReturnable<Boolean> cir) {
        TardisNavLocation tardisNavLocation = getCurrentLocation();
        int radius = BlackArchiveConfig.COMMON.tardisLifeSupportRange.get();
        try {
            if (oxygenShell) {
                oxygenShell = false;
                OxygenApi.API.removeOxygen(tardisNavLocation.getLevel(), AATools.getPositionsInRadius(tardisNavLocation.getPosition(), radius, radius));
                TemperatureApi.API.removeTemperature(tardisNavLocation.getLevel(), AATools.getPositionsInRadius(tardisNavLocation.getPosition(), radius, radius));
            }
        } catch (Exception e) {
            BlackArchive.LOGGER.error("Could not deactivate Tardis life support systems: " + e.getMessage());
        }
    }
}