package net.SpectrumFATM.black_archive.mixin;

import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;

@Mixin(TardisPlayerInfo.class)
public class TardisPlayerInfoMixin {

    private static ServerPlayer serverPlayer;

    @Inject(method = "startShellView", at = @At("HEAD"), remap = false, cancellable = true)
    private void startShellView(ServerPlayer serverPlayer, TardisLevelOperator tardisLevelOperator, TardisNavLocation spectateTarget, boolean timeVortex, CallbackInfo ci) {
        this.serverPlayer = serverPlayer;
        serverPlayer.setInvulnerable(true);
    }

    @Inject(method = "onExitKeybindPressed", at = @At("HEAD"), remap = false, cancellable = true)
    private static void stopShellView(CallbackInfo ci) {
        serverPlayer.setInvulnerable(false);
    }
}
