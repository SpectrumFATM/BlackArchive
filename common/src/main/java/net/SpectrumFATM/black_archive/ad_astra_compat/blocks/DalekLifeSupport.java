package net.SpectrumFATM.black_archive.ad_astra_compat.blocks;

import earth.terrarium.adastra.api.systems.GravityApi;
import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.ad_astra_compat.blockentities.DalekLifeSupportBlockEntity;
import net.SpectrumFATM.black_archive.ad_astra_compat.util.AATools;
import net.SpectrumFATM.black_archive.blockentity.ModBlockEntities;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.registry.TRItemRegistry;

public class DalekLifeSupport extends BaseEntityBlock {

    public static final BooleanProperty POWERED = BooleanProperty.create("powered");

    private final int radiusXY;
    private final int radiusZ;

    public DalekLifeSupport(Properties settings, int radiusXY, int radiusZ) {
        super(settings.lightLevel((state) -> state.getValue(POWERED) ? 15 : 0).strength(3.0f, 3.0f).mapColor(MapColor.COLOR_ORANGE));
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
        this.radiusXY = radiusXY;
        this.radiusZ = radiusZ;
    }

    public int getRadiusXY() {
        return radiusXY;
    }

    public int getRadiusZ() {
        return radiusZ;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DalekLifeSupportBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.DALEK_LIFE_SUPPORT_BE.get(), DalekLifeSupportBlockEntity::tick);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
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
                world.setBlock(pos, state.cycle(POWERED), Block.UPDATE_CLIENTS);
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (state.getValue(POWERED) && !world.hasNeighborSignal(pos)) {
            world.setBlock(pos, state.cycle(POWERED), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.spawnAfterBreak(state, world, pos, tool, dropExperience);
        if (!world.isClientSide) {
            if (tool.isEnchanted() && EnchantmentHelper.hasSilkTouch(tool)) {
                popResource(world, pos, new ItemStack(ModItems.DALEK_GRAV_GEN.get(), 1));
            } else if (tool.getItem() == Items.DIAMOND_PICKAXE) {
                popResource(world, pos, new ItemStack(TRItemRegistry.RAW_ZEITON.get(), 3));
                popResource(world, pos, new ItemStack(Items.COPPER_BLOCK.asItem(), 2));
            }
        }
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
        super.playerDestroy(level, player, pos, blockState, blockEntity, itemStack);
        try {
            OxygenApi.API.removeOxygen(level, AATools.getPositionsInRadius(pos, 33, 18));
            TemperatureApi.API.removeTemperature(level, AATools.getPositionsInRadius(pos, 33, 18));
            GravityApi.API.removeGravity(level, AATools.getPositionsInRadius(pos, 33, 18));
        } catch (Exception e) {
            BlackArchive.LOGGER.error("Error removing Life Support effects: " + e.getMessage());
        }
    }
}
