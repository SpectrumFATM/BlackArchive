package net.SpectrumFATM.black_archive.entity.custom;

import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class LaserEntity extends ThrowableProjectile {

    private static final EntityDataAccessor<Integer> RED = SynchedEntityData.defineId(LaserEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GREEN = SynchedEntityData.defineId(LaserEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BLUE = SynchedEntityData.defineId(LaserEntity.class, EntityDataSerializers.INT);

    private float damage;
    private boolean shouldDamageBlocks;
    private int lifeTime;

    public LaserEntity(EntityType<? extends ThrowableProjectile> entityType, Level world) {
        super(entityType, world);
        this.lifeTime = 200; // 10 seconds (20 ticks per second)
    }

    public LaserEntity(Level world, double x, double y, double z, int r, int g, int b) {
        this(ModEntities.LASER.get(), world);
        this.setPos(x, y, z);
        this.entityData.set(RED, r);
        this.entityData.set(GREEN, g);
        this.entityData.set(BLUE, b);
    }

    public LaserEntity(Level world, LivingEntity owner, float damage, boolean shouldDamageBlocks, int r, int g, int b) {
        this(world, owner.getX(), owner.getEyeY() - 0.1, owner.getZ(), r, g, b);
        this.setOwner(owner);
        this.setNoGravity(true);
        this.damage = damage;
        this.shouldDamageBlocks = shouldDamageBlocks;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(RED, 255);
        this.entityData.define(GREEN, 255);
        this.entityData.define(BLUE, 255);
    }

    public int getRed() {
        return this.entityData.get(RED);
    }

    public int getGreen() {
        return this.entityData.get(GREEN);
    }

    public int getBlue() {
        return this.entityData.get(BLUE);
    }

    @Override
    protected float getGravity() {
        return 0.0f;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        entityHitResult.getEntity().hurt(entityHitResult.getEntity().damageSources().generic(), this.damage);
        this.kill();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        BlockState state = this.level().getBlockState(blockHitResult.getBlockPos());

        if (state.getBlock() instanceof DoorBlock && BlackArchiveConfig.COMMON.shouldDalekGunStickDestroyDoors.get()) {
            BlockPos pos = blockHitResult.getBlockPos();
            this.level().addParticle(ParticleTypes.EXPLOSION, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
            level().playSound(null, pos, SoundEvents.GENERIC_EXPLODE, SoundSource.AMBIENT, 1.2f, 1.0f);
            if (shouldDamageBlocks) {
                level().setBlock(pos, Blocks.AIR.defaultBlockState(), DoorBlock.UPDATE_ALL);
            }
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        double particleX = pos.getX() + 0.5 + x;
                        double particleY = pos.getY() + 0.5 + y;
                        double particleZ = pos.getZ() + 0.5 + z;
                        double dirX = particleX - (pos.getX() + 0.5);
                        double dirY = particleY - (pos.getY() + 0.5);
                        double dirZ = particleZ - (pos.getZ() + 0.5);
                        double length = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
                        dirX /= length;
                        dirY /= length;
                        dirZ /= length;
                        this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, particleX, particleY, particleZ, dirX * 0.1, dirY * 0.1, dirZ * 0.1);
                    }
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.lifeTime-- <= 0) {
            this.kill();
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
