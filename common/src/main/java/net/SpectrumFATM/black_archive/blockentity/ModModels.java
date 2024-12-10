package net.SpectrumFATM.black_archive.blockentity;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.blockentity.door.PillarDoorModel;
import net.SpectrumFATM.black_archive.blockentity.shell.PillarShellModel;
import net.SpectrumFATM.black_archive.blockentity.shell.ShellEntryRegistry;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.util.Identifier;
import whocraft.tardis_refined.api.event.TardisClientEvents;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.common.util.PlatformWarning;

import java.util.function.Supplier;

public class ModModels {

    public static PillarShellModel PILLAR_EXT_MODEL;

    public static PillarDoorModel PILLAR_INT_MODEL;

    public static EntityModelLayer PILLAR_EXT;
    public static EntityModelLayer PILLAR_INT;

    public static void init() {
        if (Platform.isClient()) {
            setupEventas();
        }

        PILLAR_EXT = register(new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "pillar_ext"), "pillar_ext"), PillarShellModel::getTexturedModelData);
        PILLAR_INT = register(new EntityModelLayer(new Identifier(BlackArchive.MOD_ID, "pillar_int"), "pillar_int"), PillarDoorModel::getTexturedModelData);
    }

    public static void setupModelInstances(EntityModelLoader entityModels) {
        ModModels.PILLAR_EXT_MODEL = new PillarShellModel(entityModels.getModelPart(ModModels.PILLAR_EXT));
        ModModels.PILLAR_INT_MODEL = new PillarDoorModel(entityModels.getModelPart(ModModels.PILLAR_INT));
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
