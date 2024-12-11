package net.SpectrumFATM.black_archive.entity.client;

import net.SpectrumFATM.black_archive.blockentity.door.PillarDoorModel;
import net.SpectrumFATM.black_archive.blockentity.shell.PillarShellModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.util.Identifier;

public class ModModelLayers {

    public static PillarShellModel PILLAR_EXT;
    public static PillarDoorModel PILLAR_DOOR;

    public static final EntityModelLayer DALEK = new EntityModelLayer(new Identifier("black_archive", "dalek"), "main");
    public static final EntityModelLayer DALEK_SLAVE = new EntityModelLayer(new Identifier("black_archive", "dalek_slave"), "main");
    public static final EntityModelLayer CYBERMAN = new EntityModelLayer(new Identifier("black_archive", "cyberman"), "main");
    public static final EntityModelLayer CYBERMAT = new EntityModelLayer(new Identifier("black_archive", "cybermat"), "main");
    public static final EntityModelLayer TIME_FISSURE = new EntityModelLayer(new Identifier("black_archive", "time_fissure"), "main");

    public static final EntityModelLayer PILLAR_SHELL_LAYER = new EntityModelLayer(new Identifier("black_archive", "pillar_shell"), "main");
    public static final EntityModelLayer PILLAR_DOOR_LAYER = new EntityModelLayer(new Identifier("black_archive", "pillar_door"), "main");

    public static void setupModels(EntityModelLoader entityModels) {
        PILLAR_EXT = new PillarShellModel(entityModels.getModelPart(PILLAR_SHELL_LAYER));
        PILLAR_DOOR = new PillarDoorModel(entityModels.getModelPart(PILLAR_DOOR_LAYER));
    }
}
