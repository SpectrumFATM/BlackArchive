package net.SpectrumFATM.black_archive.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.common.util.DimensionUtil;

import java.util.List;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

@Mixin(DimensionUtil.class)
public abstract class DimensionUtilMixin {

    @Inject(method = "isAllowedDimension", at = @At("HEAD"), cancellable = true)
    private static void isAllowedDimension(ResourceKey<Level> level, CallbackInfoReturnable callbackInfo) {
        List<? extends String> bannedDimensions = (List)TRConfig.SERVER.BANNED_DIMENSIONS.get();
        if (level.location().toString().equals("black_archive:time_vortex") ||
                level.location().getNamespace().toString().contains("tardis") ||
                bannedDimensions.contains(level.location().toString())) {
            callbackInfo.setReturnValue(false);
        }
    }
}