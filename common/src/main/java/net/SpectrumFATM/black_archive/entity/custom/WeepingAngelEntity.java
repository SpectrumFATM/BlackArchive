package net.SpectrumFATM.black_archive.entity.custom;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.world.dimension.ModDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WeepingAngelEntity extends HostileEntity {

    public String pose = "default";

    public WeepingAngelEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();

        // Check if any player is observing the entity
        boolean isObserved = this.getWorld().getPlayers().stream().anyMatch(this::isBeingObserved);

        if (isObserved) {
            // Immediately stop movement and disable navigation when observed
            if (this.getTarget() != null) {
                this.lookAtEntity(this.getTarget(), 30.0F, 30.0F);
            }

            this.getNavigation().stop();
            if (this.getWorld().getBlockState(this.getBlockPos().down()).isSolid()) {
                this.setVelocity(Vec3d.ZERO);
                this.setAiDisabled(true);
            }
        } else {
            // Re-enable AI and only start navigation if AI was previously disabled
            if (this.isAiDisabled()) {
                this.setAiDisabled(false);
            }

            if (getTarget() != null && pose == "default" && getTarget().distanceTo(this) < 10) {
                setStatuePose("attack");
            } if (getTarget() == null && pose == "attack") {
                setStatuePose("default");
            }

            // Move towards the nearest player within a 20-block radius if not observed
            PlayerEntity nearestPlayer = this.getWorld().getClosestPlayer(this, 20.0D);
            if (nearestPlayer != null) {
                this.setTarget(nearestPlayer); // Set the target to the nearest player
                if (!this.getNavigation().isFollowingPath()) {
                    this.getNavigation().startMovingTo(nearestPlayer, 2.0D);
                }

                // If the player is within melee range, attack the player
                if (this.squaredDistanceTo(nearestPlayer) < 2.0D) {
                    tryAttack(nearestPlayer);
                }
            }
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, false));
    }

    public static DefaultAttributeContainer.Builder createAngelAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20.0);
    }

    private boolean isBeingObserved(PlayerEntity player) {

//        if (player.isCreative() || player.isSpectator()) {
//            return false;
//        }

        // Get the vector from the player's position to the Weeping Angel's position
        Vec3d playerPos = player.getCameraPosVec(1.0F);
        Vec3d angelPos = this.getPos();
        Vec3d directionToAngel = angelPos.subtract(playerPos).normalize();

        // Get the player's current look direction
        Vec3d playerLookDirection = player.getRotationVec(1.0F);

        // Calculate the dot product between the player's look direction and the direction to the angel
        double dotProduct = playerLookDirection.dotProduct(directionToAngel);

        BlackArchive.LOGGER.info("Checking if player is observing Weeping Angel: " + Boolean.valueOf(dotProduct > 0).toString());

        // If the dot product is greater than a threshold, the angel is in the player's field of view
        return dotProduct > 0;
    }

    @Override
    public boolean tryAttack(Entity target) {
        // 50% chance to teleport the target to the time vortex
        if (random.nextInt(10) == 1  && !this.getWorld().isClient) {
            if (target instanceof ServerPlayerEntity) {
                ServerWorld targetWorld = target.getServer().getWorld(ModDimensions.TIMEDIM_LEVEL_KEY);
                ((ServerPlayerEntity) target).teleport(targetWorld, target.getX(), target.getWorld().getTopY(), target.getZ(), target.getYaw(), target.getPitch());

                this.setHealth(this.getHealth() + ((ServerPlayerEntity) target).getHealth());
            }

            // Deal damage to the target
            target.damage(this.getDamageSources().generic(), (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
        }
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {

        boolean isObserved = this.getWorld().getPlayers().stream().anyMatch(this::isBeingObserved);

        if (isObserved) {
            return false;
        }

        return super.damage(source, isObserved ? 0 : amount);
    }

    public String getStatuePose() {
        return pose;
    }

    public void setStatuePose(String pose) {
        this.pose = pose;
    }
}
