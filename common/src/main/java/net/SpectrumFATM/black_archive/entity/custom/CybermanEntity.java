package net.SpectrumFATM.black_archive.entity.custom;

import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Random;

public class CybermanEntity extends Monster implements RangedAttackMob {

    private int firingTicks = 0; // Counter to track firing time
    private int laserCooldown = 0; // Counter to manage laser firing cooldown
    private int firingDuration = 0; // Counter to manage the duration of isFiring
    private static final int LASER_COOLDOWN_TICKS = 40; // Laser cooldown duration
    private static final int FIRING_DURATION_TICKS = 10; // Duration for which isFiring should remain true
    private static final EntityDataAccessor<Boolean> IS_FIRING = SynchedEntityData.defineId(CybermanEntity.class, EntityDataSerializers.BOOLEAN);
    private static final double MELEE_ATTACK_RANGE = 2.0; // Define melee attack range

    public CybermanEntity(EntityType<CybermanEntity> type, Level world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_FIRING, false);
    }

    @Override
    public void tick() {
        super.tick();

        if (firingTicks > 0) {
            firingTicks--;
        }

        // Server-side logic
        if (!this.level().isClientSide) {

            Random random = new Random();
            if (random.nextInt(10000) == 1 && BlackArchiveConfig.COMMON.shouldCybermatSpawnAroundCybermen.get()) {
                spawnCybermat();
            }

            LivingEntity target = this.getTarget();
            if (target instanceof Player) {
                double distance = this.distanceToSqr(target);

                // Check if target is within melee range
                if (distance <= MELEE_ATTACK_RANGE * MELEE_ATTACK_RANGE) {
                    setFiring(false); // Stop firing if in melee range
                    meleeAttack(target); // Perform melee attack
                } else {
                    // If outside of melee range, check for ranged attack
                    if (laserCooldown <= 0) {
                        // Set firing flag to true and shoot laser
                        setFiring(true);
                        performRangedAttack(target, 0.0F); // Attack will fire the laser
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
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true)); // Melee attack goal
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 2.0D, 40, 35.0F));  // Ranged attack goal
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 5.0F, 0.02F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 2.0D));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false,
                livingEntity -> !(livingEntity instanceof CybermanEntity || livingEntity instanceof CybermatEntity)));
    }

    public static AttributeSupplier.Builder createCyberAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.ARMOR, 10.0)
                .add(Attributes.ARMOR_TOUGHNESS, 5.0)
                .add(Attributes.FOLLOW_RANGE, 8.0)
                .add(Attributes.ATTACK_DAMAGE, 6.0); // Increased attack damage for melee
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        super.playStepSound(pos, state);
        this.playSound(ModSounds.CYBERMAN_STEP.get(), 0.05F, 1.0F);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    public void meleeAttack(LivingEntity target) {
        // Apply melee damage to the target player
        target.hurt(this.damageSources().generic(), (float) getAttributeValue(Attributes.ATTACK_DAMAGE));
    }

    @Override
    public void performRangedAttack(LivingEntity target, float pullProgress) {
        if (this.getTarget() instanceof Player && this.firingTicks <= 0) {
            double armX = this.getX() - 0.5 * Math.cos(Math.toRadians(this.yBodyRot));
            double armY = this.getY() + this.getBbHeight() * 0.75;
            double armZ = this.getZ() - 0.5 * Math.sin(Math.toRadians(this.yBodyRot));

            LaserEntity laser = new LaserEntity(this.level(), this, 2.0f, false, 117, 117, 255);
            laser.setPos(armX, armY, armZ);

            double d0 = target.getY() + (target.getBbHeight() / 2.0) - laser.getY();
            double d1 = target.getX() - laser.getX();
            double d2 = d0;
            double d3 = target.getZ() - laser.getZ();
            laser.shoot(d1, d2, d3, 1.6f, 0.0f);

            this.level().addFreshEntity(laser);
            this.playSound(ModSounds.CYBERMAN_GUN.get(), 1f, 1.0f);
        } else if (this.firingTicks <= 0) {
            this.playSound(ModSounds.CYBERMAN_MALFUNCTION.get(), 1f, 1.0f);
        }
    }

    public boolean isFiring() {
        return this.entityData.get(IS_FIRING);
    }

    public void setFiring(boolean firing) {
        this.entityData.set(IS_FIRING, firing);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) source.getEntity();
            ItemStack weapon = attacker.getMainHandItem(); // Get the weapon in the main hand

            // Check if the weapon is gold and apply a 1.5x damage multiplier
            if (weapon.getItem() == Items.GOLDEN_SWORD || weapon.getItem() == Items.GOLDEN_AXE || weapon.getItem() == Items.GOLDEN_PICKAXE || weapon.getItem() == Items.GOLDEN_SHOVEL || weapon.getItem() == Items.GOLDEN_HOE) {
                amount *= 1.5; // Multiply damage by 1.5
            }
        }

        return super.hurt(source, amount); // Apply the damage to the entity
    }

    public void disableFire(int ticks) {
        this.firingTicks = ticks;
    }

    public void spawnCybermat() {
        CybermatEntity cybermat = new CybermatEntity(ModEntities.CYBERMAT.get(), this.level());
        Level world = this.level();

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

            cybermat.moveTo(x, y, z, this.random.nextFloat() * 360.0F, 0.0F);
            this.level().addFreshEntity(cybermat);
        }
    }
}
