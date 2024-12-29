package net.SpectrumFATM.black_archive.entity.custom;

import net.SpectrumFATM.black_archive.item.ModItems;
import net.SpectrumFATM.black_archive.sound.ModSounds;
import net.minecraft.core.BlockPos;
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
import whocraft.tardis_refined.registry.TRItemRegistry;

public class DalekEntity extends Monster implements RangedAttackMob {
    public DalekEntity(EntityType<DalekEntity> type, Level world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 2.0D, 40, 15.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 5.0F, 0.02F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 2.0D));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false,
                livingEntity -> !(livingEntity instanceof DalekEntity || livingEntity instanceof DalekPuppetEntity)));
    }

    public static AttributeSupplier.Builder createDalekAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.ARMOR, 10.0)
                .add(Attributes.ARMOR_TOUGHNESS, 5.0)
                .add(Attributes.FOLLOW_RANGE, 8.0)
                .add(Attributes.ATTACK_DAMAGE, 2.0);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        super.playStepSound(pos, state);
        this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(8), entity -> true)
                .forEach(entity -> entity.playSound(ModSounds.DALEK_MOVE.get(), 0.01F, 1.0F));
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float pullProgress) {
        LaserEntity laser = new LaserEntity(this.level(), this, 2.0f, false, 117, 117, 255);
        double d0 = target.getY() + (target.getBbHeight() / 2.0) - laser.getY();
        double d1 = target.getX() - this.getX();
        double d2 = d0;
        double d3 = target.getZ() - this.getZ();
        laser.shoot(d1, d2, d3, 1.6f, 0.0f);
        this.playSound(ModSounds.DALEK_LASER.get(), 0.5f, 1.0f);
        this.level().addFreshEntity(laser);
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (!this.level().isClientSide) {
            if (random.nextInt(20) == 1) {
                this.spawnAtLocation(new ItemStack(ModItems.DALEK_LASER_GUN.get(), 1));
            } else {
                this.spawnAtLocation(new ItemStack(Items.COPPER_BLOCK, 2));
                this.spawnAtLocation(new ItemStack(TRItemRegistry.RAW_ZEITON.get(), 1));
            }

            if (random.nextInt(20) == 1) {
                this.spawnAtLocation(new ItemStack(ModItems.DALEK_BRACELET.get(), 1));
            }
        }
    }
}