package net.SpectrumFATM.black_archive.item.custom;

import net.SpectrumFATM.black_archive.entity.custom.CybermanEntity;
import net.SpectrumFATM.black_archive.entity.custom.CybermatEntity;
import net.SpectrumFATM.black_archive.entity.custom.TimeFissureEntity;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.SpectrumFATM.black_archive.util.SonicEngine;
import net.SpectrumFATM.black_archive.util.SpaceTimeEventUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.TintedGlassBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.common.items.ScrewdriverMode;
import whocraft.tardis_refined.registry.TRSoundRegistry;

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
