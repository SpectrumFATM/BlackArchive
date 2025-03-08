package net.SpectrumFATM.black_archive.mixin;

import net.SpectrumFATM.black_archive.util.IMappedRegistry;
import net.minecraft.core.MappedRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MappedRegistry.class)
public class MappedRegistryMixin implements IMappedRegistry {

    @Shadow
    private boolean frozen;

    @Override
    public void setIsFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    @Override
    public boolean isFrozen() {
        return this.frozen;
    }
}
