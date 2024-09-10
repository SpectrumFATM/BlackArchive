package net.SpectrumFATM.black_archive.fabric.block.custom;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import whocraft.tardis_refined.registry.TRItemRegistry;

import java.util.List;

public class DalekGravityGenBlock extends GravityGenBlock {
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");

    public DalekGravityGenBlock(Settings settings) {
        super(settings.luminance((state) -> state.get(POWERED) ? 15 : 0).strength(3.0f, 3.0f).mapColor(MapColor.ORANGE));
        this.setDefaultState(this.stateManager.getDefaultState().with(POWERED, false));
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
                world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
            }
        }
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, tool, dropExperience);
        if (!world.isClient) {
            if (tool.hasEnchantments() && EnchantmentHelper.hasSilkTouch(tool)) {
                dropStack(world, pos, new ItemStack(ModItems.DALEK_GRAV_GEN, 1));
            } else if (tool.getItem() == Items.DIAMOND_PICKAXE) {
                BlackArchive.LOGGER.info(tool.getEnchantments().toString());
                dropStack(world, pos, new ItemStack(TRItemRegistry.RAW_ZEITON.get(), 3));
                dropStack(world, pos, new ItemStack(Items.COPPER_BLOCK.asItem(), 2));
            }
        }
    }
}