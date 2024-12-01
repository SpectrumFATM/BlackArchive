package net.SpectrumFATM.forge;

import net.minecraftforge.fml.common.Mod;

import net.SpectrumFATM.BlackArchive;

@Mod(BlackArchive.MOD_ID)
public final class BlackArchiveForge {
    public BlackArchiveForge() {
        // Submit our event bus to let Architectury API register our content on the right time.

        // Run our common setup.
        BlackArchive.init();
    }
}
