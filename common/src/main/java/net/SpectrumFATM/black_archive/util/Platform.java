package net.SpectrumFATM.black_archive.util;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class Platform {


    @ExpectPlatform
    public static boolean isModLoaded(String modid) {
        return false;
    }
}
