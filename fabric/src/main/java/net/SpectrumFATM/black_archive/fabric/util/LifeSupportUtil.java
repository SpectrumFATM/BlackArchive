package net.SpectrumFATM.black_archive.fabric.util;

import net.SpectrumFATM.black_archive.fabric.block.custom.DalekGravityGenBlock;
import net.SpectrumFATM.black_archive.fabric.block.custom.GravityGenBlock;
import net.SpectrumFATM.black_archive.fabric.block.custom.OxygenGenBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import whocraft.tardis_refined.registry.TRBlockRegistry;

public class LifeSupportUtil {

    public static boolean tardisNearby(Entity entity, int radius) {
        BlockPos entityPos = entity.getBlockPos();
        World world = entity.getWorld();

        for (BlockPos pos : BlockPos.iterate(entityPos.add(-radius, -radius, -radius), entityPos.add(radius, radius, radius))) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() == TRBlockRegistry.GLOBAL_SHELL_BLOCK.get() && isSolidPlatform(world, pos.down(), radius)) {
                return true;
            }
        }
        return false;
    }

    public static boolean gravityGenNearby(Entity entity) {
        return gravityGenNearby(entity, 33);
    }

    public static boolean gravityGenNearby(Entity entity, int searchRadius) {
        BlockPos entityPos = entity.getBlockPos();
        World world = entity.getWorld();

        boolean dalekGenFound = false;

        for (BlockPos generatorPos : BlockPos.iterate(entityPos.add(-searchRadius, -searchRadius, -searchRadius), entityPos.add(searchRadius, searchRadius, searchRadius))) {
            BlockState state = world.getBlockState(generatorPos);
            if (state.getBlock() instanceof DalekGravityGenBlock) {
                if (state.get(GravityGenBlock.POWERED)) {
                    dalekGenFound = true;
                }
            } else if (state.getBlock() instanceof GravityGenBlock) {
                if (state.get(GravityGenBlock.POWERED)) {
                    return true;
                }
            }
        }

        if (dalekGenFound) {
            return true;
        }

        return false;
    }

    public static boolean dalekGravityGenNearby(Entity entity, int searchRadiusXY, int searchRadiusZ) {
        BlockPos entityPos = entity.getBlockPos();
        World world = entity.getWorld();

        for (BlockPos generatorPos : BlockPos.iterate(entityPos.add(-searchRadiusXY, -searchRadiusZ, -searchRadiusXY), entityPos.add(searchRadiusXY, searchRadiusZ, searchRadiusXY))) {
            BlockState state = world.getBlockState(generatorPos);
            if (state.getBlock() instanceof DalekGravityGenBlock) {
                return true;
            }
        }
        return false;
    }

    public static boolean dalekGravityGenNearby(Entity entity, int searchRadius) {
        return dalekGravityGenNearby(entity, searchRadius, searchRadius);
    }

    public static boolean oxygenNearby(Entity entity, int radius) {
        BlockPos entityPos = entity.getBlockPos();
        World world = entity.getWorld();

        for (BlockPos pos : BlockPos.iterate(entityPos.add(-radius, -radius, -radius), entityPos.add(radius, radius, radius))) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof OxygenGenBlock) {
                if (state.get(OxygenGenBlock.POWERED)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSolidPlatform(World world, BlockPos pos, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                BlockPos checkPos = pos.add(x, 0, z);
                BlockState state = world.getBlockState(checkPos);
                if (!state.isSolidBlock(world, checkPos)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isInZeroGravityDimension(World world) {
        return world.getRegistryKey().getValue().toString().equals("black_archive:space");
    }
}