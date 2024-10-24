package net.SpectrumFATM.black_archive.fabric.mixin;

import net.SpectrumFATM.black_archive.fabric.util.WorldUtil;
import net.SpectrumFATM.black_archive.fabric.world.dimension.ModDimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.util.DimensionUtil;

import java.util.Random;
import java.util.Set;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        PlayerEntity entity = (PlayerEntity) (Object) this;

        if (!entity.getWorld().isClient && entity.getY() <= 0 && entity.getWorld().getRegistryKey().getValue().toString().equals("black_archive:time_vortex")) {
            Set<RegistryKey<World>> dimensions = DimensionUtil.getAllowedDimensions(entity.getServer());
            dimensions.remove(ModDimensions.TIMEDIM_LEVEL_KEY);

            Random random = new Random();
            RegistryKey<World> randomDimension = dimensions.stream()
                    .skip(random.nextInt(dimensions.size()))
                    .findFirst()
                    .orElse(World.OVERWORLD);

            ServerWorld targetWorld = entity.getServer().getWorld(randomDimension);
            if (targetWorld != null) {
                double x = entity.getX() + (random.nextInt(2000) - 1000);
                double z = entity.getZ() + (random.nextInt(2000) - 1000);
                BlockPos safePos = WorldUtil.findSafeLandingPos(targetWorld, x, (double) targetWorld.getTopY() / 2, z);

                if (safePos != null) {
                    targetWorld.spawnParticles(ParticleTypes.SMOKE, safePos.getX(), safePos.getY(), safePos.getZ(), 1, 0.5, 0.5, 0.5, 0.0);
                    ((ServerPlayerEntity) entity).teleport(targetWorld, safePos.getX(), safePos.getY(), safePos.getZ(), entity.getYaw(), entity.getPitch());
                    entity.getWorld().playSound(null, entity.getBlockPos(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.AMBIENT);
                }
            }
        }
    }
}