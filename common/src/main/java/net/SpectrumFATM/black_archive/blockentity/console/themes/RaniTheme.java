package net.SpectrumFATM.black_archive.blockentity.console.themes;

import net.SpectrumFATM.black_archive.tardis.control.ModControls;
import net.minecraft.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.themes.console.ConsoleThemeDetails;
import whocraft.tardis_refined.registry.TRControlRegistry;

public class RaniTheme extends ConsoleThemeDetails {
    public RaniTheme() {
    }

    //TODO: Finish control positions.

    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[]{new ControlSpecification(TRControlRegistry.THROTTLE, new Vector3f(0.16875F, 0.625F, -0.80625F), EntityDimensions.changing(0.125F, 0.125F)),
                new ControlSpecification(TRControlRegistry.HANDBRAKE, new Vector3f(-0.16875F, 0.625F, -0.80625F), EntityDimensions.changing(0.125F, 0.125F)),
                new ControlSpecification(TRControlRegistry.FUEL, new Vector3f(0.0F, 0.6875F, -0.61875F), EntityDimensions.changing(0.125F, 0.125F)),
                new ControlSpecification(TRControlRegistry.MONITOR, new Vector3f(0.625F, 0.65625F, -0.34375F), EntityDimensions.changing(0.125F, 0.125F)),
                new ControlSpecification(TRControlRegistry.RANDOM, new Vector3f(0.765625F, 0.625F, 0.265625F), EntityDimensions.changing(0.125F, 0.125F)),
                new ControlSpecification(TRControlRegistry.DIMENSION, new Vector3f(0.6125F, 0.625F, 0.55F), EntityDimensions.changing(0.125F, 0.125F)),
                new ControlSpecification(TRControlRegistry.FAST_RETURN, new Vector3f(0.5375F, 0.678F, 0.3125F), EntityDimensions.changing(0.125F, 0.125F)),
                new ControlSpecification(TRControlRegistry.READOUT, new Vector3f(0.0F, 0.65625F, 0.75F), EntityDimensions.changing(0.125F, 0.125F)),
                new ControlSpecification(TRControlRegistry.X, new Vector3f(-0.78125F, 0.625F, 0.2625F), EntityDimensions.changing(0.125F, 0.125F)),
                new ControlSpecification(TRControlRegistry.Y, new Vector3f(-0.5375F, 0.6875F, 0.3F), EntityDimensions.changing(0.125F, 0.125F)),
                new ControlSpecification(TRControlRegistry.Z, new Vector3f(-0.6125F, 0.625F, 0.5375F), EntityDimensions.changing(0.125F, 0.125F)),
                new ControlSpecification(TRControlRegistry.INCREMENT, new Vector3f(-0.69988125F, 0.625F, 0.39375F), EntityDimensions.changing(0.125F, 0.125F)),
                new ControlSpecification(ModControls.TELEPATHIC, new Vector3f(-0.625F, 0.65625F, -0.34375F), EntityDimensions.changing(0.125F, 0.125F)),
                new ControlSpecification(TRControlRegistry.ROTATE, new Vector3f(0.69988125F, 0.625F, 0.39375F), EntityDimensions.changing(0.125F, 0.125F)),
                new ControlSpecification(TRControlRegistry.DOOR_TOGGLE, new Vector3f(0.0F, 0.625F, -0.80625F), EntityDimensions.changing(0.125F, 0.125F))
        };
    }
}
