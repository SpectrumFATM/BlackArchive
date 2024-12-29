package net.SpectrumFATM.black_archive.entity.custom;

import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.world.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import java.util.Collections;
import java.util.Random;

public class TimeFissureEntity extends LivingEntity {

    private final Random random = new Random();

    public TimeFissureEntity(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createTimeFissureAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.0);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return true; // Make the entity immune to all damage sources
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    // Prevent rendering distance cutoff
    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    // Particle effects
    private void spawnSmokeCloud() {
        if (!this.level().isClientSide) {
            ((ServerLevel) this.level()).sendParticles(
                    ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    this.getX(), this.getY() + 1, this.getZ(),
                    20, 0.25, 0.25, 0.25, 0.1
            );
        }
    }

    private void spawnGlowParticles() {
        if (!this.level().isClientSide) {
            ((ServerLevel) this.level()).sendParticles(
                    ParticleTypes.GLOW,
                    this.getX(), this.getY() + 1, this.getZ(),
                    1, 4.0, 4.0, 4.0, 0.01
            );
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (random.nextInt(1000) == 1) {
            aggrovate();
        }
        spawnGlowParticles();
    }

    // Event handling
    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        spawnSmokeCloud();
    }

    @Override
    public void remove(RemovalReason reason) {
        // Ensure this code only runs on the server
        if (!this.level().isClientSide) {
            spawnSmokeCloud();  // Spawn smoke before removal
        }
        playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
        super.remove(reason);
    }

    @Override
    public void die(DamageSource damageSource) {
        // Ensure this code only runs on the server
        if (!this.level().isClientSide) {
            spawnSmokeCloud();  // Spawn smoke before calling remove()
        }
        playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
        super.die(damageSource);
    }

    @Override
    public void playerTouch(Player player) {
        super.playerTouch(player);
        if (!this.level().isClientSide && player instanceof ServerPlayer) {
            ServerLevel targetWorld = player.getServer().getLevel(ModDimensions.TIMEDIM_LEVEL_KEY);
            ((ServerPlayer) player).teleportTo(targetWorld, player.getX(), player.level().getMaxBuildHeight(), player.getZ(), player.getYRot(), player.getXRot());

            playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
            ((ServerLevel) this.level()).sendParticles(
                    ParticleTypes.GLOW, player.getX(), player.getY() + 1, player.getZ(),
                    10, 0.2, 0.2, 0.2, 0.1
            );
        }
    }

    public void aggrovate() {
        if (this.level().isClientSide) return;

        EntityType<?>[] possibleEntities = new EntityType<?>[]{
                ModEntities.DALEK.get(), ModEntities.CYBERMAT.get(), ModEntities.CYBERMAN.get()
        };

        for (int i = 0; i < 4; i++) {
            spawnRandomEntity(possibleEntities);
        }

        if (random.nextInt(50) == 1) {
            spawnAdditionalTimeFissures(3);
        }

        playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
    }

    private void spawnRandomEntity(EntityType<?>[] possibleEntities) {
        Level world = this.level();
        EntityType<?> chosenEntityType = possibleEntities[random.nextInt(possibleEntities.length)];
        double offsetX = (random.nextDouble() * 10) - 5;
        double offsetY = random.nextDouble() * 2;
        double offsetZ = (random.nextDouble() * 10) - 5;
        BlockPos spawnPos = new BlockPos((int) (this.getX() + offsetX), (int) (this.getY() + offsetY), (int) (this.getZ() + offsetZ));

        LivingEntity spawnedEntity = (LivingEntity) chosenEntityType.create(world);
        if (spawnedEntity != null) {
            spawnedEntity.moveTo(spawnPos, random.nextFloat() * 360, 0);
            ((ServerLevel) world).sendParticles(
                    ParticleTypes.CAMPFIRE_COSY_SMOKE, spawnedEntity.getX(), spawnedEntity.getY() + 1, spawnedEntity.getZ(),
                    20, 0.25, 0.25, 0.25, 0.1
            );
            world.addFreshEntity(spawnedEntity);
        }
    }

    private void spawnAdditionalTimeFissures(int count) {
        Level world = this.level();
        for (int i = 0; i < count; i++) {
            TimeFissureEntity fissureEntity = new TimeFissureEntity(ModEntities.TIME_FISSURE.get(), world);
            fissureEntity.moveTo(this.getX() + random.nextInt(16) - 8, this.getY(), this.getZ() + random.nextInt(16) - 8, random.nextFloat() * 360.0f, 0);
            world.addFreshEntity(fissureEntity);
        }
    }

    // Custom data handling (if needed)
    @Override
    public void readAdditionalSaveData(CompoundTag nbt) { }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) { }

    // Equipment-related overrides
    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) { }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }
}
