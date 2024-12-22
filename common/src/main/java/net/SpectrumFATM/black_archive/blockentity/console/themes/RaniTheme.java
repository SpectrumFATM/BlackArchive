package net.SpectrumFATM.black_archive.blockentity.console.themes;

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
        return new ControlSpecification[]{new ControlSpecification(TRControlRegistry.THROTTLE, new Vector3f(0.16875F, 0.625F, -0.80625F), EntityDimensions.changing(0.125F, 0.125F)), new ControlSpecification(TRControlRegistry.HANDBRAKE, new Vector3f(-0.16875F, 0.625F, -0.80625F), EntityDimensions.changing(0.125F, 0.125F)), new ControlSpecification(TRControlRegistry.FUEL, new Vector3f(0.0F, 0.75F, -0.61875F), EntityDimensions.changing(0.125F, 0.125F))};
    }
}
