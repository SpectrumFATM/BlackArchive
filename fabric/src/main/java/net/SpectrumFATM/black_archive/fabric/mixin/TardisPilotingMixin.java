package net.SpectrumFATM.black_archive.fabric.mixin;

import net.SpectrumFATM.black_archive.world.dimension.ModDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;

@Mixin(TardisPilotingManager.class)
public abstract class TardisPilotingMixin {

    @Inject(method = "findClosestValidPosition", at = @At("HEAD"), cancellable = true, remap = false)
    public void findClosestValidPosition(TardisNavLocation location, CallbackInfoReturnable<TardisNavLocation> ci) {
        if (location.getDimensionKey().getValue() == ModDimensions.SPACEDIM_LEVEL_KEY.getValue() || location.getDimensionKey().getValue() == ModDimensions.TIMEDIM_LEVEL_KEY.getValue()) {
            ci.setReturnValue(location);
        }
    }
}