package net.SpectrumFATM.black_archive.fabric.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    protected abstract void readNbt(NbtCompound nbt);

    private Float cachedScale = null;

    private void updateScaleFromNbt() {
        NbtCompound nbt = new NbtCompound();
        try {
            this.readNbt(nbt);
            if (nbt.contains("Scale", NbtCompound.FLOAT_TYPE)) {
                float scale = nbt.getFloat("Scale");
                cachedScale = scale > 0 ? scale : null;
            } else {
                cachedScale = null;
            }
        } catch (Exception e) {
            cachedScale = null;
        }
    }

    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void modifyEntityDimensions(CallbackInfoReturnable<EntityDimensions> cir) {
        if (cachedScale == null) {
            updateScaleFromNbt();
        }
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
