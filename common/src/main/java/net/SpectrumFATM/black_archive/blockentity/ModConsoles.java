package net.SpectrumFATM.black_archive.blockentity;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.blockentity.console.themes.RaniTheme;
import net.minecraft.util.Identifier;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.tardis.themes.console.ConsoleThemeDetails;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ModConsoles {
    public static final DeferredRegistry<ConsoleTheme> CONSOLE_THEMES = DeferredRegistry.create(BlackArchive.MOD_ID, ConsoleTheme.CONSOLE_THEME_REGISTRY_KEY);

    public static final RegistrySupplier<ConsoleTheme> RANI = registerConsoleTheme("rani", new RaniTheme());

    private static RegistrySupplier<ConsoleTheme> registerConsoleTheme(String id, ConsoleThemeDetails themeDetails) {
        return CONSOLE_THEMES.register(id, () -> {
            return new ConsoleTheme(new Identifier(BlackArchive.MOD_ID, id), themeDetails);
        });
    }
}
