package net.SpectrumFATM.black_archive.block.custom;

import net.SpectrumFATM.black_archive.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

public class GravityGenBlock extends Block {
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");

    public GravityGenBlock(Properties settings) {
        super(settings.lightLevel((state) -> state.getValue(POWERED) ? 15 : 0).strength(3.0f, 3.0f).mapColor(MapColor.COLOR_GRAY));
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
        settings.destroyTime(2.0f);
    }
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return (BlockState)this.defaultBlockState().setValue(POWERED, ctx.getLevel().hasNeighborSignal(ctx.getClickedPos()));
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (world.isClientSide) {
            return;
        }
        boolean bl = state.getValue(POWERED);
        if (bl != world.hasNeighborSignal(pos)) {
            if (bl) {
                world.scheduleTick(pos, this, 4);
            } else {
                world.setBlock(pos, (BlockState)state.cycle(POWERED), Block.UPDATE_CLIENTS);
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (state.getValue(POWERED).booleanValue() && !world.hasNeighborSignal(pos)) {
            world.setBlock(pos, (BlockState)state.cycle(POWERED), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.spawnAfterBreak(state, world, pos, tool, dropExperience);
        if (!world.isClientSide) {
            popResource(world, pos, new ItemStack(ModItems.GRAVITY_GEN.get(), 1));
        }
    }
}