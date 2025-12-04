package net.SpectrumFATM.black_archive.util;

import dev.jeryn.doctorwho.common.WCItems;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.entity.custom.*;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.SpectrumFATM.black_archive.network.messages.sonic.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import org.joml.Random;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.common.items.ScrewdriverMode;
import whocraft.tardis_refined.registry.TRSoundRegistry;

public class SonicEngine {

    private static final int COOLDOWN_TIME = 40;

    public static void miscUse(Level level, Player player, InteractionHand hand) {
        if (!BlackArchiveConfig.COMMON.enableSonicEngine.get()) return;

        ItemStack stack = player.getItemInHand(hand);
        ScrewdriverItem item = (ScrewdriverItem) stack.getItem();

        if (item.isScrewdriverMode(stack, ScrewdriverMode.ENABLED)) {
            if (player.isCrouching()) {
                ScreenUtil.openSonicScreen(0);
            } else {
                handleSonicSetting(level, player, stack);
            }
        }
    }

    private static void handleSonicSetting(Level level, Player player, ItemStack stack) {
        String setting = getSonicSetting(stack);
        switch (setting) {
            case "lock" -> {
                if (level.isClientSide()) lockSet();
            }
            case "homing" -> homingSet(player);
        }
    }

    public static void blockActivate(UseOnContext context) {
        if (!BlackArchiveConfig.COMMON.enableSonicEngine.get()) return;

        ScrewdriverItem item = (ScrewdriverItem) context.getItemInHand().getItem();
        Player player = context.getPlayer();
        if (item.isScrewdriverMode(context.getItemInHand(), ScrewdriverMode.ENABLED) && !player.isCrouching()) {
            handleBlockSetting(context, player);
        }
    }

    private static void handleBlockSetting(UseOnContext context, Player player) {
        String setting = getSonicSetting(context.getItemInHand());
        if (setting.equals("location")) {
            locationSet(player, context);
        } else {
            modifyBlockState(context, player);
        }
    }

    private static void modifyBlockState(UseOnContext context, Player player) {
        Level world = context.getLevel();
        BlockState state = world.getBlockState(context.getClickedPos());
        ItemStack stack = context.getItemInHand();

        if (world instanceof ServerLevel serverWorld && !world.isClientSide) {
            if (state.hasProperty(BlockStateProperties.POWERED) && !(state.getBlock() instanceof TrapDoorBlock || state.getBlock() instanceof DoorBlock) ) {
                cycleBlockState(serverWorld, state, context, player, stack, BlockStateProperties.POWERED);
            } else if (state.hasProperty(BlockStateProperties.LIT)) {
                cycleBlockState(serverWorld, state, context, player, stack, BlockStateProperties.LIT);
            } else if (state.hasProperty(BlockStateProperties.OPEN)) {
                cycleBlockState(serverWorld, state, context, player, stack, BlockStateProperties.OPEN);
            } else if (state.getBlock() instanceof TntBlock) {
                handleTNTBlock(serverWorld, context, player, stack);
            } else if (state.getBlock() instanceof IceBlock || state.getBlock() == Blocks.BLUE_ICE || state.getBlock() == Blocks.PACKED_ICE) {
                world.setBlock(context.getClickedPos(), Blocks.WATER.defaultBlockState(), Block.UPDATE_ALL);
            } else if (state.getBlock() instanceof GlassBlock || state.getBlock() instanceof StainedGlassBlock || state.getBlock() instanceof StainedGlassPaneBlock || state.getBlock() instanceof TintedGlassBlock || state.getBlock() == Blocks.GLASS_PANE) {
                destroyBlock(serverWorld, context, player, stack);
            } else if (state.getBlock() instanceof PowderSnowBlock) {
                world.setBlock(context.getClickedPos(), Blocks.SNOW_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
            } else if (state.getBlock() instanceof DoorBlock) {
                cycleBlockState(serverWorld, state, context, player, stack, BlockStateProperties.OPEN);
            } else if (state.getBlock() instanceof TrapDoorBlock) {
                cycleBlockState(serverWorld, state, context, player, stack, BlockStateProperties.OPEN);
            }
        }
    }

    private static void cycleBlockState(ServerLevel world, BlockState state, UseOnContext context, Player player, ItemStack stack, Property<?> property) {
        world.setBlock(context.getClickedPos(), state.cycle(property), Block.UPDATE_ALL);
        playSoundAndCooldown(world, player, stack, context.getClickedPos());
    }

    private static void handleTNTBlock(ServerLevel world, UseOnContext context, Player player, ItemStack stack) {
        world.removeBlock(context.getClickedPos(), false);
        TntBlock.explode(world, context.getClickedPos());
        playSoundAndCooldown(world, player, stack, context.getClickedPos());
    }

    private static void destroyBlock(ServerLevel world, UseOnContext context, Player player, ItemStack stack) {
        world.destroyBlock(context.getClickedPos(), true);
        playSoundAndCooldown(world, player, stack, context.getClickedPos());
    }

    public static void entityActivate(ItemStack stack, Player user, LivingEntity entity) {
        if (!BlackArchiveConfig.COMMON.enableSonicEngine.get() || !getSonicSetting(stack).equals("block")) return;

        ScrewdriverItem item = (ScrewdriverItem) stack.getItem();
        if (!(user.level() instanceof ServerLevel serverWorld) || !item.isScrewdriverMode(stack, ScrewdriverMode.ENABLED)) return;

        handleEntityInteractions(serverWorld, user, entity, item);
    }

    private static void handleEntityInteractions(ServerLevel world, Player user, LivingEntity entity, ScrewdriverItem item) {
        if (!user.isShiftKeyDown()) {
            if (entity instanceof Creeper creeper) {
                creeper.setSwellDir(0);
                creeper.ignite();
            } else if (entity instanceof Skeleton || entity instanceof WitherSkeleton || entity instanceof Blaze) {
                entity.kill();
            } else if (entity instanceof CybermanEntity cyberman) {
                cyberman.disableFire(100);
            } else if (entity instanceof TimeFissureEntity timeFissure) {
                Random random = new Random();
                if (random.nextInt(10) == 1) {
                    timeFissure.aggrovate();
                } else {
                    timeFissure.close();
                }
            } else {
                scanEntity(user, entity);
            }
        }

        item.playScrewdriverSound(world, user.blockPosition(), TRSoundRegistry.SCREWDRIVER_SHORT.get());
    }

    private static void scanEntity(Player user, LivingEntity entity) {
        ChatFormatting colorFormat = getSonicChatFormatting(user.getMainHandItem().getItem());

        String entityName = entity.getName().getString();
        int xPos = entity.getBlockX();
        int yPos = entity.getBlockY();
        int zPos = entity.getBlockZ();
        double health = entity.getHealth();

        user.displayClientMessage(Component.translatable("sonic.title.scan")
                .withStyle(colorFormat, ChatFormatting.BOLD, ChatFormatting.UNDERLINE), false);
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

    public static void cooldown(Player player, ItemStack stack) {
        if (!player.isCreative()) {
            player.getCooldowns().addCooldown(stack.getItem(), COOLDOWN_TIME);
        }
    }

    public static void lockSet() {
        new C2SLockFunction().send();
    }

    public static void homingSet(Player player) {
        player.displayClientMessage(Component.translatable("item.sonic.homing"), true);
        new C2SHomeFunction().send();
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
                return new Vector3f(0.2f, 1.0f, 0.2f);
            } else if (item == ModItems.SONIC11.get()) {
                return new Vector3f(0.2f, 1.0f, 0.2f);
            } else if (item == ModItems.SONIC12.get()) {
                return new Vector3f(0.0f, 0.0f, 1.0f);
            } else if (item == ModItems.SONIC13.get()) {
                return new Vector3f(1.0f, 0.647f, 0.0f);
            } else if (item == ModItems.SONIC14.get()) {
                return new Vector3f(0.2f, 1.0f, 0.2f);
            }else if (item == ModItems.SONIC15.get()) {
                return new Vector3f(0.2f, 1.0f, 0.2f);
            } else if (item == ModItems.SONIC_WAR.get()) {
                return new Vector3f(1f, 0f, 0f);
            }
        }
        return new Vector3f(0.2f, 0.2f, 1.0f);
    }

