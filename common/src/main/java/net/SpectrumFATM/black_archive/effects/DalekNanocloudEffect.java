package net.SpectrumFATM.black_archive.effects;

import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.entity.custom.DalekPuppetEntity;
import net.SpectrumFATM.black_archive.item.ModItems;
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

            if (player.getInventory().contains(new ItemStack(ModItems.DALEK_BRACELET.get()))) {
                player.removeStatusEffect(ModEffects.DALEK_NANOCLOUD.get());
            }

            if (player.getHealth() <= 1f && !player.isDead()) {
                DalekPuppetEntity dalekSlave = new DalekPuppetEntity(ModEntities.DALEK_PUPPET.get(), player.getWorld());
                dalekSlave.refreshPositionAndAngles(player.getX(), player.getY(), player.getZ(), player.bodyYaw, player.getPitch());
                dalekSlave.setPlayerUUID(player.getUuid());
                player.getWorld().spawnEntity(dalekSlave);
                player.setHealth(0); // Ensure the player is marked as dead
            }
        }
    }
}
