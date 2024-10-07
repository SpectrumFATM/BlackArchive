package net.SpectrumFATM.black_archive.fabric.entity.custom;

import net.SpectrumFATM.black_archive.fabric.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.fabric.entity.ModEntities;
import net.SpectrumFATM.black_archive.fabric.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class CybermanEntity extends HostileEntity implements RangedAttackMob {

    private int firingTicks = 0; // Counter to track firing time
    private int laserCooldown = 0; // Counter to manage laser firing cooldown
    private int firingDuration = 0; // Counter to manage the duration of isFiring
    private static final int LASER_COOLDOWN_TICKS = 40; // Laser cooldown duration
    private static final int FIRING_DURATION_TICKS = 10; // Duration for which isFiring should remain true
    private static final TrackedData<Boolean> IS_FIRING = DataTracker.registerData(CybermanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final double MELEE_ATTACK_RANGE = 2.0; // Define melee attack range

    public CybermanEntity(EntityType<CybermanEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(IS_FIRING, false);
    }

    @Override
    public void tick() {
        super.tick();

        if (firingTicks > 0) {
            firingTicks--;
        }

        // Server-side logic
        if (!this.getWorld().isClient) {

            Random random = new Random();
            if (random.nextInt(10000) == 1 && BlackArchiveConfig.COMMON.shouldCybermatSpawnAroundCybermen.get()) {
                spawnCybermat();
            }

            LivingEntity target = this.getTarget();
            if (target instanceof PlayerEntity) {
                double distance = this.squaredDistanceTo(target);

                // Check if target is within melee range
                if (distance <= MELEE_ATTACK_RANGE * MELEE_ATTACK_RANGE) {
                    setFiring(false); // Stop firing if in melee range
                    meleeAttack(target); // Perform melee attack
                } else {
                    // If outside of melee range, check for ranged attack
                    if (laserCooldown <= 0) {
                        // Set firing flag to true and shoot laser
                        setFiring(true);
                        attack(target, 0.0F); // Attack will fire the laser
                        laserCooldown = LASER_COOLDOWN_TICKS; // Reset cooldown
                        firingDuration = FIRING_DURATION_TICKS; // Start the firing duration
                    } else {
                        laserCooldown--; // Decrease cooldown timer
                    }
                }
            }

            // Manage firing duration
            if (isFiring() && firingDuration > 0) {
                firingDuration--; // Decrement firing duration counter
            }

            // Reset isFiring after duration expires
            if (firingDuration <= 0) {
                setFiring(false);
            }
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true)); // Melee attack goal
        this.goalSelector.add(2, new ProjectileAttackGoal(this, 2.0D, 40, 35.0F));  // Ranged attack goal
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 5.0F, 0.02F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new GoToWalkTargetGoal(this, 2.0D));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, LivingEntity.class, 10, true, false,
                livingEntity -> !(livingEntity instanceof CybermanEntity || livingEntity instanceof CybermatEntity)));
    }

    public static DefaultAttributeContainer.Builder createCyberAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0)
                .add(EntityAttributes.GENERIC_ARMOR, 10.0)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 5.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 8.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0); // Increased attack damage for melee
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        super.playStepSound(pos, state);
        this.playSound(ModSounds.CYBERMAN_STEP, 0.05F, 1.0F);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_IRON_GOLEM_HURT;
    }

    public void meleeAttack(LivingEntity target) {
        // Apply melee damage to the target player
        target.damage(this.getDamageSources().generic(), (float) getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        if (this.getTarget() instanceof PlayerEntity && this.firingTicks <= 0) {
            double armX = this.getX() - 0.5 * Math.cos(Math.toRadians(this.bodyYaw));
            double armY = this.getY() + this.getHeight() * 0.75;
            double armZ = this.getZ() - 0.5 * Math.sin(Math.toRadians(this.bodyYaw));

            LaserEntity laser = new LaserEntity(this.getWorld(), this, 2.0f, false);
            laser.setPosition(armX, armY, armZ);

            double d0 = target.getY() + (target.getHeight() / 2.0) - laser.getY();
            double d1 = target.getX() - laser.getX();
            double d2 = d0;
            double d3 = target.getZ() - laser.getZ();
            laser.setVelocity(d1, d2, d3, 1.6f, 0.0f);

            this.getWorld().spawnEntity(laser);
            this.playSound(ModSounds.CYBERMAN_GUN, 1f, 1.0f);
        } else if (this.firingTicks <= 0) {
            this.playSound(ModSounds.CYBERMAN_MALFUNCTION, 1f, 1.0f);
        }
    }

    public boolean isFiring() {
        return this.dataTracker.get(IS_FIRING);
    }

    public void setFiring(boolean firing) {
        this.dataTracker.set(IS_FIRING, firing);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getAttacker() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) source.getAttacker();
            ItemStack weapon = attacker.getMainHandStack(); // Get the weapon in the main hand

            // Check if the weapon is gold and apply a 1.5x damage multiplier
            if (weapon.getItem() == Items.GOLDEN_SWORD || weapon.getItem() == Items.GOLDEN_AXE || weapon.getItem() == Items.GOLDEN_PICKAXE || weapon.getItem() == Items.GOLDEN_SHOVEL || weapon.getItem() == Items.GOLDEN_HOE) {
                amount *= 1.5; // Multiply damage by 1.5
            }
        }

        return super.damage(source, amount); // Apply the damage to the entity
    }

    public void disableFire(int ticks) {
        this.firingTicks = ticks;
    }

    public void spawnCybermat() {
        CybermatEntity cybermat = new CybermatEntity(ModEntities.CYBERMAT, this.getWorld());
        World world = this.getWorld();

        double x = this.getX() + this.random.nextGaussian() * 4;
        double z = this.getZ() + this.random.nextGaussian() * 4;
        double y = this.getY();

        BlockState pos = world.getBlockState(new BlockPos((int) x, (int) y, (int) z));

        while (!pos.isAir()) {
            x = this.getX() + this.random.nextGaussian() * 4;
            z = this.getZ() + this.random.nextGaussian() * 4;


            for (double i = this.getY(); pos.isAir(); y--) {
                if (!world.getBlockState(new BlockPos((int) x, (int) i, (int) z)).isAir()) {
                    y = i + 1;
                    break;
                }
            }

            pos = world.getBlockState(new BlockPos((int) x, (int) y, (int) z));

            cybermat.refreshPositionAndAngles(x, y, z, this.random.nextFloat() * 360.0F, 0.0F);
            this.getWorld().spawnEntity(cybermat);
        }
    }
}
