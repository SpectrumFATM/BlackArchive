package net.SpectrumFATM.black_archive.blockentity.shell;

import net.SpectrumFATM.black_archive.blockentity.ModModels;
import net.SpectrumFATM.black_archive.blockentity.ModShells;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModelCollection;

public class ShellEntryRegistry {

    public static void init(){
        ShellModelCollection.registerShellEntry(ModShells.DORIC.get(), ModModels.PILLAR_EXT_MODEL, ModModels.PILLAR_INT_MODEL);
        ShellModelCollection.registerShellEntry(ModShells.RANI.get(), ModModels.RANI_EXT_MODEL, ModModels.RANI_INT_MODEL);
        ShellModelCollection.registerShellEntry(ModShells.SIDRAT.get(), ModModels.SIDRAT_EXT_MODEL, ModModels.SIDRAT_INT_MODEL);
    }
}