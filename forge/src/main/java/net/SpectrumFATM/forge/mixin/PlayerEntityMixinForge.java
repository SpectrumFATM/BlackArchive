package net.SpectrumFATM.forge.mixin;

import net.SpectrumFATM.black_archive.util.WorldUtil;
import net.SpectrumFATM.black_archive.world.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.util.DimensionUtil;

import java.util.Random;
import java.util.Set;

@Mixin(Player.class)
public class PlayerEntityMixinForge {

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        Player entity = (Player) (Object) this;

        if (!entity.level().isClientSide && entity.getY() <= 0 && entity.level().dimension().location().toString().equals("black_archive:time_vortex")) {
            Set<ResourceKey<Level>> dimensions = DimensionUtil.getAllowedDimensions(entity.getServer());
            dimensions.remove(ModDimensions.TIMEDIM_LEVEL_KEY);
            dimensions.remove(Level.NETHER);
            dimensions.remove(Level.END);

            Random random = new Random();
            ResourceKey<Level> randomDimension = dimensions.stream()
                    .skip(random.nextInt(dimensions.size()))
                    .findFirst()
                    .orElse(Level.OVERWORLD);

            ServerLevel targetWorld = entity.getServer().getLevel(randomDimension);
            if (targetWorld != null) {
                double x = entity.getX() + (random.nextInt(2000) - 1000);
                double z = entity.getZ() + (random.nextInt(2000) - 1000);
                BlockPos safePos = WorldUtil.findSafeLandingPos(targetWorld, x, (double) targetWorld.getMaxBuildHeight() / 2, z);

                if (safePos != null) {
                    targetWorld.sendParticles(ParticleTypes.SMOKE, safePos.getX(), safePos.getY(), safePos.getZ(), 1, 0.5, 0.5, 0.5, 0.0);
                    ((ServerPlayer) entity).teleportTo(targetWorld, safePos.getX(), safePos.getY(), safePos.getZ(), entity.getYRot(), entity.getXRot());
                    entity.level().playSound(null, entity.blockPosition(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.AMBIENT);
                }
            }
        }
    }

    private static final EntityDataAccessor<Boolean> IS_COMPLEX_SPACE_TIME_EVENT = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);

    @Inject(method = "defineSynchedData", at = @At("RETURN"))
    private void defineSynchedData(CallbackInfo info) {
        ((Player) (Object) this).getEntityData().define(IS_COMPLEX_SPACE_TIME_EVENT, false);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void addAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
        nbt.putBoolean("isComplexSpaceTimeEvent", ((Player) (Object) this).getEntityData().get(IS_COMPLEX_SPACE_TIME_EVENT));
    }

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    private void readAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
        ((Player) (Object) this).getEntityData().set(IS_COMPLEX_SPACE_TIME_EVENT, nbt.getBoolean("isComplexSpaceTimeEvent"));
    }

    public void setComplexSpaceTimeEvent(boolean status) {
        ((Player) (Object) this).getEntityData().set(IS_COMPLEX_SPACE_TIME_EVENT, status);
    }

    public boolean isComplexSpaceTimeEvent() {
        return ((Player) (Object) this).getEntityData().get(IS_COMPLEX_SPACE_TIME_EVENT);
    }
}