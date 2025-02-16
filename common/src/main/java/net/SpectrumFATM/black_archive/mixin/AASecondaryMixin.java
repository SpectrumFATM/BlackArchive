package net.SpectrumFATM.black_archive.mixin;

import dev.jeryn.audreys_additions.console.theme.BrachackiConsoleTheme;
import dev.jeryn.audreys_additions.console.theme.KeltConsoleTheme;
import dev.jeryn.audreys_additions.console.theme.NewberyConsoleTheme;
import net.SpectrumFATM.black_archive.tardis.control.ModControls;
import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.registry.TRControlRegistry;

@Mixin(NewberyConsoleTheme.class)
public abstract class AASecondaryMixin {

    @Inject(method = "getControlSpecification()[Lwhocraft/tardis_refined/common/tardis/control/ControlSpecification;", at = @At("HEAD"), cancellable = true, remap = false)
    public void getControlSpecification(CallbackInfoReturnable<ControlSpecification[]> cir) {
        ControlSpecification[] s = new ControlSpecification[]{new ControlSpecification((Control)TRControlRegistry.THROTTLE.get(), new Vector3f(0.33F, 0.47F, -0.33F), EntityDimensions.scalable(0.06F, 0.06F)), new ControlSpecification((Control)TRControlRegistry.X.get(), new Vector3f(0.37F, 0.57F, -0.05F), EntityDimensions.scalable(0.03F, 0.03F)), new ControlSpecification((Control)TRControlRegistry.Y.get(), new Vector3f(0.34F, 0.57F, -0.11F), EntityDimensions.scalable(0.03F, 0.03F)), new ControlSpecification((Control)TRControlRegistry.Z.get(), new Vector3f(0.31F, 0.57F, -0.16F), EntityDimensions.scalable(0.03F, 0.03F)), new ControlSpecification((Control)TRControlRegistry.INCREMENT.get(), new Vector3f(0.28F, 0.52F, 0.34F), EntityDimensions.scalable(0.03F, 0.03F)),  new ControlSpecification((Control)ModControls.TELEPATHIC.get(), new Vector3f(0.325F, 0.5875F, 0.0625F), EntityDimensions.scalable(0.03F, 0.03F)), new ControlSpecification((Control)TRControlRegistry.ROTATE.get(), new Vector3f(0.28F, 0.57F, -0.22F), EntityDimensions.scalable(0.03F, 0.03F)), new ControlSpecification((Control)TRControlRegistry.RANDOM.get(), new Vector3f(-0.28F, 0.5F, -0.23F), EntityDimensions.scalable(0.09F, 0.09F)), new ControlSpecification((Control)TRControlRegistry.DOOR_TOGGLE.get(), new Vector3f(-0.33F, 0.55F, -0.05F), EntityDimensions.scalable(0.03F, 0.03F)), new ControlSpecification((Control)TRControlRegistry.MONITOR.get(), new Vector3f(-0.34F, 0.45F, 0.21F), EntityDimensions.scalable(0.09F, 0.09F)), new ControlSpecification((Control)TRControlRegistry.DIMENSION.get(), new Vector3f(-0.4F, 0.5F, 0.11F), EntityDimensions.scalable(0.03F, 0.03F)), new ControlSpecification((Control)TRControlRegistry.FAST_RETURN.get(), new Vector3f(-0.34F, 0.53F, 0.1F), EntityDimensions.scalable(0.06F, 0.06F)), new ControlSpecification((Control)TRControlRegistry.READOUT.get(), new Vector3f(-0.07F, 0.47F, 0.39F), EntityDimensions.scalable(0.06F, 0.06F)), new ControlSpecification((Control)TRControlRegistry.HANDBRAKE.get(), new Vector3f(-0.25F, 0.5F, 0.38F), EntityDimensions.scalable(0.03F, 0.03F)), new ControlSpecification((Control)TRControlRegistry.FUEL.get(), new Vector3f(-0.07F, 0.47F, -0.34F), EntityDimensions.scalable(0.06F, 0.06F))};
        cir.setReturnValue(s);
    }
}