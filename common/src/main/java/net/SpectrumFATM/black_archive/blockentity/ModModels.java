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
import whocraft.tardis_refined.client.model.blockentity.console.ConsoleModelCollection;
import whocraft.tardis_refined.client.model.blockentity.console.ConsoleUnit;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.common.util.PlatformWarning;

import java.util.function.Supplier;

public class ModModels {

    public static PillarShellModel PILLAR_EXT_MODEL;
    public static RaniShellModel RANI_EXT_MODEL;
    public static SIDRATModel SIDRAT_EXT_MODEL;

    public static PillarDoorModel PILLAR_INT_MODEL;
    public static RaniDoorModel RANI_INT_MODEL;
    public static SIDRATDoorModel SIDRAT_INT_MODEL;

    public static ConsoleUnit RANI_CONSOLE_MODEL;

    public static EntityModelLayer RANI_CONSOLE;
    public static EntityModelLayer PILLAR_EXT, RANI_EXT, SIDRAT_EXT;
    public static EntityModelLayer PILLAR_INT, RANI_INT, SIDRAT_INT;

    public static void init() {
        if (Platform.isClient()) {
            setupEventas();
        }

        PILLAR_EXT = register(new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "pillar_ext"), "pillar_ext"), PillarShellModel::getTexturedModelData);
        PILLAR_INT = register(new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "pillar_int"), "pillar_int"), PillarDoorModel::getTexturedModelData);
        RANI_EXT = register(new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "rani_ext"), "rani_ext"), RaniShellModel::getTexturedModelData);
        RANI_INT = register(new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "rani_int"), "rani_int"), RaniDoorModel::getTexturedModelData);
        SIDRAT_INT = register(new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "sidrat_int"), "sidrat_int"), SIDRATDoorModel::getTexturedModelData);
        SIDRAT_EXT = register(new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "sidrat_ext"), "sidrat_ext"), SIDRATModel::getTexturedModelData);

        RANI_CONSOLE = register(new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "rani_console"), "rani_console"), RaniConsole::getTexturedModelData);
    }

    public static void setupModelInstances(EntityModelLoader entityModels) {
        ModModels.PILLAR_EXT_MODEL = new PillarShellModel(entityModels.getModelPart(ModModels.PILLAR_EXT));
        ModModels.PILLAR_INT_MODEL = new PillarDoorModel(entityModels.getModelPart(ModModels.PILLAR_INT));
        ModModels.RANI_EXT_MODEL = new RaniShellModel(entityModels.getModelPart(ModModels.RANI_EXT));
        ModModels.RANI_INT_MODEL = new RaniDoorModel(entityModels.getModelPart(ModModels.RANI_INT));
        ModModels.SIDRAT_EXT_MODEL = new SIDRATModel(entityModels.getModelPart(ModModels.SIDRAT_EXT));
        ModModels.SIDRAT_INT_MODEL = new SIDRATDoorModel(entityModels.getModelPart(ModModels.SIDRAT_INT));
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
