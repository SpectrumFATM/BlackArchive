package net.SpectrumFATM.black_archive.util.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class PlatformImpl {
    public static boolean isModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }
}
