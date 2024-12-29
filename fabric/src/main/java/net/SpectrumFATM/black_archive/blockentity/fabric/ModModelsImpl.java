package net.SpectrumFATM.black_archive.blockentity.fabric;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import whocraft.tardis_refined.client.model.pallidium.BedrockModelUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.Supplier;

public class ModModelsImpl {

    public static ModelLayerLocation register(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
        EntityModelLayerRegistry.registerModelLayer(location, definition::get);

        Objects.requireNonNull(definition);
        System.out.println(location);
        JsonObject model = BedrockModelUtil.toJsonModel((LayerDefinition)definition.get(), location.getModel().getPath());
        Path exportFolder = Paths.get("export_models/" + location.getLayer());

        try {
            Files.createDirectories(exportFolder);
        } catch (IOException var9) {
            IOException e = var9;
            throw new RuntimeException("Failed to create export_models directory", e);
        }

        String var10001 = location.getModel().getPath().replaceAll("_ext", "");
        Path modelFile = exportFolder.resolve(var10001.replaceAll("int", "door") + ".json");

        try {
            BufferedWriter writer = Files.newBufferedWriter(modelFile);

            try {
                writer.write(model.toString());
            } catch (Throwable var10) {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (Throwable var8) {
                        var10.addSuppressed(var8);
                    }
                }

                throw var10;
            }

            if (writer != null) {
                writer.close();
            }

            return location;
        } catch (IOException var11) {
            IOException e = var11;
            throw new RuntimeException("Failed to write model to file", e);
        }
    }

}
