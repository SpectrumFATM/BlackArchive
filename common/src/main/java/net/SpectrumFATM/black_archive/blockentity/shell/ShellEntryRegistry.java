package net.SpectrumFATM.black_archive.blockentity.shell;

import net.SpectrumFATM.black_archive.blockentity.ModModels;
import net.SpectrumFATM.black_archive.blockentity.ModShells;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModelCollection;

public class ShellEntryRegistry {

    public static void init(){
        ShellModelCollection.registerShellEntry(ModShells.DORIC.get(), ModModels.PILLAR_SHELL_MODEL, ModModels.PILLAR_DOOR_MODEL);
        ShellModelCollection.registerShellEntry(ModShells.RANI.get(), ModModels.RANI_SHELL_MODEL, ModModels.RANI_DOOR_MODEL);
        ShellModelCollection.registerShellEntry(ModShells.SIDRAT.get(), ModModels.SIDRAT_SHELL_MODEL, ModModels.SIDRAT_DOOR_MODEL);
    }
}