package net.SpectrumFATM.black_archive.blockentity;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.blockentity.console.RaniConsole;
import net.SpectrumFATM.black_archive.blockentity.door.PillarDoorModel;
import net.SpectrumFATM.black_archive.blockentity.door.RaniDoorModel;
import net.SpectrumFATM.black_archive.blockentity.door.SIDRATDoorModel;
import net.SpectrumFATM.black_archive.blockentity.shell.PillarShellModel;
import net.SpectrumFATM.black_archive.blockentity.shell.RaniShellModel;
import net.SpectrumFATM.black_archive.blockentity.shell.SIDRATModel;
import net.SpectrumFATM.black_archive.blockentity.shell.ShellEntryRegistry;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.util.Identifier;
import whocraft.tardis_refined.api.event.TardisClientEvents;
import whocraft.tardis_refined.client.model.blockentity.console.ConsoleUnit;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.common.util.PlatformWarning;

import java.util.function.Supplier;

public class ModModels {

    public static PillarShellModel PILLAR_SHELL_MODEL;
    public static RaniShellModel RANI_SHELL_MODEL;
    public static SIDRATModel SIDRAT_SHELL_MODEL;

    public static PillarDoorModel PILLAR_DOOR_MODEL;
    public static RaniDoorModel RANI_DOOR_MODEL;
    public static SIDRATDoorModel SIDRAT_DOOR_MODEL;

    public static ConsoleUnit RANI_CONSOLE_MODEL;

    public static EntityModelLayer PILLAR_SHELL = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "pillar_shell"), "shell");
    public static EntityModelLayer PILLAR_DOOR = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "pillar_door"), "door");
    public static EntityModelLayer RANI_SHELL = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "rani_shell"), "shell");
    public static EntityModelLayer RANI_DOOR = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "rani_door"), "door");
    public static EntityModelLayer SIDRAT_DOOR = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "sidrat_door"), "door");
    public static EntityModelLayer SIDRAT_SHELL = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "sidrat_shell"), "shell");
    public static EntityModelLayer RANI_CONSOLE = new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "rani_console"), "console");


    public static void init() {
        if (Platform.isClient()) {
            setupEventas();
        }
    }

    public static void setupModelInstances(EntityModelLoader entityModels) {
        ModModels.PILLAR_SHELL_MODEL = new PillarShellModel(entityModels.getModelPart(ModModels.PILLAR_SHELL));
        ModModels.PILLAR_DOOR_MODEL = new PillarDoorModel(entityModels.getModelPart(ModModels.PILLAR_DOOR));
        ModModels.RANI_SHELL_MODEL = new RaniShellModel(entityModels.getModelPart(ModModels.RANI_SHELL));
        ModModels.RANI_DOOR_MODEL = new RaniDoorModel(entityModels.getModelPart(ModModels.RANI_DOOR));
        ModModels.SIDRAT_SHELL_MODEL = new SIDRATModel(entityModels.getModelPart(ModModels.SIDRAT_SHELL));
        ModModels.SIDRAT_DOOR_MODEL = new SIDRATDoorModel(entityModels.getModelPart(ModModels.SIDRAT_DOOR));
        ModModels.RANI_CONSOLE_MODEL = new RaniConsole(entityModels.getModelPart(ModModels.RANI_CONSOLE));
        ShellEntryRegistry.init();
    }

    private static void setupEventas() {
        TardisClientEvents.SHELLENTRY_MODELS_SETUP.register(ModModels::setupModelInstances);
    }

    @ExpectPlatform
    public static EntityModelLayer register(EntityModelLayer location, Supplier<TexturedModelData> definitionSupplier) {
        throw new RuntimeException(PlatformWarning.addWarning(ModModels.class));
    }
}
