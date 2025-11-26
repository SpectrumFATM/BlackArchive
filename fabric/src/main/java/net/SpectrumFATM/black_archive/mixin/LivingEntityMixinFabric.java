package net.SpectrumFATM.black_archive.mixin;

import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.util.LifeSupportUtil;
import net.SpectrumFATM.black_archive.util.Platform;
import net.SpectrumFATM.black_archive.world.dimension.ModDimensions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixinFabric {

    private double prevMotionX;
    private double prevMotionY;
    private double prevMotionZ;
    private boolean shouldSuffocate = false;
    private int air = 300;
    private static final EntityDataAccessor<Float> ENTITY_SCALE = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.FLOAT);

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        Entity entity = (Entity) (Object) this;
        applyZeroGravity(entity);

        if (entity instanceof Player player) {
            suffocate(player);
        }
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedData(CallbackInfo ci) {
        ((LivingEntity) (Object) this).getEntityData().define(ENTITY_SCALE, 1.0F);
    }

    @Inject(method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("HEAD"))
    private void writeNbt(CompoundTag nbt, CallbackInfo info) {
        nbt.putFloat("Scale", ((LivingEntity) (Object) this).getEntityData().get(ENTITY_SCALE));
    }

    @Inject(method = "readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("HEAD"))
    private void readNbt(CompoundTag nbt, CallbackInfo info) {
        if (nbt.contains("Scale")) {
            ((LivingEntity) (Object) this).getEntityData().set(ENTITY_SCALE, nbt.getFloat("Scale"));
        }
    }

    private void applyZeroGravity(Entity entity) {
        Level world = entity.level();

        if (!LifeSupportUtil.isInZeroGravityDimension(world)) {
            return;
        }

        shouldSuffocate = !LifeSupportUtil.oxygenNearby(entity, BlackArchiveConfig.COMMON.oxygenFieldRange.get()) && !LifeSupportUtil.tardisNearby(entity) && world.dimension() == ModDimensions.SPACEDIM_LEVEL_KEY && !Platform.isModLoaded("ad_astra");

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

        if (!Platform.isModLoaded("ad_astra")) {
            handleFreefallMotion(entity);
        }

        this.prevMotionX = entity.getDeltaMovement().x;
        this.prevMotionY = entity.getDeltaMovement().y;
        this.prevMotionZ = entity.getDeltaMovement().z;
    }

    private boolean isInFreefall(Entity entity) {
        AABB boundingBox = entity.getBoundingBox();
        BlockPos minPos = new BlockPos(Mth.floor(boundingBox.minX), Mth.floor(boundingBox.minY - 0.01D), Mth.floor(boundingBox.minZ));
        BlockPos maxPos = new BlockPos(Mth.floor(boundingBox.maxX), Mth.floor(boundingBox.maxY), Mth.floor(boundingBox.maxZ));

        for (int x = minPos.getX(); x <= maxPos.getX(); x++) {
            for (int y = minPos.getY(); y <= maxPos.getY(); y++) {
                for (int z = minPos.getZ(); z <= maxPos.getZ(); z++) {
                    BlockState state = entity.level().getBlockState(new BlockPos(x, y, z));
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
        entity.setDeltaMovement(
                entity.getDeltaMovement().x() / 0.91F,
                entity.getDeltaMovement().y / 0.9800000190734863D,
                entity.getDeltaMovement().z / 0.91F
        );

        double deltaX = entity.getDeltaMovement().x - this.prevMotionX;
        double deltaY = entity.getDeltaMovement().y - this.prevMotionY;
        double deltaZ = entity.getDeltaMovement().z - this.prevMotionZ;

        if (deltaY == -0.08) {
            deltaY = 0;
        }

        entity.setDeltaMovement(
                this.prevMotionX + deltaX,
                this.prevMotionY + deltaY,
                this.prevMotionZ + deltaZ
        );

        float speedLimit = 0.1f;
        entity.setDeltaMovement(
                Math.max(Math.min(entity.getDeltaMovement().x, speedLimit), -speedLimit),
                Math.max(Math.min(entity.getDeltaMovement().y, speedLimit), -speedLimit),
                Math.max(Math.min(entity.getDeltaMovement().z, speedLimit), -speedLimit)
        );
    }

    private void suffocate(Entity entity) {
        if (shouldSuffocate && entity instanceof Player player) {
            if (!player.isCreative() && !player.isSpectator()) {
                if (air > -20) {
                    air--;
                    player.setAirSupply(air);
                } else {
                    player.hurt(entity.damageSources().generic(), 1.0f);
                    air = -20;
                }
            }
        } else {
            if (air < 300) {
                air = Math.min(air + 1, 300);
                if (entity instanceof Player player) {
                    player.setAirSupply(air);
                }
            }
        }
    }

    @Inject(method = "getEyeHeight", at = @At("HEAD"), cancellable = true)
    protected void getEyeHeight(Pose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> cir) {
        if ((Object) this instanceof Player) {
            cir.setReturnValue(dimensions.height * 0.85f * (float)((LivingEntity) (Object) this).getEntityData().get(ENTITY_SCALE));
        }
        ((LivingEntity) (Object) this).getEntityData().get(ENTITY_SCALE);
    }
}