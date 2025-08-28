package net.SpectrumFATM.black_archive.tardis.control;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.tardis.upgrades.ModUpgrades;
import net.SpectrumFATM.black_archive.util.TimeLockManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public class TimeLockControl extends Control {

    private final TimeLockManager weaponManager = new TimeLockManager();
    private boolean isTimeLockEngaged = false;

    protected TimeLockControl(ResourceLocation id, String langId, boolean isCriticalForTardisOperation) {
        super(id, langId, isCriticalForTardisOperation);
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator tardisLevelOperator, ConsoleTheme consoleTheme, ControlEntity controlEntity, Player playerEntity) {
        return false;
    }

    @Override
    public boolean onRightClick(TardisLevelOperator tardisLevelOperator, ConsoleTheme consoleTheme, ControlEntity controlEntity, Player playerEntity) {
        if (tardisLevelOperator.getUpgradeHandler().isUpgradeUnlocked(ModUpgrades.TIME_LOCK_UPGRADE.get())) {
            if (isTimeLockEngaged) {
                weaponManager.releaseEntities();
                BlackArchive.LOGGER.info("Time Lock disengaged.");
                playerEntity.displayClientMessage(
                        Component.literal("Time Lock disengaged!"),
                        true
                );
                controlEntity.playSound(SoundEvents.BEACON_DEACTIVATE, 1.0F, 1.0F);
            } else {
                weaponManager.engageTimeLock(tardisLevelOperator);
                BlackArchive.LOGGER.info("Time Lock engaged.");
                playerEntity.displayClientMessage(
                        Component.literal("Time Lock engaged!"),
                        true
                );
                controlEntity.playSound(SoundEvents.BEACON_ACTIVATE, 1.0F, 1.0F);
            }
            isTimeLockEngaged = !isTimeLockEngaged;
            return true;
        }
        return false;
    }
}