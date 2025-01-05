package net.SpectrumFATM.black_archive.mixin;

import net.SpectrumFATM.black_archive.world.dimension.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.TardisClientLogic;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;

import java.util.Optional;

@Mixin(TardisClientLogic.class)
public class TardisClientLogicMixin {

    @Inject(method = "update(Lwhocraft/tardis_refined/client/TardisClientData;)V", at = @At("HEAD"), cancellable = true, remap = false)
    private static void preventRotorStopOnLanding(TardisClientData tardisClientData, CallbackInfo ci) {
        Player player = Minecraft.getInstance().player;
        ServerLevel tardisWorld = Minecraft.getInstance().getSingleplayerServer().getLevel(tardisClientData.getLevelKey());
        Optional<TardisLevelOperator> tardisLevelOperator = TardisLevelOperator.get(tardisWorld);

        if (tardisLevelOperator.get().getPilotingManager().getCurrentLocation().getDimensionKey() == ModDimensions.TIMEDIM_LEVEL_KEY) {
            tardisClientData.setFlying(true);
            if (tardisClientData.isFlying() && !tardisClientData.ROTOR_ANIMATION.isStarted()) {
                tardisClientData.ROTOR_ANIMATION.start(0);
            } else if (!tardisClientData.isFlying()) {
                tardisClientData.isFlying();
            }
        }

    }
}