package net.SpectrumFATM.black_archive.fabric.block.custom;

import net.SpectrumFATM.black_archive.fabric.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GravityGenBlock extends Block {
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");

    public GravityGenBlock(Settings settings) {
        super(settings.luminance((state) -> state.get(POWERED) ? 15 : 0).strength(3.0f, 3.0f).mapColor(MapColor.GRAY));
        this.setDefaultState(this.stateManager.getDefaultState().with(POWERED, false));
        settings.hardness(2.0f);
    }
    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (world.isClient) {
            return;
        }
        boolean bl = state.get(POWERED);
        if (bl != world.isReceivingRedstonePower(pos)) {
            if (bl) {
                world.scheduleBlockTick(pos, this, 4);
            } else {
                world.setBlockState(pos, (BlockState)state.cycle(POWERED), Block.NOTIFY_LISTENERS);
            }
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(POWERED).booleanValue() && !world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, (BlockState)state.cycle(POWERED), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, tool, dropExperience);
        if (!world.isClient) {
            dropStack(world, pos, new ItemStack(ModItems.GRAVITY_GEN, 1));
        }
    }
}