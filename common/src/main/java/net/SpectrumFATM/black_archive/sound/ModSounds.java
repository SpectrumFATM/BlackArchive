package net.SpectrumFATM.black_archive.sound;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.SpectrumFATM.BlackArchive;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;


public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BlackArchive.MOD_ID, RegistryKeys.SOUND_EVENT);

    public static final RegistrySupplier<SoundEvent> VORTEX_TP = registerSoundEvent("vortex_teleport");
    public static final RegistrySupplier<SoundEvent> DALEK_LASER = registerSoundEvent("dalek_laser");
    public static final RegistrySupplier<SoundEvent> DALEK_MALFUNCTION = registerSoundEvent("dalek_laser_malfunction");
    public static final RegistrySupplier<SoundEvent> DALEK_MOVE = registerSoundEvent("dalek_move");
    public static final RegistrySupplier<SoundEvent> CYBERMAN_STEP = registerSoundEvent("cyberman_step");
    public static final RegistrySupplier<SoundEvent> CYBERMAN_GUN = registerSoundEvent("cyberman_gun");
    public static final RegistrySupplier<SoundEvent> CYBERMAN_MALFUNCTION = registerSoundEvent("cyberman_malfunction");
    public static final RegistrySupplier<SoundEvent> TARDIS_GROAN = registerSoundEvent("tardis_groan");
    public static final RegistrySupplier<SoundEvent> TCE = registerSoundEvent("tce");

    private static RegistrySupplier<SoundEvent> registerSoundEvent(String name) {
        Identifier id = new Identifier(BlackArchive.MOD_ID, name);
        return SOUNDS.register(name, () -> SoundEvent.of(id));
    }
}