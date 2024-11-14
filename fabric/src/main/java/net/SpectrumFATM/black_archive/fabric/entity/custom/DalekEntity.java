package net.SpectrumFATM.black_archive.fabric.entity.custom;

import net.SpectrumFATM.black_archive.fabric.item.ModItems;
import net.SpectrumFATM.black_archive.fabric.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import whocraft.tardis_refined.registry.TRItemRegistry;

public class DalekEntity extends HostileEntity implements RangedAttackMob {
    public DalekEntity(EntityType<DalekEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new ProjectileAttackGoal(this, 2.0D, 40, 15.0F));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 5.0F, 0.02F));
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(4, new GoToWalkTargetGoal(this, 2.0D));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, LivingEntity.class, 10, true, false,
                livingEntity -> !(livingEntity instanceof DalekEntity || livingEntity instanceof DalekPuppetEntity)));
    }

    public static DefaultAttributeContainer.Builder createDalekAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0)
                .add(EntityAttributes.GENERIC_ARMOR, 10.0)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 5.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 8.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        super.playStepSound(pos, state);
        this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(8), entity -> true)
                .forEach(entity -> entity.playSound(ModSounds.DALEK_MOVE, 0.01F, 1.0F));
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_IRON_GOLEM_HURT;
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        LaserEntity laser = new LaserEntity(this.getWorld(), this, 2.0f, false, 0, 0, 255);
        double d0 = target.getY() + (target.getHeight() / 2.0) - laser.getY();
        double d1 = target.getX() - this.getX();
        double d2 = d0;
        double d3 = target.getZ() - this.getZ();
        laser.setVelocity(d1, d2, d3, 1.6f, 0.0f);
        this.playSound(ModSounds.DALEK_LASER, 0.5f, 1.0f);
        this.getWorld().spawnEntity(laser);
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        if (!this.getWorld().isClient) {
            if (random.nextInt(20) == 1) {
                this.dropStack(new ItemStack(ModItems.DALEK_LASER_GUN, 1));
            } else {
                this.dropStack(new ItemStack(Items.COPPER_BLOCK, 2));
                this.dropStack(new ItemStack(TRItemRegistry.RAW_ZEITON.get(), 1));
            }

            if (random.nextInt(20) == 1) {
                this.dropStack(new ItemStack(ModItems.DALEK_BRACELET, 1));
            }
        }
    }
}