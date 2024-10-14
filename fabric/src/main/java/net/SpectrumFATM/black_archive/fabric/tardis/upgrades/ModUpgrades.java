package net.SpectrumFATM.black_archive.fabric.tardis.upgrades;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import whocraft.tardis_refined.common.capability.upgrades.Upgrade;
import whocraft.tardis_refined.common.util.RegistryHelper;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.TRUpgrades;

import java.util.Objects;

public class ModUpgrades {
    public static RegistrySupplier<Upgrade> TELEPATHIC_UPGRADE;

    static {
        TELEPATHIC_UPGRADE= TRUpgrades.UPGRADE_DEFERRED_REGISTRY.register("telepathic_upgrade", () -> {
            Item i = Items.PLAYER_HEAD;
            Objects.requireNonNull(i);
            return (new Upgrade(i::getDefaultStack, TRUpgrades.DIMENSION_TRAVEL, RegistryHelper.makeKey("telepathic_upgrade"), Upgrade.UpgradeType.SUB_UPGRADE)).setSkillPointsRequired(25).setPosition(7.0, 6.0);
        });
    }

    public static void register() {
        BlackArchive.LOGGER.info("Registering tardis upgrades for black_archive");
    }
}