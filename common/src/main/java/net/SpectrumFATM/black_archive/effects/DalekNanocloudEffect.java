package net.SpectrumFATM.black_archive.effects;

import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.entity.custom.DalekPuppetEntity;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DalekNanocloudEffect extends MobEffect {
    public DalekNanocloudEffect() {
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

            if (player.getInventory().contains(new ItemStack(ModItems.DALEK_BRACELET.get()))) {
                player.removeEffect(ModEffects.DALEK_NANOCLOUD.get());
            }

            if (player.getHealth() <= 1f && !player.isDeadOrDying()) {
                DalekPuppetEntity dalekSlave = new DalekPuppetEntity(ModEntities.DALEK_PUPPET.get(), player.level());
                dalekSlave.moveTo(player.getX(), player.getY(), player.getZ(), player.yBodyRot, player.getXRot());
                dalekSlave.setPlayerUUID(player.getUUID());
                player.level().addFreshEntity(dalekSlave);
                player.setHealth(0); // Ensure the player is marked as dead
            }
        }
    }
}
