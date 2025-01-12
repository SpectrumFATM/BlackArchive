package net.SpectrumFATM.black_archive.util.forge;

import net.minecraftforge.fml.ModList;

public class PlatformImpl {
    public static boolean isModLoaded(String modid) {
        ModList modList = ModList.get();
        return modList != null && modList.isLoaded(modid);
    }
}
