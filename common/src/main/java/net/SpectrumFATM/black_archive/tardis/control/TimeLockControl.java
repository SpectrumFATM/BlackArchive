package net.SpectrumFATM.black_archive.tardis.control;

import net.SpectrumFATM.black_archive.util.TardisWeaponManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public class TimeLockControl extends Control {

    TardisWeaponManager weaponManager = new TardisWeaponManager();

    protected TimeLockControl(ResourceLocation id, String langId, boolean isCriticalForTardisOperation) {
        super(id, langId, isCriticalForTardisOperation);
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator tardisLevelOperator, ConsoleTheme consoleTheme, ControlEntity controlEntity, Player playerEntity) {
        weaponManager.releaseEntities();
        playerEntity.displayClientMessage(
        Component.literal("Time Lock disengaged!"),
        true
        );
        return true;
    }

    @Override
    public boolean onRightClick(TardisLevelOperator tardisLevelOperator, ConsoleTheme consoleTheme, ControlEntity controlEntity, Player playerEntity) {
        weaponManager.engageTimeLock(tardisLevelOperator, playerEntity);
        playerEntity.displayClientMessage(
                Component.literal("Time Lock engaged!"),
                true
        );
        return true;
    }
}