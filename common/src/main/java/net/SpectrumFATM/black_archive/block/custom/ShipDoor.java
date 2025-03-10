package net.SpectrumFATM.black_archive.block.custom;

import net.SpectrumFATM.black_archive.blockentity.entities.ShipDoorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ShipDoor extends BaseEntityBlock {
    public ShipDoor() {
        super(BlockBehaviour.Properties.of().destroyTime(-1).noOcclusion().noCollission());
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        super.entityInside(blockState, level, blockPos, entity);
        if (entity instanceof ServerPlayer player) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            BlockPos pos = BlockPos.ZERO;
            if (blockEntity instanceof ShipDoorEntity shipDoor) {
                pos = shipDoor.getPos().north(1);
            }
            player.teleportTo(player.getServer().overworld(), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, player.getYRot(), player.getXRot());
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ShipDoorEntity(blockPos, blockState);
    }
}
