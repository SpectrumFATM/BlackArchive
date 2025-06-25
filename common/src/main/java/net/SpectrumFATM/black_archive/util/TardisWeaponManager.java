package net.SpectrumFATM.black_archive.util;

        import net.SpectrumFATM.BlackArchive;
        import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
        import net.minecraft.core.BlockPos;
        import net.minecraft.nbt.CompoundTag;
        import net.minecraft.network.chat.Component;
        import net.minecraft.server.level.ServerLevel;
        import net.minecraft.world.entity.Entity;
        import net.minecraft.world.entity.LivingEntity;
        import net.minecraft.world.entity.Mob;
        import net.minecraft.world.entity.player.Player;
        import net.minecraft.world.phys.AABB;
        import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;

        import java.util.ArrayList;
        import java.util.List;

        public class TardisWeaponManager {

            private final List<LivingEntity> frozenEntities = new ArrayList<>();
            private static final int POWER_DRAIN_RATE = 10; // Power drained per second

            public boolean engageTimeLock(TardisLevelOperator operator, Player player) {
                BlockPos landedPos = operator.getPilotingManager().getCurrentLocation().getPosition();
                int range = BlackArchiveConfig.COMMON.maximumTimeLockRange.get();

                AABB boundingBox = new AABB(landedPos).inflate(range);
                ServerLevel level = operator.getPilotingManager().getCurrentLocation().getLevel();

                if (!level.isClientSide) {
                    BlackArchive.LOGGER.info("Engaging time lock on server side.");
                    List<LivingEntity> entitiesInRange = level.getEntitiesOfClass(LivingEntity.class, boundingBox, entity -> true);

                    for (LivingEntity entity : entitiesInRange) {
                        BlackArchive.LOGGER.info("Entity in range: " + entity.getName().getString());
                        freezeEntity(entity);
                    }
                }

                startPowerDrain(operator);

                notifyPlayer(player);
                return true;
            }

            private void freezeEntity(LivingEntity entity) {
                if (!(entity instanceof Player)) {
                    if (entity instanceof Mob mob) {
                        mob.getNavigation().stop(); // Stop AI navigation
                    }
                }
                    CompoundTag entityNbt = new CompoundTag();
                    entity.saveWithoutId(entityNbt);
                    entityNbt.putBoolean("NoAI", true); // Disable AI
                    entity.setInvulnerable(true);
                    entity.readAdditionalSaveData(entityNbt);

                    frozenEntities.add(entity);
            }

            private void startPowerDrain(TardisLevelOperator operator) {
                operator.getLevel().getServer().execute(() -> {
                    while (operator.getPilotingManager().getFuel() > 0 && !frozenEntities.isEmpty()) {
                        operator.getPilotingManager().setFuel(operator.getPilotingManager().getFuel() - POWER_DRAIN_RATE);
                        try {
                            Thread.sleep(1000); // Drain power every second
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            BlackArchive.LOGGER.error("Power drain interrupted", e);
                        }
                    }
                    releaseEntities();
                });
            }

            private void notifyPlayer(Player player) {
                for (Entity entity : frozenEntities) {
                    player.displayClientMessage(
                        Component.literal(entity.toString() + " has been frozen in time!"),
                        false
                    );
                }
            }

            public void releaseEntities() {
                for (LivingEntity entity : frozenEntities) {
                    if (entity instanceof Mob mob) {
                        mob.getNavigation().recomputePath(); // Resume AI navigation
                    }

                    CompoundTag entityNbt = new CompoundTag();
                    entity.saveWithoutId(entityNbt);
                    entityNbt.putBoolean("NoAI", false); // Enable AI
                    entity.setInvulnerable(false);
                    entity.readAdditionalSaveData(entityNbt);
                }
                frozenEntities.clear();
            }
        }