package net.SpectrumFATM.black_archive.tardis.control;

import net.SpectrumFATM.BlackArchive;
import net.minecraft.util.Identifier;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.TRControlRegistry;

public class ModControls {
    public static final DeferredRegistry<Control> CONTROL_DEFERRED_REGISTRY = DeferredRegistry.create(BlackArchive.MOD_ID, TRControlRegistry.CONTROL_REGISTRY_KEY);

    public static final RegistrySupplier<Control> TELEPATHIC = CONTROL_DEFERRED_REGISTRY.register("telepathic", () -> new TelepathicControl(new Identifier("black_archive", "telepathic"), "control.black_archive.telepathic", false));
}