package net.SpectrumFATM.black_archive.ad_astra_compat.blockentities;

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

public class OxygenFieldBlockEntity extends BlockEntity {

    private static  int radius;

    public OxygenFieldBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.OXYGEN_SUPPORT_BE.get(), pos, state);
        radius = BlackArchiveConfig.COMMON.oxygenFieldRange.get();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, OxygenFieldBlockEntity be) {
        if (level.isClientSide) return;
        if (state.getValue(OxygenField.POWERED)) {
            try {
                OxygenApi.API.setOxygen(level, AATools.getPositionsInRadius(pos, radius, radius), true);
                TemperatureApi.API.setTemperature(level, AATools.getPositionsInRadius(pos, radius, radius), (short) 22);
            } catch (Exception e) {
                BlackArchive.LOGGER.error("Error applying Life Support effects: " + e.getMessage());
            }
        } else {
            try {
                OxygenApi.API.removeOxygen(level, AATools.getPositionsInRadius(pos, radius, radius));
                TemperatureApi.API.removeTemperature(level, AATools.getPositionsInRadius(pos, radius, radius));
            } catch (Exception e) {
                BlackArchive.LOGGER.error("Error removing Life Support effects: " + e.getMessage());
            }
        }
    }
}
