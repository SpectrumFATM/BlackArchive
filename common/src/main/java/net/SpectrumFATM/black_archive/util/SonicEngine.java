package net.SpectrumFATM.black_archive.util;

import dev.jeryn.doctorwho.common.items.SonicItem;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.entity.custom.CybermanEntity;
import net.SpectrumFATM.black_archive.entity.custom.CybermatEntity;
import net.SpectrumFATM.black_archive.entity.custom.TimeFissureEntity;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.common.items.ScrewdriverMode;
import whocraft.tardis_refined.registry.TRSoundRegistry;

import java.util.Random;

public class SonicEngine {

    public static void blockActivate(UseOnContext context) {
        if (BlackArchiveConfig.COMMON.enableSonicEngine.get()) {

            ScrewdriverItem item = (ScrewdriverItem) context.getItemInHand().getItem();

            if (!item.isScrewdriverMode(context.getItemInHand(), ScrewdriverMode.DRAWING)) {
                Level world = context.getLevel();
                BlockState state = context.getLevel().getBlockState(context.getClickedPos());
                Player player = context.getPlayer();
                ItemStack stack = context.getItemInHand();

                if (!world.isClientSide) {
                    if (world instanceof ServerLevel serverWorld) {

                        if (state.hasProperty(BlockStateProperties.POWERED)) {
                            world.setBlock(context.getClickedPos(), state.cycle(BlockStateProperties.POWERED), Block.UPDATE_ALL);
                            item.playScrewdriverSound(serverWorld, context.getClickedPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());
                            cooldown(player, stack);
                        }

                        if (state.hasProperty(BlockStateProperties.LIT)) {
                            world.setBlock(context.getClickedPos(), state.cycle(BlockStateProperties.LIT), Block.UPDATE_ALL);
                            item.playScrewdriverSound(serverWorld, context.getClickedPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());
                            cooldown(player, stack);
                        }

                        if (state.hasProperty(BlockStateProperties.OPEN)) {
                            world.setBlock(context.getClickedPos(), state.cycle(BlockStateProperties.OPEN), Block.UPDATE_ALL);
                            item.playScrewdriverSound(serverWorld, context.getClickedPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());
                            cooldown(player, stack);
                        }

                        if (state.getBlock() instanceof TntBlock) {
                            serverWorld.removeBlock(context.getClickedPos(), false);
                            TntBlock.explode(world, context.getClickedPos());

                            item.playScrewdriverSound(serverWorld, context.getClickedPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                            cooldown(player, stack);
                        }

                        if (state.getBlock() instanceof IronBarsBlock) {
                            world.destroyBlock(context.getClickedPos(), true);

                            item.playScrewdriverSound(serverWorld, context.getClickedPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                            cooldown(player, stack);
                        }

                        if (state.getBlock() instanceof GlassBlock) {
                            world.destroyBlock(context.getClickedPos(), true);

                            item.playScrewdriverSound(serverWorld, context.getClickedPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                            cooldown(player, stack);
                        }

                        if (state.getBlock() instanceof StainedGlassPaneBlock) {
                            world.destroyBlock(context.getClickedPos(), true);

                            item.playScrewdriverSound(serverWorld, context.getClickedPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                            cooldown(player, stack);
                        }

                        if (state.getBlock() instanceof StainedGlassBlock) {
                            world.destroyBlock(context.getClickedPos(), true);

                            item.playScrewdriverSound(serverWorld, context.getClickedPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                            cooldown(player, stack);
                        }

                        if (state.getBlock() instanceof TintedGlassBlock) {
                            world.destroyBlock(context.getClickedPos(), true);

                            item.playScrewdriverSound(serverWorld, context.getClickedPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                            cooldown(player, stack);
                        }

                        if (state.getBlock() instanceof IceBlock) {
                            world.setBlock(context.getClickedPos(), Blocks.WATER.defaultBlockState(), Block.UPDATE_ALL);

                            item.playScrewdriverSound(serverWorld, context.getClickedPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                            cooldown(player, stack);
                        }

                        if (state.getBlock() instanceof PowderSnowBlock) {
                            world.setBlock(context.getClickedPos(), Blocks.SNOW_BLOCK.defaultBlockState(), Block.UPDATE_ALL);

                            item.playScrewdriverSound(serverWorld, context.getClickedPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());

                            cooldown(player, stack);
                        }
                    }
                }
            }
        }
    }

    public static void entityActivate(ItemStack stack, Player user, LivingEntity entity, ChatFormatting colorFormat) {
        if (BlackArchiveConfig.COMMON.enableSonicEngine.get()) {
            ScrewdriverItem item = (ScrewdriverItem) stack.getItem();
            Random random = new Random();

            if (colorFormat == null) {
                colorFormat = ChatFormatting.BLUE;
            }

            if (user.level() instanceof ServerLevel serverWorld) {
                if (!user.level().isClientSide) {
                    if (entity instanceof Creeper creeper && !user.isShiftKeyDown()) {
                        creeper.setSwellDir(0);
                        creeper.ignite();
                    } else if (entity instanceof Skeleton skeleton && !user.isShiftKeyDown()) {
                        skeleton.kill();
                    } else if (entity instanceof WitherSkeleton skeleton && !user.isShiftKeyDown()) {
                        skeleton.kill();
                    } else if (entity instanceof Blaze blazeEntity && !user.isShiftKeyDown()) {
                        blazeEntity.kill();
                    } else if (entity instanceof CybermanEntity cybermanEntity && !user.isShiftKeyDown()) {
                        cybermanEntity.disableFire(100);
                    } else if (entity instanceof TimeFissureEntity timeFissureEntity && !user.isShiftKeyDown()) {
                        if (random.nextInt(10) == 1) {
                            timeFissureEntity.aggrovate();
                            user.displayClientMessage(Component.translatable("item.sonic.temporal_escalation").withStyle(ChatFormatting.RED), true);
                        } else {
                            timeFissureEntity.remove(Entity.RemovalReason.KILLED);
                        }
                    } else if (entity instanceof CybermatEntity cybermatEntity && !user.isShiftKeyDown()) {
                        BlockPos pos = entity.blockPosition();
                        entity.remove(Entity.RemovalReason.KILLED);
                        cybermatEntity.spawnAtLocation(new ItemStack(ModItems.CYBERMAT_EGG.get()), 0.0f);
                    } else {
                        String entityName = entity.getName().getString();
                        int xPos = entity.getBlockX();
                        int yPos = entity.getBlockY();
                        int zPos = entity.getBlockZ();
                        double health = entity.getHealth();

                        user.displayClientMessage(Component.translatable("sonic.title.scan").withStyle(colorFormat).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.UNDERLINE), false);
                        user.displayClientMessage(Component.literal("Name: " + entityName).withStyle(colorFormat), false);
                        user.displayClientMessage(Component.literal("X: " + xPos).withStyle(colorFormat), false);
                        user.displayClientMessage(Component.literal("Y: " + yPos).withStyle(colorFormat), false);
                        user.displayClientMessage(Component.literal("Z: " + zPos).withStyle(colorFormat), false);
                        user.displayClientMessage(Component.literal("Health: " + Math.round(health)).withStyle(colorFormat), false);
                        user.displayClientMessage(Component.literal("Armour: " + entity.getArmorValue()).withStyle(colorFormat), false);

                        if (entity instanceof Player player) {
                            String artron = "Earth normal.";
                            if (SpaceTimeEventUtil.isComplexSpaceTimeEvent(player)) {
                                artron = "Background Artron radiation detected.";
                            }
                            user.displayClientMessage(Component.literal("Radiation Signatures: " + artron).withStyle(colorFormat), false);
                        }
                    }

                    item.playScrewdriverSound(serverWorld, user.blockPosition(), TRSoundRegistry.SCREWDRIVER_SHORT.get());
                }
            }
        }
    }

    public static void cooldown(Player player, ItemStack stack) {
        if (!player.isCreative()) {
            player.getCooldowns().addCooldown(stack.getItem(), 40);
        }
    }
}
