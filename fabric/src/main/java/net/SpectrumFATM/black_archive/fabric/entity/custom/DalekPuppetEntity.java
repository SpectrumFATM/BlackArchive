package net.SpectrumFATM.black_archive.fabric.entity.custom;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.entity.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Random;

public class DalekPuppetEntity extends HostileEntity {

    int skinId;

    public DalekPuppetEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        BlackArchive.LOGGER.info("DalekSlaveEntity initialized");

        Random random = new Random();
        skinId = random.nextInt(8);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 5.0F, 0.02F));
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(4, new GoToWalkTargetGoal(this, 2.0D));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, LivingEntity.class, 10, true, false,
                livingEntity -> !(livingEntity instanceof DalekEntity || livingEntity instanceof DalekPuppetEntity)));
    }

    public static DefaultAttributeContainer.Builder createDalekSlaveAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

    @Override
    public boolean shouldRenderName() {
        return false;
    }

    @Override
    public boolean isCustomNameVisible() {
        return false;
    }

    public Identifier getSkin() {
        switch (this.skinId) {
            case 0:
                return new Identifier("minecraft", "textures/entity/player/wide/alex.png");
            case 1:
                return new Identifier("minecraft", "textures/entity/player/wide/ari.png");
            case 2:
                return new Identifier("minecraft", "textures/entity/player/wide/efe.png");
            case 3:
                return new Identifier("minecraft", "textures/entity/player/wide/kai.png");
            case 4:
                return new Identifier("minecraft", "textures/entity/player/wide/makena.png");
            case 5:
                return new Identifier("minecraft", "textures/entity/player/wide/noor.png");
            case 6:
                return new Identifier("minecraft", "textures/entity/player/wide/sunny.png");
            case 7:
                return new Identifier("minecraft", "textures/entity/player/wide/zuri.png");
            default:
                return new Identifier("minecraft", "textures/entity/player/wide/steve.png");
        }
    }
}