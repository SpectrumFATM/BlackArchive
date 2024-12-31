package net.SpectrumFATM.black_archive.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TARDISBindUtil {
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
