package net.SpectrumFATM.black_archive.fabric.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    protected abstract void readNbt(NbtCompound nbt);

    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void modifyEntityDimensions(CallbackInfoReturnable<EntityDimensions> cir) {
        EntityDimensions originalDimensions = cir.getReturnValue();

        // Read the NBT data
        NbtCompound nbt = new NbtCompound();
        this.readNbt(nbt);

        // Check if the Scale value exists in the NBT
        if (nbt.contains("Scale", NbtElement.FLOAT_TYPE)) {
            float scale = nbt.getFloat("Scale");

            // If a valid scale exists, adjust the dimensions accordingly
            if (scale > 0) {
                float width = originalDimensions.width * scale;
                float height = originalDimensions.height * scale;
                cir.setReturnValue(EntityDimensions.changing(width, height));
            }
        }
    }
}
