package net.SpectrumFATM.black_archive.item.custom;

import whocraft.tardis_refined.common.items.GlassesItem;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ContactLensItem extends GlassesItem {
    public ContactLensItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);
         tooltip.add(Component.translatable("item.contact_lens.tooltip").withStyle(ChatFormatting.GOLD));
    }
}