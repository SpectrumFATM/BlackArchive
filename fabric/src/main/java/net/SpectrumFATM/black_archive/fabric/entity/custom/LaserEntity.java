package net.SpectrumFATM.black_archive.fabric.entity.custom;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.fabric.entity.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LaserEntity extends ThrownEntity {

    private static final TrackedData<Integer> RED = DataTracker.registerData(LaserEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> GREEN = DataTracker.registerData(LaserEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> BLUE = DataTracker.registerData(LaserEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private float damage;
    private boolean shouldDamageBlocks;
    private int lifeTime;

    public LaserEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
        this.lifeTime = 200; // 10 seconds (20 ticks per second)
    }

    public LaserEntity(World world, double x, double y, double z, int red, int green, int blue) {
        this(ModEntities.LASER, world);
        this.setPosition(x, y, z);
        this.dataTracker.set(RED, red);
        this.dataTracker.set(GREEN, green);
        this.dataTracker.set(BLUE, blue);
    }

    public LaserEntity(World world, LivingEntity owner, float damage, boolean shouldDamageBlocks, int red, int green, int blue) {
        this(world, owner.getX(), owner.getEyeY() - 0.1, owner.getZ(), red, green, blue);
        this.setOwner(owner);
        this.setNoGravity(true);
        this.damage = damage;
        this.shouldDamageBlocks = shouldDamageBlocks;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(RED, 0);
        this.dataTracker.startTracking(GREEN, 0);
        this.dataTracker.startTracking(BLUE, 255);
    }

    public int getRed() {
        return this.dataTracker.get(RED);
    }

    public int getGreen() {
        return this.dataTracker.get(GREEN);
    }

    public int getBlue() {
        return this.dataTracker.get(BLUE);
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

        if (state.getBlock() instanceof DoorBlock && BlackArchiveConfig.COMMON.shouldDalekGunStickDestroyDoors.get()) {
            BlockPos pos = blockHitResult.getBlockPos();
            this.getWorld().addParticle(ParticleTypes.EXPLOSION, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
            getWorld().playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 1.2f, 1.0f);
            if (shouldDamageBlocks) {
                getWorld().setBlockState(pos, Blocks.AIR.getDefaultState(), DoorBlock.NOTIFY_ALL);
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
                        this.getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, particleX, particleY, particleZ, dirX * 0.1, dirY * 0.1, dirZ * 0.1);
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
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Red", this.getDataTracker().get(RED));
        nbt.putFloat("Green", this.getDataTracker().get(GREEN));
        nbt.putFloat("Blue", this.getDataTracker().get(BLUE));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Red")) {
            this.getDataTracker().set(RED, nbt.getInt("Red"));
        }

        if (nbt.contains("Green")) {
            this.getDataTracker().set(RED, nbt.getInt("Red"));
        }

        if (nbt.contains("Blue")) {
            this.getDataTracker().set(RED, nbt.getInt("Red"));
        }
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
