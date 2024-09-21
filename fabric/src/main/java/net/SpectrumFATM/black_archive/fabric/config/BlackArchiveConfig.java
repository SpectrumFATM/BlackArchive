package net.SpectrumFATM.black_archive.fabric.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "black_archive")
public class BlackArchiveConfig implements ConfigData {
    public boolean shouldDalekGunStickDestroyDoors = true;
    public int gravityGeneratorRange = 8;
    public int oxygenFieldRange = 8;
    public int vortexManipulatorCooldown = 10;
}