package net.SpectrumFATM.black_archive.fabric;

import net.fabricmc.api.ModInitializer;


public class BlackArchive implements ModInitializer {

	public static final String MOD_ID = "black_archive";

	@Override
	public void onInitialize() {
		net.SpectrumFATM.BlackArchive.init();
	}
}