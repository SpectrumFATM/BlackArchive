package net.SpectrumFATM.black_archive.entity.client;

import net.SpectrumFATM.BlackArchive;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {

    public static final EntityModelLayer DALEK = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "dalek"), "main");
    public static final EntityModelLayer DALEK_SLAVE = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "dalek_slave"), "main");
    public static final EntityModelLayer CYBERMAN = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "cyberman"), "main");
    public static final EntityModelLayer CYBERMAT = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "cybermat"), "main");
    public static final EntityModelLayer ANGEL = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "weeping_angel"), "main");
    public static final EntityModelLayer TIME_FISSURE = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "time_fissure"), "main");
}
