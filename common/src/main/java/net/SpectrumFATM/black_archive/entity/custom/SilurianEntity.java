package net.SpectrumFATM.black_archive.entity.custom;

import net.SpectrumFATM.black_archive.item.ModItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class SilurianEntity extends Monster {
    public SilurianEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1D));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 5.0F, 0.02F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false,
                livingEntity -> (livingEntity instanceof Player || livingEntity instanceof Villager || livingEntity instanceof Pillager)));
        this.targetSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
    }

    public static AttributeSupplier.Builder createSilurianAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.FOLLOW_RANGE, 20.0);
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);

        if(random.nextInt(10) == 1) {
            this.spawnAtLocation(new ItemStack(ModItems.SILURIAN_SPAWN_EGG.get()), 1);
        }

        if (random.nextInt(20) == 1) {
            this.spawnAtLocation(new ItemStack(ModItems.SILURIAN_GUN.get()), 1);
        }
    }
}
