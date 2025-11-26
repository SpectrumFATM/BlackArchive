package net.SpectrumFATM.black_archive.ad_astra_compat.util;

import net.minecraft.core.BlockPos;

import java.util.Set;

public class AATools {
    public static Set<BlockPos> getPositionsInRadius(BlockPos center, int radiusXY, int radiusZ) {
        Set<BlockPos> positions = new java.util.HashSet<>();
        int cx = center.getX();
        int cy = center.getY();
        int cz = center.getZ();

        for (int dx = -radiusXY; dx <= radiusXY; dx++) {
            int x = cx + dx;
            int dxSq = dx * dx;
            for (int dy = -radiusXY; dy <= radiusXY; dy++) {
                int y = cy + dy;
                int dySq = dy * dy;
                if (dxSq + dySq <= radiusXY * radiusXY) {
                    for (int dz = -radiusZ; dz <= radiusZ; dz++) {
                        int z = cz + dz;
                        if (dz * dz <= radiusZ * radiusZ) {
                            positions.add(new BlockPos(x, y, z));
                        }
                    }
                }
            }
        }
        return positions;
    }
}
