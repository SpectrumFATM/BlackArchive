package net.SpectrumFATM.black_archive.entity.custom;

import net.SpectrumFATM.BlackArchive;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.level.Level;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class DalekPuppetEntity extends Monster {
    private static final EntityDataAccessor<Optional<UUID>> PLAYER_UUID = SynchedEntityData.defineId(DalekPuppetEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private int skinId;

    public DalekPuppetEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);

        Random random = new Random();
        this.skinId = random.nextInt(8); // Randomly assigns skinId between 0 and 7
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        // Initialize the DataTracker for the UUID with an empty optional (no UUID initially)
        this.entityData.define(PLAYER_UUID, Optional.empty());
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 5.0F, 0.02F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 2.0D));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false,
                livingEntity -> !(livingEntity instanceof DalekEntity || DalekPuppetEntity.class.isAssignableFrom(livingEntity.getClass()))));
    }

    public static AttributeSupplier.Builder createDalekSlaveAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    public boolean shouldShowName() {
        return false;
    }

    @Override
    public boolean isCustomNameVisible() {
        return false;
    }

    // Save player UUID and skinId to NBT
    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        // Save the UUID to NBT if present
        if (this.getPlayerUUID() != null) {
            nbt.putUUID("playerUUID", this.getPlayerUUID());
        }
    }

    // Read player UUID and skinId from NBT
    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        // Load the UUID from NBT if present
        if (nbt.hasUUID("playerUUID")) {
            this.setPlayerUUID(nbt.getUUID("playerUUID"));
        }
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.entityData.set(PLAYER_UUID, Optional.ofNullable(playerUUID));
    }

    public UUID getPlayerUUID() {
        // Retrieve the UUID from the DataTracker
        return this.entityData.get(PLAYER_UUID).orElse(null);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        super.onSyncedDataUpdated(data);
        // Handle when the PLAYER_UUID is updated
        if (PLAYER_UUID.equals(data)) {
            BlackArchive.LOGGER.info("Synchronized player UUID: " + getPlayerUUID());
        }
    }

    public int getSkinId() {
        return this.skinId;
    }
}