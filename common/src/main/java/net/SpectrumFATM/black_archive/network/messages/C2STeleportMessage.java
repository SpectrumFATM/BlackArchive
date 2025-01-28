package net.SpectrumFATM.black_archive.network.messages;

import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.entity.custom.TimeFissureEntity;
import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.SpectrumFATM.black_archive.sound.ModSounds;
import net.SpectrumFATM.black_archive.util.SpaceTimeEventUtil;
import net.SpectrumFATM.black_archive.util.WorldUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;

import java.util.Random;

public class C2STeleportMessage extends MessageC2S {

    private static final int COOLDOWN_TIME = BlackArchiveConfig.COMMON.vortexManipulatorCooldown.get() * 20;

    private final double x;
    private final double y;
    private final double z;
    private final String dimension;

    public C2STeleportMessage(double x, double y, double z, String dimension) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
    }

    public C2STeleportMessage(FriendlyByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.dimension = buf.readUtf();
    }

    @Override
    public MessageType getType() {
        return BlackArchiveNetworkHandler.VM_TELEPORT;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeUtf(dimension);
    }

    @Override
    public void handle(MessageContext messageContext) {
        ServerPlayer player = messageContext.getPlayer();
        SpaceTimeEventUtil.setComplexSpaceTimeEvent(player, true);

        if (WorldUtil.isTardisesInRange(player.serverLevel(), player.blockPosition(), 10, BlackArchiveConfig.COMMON.minimumTardisesToCreateTimeFissure.get())) {
            Random random = new Random();
            TimeFissureEntity fissureEntity = new TimeFissureEntity(ModEntities.TIME_FISSURE.get(), player.serverLevel());

            fissureEntity.moveTo(player.getX() + player.getRandom().nextInt(16) - 8, player.getY(), player.getZ() + player.getRandom().nextInt(16) - 8, random.nextFloat() * 360.0f, 0);
            player.level().addFreshEntity(fissureEntity);
            fissureEntity.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
            player.displayClientMessage(Component.translatable("vortexmanipulator.black_archive.time_fissure").withStyle(ChatFormatting.RED), true);
            return;
        }

        if (player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem())) {
            player.displayClientMessage(Component.translatable("vortexmanipulator.black_archive.cooldown").withStyle(ChatFormatting.RED), true);
            return;
        }

        ResourceLocation dimensionId = new ResourceLocation(dimension);
        ResourceKey<Level> dimensionKey = ResourceKey.create(Registries.DIMENSION, dimensionId);
        ServerLevel targetWorld = player.getServer().getLevel(dimensionKey);

        if (targetWorld != null) {
            player.serverLevel().sendParticles(ParticleTypes.SMOKE, player.getX(), player.getY(), player.getZ(), 10, 0.5, 0.5, 0.5, 0.0);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.VORTEX_TP.get(), SoundSource.PLAYERS, 0.25f, 1.0f);

            BlockPos safePos = WorldUtil.findSafeLandingPos(targetWorld, x, y, z);

            if (safePos != null) {
                player.teleportTo(targetWorld, safePos.getX(), safePos.getY(), safePos.getZ(), player.getYRot(), player.getXRot());
                targetWorld.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.VORTEX_TP.get(), SoundSource.PLAYERS, 0.25f, 1.0f);
                if (!player.isCreative()) {
                    player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), COOLDOWN_TIME);
                }
            } else {
                player.displayClientMessage(Component.translatable("vortexmanipulator.black_archive.landing_error").withStyle(ChatFormatting.RED), true);
            }
        }
    }


}
