package net.SpectrumFATM.black_archive.ad_astra_compat.blockentities;

import earth.terrarium.adastra.api.systems.GravityApi;
import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.ad_astra_compat.blocks.GravityField;
import net.SpectrumFATM.black_archive.ad_astra_compat.blocks.OxygenField;
import net.SpectrumFATM.black_archive.ad_astra_compat.util.AATools;
import net.SpectrumFATM.black_archive.blockentity.ModBlockEntities;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GravityFieldBlockEntity extends BlockEntity {

    private static  int radius;

    public GravityFieldBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GRAVITY_SUPPORT_BE.get(), pos, state);
        radius = BlackArchiveConfig.COMMON.gravityFieldRange.get();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, GravityFieldBlockEntity be) {
        if (level.isClientSide) return;
        if (state.getValue(GravityField.POWERED)) {
            try {
                GravityApi.API.setGravity(level, AATools.getPositionsInRadius(pos, radius, radius), 0.98f);
            } catch (Exception e) {
                BlackArchive.LOGGER.error("Error applying Life Support effects: " + e.getMessage());
            }
        } else {
            try {
                GravityApi.API.removeGravity(level, AATools.getPositionsInRadius(pos, radius, radius));
            } catch (Exception e) {
                BlackArchive.LOGGER.error("Error removing Life Support effects: " + e.getMessage());
            }
        }
    }
}
