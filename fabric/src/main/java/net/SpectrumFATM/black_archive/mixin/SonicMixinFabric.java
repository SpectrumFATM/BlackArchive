package net.SpectrumFATM.black_archive.mixin;

import net.SpectrumFATM.black_archive.util.sonic.SonicEngine;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.common.items.ScrewdriverMode;

@Mixin(ScrewdriverItem.class)
public class SonicMixinFabric extends Item {

    ScrewdriverItem item = (ScrewdriverItem) (Object) this;

    public SonicMixinFabric(Properties properties) {
        super(properties);
    }

    @Inject(method = "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;", at = @At("HEAD"), cancellable = true)
    private void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (item.isScrewdriverMode(new ItemStack(item), ScrewdriverMode.ENABLED)) {
            SonicEngine.blockActivate(context);
            cir.setReturnValue(InteractionResult.SUCCESS);
        }

    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        SonicEngine.entityActivate(itemStack, player, livingEntity, null);
        return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand);
    }
}
