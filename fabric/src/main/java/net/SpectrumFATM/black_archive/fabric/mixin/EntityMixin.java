package net.SpectrumFATM.black_archive.fabric.mixin;

import net.SpectrumFATM.black_archive.fabric.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.fabric.util.LifeSupportUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
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
        if (entity instanceof PlayerEntity) {
            suffocate(entity);
        }
    }

    private void applyZeroGravity(Entity entity) {
        World world = entity.getWorld();

        if (!LifeSupportUtil.isInZeroGravityDimension(world)) {
            return;
        }

        shouldSuffocate = !LifeSupportUtil.oxygenNearby(entity, BlackArchiveConfig.COMMON.oxygenFieldRange.get()) && !LifeSupportUtil.tardisNearby(entity);

        if (LifeSupportUtil.dalekGravityGenNearby(entity, 33, 18)) {
            shouldSuffocate = false;
            return;
        }

        if (LifeSupportUtil.tardisNearby(entity)) {
            return;
        }

        if (LifeSupportUtil.gravityGenNearby(entity)) {
            return;
        }

        if (!isInFreefall(entity)) {
            return;
        }

        handleFreefallMotion(entity);

        this.prevMotionX = entity.getVelocity().x;
        this.prevMotionY = entity.getVelocity().y;
        this.prevMotionZ = entity.getVelocity().z;
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
        entity.setVelocity(
                entity.getVelocity().getX() / 0.91F,
                entity.getVelocity().y / 0.9800000190734863D,
                entity.getVelocity().z / 0.91F
        );

        double deltaX = entity.getVelocity().x - this.prevMotionX;
        double deltaY = entity.getVelocity().y - this.prevMotionY;
        double deltaZ = entity.getVelocity().z - this.prevMotionZ;

        if (deltaY == -0.08) {
            deltaY = 0;
        }

        entity.setVelocity(
                this.prevMotionX + deltaX,
                this.prevMotionY + deltaY,
                this.prevMotionZ + deltaZ
        );

        float speedLimit = 0.1f;
        entity.setVelocity(
                Math.max(Math.min(entity.getVelocity().x, speedLimit), -speedLimit),
                Math.max(Math.min(entity.getVelocity().y, speedLimit), -speedLimit),
                Math.max(Math.min(entity.getVelocity().z, speedLimit), -speedLimit)
        );
    }

    private void suffocate(Entity entity) {
        if (shouldSuffocate && entity instanceof PlayerEntity player) {
            if (!player.isCreative() && !player.isSpectator()) {
                if (air > -20) {
                    air--;
                    player.setAir(air);
                } else {
                    player.damage(entity.getDamageSources().generic(), 1.0f);
                    air = -20;
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
}