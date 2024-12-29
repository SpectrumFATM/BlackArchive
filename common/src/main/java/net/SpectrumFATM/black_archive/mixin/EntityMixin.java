package net.SpectrumFATM.black_archive.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
/*
    private Float cachedScale = null;

    // Inject into the readNbt method to load the scale
    @Inject(method = "readAdditionalSaveDat(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("HEAD"))
    private void onReadNbt(CompoundTag nbt, CallbackInfo ci) {
        if (nbt.contains("Scale", CompoundTag.TAG_FLOAT)) {
            float scale = nbt.getFloat("Scale");
            cachedScale = scale > 0 ? scale : null;
        } else {
            cachedScale = null; // Reset if no scale is found
        }
    }

    // Inject into the writeNbt method to save the scale
    @Inject(method = "writeAdditionalSaveDat(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", at = @At("HEAD"))
    private void onWriteNbt(CompoundTag nbt, CallbackInfoReturnable ci) {
        if (cachedScale != null) {
            nbt.putFloat("Scale", cachedScale);
        }

    }

    // Safely modify entity dimensions
    @Inject(method = "getDimensions(Lnet/minecraft/entity/EntityPose;)Lnet/minecraft/entity/EntityDimensions;", at = @At("RETURN"), cancellable = true)
    private void modifyEntityDimensions(CallbackInfoReturnable<EntityDimensions> cir) {
        if (cachedScale != null) {
            EntityDimensions originalDimensions = cir.getReturnValue();
            EntityDimensions scaledDimensions = EntityDimensions.scalable(
                    originalDimensions.width * cachedScale,
                    originalDimensions.height * cachedScale
            );
            cir.setReturnValue(scaledDimensions);
        }
    }
    */
}
