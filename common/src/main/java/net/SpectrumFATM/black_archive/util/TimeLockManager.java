package net.SpectrumFATM.black_archive.util;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeLockManager {

    private final List<LivingEntity> frozenEntities = new ArrayList<>();
    private static final int POWER_DRAIN_RATE = 10;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private AABB timeLockBoundingBox;

    public boolean engageTimeLock(TardisLevelOperator operator) {
        BlockPos landedPos = operator.getPilotingManager().getCurrentLocation().getPosition();
        int range = BlackArchiveConfig.COMMON.maximumTimeLockRange.get();

        timeLockBoundingBox = new AABB(landedPos).inflate(range);
        ServerLevel level = operator.getPilotingManager().getCurrentLocation().getLevel();

        if (!level.isClientSide) {
            List<LivingEntity> entitiesInRange = level.getEntitiesOfClass(LivingEntity.class, timeLockBoundingBox, entity -> true);

            entitiesInRange.removeIf(entity -> entity instanceof Player);

            for (LivingEntity entity : entitiesInRange) {
                freezeEntity(entity);
            }
        }

        startPowerDrain(operator);

        return true;
    }

    private void freezeEntity(LivingEntity entity) {
        if (!(entity instanceof Player)) {
            if (entity instanceof Mob) {
            }
            CompoundTag entityNbt = new CompoundTag();
            entity.saveWithoutId(entityNbt);
            entityNbt.putBoolean("NoAI", true);
            entity.setInvulnerable(true);
            entity.readAdditionalSaveData(entityNbt);

            frozenEntities.add(entity);
        }
    }

    private void startPowerDrain(TardisLevelOperator operator) {
        if (scheduler.isShutdown() || scheduler.isTerminated()) {
            BlackArchive.LOGGER.error("Scheduler is terminated. Cannot schedule tasks.");
            return;
        }

        scheduler.scheduleAtFixedRate(() -> {
            ServerLevel level = operator.getPilotingManager().getCurrentLocation().getLevel();
            if (operator.getPilotingManager().getFuel() > 0 && !frozenEntities.isEmpty()) {
                operator.getPilotingManager().setFuel(operator.getPilotingManager().getFuel() - POWER_DRAIN_RATE);
                preventEntityEntry(level);
            } else {
                releaseEntities();
                shutdownScheduler();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void preventEntityEntry(ServerLevel level) {
        if (timeLockBoundingBox == null) return;

        List<LivingEntity> entitiesNearBoundary = level.getEntitiesOfClass(LivingEntity.class, timeLockBoundingBox.inflate(1), entity -> true);

        for (LivingEntity entity : entitiesNearBoundary) {
            if (!timeLockBoundingBox.contains(entity.position())) {
                Vec3 knockbackDirection = entity.position().subtract(timeLockBoundingBox.getCenter()).normalize();
                entity.setDeltaMovement(knockbackDirection.scale(1.5)); // Apply knockback
                entity.hurtMarked = true;
            }
        }
    }

    public void releaseEntities() {
        for (LivingEntity entity : frozenEntities) {
            if (entity instanceof Mob mob) {
                mob.getNavigation().recomputePath();
            }

            CompoundTag entityNbt = new CompoundTag();
            entity.saveWithoutId(entityNbt);
            entityNbt.putBoolean("NoAI", false);
            entity.setInvulnerable(false);
            entity.readAdditionalSaveData(entityNbt);
        }
        frozenEntities.clear();
        timeLockBoundingBox = null;
    }

    public void shutdownScheduler() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}