package net.SpectrumFATM.black_archive.fabric.item.custom;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TooltipBlockItem extends BlockItem {

    private final String tooltipKey;

    public TooltipBlockItem(Block block, Settings settings, String tooltipKey) {
        super(block, settings);
        this.tooltipKey = tooltipKey;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(tooltipKey).formatted(Formatting.GOLD));
    }
}
