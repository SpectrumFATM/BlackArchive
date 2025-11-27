package net.SpectrumFATM.black_archive.ad_astra_compat.blockentities;

import earth.terrarium.adastra.api.systems.GravityApi;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.ad_astra_compat.blocks.GravityField;
import net.SpectrumFATM.black_archive.ad_astra_compat.util.AATools;
import net.SpectrumFATM.black_archive.blockentity.ModBlockEntities;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collection;

public class GravityFieldBlockEntity extends BlockEntity {

    private final int radius;
    private Collection<BlockPos> cachedPositions;
    private BlockPos cachedOrigin;
    private int tickCooldown = 0;
    private boolean lastPowered = false;

    public GravityFieldBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GRAVITY_SUPPORT_BE.get(), pos, state);
        this.radius = BlackArchiveConfig.COMMON.gravityFieldRange.get();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, GravityFieldBlockEntity be) {
        if (level.isClientSide) return;

        boolean powered = state.getValue(GravityField.POWERED);

        if (powered) {
            if (!be.lastPowered) {
                be.ensureCache(pos);
                be.applyEffects(level);
                be.tickCooldown = 20;
                be.lastPowered = true;
            } else {
                if (be.tickCooldown-- <= 0) {
                    be.ensureCache(pos);
                    be.applyEffects(level);
                    be.tickCooldown = 20;
                }
            }
        } else {
            if (be.lastPowered) {
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
            GravityApi.API.setGravity(level, cachedPositions, 0.98f);
        } catch (Exception e) {
            BlackArchive.LOGGER.error("Error applying Gravity effects: " + e.getMessage());
        }
    }

    private void removeEffects(Level level) {
        if (cachedPositions == null || level == null) return;
        try {
            GravityApi.API.removeGravity(level, cachedPositions);
        } catch (Exception e) {
            BlackArchive.LOGGER.error("Error removing Gravity effects: " + e.getMessage());
        }
    }

    @Override
    public void setRemoved() {
        try {
            if (level != null) {
                if (cachedPositions == null) {
                    cachedPositions = AATools.getPositionsInRadius(getBlockPos(), radius, radius);
                }
                GravityApi.API.removeGravity(level, cachedPositions);
            }
        } catch (Exception e) {
            BlackArchive.LOGGER.error("Error removing Gravity effects on removal: " + e.getMessage());
        }
        super.setRemoved();
    }
}