package net.SpectrumFATM.black_archive.fabric.util;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;

public class WorldUtil {

    public static BlockPos findSafeLandingPos(ServerWorld world, double x, double y, double z) {
        int newY = (int) y;
        boolean foundSafePosition = false;

        BlockPos targetPos = new BlockPos((int) x, newY, (int) z);

        // Check if the target position is in mid-air
        if (world.getBlockState(targetPos).isAir() && world.getBlockState(targetPos.down()).isAir()) {
            // Search downwards for the nearest ground position
            while (newY > -64) {
                if (world.getBlockState(targetPos).isSolidBlock(world, targetPos)) {
                    foundSafePosition = true;
                    newY++; // Move to the first air block above the ground
                    break;
                }
                newY--;
                targetPos = targetPos.down();
            }
        } else {
            // Search upwards for the nearest safe position
            if (world.getBlockState(targetPos).isSolidBlock(world, targetPos)) {
                while (newY < world.getHeight()) {
                    if (world.getBlockState(targetPos).isAir() && world.getBlockState(targetPos.up()).isAir()) {
                        foundSafePosition = true;
                        break;
                    }
                    newY++;
                    targetPos = targetPos.up();
                }
            } else {
                foundSafePosition = true; // Current position is already safe
            }
        }

        return foundSafePosition ? new BlockPos((int) x, newY, (int) z) : null;
    }

    public static boolean isTardisesInRange(ServerWorld world, BlockPos pos, int radius, int maxCount) {

        int count = 0;

        for (BlockPos pos1 : BlockPos.iterate(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
            BlockState state = world.getBlockState(pos1);
            if (state.getBlock() instanceof ShellBaseBlock) {
                count++;
            }
        }

        if (count >= maxCount) {
            return true;
        }

        return false;
    }
}