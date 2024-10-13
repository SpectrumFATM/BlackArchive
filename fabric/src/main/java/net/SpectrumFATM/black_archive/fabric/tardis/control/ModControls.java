package net.SpectrumFATM.black_archive.fabric.tardis.control;

import net.minecraft.util.Identifier;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.TRControlRegistry;

public class ModControls {
    public static final DeferredRegistry<Control> CONTROL_DEFERRED_REGISTRY;
    public static final RegistrySupplier<Control> TELEPATHIC;

    public ModControls() {
    }

    public static Control get(Identifier id) {
        return CONTROL_DEFERRED_REGISTRY.get(id);
    }

    public static Identifier getKey(Control control) {
        return CONTROL_DEFERRED_REGISTRY.getKey(control);
    }

    private static RegistrySupplier<Control> register(Control control) {
        return register(control, control.getId().getPath());
    }

    private static RegistrySupplier<Control> register(Control control, String id) {
        return CONTROL_DEFERRED_REGISTRY.register(id, () -> control);
    }

    public static void register() {
        // This method can be used to trigger the static block and ensure controls are registered
    }

    static {
        CONTROL_DEFERRED_REGISTRY = TRControlRegistry.CONTROL_DEFERRED_REGISTRY;
        TELEPATHIC = register(new TelepathicControl(new Identifier("black_archive", "telepathic"), "control.black_archive.telepathic", false));
    }
}