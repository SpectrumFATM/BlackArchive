package net.SpectrumFATM.black_archive.entity.custom;

import net.SpectrumFATM.black_archive.effects.ModEffects;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CybermatEntity extends Monster {
    public CybermatEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true)); // Melee attack goal
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 5.0F, 0.02F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 2.5D));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false,
                livingEntity -> !(livingEntity instanceof CybermanEntity || livingEntity instanceof CybermatEntity)));
    }

    public static AttributeSupplier.Builder createCyberAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.ARMOR, 10.0)
                .add(Attributes.ARMOR_TOUGHNESS, 5.0)
                .add(Attributes.FOLLOW_RANGE, 8.0)
                .add(Attributes.ATTACK_DAMAGE, 6.0); // Increased attack damage for melee
    }

    @Override
    public boolean doHurtTarget(Entity target) {

        if (random.nextInt(1000) == 1 && target instanceof Player player) {
           applyInfinitePotionEffect(player);
        }

        return super.doHurtTarget(target);
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (!this.level().isClientSide) {
            if (random.nextInt(20) == 1) {
                this.spawnAtLocation(new ItemStack(ModItems.CYBERMAT_EGG.get(), 1));
            } else {
                this.spawnAtLocation(new ItemStack(ModItems.STEEL_INGOT.get(), 1));
            }
        }
    }

    public static void applyInfinitePotionEffect(Player player) {
        MobEffectInstance infiniteEffect = new MobEffectInstance(
                ModEffects.CYBER_CONVERSION.get(),
                -1,
                0,
                false,
                false,
                true
        );
        player.addEffect(infiniteEffect); // Apply the effect to the player
    }
}