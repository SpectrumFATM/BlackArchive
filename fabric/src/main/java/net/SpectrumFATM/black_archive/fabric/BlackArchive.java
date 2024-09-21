package net.SpectrumFATM.black_archive.fabric;

import net.SpectrumFATM.black_archive.fabric.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.fabric.entity.ModEntities;
import net.SpectrumFATM.black_archive.fabric.entity.custom.DalekEntity;
import net.SpectrumFATM.black_archive.fabric.network.NetworkPackets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;

import net.SpectrumFATM.black_archive.fabric.item.ModItemGroups;
import net.SpectrumFATM.black_archive.fabric.item.ModItems;
import net.SpectrumFATM.black_archive.fabric.sound.ModSounds;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlackArchive implements ModInitializer {

	public static final String MOD_ID = "black_archive";
	public static final Logger LOGGER = LoggerFactory.getLogger("black_archive");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		// Initialize the configuration
		BlackArchiveConfig.register();

		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
		ModItemGroups.registerToVanillaItemGroups();
		NetworkPackets.registerPackets();

		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			NetworkPackets.registerClientSidePackets();
		}

		ModSounds.registerSounds();
		ModEntities.registerModEntities();

		FabricDefaultAttributeRegistry.register(ModEntities.DALEK, DalekEntity.createDalekAttributes());
	}
}