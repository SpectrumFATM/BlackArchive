package net.SpectrumFATM.black_archive.effects;

import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;
import net.SpectrumFATM.BlackArchive;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;

public class ModEffects {
    public static DeferredRegistry<MobEffect> EFFECTS = DeferredRegistry.create(BlackArchive.MOD_ID, Registries.MOB_EFFECT);

    public static final RegistrySupplier<MobEffect> DALEK_NANOCLOUD = EFFECTS.register("dalek_nanocloud", () -> new DalekNanocloudEffect());
    public static final RegistrySupplier<MobEffect> CYBER_CONVERSION = EFFECTS.register("cyber_conversion", () -> new CyberConversionEffect());
}
