package net.SpectrumFATM.black_archive.item.custom;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class TooltipItem extends Item {

    public String tooltipKey;

    public TooltipItem(Properties settings, String tooltipKey) {
        super(settings);
        this.tooltipKey = tooltipKey;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);
        if (tooltipKey != "") {
            tooltip.add(Component.translatable(tooltipKey).withStyle(ChatFormatting.GOLD));
        }
    }
}
