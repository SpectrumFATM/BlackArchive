package net.SpectrumFATM.black_archive.entity.custom;

import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.util.SpaceTimeEventUtil;
import net.SpectrumFATM.black_archive.world.dimension.ModDimensions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WeepingAngelEntity extends Monster {

    public String pose = "default";

    public WeepingAngelEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();

        // Check if any player is observing the entity
        boolean isObserved = this.level().players().stream().anyMatch(this::isBeingObserved);

        if (isObserved) {
            // Immediately stop movement and disable navigation when observed
            if (this.getTarget() != null) {
                this.lookAt(this.getTarget(), 30.0F, 30.0F);
            }

            this.getNavigation().stop();
            if (this.level().getBlockState(this.blockPosition().below()).isSolid()) {
                this.setDeltaMovement(Vec3.ZERO);
                this.setNoAi(true);
            }
        } else {
            // Re-enable AI and only start navigation if AI was previously disabled
            if (this.isNoAi()) {
                this.setNoAi(false);
            }

            if (getTarget() != null && pose == "default" && getTarget().distanceTo(this) < 10) {
                setStatuePose("attack");
            } if (getTarget() == null && pose == "attack") {
                setStatuePose("default");
            }

            // Move towards the nearest player within a 20-block radius if not observed
            Player nearestPlayer = this.level().getNearestPlayer(this, 20.0D);
            if (nearestPlayer != null) {
                this.setTarget(nearestPlayer); // Set the target to the nearest player
                if (!this.getNavigation().isInProgress()) {
                    this.getNavigation().moveTo(nearestPlayer, 2.0D);
                }

                // If the player is within melee range, attack the player
                if (this.distanceToSqr(nearestPlayer) < 2.0D) {
                    doHurtTarget(nearestPlayer);
                }
            }
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
    }

    public static AttributeSupplier.Builder createAngelAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 8.0)
                .add(Attributes.FOLLOW_RANGE, 20.0);
    }

    private boolean isBeingObserved(Player player) {

        if (player.isCreative() || player.isSpectator()) {
            return true;
        }

        // Get the vector from the player's position to the Weeping Angel's position
        Vec3 playerPos = player.getEyePosition(1.0F);
        Vec3 angelPos = this.position();
        Vec3 directionToAngel = angelPos.subtract(playerPos).normalize();

        // Get the player's current look direction
        Vec3 playerLookDirection = player.getViewVector(1.0F);

        // Calculate the dot product between the player's look direction and the direction to the angel
        double dotProduct = playerLookDirection.dot(directionToAngel);

        // If the dot product is greater than a threshold, the angel is in the player's field of view
        return dotProduct > 0;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        // 50% chance to teleport the target to the time vortex
        if (!this.level().isClientSide) {
            if (random.nextInt(10) == 1  && target instanceof Player player) {
                ServerLevel targetWorld = target.getServer().getLevel(ModDimensions.TIMEDIM_LEVEL_KEY);
                ((ServerPlayer) player).teleportTo(targetWorld, target.getX(), target.level().getMaxBuildHeight(), target.getZ(), target.getYRot(), target.getXRot());

                this.setHealth(this.getHealth() + ((ServerPlayer) target).getHealth());

                if (random.nextInt(5) == 1  && SpaceTimeEventUtil.isComplexSpaceTimeEvent(player)) {
                    TimeFissureEntity fissure = new TimeFissureEntity(ModEntities.TIME_FISSURE.get(), targetWorld);
                    fissure.setPosRaw(target.getX(), target.getY(), target.getZ());
                    this.level().addFreshEntity(fissure);
                    this.kill();
                }
            }

            // Deal damage to the target
            target.hurt(this.damageSources().generic(), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
        }
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean isObserved = this.level().players().stream().anyMatch(this::isBeingObserved);

        if (isObserved && source.getEntity() instanceof Player player && !player.isSpectator()) {
            if (!player.isCreative()) {
                return false;
            }
        }

        return super.hurt(source, amount);
    }

    public String getStatuePose() {
        return pose;
    }

    public void setStatuePose(String pose) {
        this.pose = pose;
    }
}
