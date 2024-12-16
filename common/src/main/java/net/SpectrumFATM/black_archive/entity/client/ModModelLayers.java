package net.SpectrumFATM.black_archive.entity.client;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.blockentity.door.PillarDoorModel;
import net.SpectrumFATM.black_archive.blockentity.door.RaniDoorModel;
import net.SpectrumFATM.black_archive.blockentity.shell.PillarShellModel;
import net.SpectrumFATM.black_archive.blockentity.shell.RaniShellModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.util.Identifier;

public class ModModelLayers {

    public static PillarShellModel PILLAR_EXT;
    public static PillarDoorModel PILLAR_DOOR;
    public static RaniShellModel RANI_EXT;
    public static RaniDoorModel RANI_DOOR;

    public static final EntityModelLayer DALEK = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "dalek"), "main");
    public static final EntityModelLayer DALEK_SLAVE = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "dalek_slave"), "main");
    public static final EntityModelLayer CYBERMAN = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "cyberman"), "main");
    public static final EntityModelLayer CYBERMAT = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "cybermat"), "main");
    public static final EntityModelLayer ANGEL = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "weeping_angel"), "main");
    public static final EntityModelLayer TIME_FISSURE = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "time_fissure"), "main");

    public static final EntityModelLayer PILLAR_SHELL_LAYER = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "pillar_shell"), "main");
    public static final EntityModelLayer PILLAR_DOOR_LAYER = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "pillar_door"), "main");
    public static final EntityModelLayer RANI_SHELL_LAYER = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "rani_shell"), "main");
    public static final EntityModelLayer RANI_DOOR_LAYER = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "rani_door"), "main");

    public static void setupModels(EntityModelLoader entityModels) {
        PILLAR_EXT = new PillarShellModel(entityModels.getModelPart(PILLAR_SHELL_LAYER));
        PILLAR_DOOR = new PillarDoorModel(entityModels.getModelPart(PILLAR_DOOR_LAYER));
        RANI_EXT = new RaniShellModel(entityModels.getModelPart(RANI_SHELL_LAYER));
        RANI_DOOR = new RaniDoorModel(entityModels.getModelPart(RANI_DOOR_LAYER));
    }
}
