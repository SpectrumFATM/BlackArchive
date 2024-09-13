package net.SpectrumFATM.black_archive.fabric.entity.custom;

import net.SpectrumFATM.black_archive.fabric.entity.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class LaserEntity extends ThrownEntity {
    public LaserEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
    }

    public LaserEntity(World world, double x, double y, double z) {
        this(ModEntities.LASER, world);
        this.setPosition(x, y, z);
    }

    public LaserEntity(World world, LivingEntity owner) {
        this(world, owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
        this.setOwner(owner);
        this.setNoGravity(true);
    }

    @Override
    protected float getGravity() {
        return 0.0f;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().damage(entityHitResult.getEntity().getDamageSources().generic(), 1.0f);
        this.kill();
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);

    }
}