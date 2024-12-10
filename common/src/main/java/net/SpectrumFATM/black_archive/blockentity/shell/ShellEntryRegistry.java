package net.SpectrumFATM.black_archive.blockentity.shell;

import net.SpectrumFATM.black_archive.blockentity.ModModels;
import net.SpectrumFATM.black_archive.blockentity.ModShells;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModelCollection;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

public class ShellEntryRegistry {

    public static void init(){
        ShellModelCollection.registerShellEntry((ShellTheme) ModShells.DORIC.get(), ModModels.PILLAR_EXT_MODEL, ModModels.PILLAR_INT_MODEL);
    }
}