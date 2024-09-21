package net.SpectrumFATM.black_archive.fabric.entity.custom;

import net.SpectrumFATM.black_archive.fabric.entity.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LaserEntity extends ThrownEntity {

    private float damage;
    private boolean shouldDamageBlocks;

    public LaserEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
    }

    public LaserEntity(World world, double x, double y, double z) {
        this(ModEntities.LASER, world);
        this.setPosition(x, y, z);
    }

    public LaserEntity(World world, LivingEntity owner, float damage, boolean shouldDamageBlocks) {
        this(world, owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
        this.setOwner(owner);
        this.setNoGravity(true);
        this.damage = damage;
        this.shouldDamageBlocks = shouldDamageBlocks;
    }

    @Override
    protected float getGravity() {
        return 0.0f;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().damage(entityHitResult.getEntity().getDamageSources().generic(), this.damage);
        this.kill();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        BlockState state = this.getWorld().getBlockState(blockHitResult.getBlockPos());

        if (state.getBlock() instanceof DoorBlock) {
            // Get the position where the block was hit
            BlockPos pos = blockHitResult.getBlockPos();

            // Play explosion particle effect
            this.getWorld().addParticle(ParticleTypes.EXPLOSION,
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    0.0, 0.0, 0.0);

            getWorld().playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 1.2f, 1.0f);
            if (shouldDamageBlocks) {
                getWorld().setBlockState(pos, Blocks.AIR.getDefaultState(), DoorBlock.NOTIFY_ALL);
            }

            // Add smoke particles in a 3-block radius
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        double particleX = pos.getX() + 0.5 + x;
                        double particleY = pos.getY() + 0.5 + y;
                        double particleZ = pos.getZ() + 0.5 + z;

                        // Calculate direction vector
                        double dirX = particleX - (pos.getX() + 0.5);
                        double dirY = particleY - (pos.getY() + 0.5);
                        double dirZ = particleZ - (pos.getZ() + 0.5);

                        // Normalize direction vector
                        double length = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
                        dirX /= length;
                        dirY /= length;
                        dirZ /= length;

                        // Add particle with velocity
                        this.getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                                particleX, particleY, particleZ,
                                dirX * 0.1, dirY * 0.1, dirZ * 0.1);
                    }
                }
            }
        }
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);

    }
}