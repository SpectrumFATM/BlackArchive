package net.SpectrumFATM.black_archive.mixin;

import net.SpectrumFATM.black_archive.util.SpaceTimeEventUtil;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.flight.ThrottleControl;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

@Mixin(ThrottleControl.class)
public class ThrottleMixin {

    @Inject(method = "onRightClick(Lwhocraft/tardis_refined/common/capability/tardis/TardisLevelOperator;Lwhocraft/tardis_refined/common/tardis/themes/ConsoleTheme;Lwhocraft/tardis_refined/common/entity/ControlEntity;Lnet/minecraft/world/entity/player/Player;)Z", at = @At("HEAD"))
    public void onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player, CallbackInfoReturnable<Boolean> cir) {
        SpaceTimeEventUtil.setComplexSpaceTimeEvent(player, true);
    }
}