    public static ChatFormatting getSonicChatFormatting(Item item) {
        if (Platform.isModLoaded("whocosmetics")) {
            if (item == WCItems.SONIC_10.get()) {
                return ChatFormatting.BLUE;
            } else if (item == WCItems.SONIC_11.get()) {
                return ChatFormatting.GREEN;
            } else if (item == WCItems.SONIC_12.get()) {
                return ChatFormatting.BLUE;
            } else if (item == WCItems.SONIC_13.get()) {
                return ChatFormatting.GOLD;
            } else if (item == WCItems.SONIC_14.get()) {
                return ChatFormatting.BLUE;
            } else if (item == WCItems.SONIC_RIVER.get()) {
                return ChatFormatting.RED;
            } else if (item == WCItems.SONIC_TROWEL.get()) {
                return ChatFormatting.BLUE;
            }
        } else {
            if (item == ModItems.SONIC10.get()) {
                return ChatFormatting.BLUE;
            } else if (item == ModItems.SONIC11.get()) {
                return ChatFormatting.GREEN;
            } else if (item == ModItems.SONIC12.get()) {
                return ChatFormatting.BLUE;
            } else if (item == ModItems.SONIC13.get()) {
                return ChatFormatting.GOLD;
            } else if (item == ModItems.SONIC14.get()) {
                return ChatFormatting.BLUE;
            }else if (item == ModItems.SONIC15.get()) {
                return ChatFormatting.BLUE;
            }
        }
        return ChatFormatting.BLUE;
    }

    public static void setSetting(String setting, boolean shouldDisplayChangeMessage) {
        C2SChangeSonicMode message = new C2SChangeSonicMode(setting, shouldDisplayChangeMessage);
        message.send();
    }

    // Method to retrieve the saved TARDIS level name from the item's NBT
    public static String getSonicSetting(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        return nbt != null && nbt.contains("SonicSetting") ? nbt.getString("SonicSetting") : "";
    }

    private static void playSoundAndCooldown(ServerLevel world, Player player, ItemStack stack, BlockPos pos) {
        ScrewdriverItem item = (ScrewdriverItem) stack.getItem();
        item.playScrewdriverSound(world, pos, TRSoundRegistry.SCREWDRIVER_SHORT.get());
        cooldown(player, stack);
    }

    public static String getSettingKey(String setting) {
        return switch (setting) {
            case "location" -> "item.sonic.locator_name";
            case "homing" -> "item.sonic.homing_name";
            case "lock" -> "item.sonic.lock_name";
            case "block" -> "item.sonic.block_name";
            default -> "null";
        };
    }
}