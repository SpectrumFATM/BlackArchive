package net.SpectrumFATM.black_archive.fabric.sound;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent VORTEX_TP = registerSoundEvent("vortex_teleport");
    public static final SoundEvent DALEK_LASER = registerSoundEvent("dalek_laser");
    public static final SoundEvent DALEK_MOVE = registerSoundEvent("dalek_move");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(BlackArchive.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        BlackArchive.LOGGER.info("Registering Sounds for " + BlackArchive.MOD_ID);
    }
}
