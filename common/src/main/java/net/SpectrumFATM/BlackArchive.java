package net.SpectrumFATM;

import net.SpectrumFATM.black_archive.block.ModBlocks;
import net.SpectrumFATM.black_archive.blockentity.ModShells;
import net.SpectrumFATM.black_archive.effects.ModEffects;
import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
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
        ModEntities.ENTITY_TYPES.registerToModBus();
        ModItems.TABS.registerToModBus();
        ModBlocks.BLOCKS.registerToModBus();
        ModSounds.SOUNDS.registerToModBus();
        ModEffects.EFFECTS.registerToModBus();
        ModControls.CONTROL_DEFERRED_REGISTRY.registerToModBus();
        ModItems.ITEMS.registerToModBus();
        ModUpgrades.UPGRADE_DEFERRED_REGISTRY.registerToModBus();
        BlackArchiveNetworkHandler.registerPackets();
        ModShells.SHELL_THEMES.registerToModBus();
    }
}
