package net.SpectrumFATM.black_archive.fabric.config;

import me.shedaniel.autoconfig.AutoConfig;

public class ConfigHelper {
    public static BlackArchiveConfig getConfig() {
        return AutoConfig.getConfigHolder(BlackArchiveConfig.class).getConfig();
    }
}