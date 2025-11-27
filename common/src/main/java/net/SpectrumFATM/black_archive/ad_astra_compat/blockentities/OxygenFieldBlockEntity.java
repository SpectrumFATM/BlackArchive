package net.SpectrumFATM.black_archive.ad_astra_compat.blockentities;

import earth.terrarium.adastra.api.systems.GravityApi;
import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.ad_astra_compat.blocks.OxygenField;
import net.SpectrumFATM.black_archive.ad_astra_compat.util.AATools;
import net.SpectrumFATM.black_archive.blockentity.ModBlockEntities;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collection;

public class OxygenFieldBlockEntity extends BlockEntity {

    private final int radius;
    private Collection<BlockPos> cachedPositions;
    private BlockPos cachedOrigin;
    private int tickCooldown = 0; // ticks until next refresh
    private boolean lastPowered = false;

    public OxygenFieldBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.OXYGEN_SUPPORT_BE.get(), pos, state);
        this.radius = BlackArchiveConfig.COMMON.oxygenFieldRange.get();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, OxygenFieldBlockEntity be) {
        if (level.isClientSide) return;

        boolean powered = state.getValue(OxygenField.POWERED);

        if (powered) {
            if (!be.lastPowered) {
                // just turned on: compute area and apply immediately
                be.ensureCache(pos);
                be.applyEffects(level);
                be.tickCooldown = 20; // refresh every 20 ticks
                be.lastPowered = true;
            } else {
                // already on: refresh occasionally to maintain state without per-tick allocations
                if (be.tickCooldown-- <= 0) {
                    be.ensureCache(pos);
                    be.applyEffects(level);
                    be.tickCooldown = 20;
                }
            }
        } else {
            if (be.lastPowered) {
                // turned off: remove effects and clear cache
                be.ensureCache(pos);
                be.removeEffects(level);
                be.cachedPositions = null;
                be.cachedOrigin = null;
                be.lastPowered = false;
            }
        }
    }

    private void ensureCache(BlockPos origin) {
        if (cachedPositions == null || cachedOrigin == null || !cachedOrigin.equals(origin)) {
            cachedPositions = AATools.getPositionsInRadius(origin, radius, radius);
            cachedOrigin = origin;
        }
    }

    private void applyEffects(Level level) {
        if (cachedPositions == null || level == null) return;
        try {
            OxygenApi.API.setOxygen(level, cachedPositions, true);
            TemperatureApi.API.setTemperature(level, cachedPositions, (short) 22);
        } catch (Exception e) {
            BlackArchive.LOGGER.error("Error applying Life Support effects: " + e.getMessage());
        }
    }

    private void removeEffects(Level level) {
        if (cachedPositions == null || level == null) return;
        try {
            OxygenApi.API.removeOxygen(level, cachedPositions);
        } catch (Exception e) {
            BlackArchive.LOGGER.error("Error removing Life Support effects: " + e.getMessage());
        }
    }

    @Override
    public void setRemoved() {
        // clean up oxygen when block entity is removed
        try {
            if (level != null) {
                if (cachedPositions == null) {
                    // if cache missing, compute once for current position
                    cachedPositions = AATools.getPositionsInRadius(getBlockPos(), radius, radius);
                }
                OxygenApi.API.removeOxygen(level, cachedPositions);
            }
        } catch (Exception e) {
            BlackArchive.LOGGER.error("Error removing Life Support effects on removal: " + e.getMessage());
        }
        super.setRemoved();
    }
}
