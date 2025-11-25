package net.SpectrumFATM.black_archive.fabric;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.fabric.entity.ModEntitiesFabric;
import net.fabricmc.api.ModInitializer;
import net.minecraftforge.fml.config.ModConfig;


public class FabricBlackArchive implements ModInitializer {

	public static final String MOD_ID = "black_archive";

	@Override
	public void onInitialize() {
		BlackArchive.init();
		ModEntitiesFabric.createEntityAttributes();
		registerConfig();
	}

	public static void registerConfig() {
		ForgeConfigRegistry.INSTANCE.register("black_archive", ModConfig.Type.COMMON, BlackArchiveConfig.COMMON_SPEC);
		ForgeConfigRegistry.INSTANCE.register("black_archive", ModConfig.Type.CLIENT, BlackArchiveConfig.CLIENT_SPEC);
	}
}