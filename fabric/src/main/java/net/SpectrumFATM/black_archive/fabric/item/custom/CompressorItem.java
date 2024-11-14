package net.SpectrumFATM.black_archive.fabric.item.custom;

import net.SpectrumFATM.black_archive.fabric.sound.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class CompressorItem extends TooltipItem {
    public CompressorItem(Settings settings, String tooltipKey) {
        super(settings, tooltipKey);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {

        Random random = new Random();

        BlockPos pos = entity.getBlockPos();
        entity.getWorld().addParticle(
                ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,
                pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                0.0, 0.05, 0.0
        );

        for (int i = 0; i < 5; i++) {
            entity.getWorld().addParticle(
                    ParticleTypes.SMOKE,
                    pos.getX() + (random.nextInt(10) * 0.1), pos.getY() + (random.nextInt(10) * 0.1), (random.nextInt(10) * 0.1),
                    0.0, 0.075, 0.0
            );
        }

        if (!entity.getWorld().isClient) {

            NbtCompound entityNbt = new NbtCompound();
            entity.writeNbt(entityNbt);

            if (entityNbt.getFloat("Scale") == 1.0f) {
                entityNbt.putFloat("Scale", 0.1f);

                if (!(entity instanceof PlayerEntity)) {
                    entityNbt.putBoolean("NoAI", true);
                }

                entity.readCustomDataFromNbt(entityNbt);

                if (entity instanceof PlayerEntity) {
                    entity.damage(entity.getDamageSources().generic(), 4.0f);
                } else {
                    entity.damage(entity.getDamageSources().generic(), 15.0f);
                }

                entity.setMovementSpeed(entity.getMovementSpeed() * 0.1f);
            } else {
                entityNbt.putFloat("Scale", 1.0f);

                if (!(entity instanceof PlayerEntity)) {
                    entityNbt.putBoolean("NoAI", false);
                }

                entity.readCustomDataFromNbt(entityNbt);

                entity.setMovementSpeed(entity.getMovementSpeed() * 10.0f);
            }

            if (!player.isCreative()) {
                player.getItemCooldownManager().set(this, 200);
            }
        }
        player.playSound(ModSounds.TCE, 1.0f, 1.0f);
        return ActionResult.SUCCESS;
    }
}
