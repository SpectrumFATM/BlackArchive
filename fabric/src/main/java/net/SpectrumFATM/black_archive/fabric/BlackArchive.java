package net.SpectrumFATM.black_archive.fabric;

import net.SpectrumFATM.black_archive.fabric.network.AllowedDimensionsResponsePacket;
import net.SpectrumFATM.black_archive.fabric.network.NetworkPackets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;

import net.SpectrumFATM.black_archive.fabric.item.ModItemGroups;
import net.SpectrumFATM.black_archive.fabric.item.ModItems;
import net.SpectrumFATM.black_archive.fabric.sound.ModSounds;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import whocraft.tardis_refined.api.event.EventResult;
import whocraft.tardis_refined.api.event.TardisCommonEvents;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;

public class BlackArchive implements ModInitializer {

	public static final String MOD_ID = "black_archive";
    public static final Logger LOGGER = LoggerFactory.getLogger("black_archive");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
		NetworkPackets.registerPackets();

		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			NetworkPackets.registerClientSidePackets();
		}

		ModSounds.registerSounds();
	}
}