package net.SpectrumFATM.black_archive.fabric.effects;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DalekNanocloudEffect extends StatusEffect {
    public DalekNanocloudEffect() {
        super(StatusEffectCategory.HARMFUL, 0x123456); // Replace with the desired color
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // This method controls when the effect gets applied.
        return true; // Always apply the effect every tick
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (player.age % 200 == 0) {
                player.damage(player.getDamageSources().generic(), 1.0f);
            }
        }
    }

}
