package net.SpectrumFATM;

import net.SpectrumFATM.black_archive.block.ModBlocks;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.effects.ModEffects;
import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.SpectrumFATM.black_archive.network.NetworkHandler;
import net.SpectrumFATM.black_archive.sound.ModSounds;
import net.SpectrumFATM.black_archive.tardis.control.ModControls;
import net.SpectrumFATM.black_archive.tardis.upgrades.ModUpgrades;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BlackArchive {
    public static final String MOD_ID = "black_archive";
    public static final Logger LOGGER = LoggerFactory.getLogger("black_archive");

    public static void init() {
        // Write common init code here.
        ModBlocks.BLOCKS.register();
        ModEntities.ENTITY_TYPES.register();
        ModEntities.createEntityAttributes();
        ModItems.ITEMS.register();
        ModSounds.SOUNDS.register();
        ModEffects.EFFECTS.register();
        ModControls.CONTROL_DEFERRED_REGISTRY.registerToModBus();
        ModUpgrades.register();
        NetworkHandler.registerPackets();
    }
}
