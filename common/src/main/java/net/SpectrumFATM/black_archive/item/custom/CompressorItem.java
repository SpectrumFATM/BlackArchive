package net.SpectrumFATM.black_archive.item.custom;

import net.SpectrumFATM.black_archive.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import java.util.Random;

public class CompressorItem extends TooltipItem {
    public CompressorItem(Properties settings, String tooltipKey) {
        super(settings, tooltipKey);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {

        Random random = new Random();

        BlockPos pos = entity.blockPosition();
        entity.level().addParticle(
                ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,
                pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                0.0, 0.05, 0.0
        );

        for (int i = 0; i < 5; i++) {
            entity.level().addParticle(
                    ParticleTypes.SMOKE,
                    pos.getX() + (random.nextInt(10) * 0.1), pos.getY() + (random.nextInt(10) * 0.1), (random.nextInt(10) * 0.1),
                    0.0, 0.075, 0.0
            );
        }

        if (!entity.level().isClientSide) {

            CompoundTag entityNbt = new CompoundTag();
            entity.saveWithoutId(entityNbt);

            if (entityNbt.getFloat("Scale") == 1.0f) {
                entityNbt.putFloat("Scale", 0.1f);

                if (!(entity instanceof Player)) {
                    entityNbt.putBoolean("NoAI", true);
                }

                entity.readAdditionalSaveData(entityNbt);

                if (entity instanceof Player) {
                    entity.hurt(entity.damageSources().generic(), 4.0f);
                } else {
                    entity.hurt(entity.damageSources().generic(), 15.0f);
                }

                entity.setSpeed(entity.getSpeed() * 0.1f);
            } else {
                entityNbt.putFloat("Scale", 1.0f);

                if (!(entity instanceof Player)) {
                    entityNbt.putBoolean("NoAI", false);
                }

                entity.readAdditionalSaveData(entityNbt);

                entity.setSpeed(entity.getSpeed() * 10.0f);
            }

            if (!player.isCreative()) {
                player.getCooldowns().addCooldown(this, 200);
            }
        }
        player.playSound(ModSounds.TCE.get(), 1.0f, 1.0f);
        return InteractionResult.SUCCESS;
    }
}
