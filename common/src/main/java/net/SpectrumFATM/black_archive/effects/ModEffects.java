package net.SpectrumFATM.black_archive.effects;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.SpectrumFATM.BlackArchive;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.RegistryKeys;

public class ModEffects {
    public static DeferredRegister<StatusEffect> EFFECTS = DeferredRegister.create(BlackArchive.MOD_ID, RegistryKeys.STATUS_EFFECT);

    public static final RegistrySupplier<StatusEffect> DALEK_NANOCLOUD = EFFECTS.register("dalek_nanocloud", () -> new DalekNanocloudEffect());
    public static final RegistrySupplier<StatusEffect> CYBER_CONVERSION = EFFECTS.register("cyber_conversion", () -> new CyberConversionEffect());
}
