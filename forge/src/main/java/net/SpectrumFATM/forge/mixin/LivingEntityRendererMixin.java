package net.SpectrumFATM.forge.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> {
    
    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        // Scale down the entity to 1/10 of its original size
        NbtCompound nbt = new NbtCompound();
        livingEntity.writeNbt(nbt);
        float scale = nbt.getFloat("Scale") * 1.0F;

        matrixStack.scale(scale, scale, scale);
    }
}
