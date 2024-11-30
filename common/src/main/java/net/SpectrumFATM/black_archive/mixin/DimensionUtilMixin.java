package net.SpectrumFATM.black_archive.mixin;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.common.util.DimensionUtil;

import java.util.List;

@Mixin(DimensionUtil.class)
public abstract class DimensionUtilMixin {

    @Inject(method = "isAllowedDimension", at = @At("HEAD"), cancellable = true, remap = false)
    private static boolean isAllowedDimension(RegistryKey<World> level, CallbackInfoReturnable callbackInfo) {
        List<? extends String> bannedDimensions = (List)TRConfig.SERVER.BANNED_DIMENSIONS.get();
        if (level.getValue().toString().equals("black_archive:time_vortex") ||
                level.getValue().getNamespace().toString().contains("tardis") ||
                bannedDimensions.contains(level.getValue().toString())) {
            callbackInfo.setReturnValue(false);
        }
        return true;
    }
}