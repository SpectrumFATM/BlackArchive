package net.SpectrumFATM.black_archive.item.custom;

import net.SpectrumFATM.black_archive.util.sonic.SonicEngine;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.items.ScrewdriverItem;

import java.util.List;
import java.util.Random;

public class SonicItem extends ScrewdriverItem {

    String tooltipKey;
    ChatFormatting colorFormat;
    Random random = new Random();

    public SonicItem(Properties properties, String tooltipKey, ChatFormatting colorFormat) {
        super(properties);
        this.tooltipKey = tooltipKey;
        this.colorFormat = colorFormat;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        SonicEngine.blockActivate(context);
        return super.useOn(context);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        SonicEngine.entityActivate(itemStack, player, livingEntity, colorFormat);
        return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);
        tooltip.remove(1);
        tooltip.add(Component.translatable(tooltipKey).withStyle(ChatFormatting.GOLD));
    }
}
