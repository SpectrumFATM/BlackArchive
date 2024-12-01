package net.SpectrumFATM.forge.mixin;

import net.SpectrumFATM.black_archive.world.dimension.ModDimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.TardisClientLogic;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;

import java.util.Optional;

@Mixin(TardisClientLogic.class)
public class TardisClientLogicMixin {

    @Inject(method = "update", at = @At("HEAD"), cancellable = true, remap = false)
    private static void preventRotorStopOnLanding(TardisClientData tardisClientData, CallbackInfo ci) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        ServerWorld tardisWorld = MinecraftClient.getInstance().getServer().getWorld(tardisClientData.getLevelKey());
        Optional<TardisLevelOperator> tardisLevelOperator = TardisLevelOperator.get(tardisWorld);

        if (tardisLevelOperator.get().getPilotingManager().getCurrentLocation().getDimensionKey() == ModDimensions.TIMEDIM_LEVEL_KEY) {
            tardisClientData.setFlying(true);
            if (tardisClientData.isFlying() && !tardisClientData.ROTOR_ANIMATION.isRunning()) {
                tardisClientData.ROTOR_ANIMATION.start(0);
            } else if (!tardisClientData.isFlying()) {
                tardisClientData.isFlying();
            }
            ci.cancel();
        }

        if (tardisClientData.isTakingOff() && tardisClientData.ROTOR_ANIMATION.isRunning()) {
            ci.cancel();
        }

    }
}