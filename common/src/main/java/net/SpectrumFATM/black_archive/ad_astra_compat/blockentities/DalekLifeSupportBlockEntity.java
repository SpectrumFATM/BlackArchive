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

public class DalekLifeSupportBlockEntity extends BlockEntity {

    private static BlockPos position;

    public DalekLifeSupportBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DALEK_LIFE_SUPPORT_BE.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, DalekLifeSupportBlockEntity be) {
        if (level.isClientSide) return;
        position = pos;
        if (state.getValue(DalekLifeSupport.POWERED)) {
            try {
                OxygenApi.API.setOxygen(level, AATools.getPositionsInRadius(pos, 33, 18), true);
                GravityApi.API.setGravity(level, AATools.getPositionsInRadius(pos, 33, 18), 0.98f);
                TemperatureApi.API.setTemperature(level, AATools.getPositionsInRadius(pos, 33, 18), (short) 22);
            } catch (Exception e) {
                BlackArchive.LOGGER.error("Error applying Dalek Life Support effects: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean isRemoved() {
        try {
            GravityApi.API.removeGravity(level, AATools.getPositionsInRadius(position, 33, 18));
        } catch (Exception e) {
            BlackArchive.LOGGER.error("Error removing Life Support effects: " + e.getMessage());
        }
        return super.isRemoved();
    }
}
