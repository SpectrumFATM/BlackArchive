package net.SpectrumFATM.black_archive.fabric.item.custom;

import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.common.items.ScrewdriverMode;
import whocraft.tardis_refined.registry.TRSoundRegistry;

import java.util.List;

public class SonicItem extends ScrewdriverItem {
    public SonicItem(Settings properties) {
        super(properties);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {


        ActionResult result = super.useOnBlock(context);


       if (!isScrewdriverMode(context.getStack(), ScrewdriverMode.DRAWING)) {
           World world = context.getWorld();
           BlockState state = context.getWorld().getBlockState(context.getBlockPos());
           PlayerEntity player = context.getPlayer();
           ItemStack stack = context.getStack();

           if (!world.isClient) {
               if (world instanceof ServerWorld serverWorld) {
                   if (state.getBlock() instanceof RedstoneLampBlock) {
                       world.setBlockState(context.getBlockPos(), state.cycle(RedstoneLampBlock.LIT), Block.NOTIFY_ALL);
                       playScrewdriverSound(serverWorld, context.getBlockPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                       cooldown(player, stack);
                   }

                   if (state.getBlock() instanceof DoorBlock door) {
                       if (door.isOpen(state)) {
                            door.setOpen(player, world, state, context.getBlockPos(), false);
                          } else {
                           door.setOpen(player, world, state, context.getBlockPos(), true);
                       }

                       playScrewdriverSound(serverWorld, context.getBlockPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                       cooldown(player, stack);
                   }

                   if (state.getBlock() instanceof TntBlock) {
                       serverWorld.removeBlock(context.getBlockPos(), false);
                       TntBlock.primeTnt(world, context.getBlockPos());

                       playScrewdriverSound(serverWorld, context.getBlockPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                       cooldown(player, stack);
                   }

                   if (state.getBlock() instanceof CandleBlock && player.isSneaking()) {
                       world.setBlockState(context.getBlockPos(), state.cycle(CandleBlock.LIT), Block.NOTIFY_ALL);

                       playScrewdriverSound(serverWorld, context.getBlockPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                       cooldown(player, stack);
                   }

                   if (state.getBlock() instanceof CampfireBlock) {
                       world.setBlockState(context.getBlockPos(), state.cycle(CampfireBlock.LIT), Block.NOTIFY_ALL);

                       playScrewdriverSound(serverWorld, context.getBlockPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                       cooldown(player, stack);
                   }

                   if (state.getBlock() instanceof PaneBlock) {
                       world.breakBlock(context.getBlockPos(), true);

                       playScrewdriverSound(serverWorld, context.getBlockPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                       cooldown(player, stack);
                   }

                   if (state.getBlock() instanceof GlassBlock) {
                       world.breakBlock(context.getBlockPos(), true);

                       playScrewdriverSound(serverWorld, context.getBlockPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                       cooldown(player, stack);
                   }

                   if (state.getBlock() instanceof StainedGlassPaneBlock) {
                       world.breakBlock(context.getBlockPos(), true);

                       playScrewdriverSound(serverWorld, context.getBlockPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                       cooldown(player, stack);
                   }

                   if (state.getBlock() instanceof StainedGlassBlock) {
                       world.breakBlock(context.getBlockPos(), true);

                       playScrewdriverSound(serverWorld, context.getBlockPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                       cooldown(player, stack);
                   }

                   if (state.getBlock() instanceof TintedGlassBlock) {
                       world.breakBlock(context.getBlockPos(), true);

                       playScrewdriverSound(serverWorld, context.getBlockPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                       cooldown(player, stack);
                   }
               }
           }
       }
        return result;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        if (user.getWorld() instanceof ServerWorld serverWorld) {
            if (!user.getWorld().isClient) {
                if (entity instanceof CreeperEntity creeper && !user.isSneaking()) {
                    creeper.setFuseSpeed(0);
                    creeper.ignite();
                } else if (entity instanceof SkeletonEntity skeleton && !user.isSneaking()) {
                    skeleton.kill();
                } else if (entity instanceof WitherSkeletonEntity skeleton && !user.isSneaking()) {
                    skeleton.kill();
                } else if (entity instanceof BlazeEntity blazeEntity && !user.isSneaking()) {
                    blazeEntity.kill();
                }else {
                    String entityName = entity.getName().getString();
                    int xPos = entity.getBlockX();
                    int yPos = entity.getBlockY();
                    int zPos = entity.getBlockZ();
                    double health = entity.getHealth();

                    user.sendMessage(Text.translatable("sonic.title.scan").formatted(Formatting.BLUE).formatted(Formatting.BOLD).formatted(Formatting.UNDERLINE), false);
                    user.sendMessage(Text.literal("Name: " + entityName).formatted(Formatting.BLUE), false);
                    user.sendMessage(Text.literal("X: " + xPos).formatted(Formatting.BLUE), false);
                    user.sendMessage(Text.literal("Y: " + yPos).formatted(Formatting.BLUE), false);
                    user.sendMessage(Text.literal("Z: " + zPos).formatted(Formatting.BLUE), false);
                    user.sendMessage(Text.literal("Health: " + health).formatted(Formatting.BLUE), false);
                    user.sendMessage(Text.literal("Armour: " + entity.getArmor()).formatted(Formatting.BLUE), false);
                }

                playScrewdriverSound(serverWorld, user.getBlockPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                cooldown(user, stack);
            }
        }

        return super.useOnEntity(stack, user, entity, hand);
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.remove(1);
        tooltip.add(Text.translatable("item.sonic.tooltip").formatted(Formatting.GOLD));
    }

    public static void cooldown(PlayerEntity player, ItemStack stack) {
        if (!player.isCreative()) {
            player.getItemCooldownManager().set(stack.getItem(), 40);
        }
    }
}
