package net.SpectrumFATM.black_archive.entity.custom;

import net.SpectrumFATM.BlackArchive;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class DalekPuppetEntity extends HostileEntity {
    private static final TrackedData<Optional<UUID>> PLAYER_UUID = DataTracker.registerData(DalekPuppetEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private int skinId;

    public DalekPuppetEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);

        Random random = new Random();
        this.skinId = random.nextInt(8); // Randomly assigns skinId between 0 and 7
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        // Initialize the DataTracker for the UUID with an empty optional (no UUID initially)
        this.dataTracker.startTracking(PLAYER_UUID, Optional.empty());
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
                livingEntity -> !(livingEntity instanceof DalekEntity || DalekPuppetEntity.class.isAssignableFrom(livingEntity.getClass()))));
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

    // Save player UUID and skinId to NBT
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        // Save the UUID to NBT if present
        if (this.getPlayerUUID() != null) {
            nbt.putUuid("playerUUID", this.getPlayerUUID());
        }
    }

    // Read player UUID and skinId from NBT
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        // Load the UUID from NBT if present
        if (nbt.containsUuid("playerUUID")) {
            this.setPlayerUUID(nbt.getUuid("playerUUID"));
        }
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.dataTracker.set(PLAYER_UUID, Optional.ofNullable(playerUUID));
    }

    public UUID getPlayerUUID() {
        // Retrieve the UUID from the DataTracker
        return this.dataTracker.get(PLAYER_UUID).orElse(null);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        // Handle when the PLAYER_UUID is updated
        if (PLAYER_UUID.equals(data)) {
            BlackArchive.LOGGER.info("Synchronized player UUID: " + getPlayerUUID());
        }
    }

    public int getSkinId() {
        return this.skinId;
    }
}