package net.SpectrumFATM.black_archive.util.sonic;

import dev.jeryn.doctorwho.common.WCItems;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.entity.custom.CybermanEntity;
import net.SpectrumFATM.black_archive.entity.custom.CybermatEntity;
import net.SpectrumFATM.black_archive.entity.custom.TimeFissureEntity;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.SpectrumFATM.black_archive.network.messages.C2SChangeSonicMode;
import net.SpectrumFATM.black_archive.network.messages.C2SLockFunction;
import net.SpectrumFATM.black_archive.network.messages.C2SSetLocation;
import net.SpectrumFATM.black_archive.util.ScreenUtil;
import net.SpectrumFATM.black_archive.util.SpaceTimeEventUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.common.items.ScrewdriverMode;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.registry.TRSoundRegistry;

import java.util.Random;

public class SonicEngine {

    public static void blockActivate(UseOnContext context) {
        ScrewdriverItem item = (ScrewdriverItem) context.getItemInHand().getItem();
        if (BlackArchiveConfig.COMMON.enableSonicEngine.get()) {

                Player player = context.getPlayer();

                if (item.isScrewdriverMode(context.getItemInHand(), ScrewdriverMode.ENABLED) && !player.isCrouching()) {
                     if (getSonicSetting(context.getItemInHand()).equals("location")) {
                         locationSet(player, context);
                    } else {
                        defaultSet(context, item, player);
                    }

                } else if (player.isCrouching()) {
                    ScreenUtil.openSonicScreen(0);
                }
        }
        BlackArchive.LOGGER.info(String.valueOf(hasSonicSetting(context.getItemInHand())));
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

    public static void lock() {
        C2SLockFunction message = new C2SLockFunction();
        message.send();
    }

    public static void defaultSet(UseOnContext context, ScrewdriverItem item, Player player) {
        Level world = context.getLevel();
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
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

    public static void locationSet(Player player, UseOnContext context) {
        player.displayClientMessage(Component.translatable("item.sonic.locator"), true);
        C2SSetLocation message = new C2SSetLocation(context.getClickedPos());
        message.send();
        setSetting("block", false);
    }

    public static Vector3f getSonicItemVector(Item item) {
        if (Platform.isModLoaded("whocosmetics")) {
            if (item == WCItems.SONIC_10.get()) {
                return new Vector3f(0.2f, 0.2f, 1.0f);
            } else if (item == WCItems.SONIC_11.get()) {
                return new Vector3f(0.2f, 1.0f, 0.2f);
            } else if (item == WCItems.SONIC_12.get()) {
                return new Vector3f(0.0f, 0.0f, 1.0f);
            } else if (item == WCItems.SONIC_13.get()) {
                return new Vector3f(1.0f, 0.647f, 0.0f);
            } else if (item == WCItems.SONIC_14.get()) {
                return new Vector3f(0.5f, 0.5f, 1.0f);
            } else if (item == WCItems.SONIC_RIVER.get()) {
                return new Vector3f(1.0f, 0.2f, 0.2f);
            } else if (item == WCItems.SONIC_TROWEL.get()) {
                return new Vector3f(0.5f, 0.5f, 1.0f);
            }
        } else {
            if (item == ModItems.SONIC10.get()) {
                return new Vector3f(0.2f, 0.2f, 1.0f);
            } else if (item == ModItems.SONIC10.get()) {
                return new Vector3f(0.2f, 1.0f, 0.2f);
            } else if (item == ModItems.SONIC11.get()) {
                return new Vector3f(0.0f, 0.0f, 1.0f);
            } else if (item == ModItems.SONIC12.get()) {
                return new Vector3f(1.0f, 0.647f, 0.0f);
            } else if (item == ModItems.SONIC13.get()) {
                return new Vector3f(0.5f, 0.5f, 1.0f);
            } else if (item == ModItems.SONIC14.get()) {
                return new Vector3f(1.0f, 0.2f, 0.2f);
            }else if (item == ModItems.SONIC15.get()) {
                return new Vector3f(1.0f, 0.2f, 0.2f);
            }
        }
        return new Vector3f(0.2f, 0.2f, 1.0f);
    }

    public static void setSetting(String setting, boolean shouldDisplayChangeMessage) {
        C2SChangeSonicMode message = new C2SChangeSonicMode(setting, shouldDisplayChangeMessage);
        message.send();
    }

    // Method to retrieve the saved TARDIS level name from the item's NBT
    public static String getSonicSetting(ItemStack stack) {
        CompoundTag nbtData = stack.getTag();
        return nbtData != null && nbtData.contains("SonicSetting") ? nbtData.getString("SonicSetting") : "";
    }

    public static boolean hasSonicSetting(ItemStack stack) {
        CompoundTag nbtData = stack.getTag();
        return nbtData != null && nbtData.contains("SonicSetting");
    }

    public static String getSettingKey(String setting) {
        switch (setting) {
            case "location":
                return "item.sonic.locator_name";
            default:
                return "item.sonic.block_name";
        }
    }
}
