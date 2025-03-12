package net.SpectrumFATM.black_archive.util;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.entity.custom.ShipEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.joml.Vector3d;

public class ShipUtil {
    public static ResourceLocation getShipStructureExterior(String type) {
        switch (type) {
            default -> {
                return new ResourceLocation(BlackArchive.MOD_ID, "sontaran_pod");
            }
        }
    }

    public static ResourceLocation getShipStructureInterior(String type) {
        switch (type) {
            default -> {
                return new ResourceLocation(BlackArchive.MOD_ID, "sontaran_ship");
            }
        }
    }

    public static ServerLevel getServerLevelFromDimension(ShipEntity entity, Player player) {
        ResourceLocation location = new ResourceLocation(BlackArchive.MOD_ID, entity.getStringUUID());
        ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, location);
        ServerLevel level = player.getServer().getLevel(key);
        return level;
    }

    public static ServerLevel getServerLevelFromInteriorDoorDimension(Player player, String levelString) {
        ResourceLocation location = new ResourceLocation(levelString);
        ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, location);
        ServerLevel level = player.getServer().getLevel(key);
        return level;
    }

    public static BlockPos calcuateInteriorOffset(BlockPos pos, String type) {
        switch (type) {
            case ("sontaran") -> {
                return pos.offset(-6, 127, -5);
            }
            default -> {
                return pos;
            }
        }
    }

    public static BlockPos calculateInteriorDoorPosition(String type) {
        switch (type) {
            case ("sontaran") -> {
                return new BlockPos(0, 128, -4);
            }
            default -> {
                return BlockPos.ZERO;
            }
        }
    }

    public static Vector3d calculateExteriorOffset(String type) {
        switch (type) {
            case ("sontaran") -> {
                return new Vector3d(-2.5, 0, -2.5);
            }
            default -> {
                return new Vector3d(0, 0, 0);
            }
        }
    }
}
