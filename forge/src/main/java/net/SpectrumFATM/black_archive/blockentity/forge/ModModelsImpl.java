package net.SpectrumFATM.black_archive.blockentity.forge;

import net.SpectrumFATM.black_archive.blockentity.ModModels;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraftforge.client.event.EntityRenderersEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class ModModelsImpl {

    public static final Map<EntityModelLayer, Supplier<TexturedModelData>> DEFINITIONS = new ConcurrentHashMap<>();

    public static EntityModelLayer register(EntityModelLayer location, Supplier<TexturedModelData> definition) {
        DEFINITIONS.put(location, definition);
        return location;
    }

    public static void register(EntityRenderersEvent.RegisterLayerDefinitions event) {
        DEFINITIONS.forEach(event::registerLayerDefinition);
    }
}