package net.SpectrumFATM.black_archive.block.custom;

import net.SpectrumFATM.black_archive.blockentity.entities.ShipDoorEntity;
import net.SpectrumFATM.black_archive.entity.custom.ShipEntity;
import net.SpectrumFATM.black_archive.util.ShipUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ShipDoor extends BaseEntityBlock {

    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public ShipDoor() {
        super(BlockBehaviour.Properties.of().destroyTime(-1).noOcclusion().noCollission());
        this.registerDefaultState(this.stateDefinition.any().setValue(OPEN, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OPEN);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(OPEN, false);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        toggleDoor(blockState, level, blockPos, player);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        super.entityInside(blockState, level, blockPos, entity);
        if (entity instanceof ServerPlayer player && !level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof ShipDoorEntity shipDoor && blockState.getValue(OPEN)) {
                handlePlayerInsideShipDoor(level, player, shipDoor);
            }
        }
    }

    private void handlePlayerInsideShipDoor(Level level, ServerPlayer player, ShipDoorEntity shipDoor) {
        UUID entityUUID = UUID.fromString(level.dimension().location().toString().replace("black_archive:", ""));
        ServerLevel currentLevel = ShipUtil.getServerLevelFromInteriorDoorDimension(player, shipDoor.getExteriorDimension());

        if (currentLevel == null) {
            currentLevel = level.getServer().overworld();
        }

        Entity shipEntity = currentLevel.getEntity(entityUUID);
        if (shipEntity instanceof ShipEntity ship) {
            ship.setOpen(false);
            BlockPos pos = shipEntity.blockPosition().north(3);

            player.teleportTo(currentLevel, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, player.getYRot(), player.getXRot());
        }

        toggleDoor(level.getBlockState(shipDoor.getBlockPos()), level, shipDoor.getBlockPos(), player);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ShipDoorEntity(blockPos, blockState);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(OPEN) ? Block.box(0, 0, 0, 3, 32, 16) : Block.box(0, 0, 13, 16, 32,16);
    }

    @Override
    public VoxelShape getInteractionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return this.getShape(blockState, blockGetter, blockPos, CollisionContext.empty());
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return this.getShape(blockState, blockGetter, blockPos, collisionContext);
    }

    private void toggleDoor(BlockState blockState, Level level, BlockPos blockPos, Player player) {
        if (blockState.getValue(OPEN)) {
            level.playLocalSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.IRON_DOOR_CLOSE, player.getSoundSource(), 1.0F, 1.0F, false);
        } else {
            level.playLocalSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.IRON_DOOR_OPEN, player.getSoundSource(), 1.0F, 1.0F, false);
        }

        blockState = blockState.cycle(OPEN);
        level.setBlock(blockPos, blockState, 10);
    }
}