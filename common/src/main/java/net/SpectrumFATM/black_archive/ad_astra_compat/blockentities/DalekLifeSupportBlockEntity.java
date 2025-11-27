// java
package net.SpectrumFATM.black_archive.ad_astra_compat.blockentities;

import earth.terrarium.adastra.api.systems.GravityApi;
import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.ad_astra_compat.blocks.DalekLifeSupport;
import net.SpectrumFATM.black_archive.ad_astra_compat.util.AATools;
import net.SpectrumFATM.black_archive.blockentity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collection;

public class DalekLifeSupportBlockEntity extends BlockEntity {

    private final int radiusXY = 33;
    private final int radiusZ = 18;

    private Collection<BlockPos> cachedPositions;
    private BlockPos cachedOrigin;
    private int tickCooldown = 0;
    private boolean lastPowered = false;

    public DalekLifeSupportBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DALEK_LIFE_SUPPORT_BE.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, DalekLifeSupportBlockEntity be) {
        if (level.isClientSide) return;

        boolean powered = state.getValue(DalekLifeSupport.POWERED);

        if (powered) {
            if (!be.lastPowered) {
                // just turned on: compute area and apply immediately
                be.ensureCache(pos);
                be.applyEffects(level);
                be.tickCooldown = 20;
                be.lastPowered = true;
            } else {
                // already on: refresh occasionally to avoid per-tick allocations
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
            cachedPositions = AATools.getPositionsInRadius(origin, radiusXY, radiusZ);
            cachedOrigin = origin;
        }
    }

    private void applyEffects(Level level) {
        if (cachedPositions == null || level == null) return;
        try {
            OxygenApi.API.setOxygen(level, cachedPositions, true);
            GravityApi.API.setGravity(level, cachedPositions, 0.98f);
            TemperatureApi.API.setTemperature(level, cachedPositions, (short) 22);
        } catch (Exception e) {
            BlackArchive.LOGGER.error("Error applying Dalek Life Support effects: " + e.getMessage());
        }
    }

    private void removeEffects(Level level) {
        if (cachedPositions == null || level == null) return;
        try {
            GravityApi.API.removeGravity(level, cachedPositions);
        } catch (Exception e) {
            BlackArchive.LOGGER.error("Error removing Gravity effects: " + e.getMessage());
        }
        try {
            OxygenApi.API.removeOxygen(level, cachedPositions);
        } catch (Exception e) {
            BlackArchive.LOGGER.error("Error removing Oxygen effects: " + e.getMessage());
        }
    }

    @Override
    public void setRemoved() {
        try {
            if (level != null) {
                if (cachedPositions == null) {
                    cachedPositions = AATools.getPositionsInRadius(getBlockPos(), radiusXY, radiusZ);
                }
                GravityApi.API.removeGravity(level, cachedPositions);
                OxygenApi.API.removeOxygen(level, cachedPositions);
            }
        } catch (Exception e) {
            BlackArchive.LOGGER.error("Error removing Dalek Life Support effects on removal: " + e.getMessage());
        }
        super.setRemoved();
    }
}
