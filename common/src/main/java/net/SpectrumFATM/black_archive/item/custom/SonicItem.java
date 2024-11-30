package net.SpectrumFATM.black_archive.item.custom;

import net.SpectrumFATM.black_archive.block.custom.GravityGenBlock;
import net.SpectrumFATM.black_archive.block.custom.OxygenGenBlock;
import net.SpectrumFATM.black_archive.entity.custom.CybermanEntity;
import net.SpectrumFATM.black_archive.entity.custom.CybermatEntity;
import net.SpectrumFATM.black_archive.entity.custom.TimeFissureEntity;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.SpectrumFATM.black_archive.util.SpaceTimeEventUtil;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.common.items.ScrewdriverMode;
import whocraft.tardis_refined.registry.TRSoundRegistry;

import java.util.List;
import java.util.Random;

public class SonicItem extends ScrewdriverItem {

    String tooltipKey;
    Formatting colorFormat;
    Random random = new Random();

    public SonicItem(Settings properties, String tooltipKey, Formatting colorFormat) {
        super(properties);
        this.tooltipKey = tooltipKey;
        this.colorFormat = colorFormat;
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

                   if (state.getBlock() instanceof DoorBlock) {
                       world.setBlockState(context.getBlockPos(), state.cycle(DoorBlock.OPEN), Block.NOTIFY_ALL);
                       playScrewdriverSound(serverWorld, context.getBlockPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                       cooldown(player, stack);
                   }

                   if (state.getBlock() instanceof TrapdoorBlock) {
                          world.setBlockState(context.getBlockPos(), state.cycle(TrapdoorBlock.OPEN), Block.NOTIFY_ALL);
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

                   if (state.getBlock() instanceof IceBlock) {
                       world.setBlockState(context.getBlockPos(), Blocks.WATER.getDefaultState(), Block.NOTIFY_ALL);

                       playScrewdriverSound(serverWorld, context.getBlockPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                       cooldown(player, stack);
                   }

                   if (state.getBlock() instanceof PowderSnowBlock) {
                       world.setBlockState(context.getBlockPos(), Blocks.SNOW_BLOCK.getDefaultState(), Block.NOTIFY_ALL);

                       playScrewdriverSound(serverWorld, context.getBlockPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                       cooldown(player, stack);
                   }

                   if (state.getBlock() instanceof GravityGenBlock) {
                       world.setBlockState(context.getBlockPos(), state.cycle(GravityGenBlock.POWERED), Block.NOTIFY_ALL);

                       playScrewdriverSound(serverWorld, context.getBlockPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                       cooldown(player, stack);
                   }

                   if (state.getBlock() instanceof OxygenGenBlock) {
                       world.setBlockState(context.getBlockPos(), state.cycle(GravityGenBlock.POWERED), Block.NOTIFY_ALL);

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
                } else if (entity instanceof CybermanEntity cybermanEntity && !user.isSneaking()) {
                    cybermanEntity.disableFire(100);
                } else if (entity instanceof TimeFissureEntity timeFissureEntity && !user.isSneaking()) {
                    if (random.nextInt(10) == 1) {
                        timeFissureEntity.aggrovate();
                        user.sendMessage(Text.translatable("item.sonic.temporal_escalation").formatted(Formatting.RED), true);
                    } else {
                        timeFissureEntity.remove(Entity.RemovalReason.KILLED);
                    }
                } else if (entity instanceof CybermatEntity cybermatEntity && !user.isSneaking()) {
                    BlockPos pos = entity.getBlockPos();
                    entity.remove(Entity.RemovalReason.KILLED);
                    cybermatEntity.dropStack(new ItemStack(ModItems.CYBERMAT_EGG.get()), 0.0f);
                } else {
                    String entityName = entity.getName().getString();
                    int xPos = entity.getBlockX();
                    int yPos = entity.getBlockY();
                    int zPos = entity.getBlockZ();
                    double health = entity.getHealth();

                    user.sendMessage(Text.translatable("sonic.title.scan").formatted(colorFormat).formatted(Formatting.BOLD).formatted(Formatting.UNDERLINE), false);
                    user.sendMessage(Text.literal("Name: " + entityName).formatted(colorFormat), false);
                    user.sendMessage(Text.literal("X: " + xPos).formatted(colorFormat), false);
                    user.sendMessage(Text.literal("Y: " + yPos).formatted(colorFormat), false);
                    user.sendMessage(Text.literal("Z: " + zPos).formatted(colorFormat), false);
                    user.sendMessage(Text.literal("Health: " + Math.round(health)).formatted(colorFormat), false);
                    user.sendMessage(Text.literal("Armour: " + entity.getArmor()).formatted(colorFormat), false);

                    if (entity instanceof PlayerEntity player) {
                        String  artron = "Earth normal.";
                        if (SpaceTimeEventUtil.isComplexSpaceTimeEvent(player)) {
                            artron = "Background Artron radiation detected.";
                        }
                        user.sendMessage(Text.literal("Radiation Signatures: " + artron).formatted(colorFormat), false);
                    }
                }

                playScrewdriverSound(serverWorld, user.getBlockPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());
            }
        }

        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.remove(1);
        tooltip.add(Text.translatable(tooltipKey).formatted(Formatting.GOLD));
    }

    public static void cooldown(PlayerEntity player, ItemStack stack) {
        if (!player.isCreative()) {
            player.getItemCooldownManager().set(stack.getItem(), 40);
        }
    }
}
