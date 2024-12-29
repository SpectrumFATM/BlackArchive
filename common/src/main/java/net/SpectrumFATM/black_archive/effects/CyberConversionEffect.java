package net.SpectrumFATM.black_archive.effects;

import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.entity.custom.CybermanEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class CyberConversionEffect extends MobEffect {
    public CyberConversionEffect() {
        super(MobEffectCategory.HARMFUL, 0x000000);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            if (player.tickCount % 200 == 0) {
                player.hurt(player.damageSources().generic(), 1.0f);
            }

            if (player.getHealth() <= 1f && !player.isDeadOrDying()) {
                CybermanEntity cybermanEntity = new CybermanEntity(ModEntities.CYBERMAN.get(), player.level());
                cybermanEntity.moveTo(player.getX(), player.getY(), player.getZ(), player.yBodyRot, player.getXRot());
                player.level().addFreshEntity(cybermanEntity);
                player.setHealth(0); // Ensure the player is marked as dead
            }
        }
    }
}
