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
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
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

    public static ModelLayerLocation PILLAR_SHELL = new ModelLayerLocation(new ResourceLocation(BlackArchive.MOD_ID, "pillar_shell"), "shell");
    public static ModelLayerLocation PILLAR_DOOR = new ModelLayerLocation(new ResourceLocation(BlackArchive.MOD_ID, "pillar_door"), "door");
    public static ModelLayerLocation RANI_SHELL = new ModelLayerLocation(new ResourceLocation(BlackArchive.MOD_ID, "rani_shell"), "shell");
    public static ModelLayerLocation RANI_DOOR = new ModelLayerLocation(new ResourceLocation(BlackArchive.MOD_ID, "rani_door"), "door");
    public static ModelLayerLocation SIDRAT_DOOR = new ModelLayerLocation(new ResourceLocation(BlackArchive.MOD_ID, "sidrat_door"), "door");
    public static ModelLayerLocation SIDRAT_SHELL = new ModelLayerLocation(new ResourceLocation(BlackArchive.MOD_ID, "sidrat_shell"), "shell");
    public static ModelLayerLocation RANI_CONSOLE = new ModelLayerLocation(new ResourceLocation(BlackArchive.MOD_ID, "rani_console"), "console");


    public static void init() {
        if (Platform.isClient()) {
            setupEventas();
        }
    }

    public static void setupModelInstances(EntityModelSet entityModels) {
        ModModels.PILLAR_SHELL_MODEL = new PillarShellModel(entityModels.bakeLayer(ModModels.PILLAR_SHELL));
        ModModels.PILLAR_DOOR_MODEL = new PillarDoorModel(entityModels.bakeLayer(ModModels.PILLAR_DOOR));
        ModModels.RANI_SHELL_MODEL = new RaniShellModel(entityModels.bakeLayer(ModModels.RANI_SHELL));
        ModModels.RANI_DOOR_MODEL = new RaniDoorModel(entityModels.bakeLayer(ModModels.RANI_DOOR));
        ModModels.SIDRAT_SHELL_MODEL = new SIDRATModel(entityModels.bakeLayer(ModModels.SIDRAT_SHELL));
        ModModels.SIDRAT_DOOR_MODEL = new SIDRATDoorModel(entityModels.bakeLayer(ModModels.SIDRAT_DOOR));
        ModModels.RANI_CONSOLE_MODEL = new RaniConsole(entityModels.bakeLayer(ModModels.RANI_CONSOLE));
        ShellEntryRegistry.init();
        //TODO: Write ConsoleEntryRegistry upon next TR release.
    }

    private static void setupEventas() {
        TardisClientEvents.SHELLENTRY_MODELS_SETUP.register(ModModels::setupModelInstances);
    }

    @ExpectPlatform
    public static ModelLayerLocation register(ModelLayerLocation location, Supplier<LayerDefinition> definitionSupplier) {
        throw new RuntimeException(PlatformWarning.addWarning(ModModels.class));
    }
}
