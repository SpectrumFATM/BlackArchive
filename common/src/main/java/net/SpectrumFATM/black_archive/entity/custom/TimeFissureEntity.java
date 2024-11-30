package net.SpectrumFATM.black_archive.entity.custom;

import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.world.dimension.ModDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Arm;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.Random;

public class TimeFissureEntity extends LivingEntity {

    private final Random random = new Random();

    public TimeFissureEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createTimeFissureAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return true; // Make the entity immune to all damage sources
    }

    @Override
    public boolean isCollidable() {
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
    public boolean shouldRender(double distance) {
        return true;
    }

    // Particle effects
    private void spawnSmokeCloud() {
        if (!this.getWorld().isClient) {
            ((ServerWorld) this.getWorld()).spawnParticles(
                    ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    this.getX(), this.getY() + 1, this.getZ(),
                    20, 0.25, 0.25, 0.25, 0.1
            );
        }
    }

    private void spawnGlowParticles() {
        if (!this.getWorld().isClient) {
            ((ServerWorld) this.getWorld()).spawnParticles(
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
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        spawnSmokeCloud();
    }

    @Override
    public void remove(RemovalReason reason) {
        // Ensure this code only runs on the server
        if (!this.getWorld().isClient) {
            spawnSmokeCloud();  // Spawn smoke before removal
        }
        playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
        super.remove(reason);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        // Ensure this code only runs on the server
        if (!this.getWorld().isClient) {
            spawnSmokeCloud();  // Spawn smoke before calling remove()
        }
        playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
        super.onDeath(damageSource);
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        super.onPlayerCollision(player);
        if (!this.getWorld().isClient && player instanceof ServerPlayerEntity) {
            ServerWorld targetWorld = player.getServer().getWorld(ModDimensions.TIMEDIM_LEVEL_KEY);
            ((ServerPlayerEntity) player).teleport(targetWorld, player.getX(), player.getWorld().getTopY(), player.getZ(), player.getYaw(), player.getPitch());

            playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
            ((ServerWorld) this.getWorld()).spawnParticles(
                    ParticleTypes.GLOW, player.getX(), player.getY() + 1, player.getZ(),
                    10, 0.2, 0.2, 0.2, 0.1
            );
        }
    }

    public void aggrovate() {
        if (this.getWorld().isClient) return;

        EntityType<?>[] possibleEntities = new EntityType<?>[]{
                ModEntities.DALEK.get(), ModEntities.CYBERMAT.get(), ModEntities.CYBERMAN.get()
        };

        for (int i = 0; i < 4; i++) {
            spawnRandomEntity(possibleEntities);
        }

        if (random.nextInt(50) == 1) {
            spawnAdditionalTimeFissures(3);
        }

        playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
    }

    private void spawnRandomEntity(EntityType<?>[] possibleEntities) {
        World world = this.getWorld();
        EntityType<?> chosenEntityType = possibleEntities[random.nextInt(possibleEntities.length)];
        double offsetX = (random.nextDouble() * 10) - 5;
        double offsetY = random.nextDouble() * 2;
        double offsetZ = (random.nextDouble() * 10) - 5;
        BlockPos spawnPos = new BlockPos((int) (this.getX() + offsetX), (int) (this.getY() + offsetY), (int) (this.getZ() + offsetZ));

        LivingEntity spawnedEntity = (LivingEntity) chosenEntityType.create(world);
        if (spawnedEntity != null) {
            spawnedEntity.refreshPositionAndAngles(spawnPos, random.nextFloat() * 360, 0);
            ((ServerWorld) world).spawnParticles(
                    ParticleTypes.CAMPFIRE_COSY_SMOKE, spawnedEntity.getX(), spawnedEntity.getY() + 1, spawnedEntity.getZ(),
                    20, 0.25, 0.25, 0.25, 0.1
            );
            world.spawnEntity(spawnedEntity);
        }
    }

    private void spawnAdditionalTimeFissures(int count) {
        World world = this.getWorld();
        for (int i = 0; i < count; i++) {
            TimeFissureEntity fissureEntity = new TimeFissureEntity(ModEntities.TIME_FISSURE.get(), world);
            fissureEntity.refreshPositionAndAngles(this.getX() + random.nextInt(16) - 8, this.getY(), this.getZ() + random.nextInt(16) - 8, random.nextFloat() * 360.0f, 0);
            world.spawnEntity(fissureEntity);
        }
    }

    // Custom data handling (if needed)
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) { }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) { }

    // Equipment-related overrides
    @Override
    public Iterable<ItemStack> getArmorItems() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) { }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }
}
