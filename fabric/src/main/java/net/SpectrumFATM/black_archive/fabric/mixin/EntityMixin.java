package net.SpectrumFATM.black_archive.fabric.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    private Float cachedScale = null;

    // Inject into the readNbt method to load the scale
    @Inject(method = "readNbt", at = @At("HEAD"))
    private void onReadNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("Scale", NbtCompound.FLOAT_TYPE)) {
            float scale = nbt.getFloat("Scale");
            cachedScale = scale > 0 ? scale : null;
        } else {
            cachedScale = null; // Reset if no scale is found
        }
    }

    // Inject into the writeNbt method to save the scale
    @Inject(method = "writeNbt", at = @At("HEAD"))
    private NbtCompound onWriteNbt(NbtCompound nbt, CallbackInfoReturnable ci) {
        if (cachedScale != null) {
            nbt.putFloat("Scale", cachedScale);
        }

        return nbt;
    }

    // Safely modify entity dimensions
    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void modifyEntityDimensions(CallbackInfoReturnable<EntityDimensions> cir) {
        if (cachedScale != null) {
            EntityDimensions originalDimensions = cir.getReturnValue();
            EntityDimensions scaledDimensions = EntityDimensions.changing(
                    originalDimensions.width * cachedScale,
                    originalDimensions.height * cachedScale
            );
            cir.setReturnValue(scaledDimensions);
        }
    }
}
