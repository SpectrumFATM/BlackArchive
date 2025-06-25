package net.SpectrumFATM.black_archive.util;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;

import java.util.ArrayList;
import java.util.List;

public class TardisWeaponManager {

    private final List<Entity> frozenEntities = new ArrayList<>();

    public boolean engageTimeLock(TardisLevelOperator operator, Player player1) {
        BlockPos landedPos = operator.getPilotingManager().getCurrentLocation().getPosition();
        int range = BlackArchiveConfig.COMMON.maximumTimeLockRange.get();
        int powerDrainRate = 10; // Power drained per second

        AABB boundingBox = new AABB(landedPos).inflate(range);
        ServerLevel level = operator.getPilotingManager().getCurrentLocation().getLevel();

        if (!level.isClientSide) {
           BlackArchive.LOGGER.info("Serverside!");

            level.getEntitiesOfClass(LivingEntity.class, boundingBox, entity -> true)
                    .forEach(entity -> BlackArchive.LOGGER.info(entity.getName().getString()));
        }
//        // Freeze entities
//        for (Entity entity : entitiesInRange) {
//            if (entity instanceof Player player) {
//                if (!SpaceTimeEventUtil.isComplexSpaceTimeEvent(player)) {
//                    freezeEntity(entity);
//                }
//            } else {
//                freezeEntity(entity);
//            }
//
//            BlackArchive.LOGGER.debug("Freezing entity: " + entity.getStringUUID() + " at position: " + entity.blockPosition());
//        }

        // Start draining power
        operator.getLevel().getServer().execute(() -> {
            while (operator.getPilotingManager().getFuel() > 0 && !frozenEntities.isEmpty()) {
                operator.getPilotingManager().setFuel(operator.getPilotingManager().getFuel() - powerDrainRate);
                try {
                    Thread.sleep(1000); // Drain power every second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            releaseEntities();
        });

        for (Entity entity : frozenEntities) {
            player1.displayClientMessage(
                    Component.literal(entity.toString() + " has been frozen in time!"),
                    false
            );
        }
        return true;
    }

    private void freezeEntity(Entity entity) {
        if (entity instanceof Mob mob) {
            mob.getNavigation().stop(); // Stop AI navigation
        }
        CompoundTag entityNbt = new CompoundTag();
        entity.saveWithoutId(entityNbt);

        entityNbt.putBoolean("NoAI", true); // Disable AI
        entity.setInvulnerable(true);
        frozenEntities.add(entity);
    }

    public void releaseEntities() {
        for (Entity entity : frozenEntities) {
            if (entity instanceof Mob mob) {
                mob.getNavigation().recomputePath(); // Resume AI navigation
            }
            CompoundTag entityNbt = new CompoundTag();
            entity.saveWithoutId(entityNbt);

            entityNbt.putBoolean("NoAI", false); // Enable AI
            entity.setInvulnerable(false);
        }
        frozenEntities.clear();
    }
}