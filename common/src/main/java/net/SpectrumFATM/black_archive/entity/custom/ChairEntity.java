package net.SpectrumFATM.black_archive.entity.custom;

import net.SpectrumFATM.black_archive.block.custom.ChairBlock;
import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ChairEntity extends Entity {
    public ChairEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public ChairEntity(Level level, BlockPos pos) {
        this(ModEntities.CHAIR.get(), level);
        this.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getPassengers().isEmpty() && !this.level().isClientSide) {
            BlockState state = this.level().getBlockState(this.blockPosition());
            if (state.getBlock() instanceof ChairBlock) {
                this.level().setBlock(this.blockPosition(), state.setValue(ChairBlock.OCCUPIED, false), 3);
            }
            this.discard();
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        if (passenger instanceof LivingEntity) {
            this.positionRider(passenger);
        }
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction moveFunction) {
        BlockState state = this.level().getBlockState(this.blockPosition());
        if (state.getBlock() instanceof ChairBlock) {
            Direction facing = state.getValue(ChairBlock.FACING);
            float yaw = facing.toYRot();
            passenger.setYRot(yaw);
            passenger.setXRot(0);
            passenger.setYBodyRot(yaw);
            passenger.setPos(this.getX(), this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset(), this.getZ());
        }
    }
}