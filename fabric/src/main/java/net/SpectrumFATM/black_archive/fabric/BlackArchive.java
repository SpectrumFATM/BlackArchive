package net.SpectrumFATM.black_archive.fabric;

import net.SpectrumFATM.black_archive.fabric.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.fabric.effects.DalekNanocloudEffect;
import net.SpectrumFATM.black_archive.fabric.entity.ModEntities;
import net.SpectrumFATM.black_archive.fabric.entity.custom.CybermanEntity;
import net.SpectrumFATM.black_archive.fabric.entity.custom.CybermatEntity;
import net.SpectrumFATM.black_archive.fabric.entity.custom.DalekEntity;
import net.SpectrumFATM.black_archive.fabric.entity.custom.DalekPuppetEntity;
import net.SpectrumFATM.black_archive.fabric.network.NetworkPackets;
import net.SpectrumFATM.black_archive.fabric.tardis.control.ModControls;
import net.SpectrumFATM.black_archive.fabric.tardis.upgrades.ModUpgrades;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;

import net.SpectrumFATM.black_archive.fabric.item.ModItemGroups;
import net.SpectrumFATM.black_archive.fabric.item.ModItems;
import net.SpectrumFATM.black_archive.fabric.sound.ModSounds;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlackArchive implements ModInitializer {

	public static final String MOD_ID = "black_archive";
	public static final Logger LOGGER = LoggerFactory.getLogger("black_archive");

	public static final StatusEffect DALEK_NANOCLOUD = new DalekNanocloudEffect();
	public static final StatusEffect CYBER_CONVERSION = new DalekNanocloudEffect();

	@Override
	public void onInitialize() {
		// Initialize the configuration
		BlackArchiveConfig.register();

		// Register the effect with the game
		Registry.register(Registries.STATUS_EFFECT, new Identifier(BlackArchive.MOD_ID, "dalek_nanocloud"), DALEK_NANOCLOUD);
		Registry.register(Registries.STATUS_EFFECT, new Identifier(BlackArchive.MOD_ID, "cyber_conversion"), CYBER_CONVERSION);

		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
		ModItemGroups.registerToVanillaItemGroups();
		NetworkPackets.registerPackets();

		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			NetworkPackets.registerClientSidePackets();
		}

		ModSounds.registerSounds();
		ModEntities.registerModEntities();
		ModUpgrades.register();
		ModControls.register();

		FabricDefaultAttributeRegistry.register(ModEntities.DALEK, DalekEntity.createDalekAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.DALEK_PUPPET, DalekPuppetEntity.createDalekSlaveAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CYBERMAN, CybermanEntity.createCyberAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CYBERMAT, CybermatEntity.createCyberAttributes());
	}
}