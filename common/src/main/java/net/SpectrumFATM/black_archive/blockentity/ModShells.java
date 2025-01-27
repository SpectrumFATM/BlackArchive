package net.SpectrumFATM.black_archive.blockentity;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.util.Platform;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ModShells {
    public static final DeferredRegistry<ShellTheme> SHELL_THEMES = DeferredRegistry.create(BlackArchive.MOD_ID, ShellTheme.SHELL_THEME_REGISTRY_KEY);

    public static final RegistrySupplier<ShellTheme> DORIC = registerShellTheme("pillar", true);
    public static final RegistrySupplier<ShellTheme> RANI = registerShellTheme("rani", true);
    public static final RegistrySupplier<ShellTheme> SIDRAT = registerShellTheme("sidrat", !Platform.isModLoaded("audreys_additions"));

    private static RegistrySupplier<ShellTheme> registerShellTheme(String id, boolean shoiuldRegister) {
        if (shoiuldRegister) {
            return SHELL_THEMES.register(id, () -> new ShellTheme(new ResourceLocation(BlackArchive.MOD_ID, id)));
        }
        return null;
    }
}
