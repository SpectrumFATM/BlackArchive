package net.SpectrumFATM.black_archive.block.custom;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import whocraft.tardis_refined.registry.TRItemRegistry;

public class DalekGravityGenBlock extends GravityGenBlock {
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");

    public DalekGravityGenBlock(Properties settings) {
        super(settings.lightLevel((state) -> state.getValue(POWERED) ? 15 : 0).strength(3.0f, 3.0f).mapColor(MapColor.COLOR_ORANGE));
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
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
}