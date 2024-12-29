package net.SpectrumFATM.black_archive.item.custom;

import net.SpectrumFATM.BlackArchive;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.util.DimensionUtil;

import java.util.List;
import java.util.Optional;

public class DistressItem extends TooltipItem {
    public DistressItem(Properties settings, String tooltip) {
        super(settings, tooltip);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            // Check if the player is in a level beginning with "tardis:"
            String levelName = world.dimension().location().toString();

            if (levelName.startsWith("tardis_refined:")) {
                // Get the ItemStack in hand
                ItemStack itemStack = player.getItemInHand(hand);

                if (!hasTardisLevelName(itemStack)) {
                    // Save the TARDIS level name to the item's NBT
                    setTardisLevelName(itemStack, levelName);
                    player.displayClientMessage(Component.translatable("item.superphone.saved"), true);
                } else {
                    player.displayClientMessage(Component.translatable("item.superphone.error").withStyle(ChatFormatting.RED), true);
                }

                return InteractionResultHolder.success(itemStack);
            } else {
                // Get level from saved NBT
                Level tardisWorld = getWorldFromNBT(player.getServer(), player.getItemInHand(hand));

                if (tardisWorld != null) {
                    if (DimensionUtil.isAllowedDimension(player.level().dimension())) {
                        Optional<TardisLevelOperator> operator = TardisLevelOperator.get((ServerLevel) tardisWorld);
                        operator.get().getPilotingManager().setTargetLocation(new TardisNavLocation(player.blockPosition(), player.getDirection(), (ServerLevel) player.level()));
                        player.displayClientMessage(Component.translatable("item.superphone.sent"), true);

                        List<? extends Player> players = tardisWorld.players();

                        for (Player tardisPlayer : players) {
                            tardisPlayer.displayClientMessage(Component.translatable("item.superphone.distress").withStyle(ChatFormatting.RED), true);
                        }
                    } else {
                        player.displayClientMessage(Component.translatable("item.superphone.error2").withStyle(ChatFormatting.RED), true);
                    }
                }
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    // Method to set the TARDIS level name in the item's NBT
    public static void setTardisLevelName(ItemStack stack, String levelName) {
        CompoundTag nbtData = stack.getOrCreateTag(); // Access item's NBT
        nbtData.putString("TardisLevelName", levelName);
    }

    public static boolean hasTardisLevelName(ItemStack stack) {
        CompoundTag nbtData = stack.getTag();
        return nbtData != null && nbtData.contains("TardisLevelName");
    }

    // Method to retrieve the saved TARDIS level name from the item's NBT
    public static String getTardisLevelName(ItemStack stack) {
        CompoundTag nbtData = stack.getTag();
        return nbtData != null && nbtData.contains("TardisLevelName") ? nbtData.getString("TardisLevelName") : "";
    }

    public static Level getWorldFromNBT(MinecraftServer server, ItemStack stack) {
        String worldIdentifier = getTardisLevelName(stack);
        if (!worldIdentifier.isEmpty()) {
            ResourceKey<Level> worldKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(worldIdentifier));
            return server.getLevel(worldKey);
        }
        return null;
    }
}