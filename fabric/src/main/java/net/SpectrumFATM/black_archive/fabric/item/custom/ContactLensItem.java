package net.SpectrumFATM.black_archive.fabric.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import whocraft.tardis_refined.common.items.GlassesItem;

import java.util.List;

public class ContactLensItem extends GlassesItem {
    public ContactLensItem(Settings properties) {
        super(properties);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.literal("Allows you to fly the TARDIS without those bulky glasses.").formatted(Formatting.GOLD));
    }
}