package net.SpectrumFATM.black_archive.blockentity.entities;

import net.SpectrumFATM.black_archive.blockentity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ShipDoorEntity extends BlockEntity {

    private Long pos = 0L;
    private String dimension = "";

    public ShipDoorEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHIP_DOOR_ENTITY.get(), pos, state);
    }

    public String getDimension() {
        return dimension != null ? dimension : "";
    }

    public BlockPos getPos() {
        return pos != null ? BlockPos.of(pos) : BlockPos.ZERO;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public void setPos(Long pos) {
        this.pos = pos;
    }


    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.pos = tag.getLong("pos");
        this.dimension = tag.getString("dimension");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putLong("pos", this.pos != null ? this.pos : 0L);
        tag.putString("dimension", this.dimension != null ? this.dimension : "");
    }
}