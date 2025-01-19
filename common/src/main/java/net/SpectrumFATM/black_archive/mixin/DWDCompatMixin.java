package net.SpectrumFATM.black_archive.mixin;

import dev.jeryn.doctorwho.compat.TardisRefinedCompat;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.util.SonicEngine;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TardisRefinedCompat.class)
public class DWDCompatMixin {

    @Inject(method = "doSonicInteraction(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;", at = @At("HEAD"), cancellable = true)
    private static void doSonicInteraction(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (BlackArchiveConfig.COMMON.enableSonicEngine.get()) {
            SonicEngine.blockActivate(context);
        }
        cir.setReturnValue(InteractionResult.SUCCESS);
        cir.cancel();
    }
}
