package net.SpectrumFATM.black_archive.mixin;

import dev.jeryn.audreys_additions.console.theme.ToyotaConsoleTheme;
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

@Mixin(ToyotaConsoleTheme.class)
public abstract class AAToyotaMixin {

    @Inject(method = "getControlSpecification()[Lwhocraft/tardis_refined/common/tardis/control/ControlSpecification;", at = @At("HEAD"), cancellable = true, remap = false)
    public void getControlSpecification(CallbackInfoReturnable<ControlSpecification[]> cir) {
        ControlSpecification[] s = new ControlSpecification[]{new ControlSpecification((Control)TRControlRegistry.THROTTLE.get(), new Vector3f(0.24F, 0.69F, 0.93F), EntityDimensions.scalable(0.25F, 0.25F)), new ControlSpecification((Control)TRControlRegistry.HANDBRAKE.get(), new Vector3f(-0.39F, 0.56F, 0.99F), EntityDimensions.scalable(0.19F, 0.19F)), new ControlSpecification((Control)TRControlRegistry.DOOR_TOGGLE.get(), new Vector3f(-0.34F, 0.72F, -1.04F), EntityDimensions.scalable(0.13F, 0.13F)), new ControlSpecification((Control)TRControlRegistry.MONITOR.get(), new Vector3f(0.68F, 0.63F, -0.51F), EntityDimensions.scalable(0.25F, 0.25F)), new ControlSpecification((Control)TRControlRegistry.ROTATE.get(), new Vector3f(-0.89F, 0.56F, -0.57F), EntityDimensions.scalable(0.38F, 0.38F)), new ControlSpecification((Control)TRControlRegistry.DIMENSION.get(), new Vector3f(-0.14F, 0.69F, -0.92F), EntityDimensions.scalable(0.19F, 0.19F)), new ControlSpecification((Control)TRControlRegistry.X.get(), new Vector3f(-0.12F, 0.75F, 0.68F), EntityDimensions.scalable(0.06F, 0.06F)), new ControlSpecification((Control)TRControlRegistry.Y.get(), new Vector3f(-0.03F, 0.75F, 0.71F), EntityDimensions.scalable(0.06F, 0.06F)), new ControlSpecification((Control)TRControlRegistry.Z.get(), new Vector3f(0.07F, 0.75F, 0.68F), EntityDimensions.scalable(0.06F, 0.06F)), new ControlSpecification((Control)TRControlRegistry.INCREMENT.get(), new Vector3f(-0.76F, 0.66F, 0.28F), EntityDimensions.scalable(0.19F, 0.19F)), new ControlSpecification((Control)TRControlRegistry.RANDOM.get(), new Vector3f(0.64F, 0.75F, 0.21F), EntityDimensions.scalable(0.13F, 0.13F)), new ControlSpecification((Control)TRControlRegistry.RANDOM.get(), new Vector3f(0.77F, 0.72F, 0.21F), EntityDimensions.scalable(0.13F, 0.13F)), new ControlSpecification((Control)TRControlRegistry.RANDOM.get(), new Vector3f(0.61F, 0.72F, 0.55F), EntityDimensions.scalable(0.13F, 0.13F)), new ControlSpecification((Control)ModControls.TELEPATHIC.get(), new Vector3f(0.77F, 0.63F, 0.42F), EntityDimensions.scalable(0.19F, 0.19F)), new ControlSpecification((Control)TRControlRegistry.RANDOM.get(), new Vector3f(1.08F, 0.56F, 0.27F), EntityDimensions.scalable(0.13F, 0.13F)), new ControlSpecification((Control)TRControlRegistry.RANDOM.get(), new Vector3f(0.77F, 0.56F, 0.77F), EntityDimensions.scalable(0.13F, 0.13F)), new ControlSpecification((Control)TRControlRegistry.READOUT.get(), new Vector3f(0.71F, 1.22F, -0.47F), EntityDimensions.scalable(0.25F, 0.25F)), new ControlSpecification((Control)TRControlRegistry.READOUT.get(), new Vector3f(-0.73F, 1.22F, 0.58F), EntityDimensions.scalable(0.25F, 0.25F)), new ControlSpecification((Control)TRControlRegistry.FUEL.get(), new Vector3f(-0.64F, 0.78F, -0.14F), EntityDimensions.scalable(0.06F, 0.06F)), new ControlSpecification((Control)TRControlRegistry.FAST_RETURN.get(), new Vector3f(0.16F, 0.75F, 0.68F), EntityDimensions.scalable(0.06F, 0.06F)), new ControlSpecification((Control)TRControlRegistry.DIMENSION.get(), new Vector3f(0.05F, 0.69F, -0.92F), EntityDimensions.scalable(0.19F, 0.19F)), new ControlSpecification((Control)TRControlRegistry.RANDOM.get(), new Vector3f(0.52F, 0.75F, 0.46F), EntityDimensions.scalable(0.13F, 0.13F))};
        cir.setReturnValue(s);
    }
}