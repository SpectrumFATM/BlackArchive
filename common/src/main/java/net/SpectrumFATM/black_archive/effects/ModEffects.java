package net.SpectrumFATM.black_archive.effects;

import net.SpectrumFATM.BlackArchive;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ModEffects {
    public static DeferredRegistry<StatusEffect> EFFECTS = DeferredRegistry.create(BlackArchive.MOD_ID, Registries.STATUS_EFFECT.getKey());

    public static final RegistrySupplier<StatusEffect> DALEK_NANOCLOUD = EFFECTS.register("dalek_nanocloud", () -> new DalekNanocloudEffect());
    public static final RegistrySupplier<StatusEffect> CYBER_CONVERSION = EFFECTS.register("cyber_conversion", () -> new CyberConversionEffect());
}
