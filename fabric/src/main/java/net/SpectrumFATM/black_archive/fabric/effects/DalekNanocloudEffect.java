package net.SpectrumFATM.black_archive.fabric.effects;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class DalekNanocloudEffect extends StatusEffect {
    public DalekNanocloudEffect() {
        super(StatusEffectCategory.HARMFUL, 0x000000);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            if (player.age % 200 == 0) {
                player.damage(player.getDamageSources().generic(), 1.0f);
            }

            if (player.getInventory().contains(new ItemStack(ModItems.DALEK_BRACELET))) {
                player.removeStatusEffect(BlackArchive.DALEK_NANOCLOUD);
            }
        }
    }
}
