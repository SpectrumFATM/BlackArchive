package net.SpectrumFATM.black_archive.tardis.upgrades;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import whocraft.tardis_refined.common.capability.tardis.upgrades.Upgrade;
import whocraft.tardis_refined.common.util.RegistryHelper;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.TRUpgrades;

import java.util.Objects;

public class ModUpgrades {
    public static DeferredRegistry<Upgrade> UPGRADE_DEFERRED_REGISTRY = DeferredRegistry.create(BlackArchive.MOD_ID, TRUpgrades.UPGRADE_REGISTRY_KEY);
    
    public static RegistrySupplier<Upgrade> TELEPATHIC_UPGRADE;
    public static RegistrySupplier<Upgrade> REMOTE_UPGRADE;
    public static RegistrySupplier<Upgrade> TEMPORAL_ORBIT_UPGRADE;

    static {
        REMOTE_UPGRADE = UPGRADE_DEFERRED_REGISTRY.register("remote_upgrade", () -> {
            Item i = ModItems.REMOTE.get();
            Objects.requireNonNull(i);
            return (new Upgrade(i::getDefaultStack, TRUpgrades.LANDING_PAD, RegistryHelper.makeKey("remote_upgrade"), Upgrade.UpgradeType.SUB_UPGRADE)).setSkillPointsRequired(15).setPosition(8.0, 3.0);
        });
        TELEPATHIC_UPGRADE = UPGRADE_DEFERRED_REGISTRY.register("telepathic_upgrade", () -> {
            Item i = Items.PLAYER_HEAD;
            Objects.requireNonNull(i);
            return (new Upgrade(i::getDefaultStack, TRUpgrades.DIMENSION_TRAVEL, RegistryHelper.makeKey("telepathic_upgrade"), Upgrade.UpgradeType.SUB_UPGRADE)).setSkillPointsRequired(25).setPosition(7.0, 6.0);
        });
        TEMPORAL_ORBIT_UPGRADE = UPGRADE_DEFERRED_REGISTRY.register("temporal_orbit_upgrade", () -> {
            Item i = Items.CLOCK;
            Objects.requireNonNull(i);
            return (new Upgrade(i::getDefaultStack, TRUpgrades.DIMENSION_TRAVEL, RegistryHelper.makeKey("temporal_orbit_upgrade"), Upgrade.UpgradeType.SUB_UPGRADE)).setSkillPointsRequired(25).setPosition(6.0, 5.0);
        });
    }

    public static void register() {
        BlackArchive.LOGGER.info("Registering tardis upgrades for black_archive");
    }
}