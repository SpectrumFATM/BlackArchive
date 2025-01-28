package net.SpectrumFATM.black_archive.item.custom;

import net.SpectrumFATM.black_archive.entity.custom.LaserEntity;
import net.SpectrumFATM.black_archive.util.LifeSupportUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LaserGunItem extends Item {

    private final String tooltipKey;
    private final SoundEvent soundEvent;
    private final SoundEvent malfunctionSound;
    int r;
    int g;
    int b;

    public LaserGunItem(Properties settings, String tooltipKey,  SoundEvent soundEvent, SoundEvent malfunctionSound, int r, int g, int b) {
        super(settings.durability(200));
        this.tooltipKey = tooltipKey;
        this.soundEvent = soundEvent;
        this.malfunctionSound = malfunctionSound;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000; // Duration for which the item can be used
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW; // Use bow animation
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        user.startUsingItem(hand); // Start using the item
        return InteractionResultHolder.consume(user.getItemInHand(hand));
    }

    @Override
    public boolean canBeDepleted() {
        return true;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (!world.isClientSide && user instanceof Player player) {
            if (stack.getDamageValue() <= stack.getMaxDamage()) {
                world.playSound(null, player.blockPosition(), soundEvent, player.getSoundSource(), 1.0F, 1.0F);
                // Logic to fire the laser entity
                LaserEntity laser = new LaserEntity(world, player, 2.0f, true, r, g ,b);
                laser.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
                world.addFreshEntity(laser);
                discharge(stack, player);
            } else {
                player.displayClientMessage(Component.translatable("laser.gun.outofcharge").withStyle(ChatFormatting.RED), true);
                world.playSound(null, player.blockPosition(), malfunctionSound, player.getSoundSource(), 1.0F, 1.0F);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);
        tooltip.add(Component.translatable(tooltipKey).withStyle(ChatFormatting.GOLD));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (LifeSupportUtil.dalekGravityGenNearby(entity, 8)) {
            if (stack.getDamageValue() > 0) {
                stack.setDamageValue(stack.getDamageValue() - 1);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void discharge(ItemStack stack, Player player) {
        if (!player.isCreative()) {
            player.getCooldowns().addCooldown(stack.getItem(), 20);
            stack.setDamageValue(stack.getDamageValue() + 10);
        }
    }
}