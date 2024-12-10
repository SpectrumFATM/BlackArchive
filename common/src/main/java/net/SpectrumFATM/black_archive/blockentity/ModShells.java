package net.SpectrumFATM.black_archive.blockentity;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.entity.client.ModModelLayers;
import net.minecraft.util.Identifier;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModelCollection;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ModShells {
    public static final DeferredRegistry<ShellTheme> SHELL_THEMES = DeferredRegistry.create(BlackArchive.MOD_ID, ShellTheme.SHELL_THEME_REGISTRY_KEY);

    public static final RegistrySupplier<ShellTheme> DORIC_COLUMN = registerShellTheme("pillar");

    private static RegistrySupplier<ShellTheme> registerShellTheme(String id) {
        return SHELL_THEMES.register(id, () -> new ShellTheme(new Identifier(BlackArchive.MOD_ID, id)));
    }

    public static void registerTheme() {
        ShellModelCollection.registerShellEntry(DORIC_COLUMN.get(), ModModelLayers.PILLAR_EXT, ModModelLayers.PILLAR_DOOR);
    }
}
