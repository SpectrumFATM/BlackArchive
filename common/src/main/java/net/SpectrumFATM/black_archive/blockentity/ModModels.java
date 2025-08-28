package net.SpectrumFATM.black_archive.blockentity;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.blockentity.door.PillarDoorModel;
import net.SpectrumFATM.black_archive.blockentity.shell.PillarShellModel;
import net.SpectrumFATM.black_archive.blockentity.shell.ShellEntryRegistry;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.api.event.TardisClientEvents;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.common.util.PlatformWarning;

import java.util.function.Supplier;

public class ModModels {

    public static PillarShellModel PILLAR_SHELL_MODEL;

    public static PillarDoorModel PILLAR_DOOR_MODEL;

    public static ModelLayerLocation PILLAR_SHELL = new ModelLayerLocation(new ResourceLocation(BlackArchive.MOD_ID, "pillar_shell"), "shell");
    public static ModelLayerLocation PILLAR_DOOR = new ModelLayerLocation(new ResourceLocation(BlackArchive.MOD_ID, "pillar_door"), "door");
    public static ModelLayerLocation RANI_CONSOLE = new ModelLayerLocation(new ResourceLocation(BlackArchive.MOD_ID, "rani_console"), "console");


    public static void init() {
        if (Platform.isClient()) {
            setupEventas();
        }
    }

    public static void setupModelInstances(EntityModelSet entityModels) {
        ModModels.PILLAR_SHELL_MODEL = new PillarShellModel(entityModels.bakeLayer(ModModels.PILLAR_SHELL));
        ModModels.PILLAR_DOOR_MODEL = new PillarDoorModel(entityModels.bakeLayer(ModModels.PILLAR_DOOR));
        ShellEntryRegistry.init();
    }

    private static void setupEventas() {
        TardisClientEvents.SHELLENTRY_MODELS_SETUP.register(ModModels::setupModelInstances);
        //TardisClientEvents.CONSOLE_MODELS_SETUP.register((consoleModelCollection, entityModelSet) -> consoleModelCollection.registerModel(ModConsoles.RANI.getId(), new ConsoleModelEntry(new RaniConsole(entityModelSet.bakeLayer(RANI_CONSOLE)))));
    }

    @ExpectPlatform
    public static ModelLayerLocation register(ModelLayerLocation location, Supplier<LayerDefinition> definitionSupplier) {
        throw new RuntimeException(PlatformWarning.addWarning(ModModels.class));
    }
}
