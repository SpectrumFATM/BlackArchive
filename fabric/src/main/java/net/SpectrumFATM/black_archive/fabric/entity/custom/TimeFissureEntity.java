package net.SpectrumFATM.black_archive.fabric.entity.custom;

import net.SpectrumFATM.black_archive.fabric.world.dimension.ModDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class TimeFissureEntity extends Entity {

    World world;

    public TimeFissureEntity(EntityType<? extends TimeFissureEntity> entityType, World world) {
        super(entityType, world);
        this.setGlowing(true);
        this.world = world;
    }

    @Override
    protected void initDataTracker() {
        // Initialize entity data here if needed
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        // Read custom data here if needed
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        // Write custom data here if needed
    }

    @Override
    public void tick() {
        super.tick();

        // Spawn glow particles around the portal every tick
        if (!world.isClient) {
            spawnGlowParticles();
        }
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);

        // Cloud of smoke when the portal spawns
        spawnSmokeCloud();
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);

        // Cloud of smoke when the portal is removed
        if (!world.isClient) {
            spawnSmokeCloud();
        }

        playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
    }

    private void spawnSmokeCloud() {
        if (!world.isClient) {
            ((ServerWorld) world).spawnParticles(
                    ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    this.getX(), this.getY() + 1, this.getZ(),
                    20, // Number of particles
                    0.25, 0.25, 0.25, // Area in which particles spread
                    0.1 // Speed
            );
        }
    }

    private void spawnGlowParticles() {
        if (!world.isClient) {
            ((ServerWorld) world).spawnParticles(
                    ParticleTypes.GLOW, // Using glow particle type
                    this.getX(), this.getY() + 1, this.getZ(),
                    1, // Number of particles
                    4.0, 4.0, 4.0, // Spread in X, Y, Z directions
                    0.01 // Speed, kept low to make it appear "glowy"
            );
        }
    }

    @Override
    public boolean shouldRender(double distance) {
        return true; // Render entity regardless of distance
    }

    @Override
    public boolean collidesWith(Entity entity) {

        return super.collidesWith(entity);
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        super.onPlayerCollision(player);

        if (!world.isClient) {
            ServerWorld targetWorld = player.getServer().getWorld(ModDimensions.TIMEDIM_LEVEL_KEY);
            ((ServerPlayerEntity) player).teleport(targetWorld, player.getX(), player.getWorld().getTopY(), player.getZ(), player.getYaw(), player.getPitch());

            this.playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);

            ((ServerWorld) world).spawnParticles(
                    ParticleTypes.GLOW, // Particle effect on collision
                    player.getX(), player.getY() + 1, player.getZ(),
                    10, // Number of particles
                    0.2, 0.2, 0.2, // Spread
                    0.1 // Speed
            );
        }
    }
}