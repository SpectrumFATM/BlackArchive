package net.SpectrumFATM.black_archive.blockentity.fabric;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;

import java.util.function.Supplier;

public class ModModelsImpl {

    public static EntityModelLayer register(EntityModelLayer location, Supplier<TexturedModelData> definition) {
        EntityModelLayerRegistry.registerModelLayer(location, definition::get);
        return location;
    }

}
