package net.SpectrumFATM.black_archive.fabric.mixin;

import net.SpectrumFATM.black_archive.fabric.world.dimension.ModDimensions;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    public void modifyGravityAndMotion(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (!syntheticGravity() && entity.getWorld().getRegistryKey().getValue() == ModDimensions.SPACEDIM_KEY.getValue()) {


            // Check if the entity is a player
            if (entity instanceof PlayerEntity player) {
                // Get the current velocity
                Vec3d velocity = entity.getVelocity();
                double dampeningFactor = 0.9; // Adjust this value for faster/slower halting

                // Calculate new vertical velocity
                double newYVelocity = 0;
                if (player.isSneaking()) {
                    // Apply gravity when sneaking
                    newYVelocity = velocity.y - 0.01;
                } else if (player instanceof ClientPlayerEntity entity1) {
                    if (entity1.input.jumping) {
                        newYVelocity = velocity.y + 0.01;
                    }
                } else {
                    // Prevent falling when not sneaking
                    newYVelocity = Math.max(0, velocity.y);
                }

                // Apply dampening to all components of velocity
                Vec3d newVelocity = new Vec3d(
                        velocity.x * dampeningFactor,
                        newYVelocity * dampeningFactor,
                        velocity.z * dampeningFactor
                );

                // If the velocity is very small, set it to zero to completely stop the player
                if (newVelocity.lengthSquared() < 0.0001) {
                    newVelocity = Vec3d.ZERO;
                }

                // Set the new velocity
                entity.setVelocity(newVelocity);
            }
        }
    }

    //If there is a diamond block within the chunk give the player normal gravity
    private boolean syntheticGravity() {
        Entity entity = (Entity) (Object) this;
        BlockPos pos = entity.getBlockPos();
        for (int x = -8; x < 8; x++) {
            for (int z = -8; z < 8; z++) {
                for (int y = -8; y < 8; y++) {
                    BlockPos blockPos = pos.add(x, y, z);
                    if (entity.getWorld().getBlockState(blockPos).getBlock() == Blocks.DIAMOND_BLOCK) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

