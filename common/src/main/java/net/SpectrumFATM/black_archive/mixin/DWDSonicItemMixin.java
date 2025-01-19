package net.SpectrumFATM.black_archive.mixin;

import dev.jeryn.doctorwho.common.items.SonicItem;
import net.SpectrumFATM.black_archive.util.SonicEngine;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SonicItem.class)
public abstract class DWDSonicItemMixin extends Item {

    public DWDSonicItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        SonicEngine.entityActivate(itemStack, player, livingEntity, null);
        return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand);
    }
}
