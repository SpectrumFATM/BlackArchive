package net.SpectrumFATM.black_archive.network;

import dev.architectury.networking.NetworkManager;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.entity.custom.TimeFissureEntity;
import net.SpectrumFATM.black_archive.sound.ModSounds;
import net.SpectrumFATM.black_archive.util.SpaceTimeEventUtil;
import net.SpectrumFATM.black_archive.util.WorldUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class VortexTeleportPacket {
    public static final Identifier ID = new Identifier(BlackArchive.MOD_ID, "vortex_teleport");
    private static final int COOLDOWN_TIME = BlackArchiveConfig.COMMON.vortexManipulatorCooldown.get() * 20;

    private final double x, y, z;
    private final String dimension;

    public VortexTeleportPacket(double x, double y, double z, String dimension) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
    }

    public VortexTeleportPacket(PacketByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.dimension = buf.readString(32767);
    }

    public void encode(PacketByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeString(dimension, 32767);
    }

    public void handle(ServerPlayerEntity player) {
        SpaceTimeEventUtil.setComplexSpaceTimeEvent(player, true);

        if (WorldUtil.isTardisesInRange(player.getServerWorld(), player.getBlockPos(), 10, BlackArchiveConfig.COMMON.minimumTardisesToCreateTimeFissure.get())) {
            Random random = new Random();
            TimeFissureEntity fissureEntity = new TimeFissureEntity(ModEntities.TIME_FISSURE.get(), player.getServerWorld());

            fissureEntity.refreshPositionAndAngles(player.getX() + player.getRandom().nextInt(16) - 8, player.getY(), player.getZ() + player.getRandom().nextInt(16) - 8, random.nextFloat() * 360.0f, 0);
            player.getWorld().spawnEntity(fissureEntity);
            fissureEntity.playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
            player.sendMessage(Text.translatable("vortex_manipulator.black_archive.time_fissure").formatted(Formatting.RED), true);
            return;
        }

        if (player.getItemCooldownManager().isCoolingDown(player.getMainHandStack().getItem())) {
            player.sendMessage(Text.translatable("vortex_manipulator.black_archive.cooldown").formatted(Formatting.RED), true);
            return;
        }

        Identifier dimensionId = new Identifier(dimension);
        RegistryKey<World> dimensionKey = RegistryKey.of(RegistryKeys.WORLD, dimensionId);
        ServerWorld targetWorld = player.getServer().getWorld(dimensionKey);

        if (targetWorld != null) {
            player.getServerWorld().spawnParticles(ParticleTypes.SMOKE, player.getX(), player.getY(), player.getZ(), 10, 0.5, 0.5, 0.5, 0.0);
            player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.VORTEX_TP.get(), SoundCategory.PLAYERS, 0.25f, 1.0f);

            BlockPos safePos = WorldUtil.findSafeLandingPos(targetWorld, x, y, z);

            if (safePos != null) {
                player.teleport(targetWorld, safePos.getX(), safePos.getY(), safePos.getZ(), player.getYaw(), player.getPitch());
                targetWorld.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.VORTEX_TP.get(), SoundCategory.PLAYERS, 0.25f, 1.0f);
                if (!player.isCreative()) {
                    player.getItemCooldownManager().set(player.getMainHandStack().getItem(), COOLDOWN_TIME);
                }
            } else {
                player.sendMessage(Text.translatable("vortex_manipulator.black_archive.landing_error").formatted(Formatting.RED), true);
            }
        }
    }

    public static void send(double x, double y, double z, String dimension) {
        PacketByteBuf buf = new PacketByteBuf(io.netty.buffer.Unpooled.buffer());
        new VortexTeleportPacket(x, y, z, dimension).encode(buf);
        NetworkManager.sendToServer(ID, buf);
    }
}
