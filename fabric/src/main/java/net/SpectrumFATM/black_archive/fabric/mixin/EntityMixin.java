package net.SpectrumFATM.black_archive.fabric.mixin;

import net.SpectrumFATM.black_archive.fabric.block.custom.DalekGravityGenBlock;
import net.SpectrumFATM.black_archive.fabric.block.custom.GravityGenBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.registry.TRBlockRegistry;

@Mixin(Entity.class)
public abstract class EntityMixin {

    private double prevMotionX;
    private double prevMotionY;
    private double prevMotionZ;
    private boolean shouldSuffocate = false;
    private int air = 300;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        Entity entity = (Entity) (Object) this;

        applyZeroGravity(entity);
        suffocate(entity);
    }

    private void applyZeroGravity(Entity entity) {

        World world = entity.getWorld();

        // Skip if not in a custom zero-gravity dimension (replace with your dimension check).
        if (!isInZeroGravityDimension(world)) {
            return;
        } else {
            shouldSuffocate = true;
        }

        // Skip if TARDIS is nearby.
        if (tardisNearby(entity, 3)) {
            shouldSuffocate = false;
            return;
        }

        // Skip if powered gravity generator is nearby.
        if (gravityGenNearby(entity)) {
            return;
        }

        // Skip if entity is on the ground.
        if (!isInFreefall(entity)) {
            return;
        }

        // Handle freefall motion.
        handleFreefallMotion(entity);

        this.prevMotionX = entity.getVelocity().x;
        this.prevMotionY = entity.getVelocity().y;
        this.prevMotionZ = entity.getVelocity().z;
    }

    private boolean isInZeroGravityDimension(World world) {
        // Replace with actual logic to determine if the dimension is zero gravity.
        return world.getRegistryKey().getValue().toString().equals("black_archive:space");
    }

    private boolean isInFreefall(Entity entity) {
        Box boundingBox = entity.getBoundingBox();
        BlockPos minPos = new BlockPos(MathHelper.floor(boundingBox.minX), MathHelper.floor(boundingBox.minY - 0.01D), MathHelper.floor(boundingBox.minZ));
        BlockPos maxPos = new BlockPos(MathHelper.floor(boundingBox.maxX), MathHelper.floor(boundingBox.maxY), MathHelper.floor(boundingBox.maxZ));

        for (int x = minPos.getX(); x <= maxPos.getX(); x++) {
            for (int y = minPos.getY(); y <= maxPos.getY(); y++) {
                for (int z = minPos.getZ(); z <= maxPos.getZ(); z++) {
                    BlockState state = entity.getWorld().getBlockState(new BlockPos(x, y, z));
                    Block block = state.getBlock();
                    if (block != Blocks.AIR && block != Blocks.WATER && block != Blocks.LAVA) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void handleFreefallMotion(Entity entity) {
        // Reverse drag. Doing it before everything makes the math easier.
        // Minecraft applies a small drag by multiplying the motion by 0.91. If we divide the motion by 0.91, the velocity is back to where it should be.
        entity.setVelocity(
                entity.getVelocity().getX() / 0.91F,
                entity.getVelocity().y / 0.9800000190734863D,
                entity.getVelocity().z / 0.91F
        ); // I have no clue why Galacticraft reversed vertical drag using the above number, but they must be right.

        // Get difference between velocity last frame and this frame.
        // In physics terms, this is the acceleration between frames.
        double deltaX = entity.getVelocity().x - this.prevMotionX;
        double deltaY = entity.getVelocity().y - this.prevMotionY;
        double deltaZ = entity.getVelocity().z - this.prevMotionZ;

        // If the acceleration in the Y direction is equal to gravity, set it to zero.
        // I worry that this might cause any slight vertical acceleration to cause the player to drop.
        if (deltaY == -0.08) { // Minecraft accelerates entities by 0.08 blocks per tick per tick. This means that deltaY changes by 0.08 each tick.
            deltaY = 0; // We're in space, so accelerate by 0 blocks per tick per tick.
        }

        // Give the updated velocity back to the entity.
        entity.setVelocity(
                this.prevMotionX + deltaX,
                this.prevMotionY + deltaY,
                this.prevMotionZ + deltaZ
        );

        // Limit speed to avoid infinite acceleration. This is just so the player doesn't fly out of loaded chunks uncontrollably.
        float speedLimit = 0.1f;
        entity.setVelocity(
                Math.max(Math.min(entity.getVelocity().x, speedLimit), -speedLimit),
                Math.max(Math.min(entity.getVelocity().y, speedLimit), -speedLimit),
                Math.max(Math.min(entity.getVelocity().z, speedLimit), -speedLimit)
        );
    }

    private void suffocate(Entity entity) {
        // Check if the entity should suffocate.
        if (shouldSuffocate && entity instanceof PlayerEntity player) {
            if (!player.isCreative() && !player.isSpectator()) {
                // Only decrement air once per tick.
                if (air > -20) {
                    air--;
                    player.setAir(air);
                } else {
                    player.damage(entity.getDamageSources().generic(), 1.0f);
                    air = -20; // Ensure air doesn't go further below the threshold.
                }
            }
        } else {
            if (air < 300) {
                air = Math.min(air + 1, 300);
                if (entity instanceof PlayerEntity player) {
                    player.setAir(air);
                }
            }
        }
    }

    private boolean tardisNearby(Entity entity, int radius) {
        BlockPos entityPos = entity.getBlockPos();

        for (int x = entityPos.getX() - radius; x <= entityPos.getX() + radius; x++) {
            for (int y = entityPos.getY() - radius; y <= entityPos.getY() + radius; y++) {
                for (int z = entityPos.getZ() - radius; z <= entityPos.getZ() + radius; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = entity.getWorld().getBlockState(pos);
                    if (state.getBlock() == TRBlockRegistry.GLOBAL_SHELL_BLOCK.get() && isSolidPlatform(entity.getWorld(), pos.down(), radius)) {
                        return true;// TARDIS is nearby and on a solid platform.
                    }
                }
            }
        }
        return false; // TARDIS is not nearby or not on a solid platform.
    }

    private boolean gravityGenNearby(Entity entity) {
        BlockPos entityPos = entity.getBlockPos();
        World world = entity.getWorld();
        int searchRadius = 33;

        for (BlockPos pos : BlockPos.iterate(entityPos.add(-searchRadius, -searchRadius, -searchRadius), entityPos.add(searchRadius, searchRadius, searchRadius))) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof DalekGravityGenBlock) {
                for (BlockPos generatorPos : BlockPos.iterate(entityPos.add(-searchRadius, -18, -searchRadius), entityPos.add(searchRadius, 18, searchRadius))) {
                    if (state.getBlock() instanceof DalekGravityGenBlock) {
                        if (state.get(GravityGenBlock.POWERED)) {
                            shouldSuffocate = false;
                            return true;
                        }
                    }
                }
            } else if (state.getBlock() instanceof GravityGenBlock) {
                searchRadius = 8;
                for (BlockPos generatorPos : BlockPos.iterate(entityPos.add(-searchRadius, -searchRadius, -searchRadius), entityPos.add(searchRadius, searchRadius, searchRadius))) {
                    if (state.get(GravityGenBlock.POWERED)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isSolidPlatform(World world, BlockPos pos, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                BlockPos checkPos = pos.add(x, 0, z);
                BlockState state = world.getBlockState(checkPos);
                if (!state.isSolidBlock(world, checkPos)) {
                    return false; // Not a solid block.
                }
            }
        }
        return true; // All blocks in the 3x3 area are solid.
    }
}