package net.SpectrumFATM.black_archive.block.custom;

import net.SpectrumFATM.black_archive.entity.custom.ChairEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChairBlock extends Block {
    public static final BooleanProperty OCCUPIED = BooleanProperty.create("occupied");
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    public ChairBlock() {
        super(Properties.of().destroyTime(-1.0F).noCollission());
        this.registerDefaultState(this.stateDefinition.any().setValue(OCCUPIED, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OCCUPIED, FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(OCCUPIED, false).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            if (state.getValue(OCCUPIED)) {
                player.displayClientMessage(Component.literal("This chair is already occupied."), true);
                return InteractionResult.SUCCESS;
            }

            if (player instanceof ServerPlayer serverPlayer) {
                ChairEntity chairEntity = new ChairEntity(level, pos);
                level.addFreshEntity(chairEntity);
                player.startRiding(chairEntity);
                player.displayClientMessage(Component.literal("You are now sitting on the chair."), true);
                level.setBlock(pos, state.setValue(OCCUPIED, true), 3);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader worldIn, BlockPos pos) {
        return !worldIn.isEmptyBlock(pos.below());
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 32.0D, 16.0D);
    }

    @Override
    public VoxelShape getInteractionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return getShape(blockState, blockGetter, blockPos, CollisionContext.empty());
    }
}