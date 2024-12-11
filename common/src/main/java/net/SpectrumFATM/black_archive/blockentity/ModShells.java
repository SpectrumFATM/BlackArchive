package net.SpectrumFATM.black_archive.blockentity;

import net.SpectrumFATM.BlackArchive;
import net.minecraft.util.Identifier;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ModShells {
    public static final DeferredRegistry<ShellTheme> SHELL_THEMES = DeferredRegistry.create(BlackArchive.MOD_ID, ShellTheme.SHELL_THEME_REGISTRY_KEY);

    public static final RegistrySupplier<ShellTheme> DORIC = registerShellTheme("pillar");
    public static final RegistrySupplier<ShellTheme> RANI = registerShellTheme("rani");

    private static RegistrySupplier<ShellTheme> registerShellTheme(String id) {
        return SHELL_THEMES.register(id, () -> new ShellTheme(new Identifier(BlackArchive.MOD_ID, id)));
    }
}
