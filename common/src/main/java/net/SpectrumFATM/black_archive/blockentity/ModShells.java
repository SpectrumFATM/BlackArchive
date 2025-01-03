package net.SpectrumFATM.black_archive.blockentity;

import net.SpectrumFATM.BlackArchive;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ModShells {
    public static final DeferredRegistry<ShellTheme> SHELL_THEMES = DeferredRegistry.create(BlackArchive.MOD_ID, ShellTheme.SHELL_THEME_REGISTRY_KEY);

    public static final RegistrySupplier<ShellTheme> DORIC = registerShellTheme("pillar");
    public static final RegistrySupplier<ShellTheme> RANI = registerShellTheme("rani");
    public static final RegistrySupplier<ShellTheme> SIDRAT = registerShellTheme("sidrat");

    private static RegistrySupplier<ShellTheme> registerShellTheme(String id) {
        return SHELL_THEMES.register(id, () -> new ShellTheme(new ResourceLocation(BlackArchive.MOD_ID, id)));
    }
}
