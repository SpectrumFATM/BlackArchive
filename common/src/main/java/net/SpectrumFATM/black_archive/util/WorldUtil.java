package net.SpectrumFATM.black_archive.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;

public class WorldUtil {

    public static BlockPos findSafeLandingPos(ServerLevel world, double x, double y, double z) {
        int newY = (int) y;
        boolean foundSafePosition = false;

        BlockPos targetPos = new BlockPos((int) x, newY, (int) z);

        // Check if the target position is in mid-air
        if (world.getBlockState(targetPos).isAir() && world.getBlockState(targetPos.below()).isAir()) {
            // Search downwards for the nearest ground position
            while (newY > -64) {
                if (world.getBlockState(targetPos).isRedstoneConductor(world, targetPos)) {
                    foundSafePosition = true;
                    newY++; // Move to the first air block above the ground
                    break;
                }
                newY--;
                targetPos = targetPos.below();
            }
        } else {
            // Search upwards for the nearest safe position
            if (world.getBlockState(targetPos).isRedstoneConductor(world, targetPos)) {
                while (newY < world.getHeight()) {
                    if (world.getBlockState(targetPos).isAir() && world.getBlockState(targetPos.above()).isAir()) {
                        foundSafePosition = true;
                        break;
                    }
                    newY++;
                    targetPos = targetPos.above();
                }
            } else {
                foundSafePosition = true; // Current position is already safe
            }
        }

        return foundSafePosition ? new BlockPos((int) x, newY, (int) z) : null;
    }

    public static boolean isTardisesInRange(ServerLevel world, BlockPos pos, int radius, int maxCount) {

        int count = 0;

        for (BlockPos pos1 : BlockPos.betweenClosed(pos.offset(-radius, -radius, -radius), pos.offset(radius, radius, radius))) {
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