package net.SpectrumFATM.black_archive.util;

import net.SpectrumFATM.black_archive.ad_astra_compat.blocks.DalekLifeSupport;
import net.SpectrumFATM.black_archive.block.custom.DalekGravityGenBlock;
import net.SpectrumFATM.black_archive.block.custom.GravityGenBlock;
import net.SpectrumFATM.black_archive.block.custom.OxygenGenBlock;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.effects.ModEffects;
import net.SpectrumFATM.black_archive.entity.custom.CybermanEntity;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;

import java.util.Random;

public class LifeSupportUtil {

    public static boolean tardisNearby(Entity entity) {
        BlockPos entityPos = entity.blockPosition();
        Level world = entity.level();
        int radius = BlackArchiveConfig.COMMON.tardisLifeSupportRange.get();

        for (BlockPos pos : BlockPos.betweenClosed(entityPos.offset(-radius, -radius, -radius), entityPos.offset(radius, radius, radius))) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof ShellBaseBlock && isSolidPlatform(world, pos, radius)) {
                return true;
            }
        }
        return false;
    }

    public static boolean gravityGenNearby(Entity entity) {
        return gravityGenNearby(entity, 33);
    }

    public static boolean gravityGenNearby(Entity entity, int searchRadius) {
        BlockPos entityPos = entity.blockPosition();
        Level world = entity.level();

        boolean dalekGenFound = false;

        if (entity instanceof CybermanEntity) {
            for (BlockPos pos : BlockPos.betweenClosed(entityPos.offset(0, -5, 0), entityPos.offset(0, 0, 0))) {
                BlockState state = world.getBlockState(pos);
                if (!state.isAir() && !state.liquid()) {
                    return true;
                }
            }
        }

        for (BlockPos generatorPos : BlockPos.betweenClosed(entityPos.offset(-searchRadius, -searchRadius, -searchRadius), entityPos.offset(searchRadius, searchRadius, searchRadius))) {
            BlockState state = world.getBlockState(generatorPos);
            if (state.getBlock() instanceof DalekGravityGenBlock && state.getBlock() instanceof DalekGravityGenBlock) {
                if (state.getValue(GravityGenBlock.POWERED)) {
                    dalekGenFound = true;
                }
            } else if (state.getBlock() instanceof GravityGenBlock) {
                searchRadius = BlackArchiveConfig.COMMON.gravityFieldRange.get(); // Set the search radius to 8 for GravityGenBlock
                for (BlockPos gravPos: BlockPos.betweenClosed(entityPos.offset(-searchRadius, -searchRadius, -searchRadius), entityPos.offset(searchRadius, searchRadius, searchRadius))) {
                    state = world.getBlockState(gravPos);
                    if (state.getBlock() instanceof GravityGenBlock && state.getValue(GravityGenBlock.POWERED)) {
                        return true;
                    }
                }
            }
        }

        if (dalekGenFound) {
            return true;
        }

        return false;
    }

    public static boolean dalekGravityGenNearby(Entity entity, int searchRadiusXY, int searchRadiusZ) {
        BlockPos entityPos = entity.blockPosition();
        Level world = entity.level();

        for (BlockPos generatorPos : BlockPos.betweenClosed(entityPos.offset(-searchRadiusXY, -searchRadiusZ, -searchRadiusXY), entityPos.offset(searchRadiusXY, searchRadiusZ, searchRadiusXY))) {
            BlockState state = world.getBlockState(generatorPos);
            if (state.getBlock() instanceof DalekGravityGenBlock && state.getValue(DalekGravityGenBlock.POWERED) || state.getBlock() instanceof DalekLifeSupport && state.getValue(DalekLifeSupport.POWERED)) {
                Random random = new Random();
                if (random.nextInt(100) < 10 && entity instanceof Player player) {
                    if (!player.getInventory().contains(new ItemStack(ModItems.DALEK_BRACELET.get()))) {
                        applyInfinitePotionEffect(player);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean dalekGravityGenNearby(Entity entity, int searchRadius) {
        return dalekGravityGenNearby(entity, searchRadius, searchRadius);
    }

    public static boolean oxygenNearby(Entity entity, int radius) {
        BlockPos entityPos = entity.blockPosition();
        Level world = entity.level();

        for (BlockPos pos : BlockPos.betweenClosed(entityPos.offset(-radius, -radius, -radius), entityPos.offset(radius, radius, radius))) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof OxygenGenBlock) {
                if (state.getValue(OxygenGenBlock.POWERED)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSolidPlatform(Level world, BlockPos pos, int radius) {
        for (BlockPos platformPos : BlockPos.betweenClosed(pos.offset(-radius, -1, -radius), pos.offset(radius, -1, radius))) {
            BlockState state = world.getBlockState(platformPos);
            if (state.isAir() || state.liquid()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInZeroGravityDimension(Level world) {
        return world.dimension().location().toString().equals("black_archive:space");
    }

    public static void applyInfinitePotionEffect(Player player) {
        MobEffectInstance infiniteEffect = new MobEffectInstance(
                ModEffects.DALEK_NANOCLOUD.get(),
                -1,
                0,
                false,
                false,
                true
        );

        // Apply the effect to the player
        player.addEffect(infiniteEffect);
    }
}