package net.SpectrumFATM.black_archive.entity.custom;

import net.SpectrumFATM.black_archive.effects.ModEffects;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CybermatEntity extends HostileEntity {
    public CybermatEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true)); // Melee attack goal
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 5.0F, 0.02F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new GoToWalkTargetGoal(this, 2.5D));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, LivingEntity.class, 10, true, false,
                livingEntity -> !(livingEntity instanceof CybermanEntity || livingEntity instanceof CybermatEntity)));
    }

    public static DefaultAttributeContainer.Builder createCyberAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ARMOR, 10.0)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 5.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 8.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0); // Increased attack damage for melee
    }

    @Override
    public boolean tryAttack(Entity target) {

        if (random.nextInt(1000) == 1 && target instanceof PlayerEntity player) {
           applyInfinitePotionEffect(player);
        }

        return super.tryAttack(target);
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        if (!this.getWorld().isClient) {
            if (random.nextInt(20) == 1) {
                //this.dropStack(new ItemStack(ModItems.CYBERMAT_EGG.get(), 1));
            } else {
                this.dropStack(new ItemStack(ModItems.STEEL_INGOT.get(), 1));
            }
        }
    }

    public static void applyInfinitePotionEffect(PlayerEntity player) {
        StatusEffectInstance infiniteEffect = new StatusEffectInstance(
                ModEffects.CYBER_CONVERSION.get(),
                -1,
                0,
                false,
                false,
                true
        );
        player.addStatusEffect(infiniteEffect); // Apply the effect to the player
    }
}