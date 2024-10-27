package net.SpectrumFATM.black_archive.fabric.item.custom;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.network.TardisWarningPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.nbt.NbtCompound;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.util.DimensionUtil;

import java.util.List;
import java.util.Optional;

public class DistressItem extends TooltipItem {
    public DistressItem(Settings settings, String tooltip) {
        super(settings, tooltip);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            // Check if the player is in a level beginning with "tardis:"
            String levelName = world.getRegistryKey().getValue().toString();

            if (levelName.startsWith("tardis_refined:")) {
                // Get the ItemStack in hand
                ItemStack itemStack = player.getStackInHand(hand);

                BlackArchive.LOGGER.info(String.valueOf(hasTardisLevelName(itemStack)));

                if (!hasTardisLevelName(itemStack)) {
                    // Save the TARDIS level name to the item's NBT
                    setTardisLevelName(itemStack, levelName);
                    player.sendMessage(Text.translatable("item.superphone.saved"), true);
                } else {
                    player.sendMessage(Text.translatable("item.superphone.error").formatted(Formatting.RED), true);
                }

                return TypedActionResult.success(itemStack);
            } else {
                // Get level from saved NBT
                World tardisWorld = getWorldFromNBT(player.getServer(), player.getStackInHand(hand));

                if (tardisWorld != null) {
                    if (DimensionUtil.isAllowedDimension(player.getWorld().getRegistryKey())) {
                        Optional<TardisLevelOperator> operator = TardisLevelOperator.get((ServerWorld) tardisWorld);
                        operator.get().getPilotingManager().setTargetLocation(new TardisNavLocation(player.getBlockPos(), player.getHorizontalFacing(), (ServerWorld) player.getWorld()));
                        player.sendMessage(Text.translatable("item.superphone.sent"), true);

                        List<? extends PlayerEntity> players = tardisWorld.getPlayers();

                        for (PlayerEntity tardisPlayer : players) {
                            tardisPlayer.sendMessage(Text.translatable("item.superphone.distress").formatted(Formatting.RED), true);
                            TardisWarningPacket.sendToClient((ServerPlayerEntity) tardisPlayer);
                        }
                    } else {
                        player.sendMessage(Text.translatable("item.superphone.error2").formatted(Formatting.RED), true);
                    }
                }
            }
        }
        return TypedActionResult.success(player.getStackInHand(hand));
    }

    // Method to set the TARDIS level name in the item's NBT
    public static void setTardisLevelName(ItemStack stack, String levelName) {
        NbtCompound nbtData = stack.getOrCreateNbt(); // Access item's NBT
        nbtData.putString("TardisLevelName", levelName);
    }

    public static boolean hasTardisLevelName(ItemStack stack) {
        NbtCompound nbtData = stack.getNbt();
        return nbtData != null && nbtData.contains("TardisLevelName");
    }

    // Method to retrieve the saved TARDIS level name from the item's NBT
    public static String getTardisLevelName(ItemStack stack) {
        NbtCompound nbtData = stack.getNbt();
        return nbtData != null && nbtData.contains("TardisLevelName") ? nbtData.getString("TardisLevelName") : "";
    }

    public static World getWorldFromNBT(MinecraftServer server, ItemStack stack) {
        String worldIdentifier = getTardisLevelName(stack);
        if (!worldIdentifier.isEmpty()) {
            RegistryKey<World> worldKey = RegistryKey.of(RegistryKeys.WORLD, new Identifier(worldIdentifier));
            return server.getWorld(worldKey);
        }
        return null;
    }
}