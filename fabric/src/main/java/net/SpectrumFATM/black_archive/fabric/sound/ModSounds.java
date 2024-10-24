package net.SpectrumFATM.black_archive.fabric.sound;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent VORTEX_TP = registerSoundEvent("vortex_teleport");
    public static final SoundEvent DALEK_LASER = registerSoundEvent("dalek_laser");
    public static final SoundEvent DALEK_MALFUNCTION = registerSoundEvent("dalek_laser_malfunction");
    public static final SoundEvent DALEK_MOVE = registerSoundEvent("dalek_move");
    public static final SoundEvent CYBERMAN_STEP = registerSoundEvent("cyberman_step");
    public static final SoundEvent CYBERMAN_GUN = registerSoundEvent("cyberman_gun");
    public static final SoundEvent CYBERMAN_MALFUNCTION = registerSoundEvent("cyberman_malfunction");
    public static final SoundEvent TARDIS_GROAN = registerSoundEvent("tardis_groan");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(BlackArchive.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        BlackArchive.LOGGER.info("Registering Sounds for " + BlackArchive.MOD_ID);
    }
}
