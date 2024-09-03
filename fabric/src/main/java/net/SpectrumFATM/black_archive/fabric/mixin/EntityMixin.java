package net.SpectrumFATM.black_archive.fabric.mixin;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    private double prevMotionX;
    private double prevMotionY;
    private double prevMotionZ;

    private boolean wasOnGround;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        applyZeroGravity();
    }

    private void applyZeroGravity() {
        Entity entity = (Entity) (Object) this;
        World world = entity.getWorld();

        // Skip if not in a custom zero-gravity dimension (replace with your dimension check)
        if (!isInZeroGravityDimension(world)) {
            return;
        }

        BlackArchive.LOGGER.info(String.valueOf(entity.getVelocity().y - this.prevMotionY));
        BlackArchive.LOGGER.info(String.valueOf(entity.getVelocity().y));
        BlackArchive.LOGGER.info(String.valueOf(this.prevMotionY));

        // Skip if entity is on the ground
        if (!isInFreefall(entity)) {
            //update previous motion, otherwise it could be invalid after the player steps off the ground
            this.prevMotionX = 0;
            this.prevMotionY = 0;
            this.prevMotionZ = 0;
            return;
        }


        // Handle freefall motion
        handleFreefallMotion(entity);

        this.prevMotionX = entity.getVelocity().x;
        this.prevMotionY = entity.getVelocity().y;
        this.prevMotionZ = entity.getVelocity().z;
    }

    private boolean isInZeroGravityDimension(World world) {
        // Replace with actual logic to determine if the dimension is zero gravity
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
        //reverse drag. doing it before everything makes the math easier.
        //minecraft applies a small drag by multiplying the motion by 0.91. if we divide the motion by 0.91, the velocity is back to where it should be
        entity.setVelocity(
                entity.getVelocity().getX() / 0.91F,
                entity.getVelocity().y / 0.9800000190734863D,
                entity.getVelocity().z / 0.91F
        );//i have no clue why galacticraft reversed vertical drag using the above number, but they must be right

        // get difference between velocity last frame and this frame.
        // In physics terms, this is the acceleration between frames.
        double deltaX = entity.getVelocity().x - this.prevMotionX;
        double deltaY = entity.getVelocity().y - this.prevMotionY;
        double deltaZ = entity.getVelocity().z - this.prevMotionZ;

        //if the acceleration in the y direction is equal to gravity, set it to zero
        //i worry that this might cause any slight vertical acceleration to cause the player to drop.
        if(deltaY - -0.08 < 0.000001){//minecraft accelerates entities by 0.08 blocks per tick per tick. This means that deltaY changes by 0.08 each tick
            deltaY = 0.0;//we're in space, so accelerate by 0 blocks per tick per tick.
        }

        //give the updated velocity back to the entity
        entity.setVelocity(
                this.prevMotionX + deltaX,
                this.prevMotionY + deltaY,
                this.prevMotionZ + deltaZ
        );

        // Limit speed to avoid infinite acceleration. this is just so the player doesn't fly out of loaded chunks uncontrollably
        float speedLimit = 0.1f;
        entity.setVelocity(
                Math.max(Math.min(entity.getVelocity().x,speedLimit),-speedLimit),
                Math.max(Math.min(entity.getVelocity().y,speedLimit),-speedLimit),
                Math.max(Math.min(entity.getVelocity().z,speedLimit),-speedLimit)
        );
    }
}
