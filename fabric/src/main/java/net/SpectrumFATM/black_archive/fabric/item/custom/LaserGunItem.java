package net.SpectrumFATM.black_archive.fabric.item.custom;

import net.SpectrumFATM.black_archive.fabric.entity.custom.LaserEntity;
import net.SpectrumFATM.black_archive.fabric.util.LifeSupportUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LaserGunItem extends Item {

    private final String tooltipKey;
    private final SoundEvent soundEvent;
    private final SoundEvent malfunctionSound;

    public LaserGunItem(Settings settings, String tooltipKey,  SoundEvent soundEvent, SoundEvent malfunctionSound) {
        super(settings.maxDamage(200));
        this.tooltipKey = tooltipKey;
        this.soundEvent = soundEvent;
        this.malfunctionSound = malfunctionSound;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000; // Duration for which the item can be used
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW; // Use bow animation
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand); // Start using the item
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!world.isClient && user instanceof PlayerEntity player) {
            if (stack.getDamage() <= stack.getMaxDamage()) {
                world.playSound(null, player.getBlockPos(), soundEvent, player.getSoundCategory(), 1.0F, 1.0F);
                // Logic to fire the laser entity
                LaserEntity laser = new LaserEntity(world, player, 2.0f, true, 117, 117 ,255);
                laser.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 3.0F, 1.0F);
                world.spawnEntity(laser);
                discharge(stack, player);
            } else {
                player.sendMessage(Text.translatable("laser.gun.outofcharge").formatted(Formatting.RED), true);
                world.playSound(null, player.getBlockPos(), malfunctionSound, player.getSoundCategory(), 1.0F, 1.0F);
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(tooltipKey).formatted(Formatting.GOLD));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (LifeSupportUtil.dalekGravityGenNearby(entity, 8)) {
            if (stack.getDamage() > 0) {
                stack.setDamage(stack.getDamage() - 1);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void discharge(ItemStack stack, PlayerEntity player) {
        if (!player.isCreative()) {
            player.getItemCooldownManager().set(stack.getItem(), 20);
            stack.setDamage(stack.getDamage() + 10);
        }
    }
}