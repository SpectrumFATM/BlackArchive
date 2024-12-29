package net.SpectrumFATM.black_archive.fabric.renderer;

import net.SpectrumFATM.black_archive.renderer.StarSkyRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.SpectrumFATM.black_archive.renderer.VortexSkyRenderer;

public class FabricSkyRenderer {
    public static void register() {
        ResourceKey<Level> vortexDimension = ResourceKey.create(ResourceKey.createRegistryKey(new ResourceLocation("minecraft", "dimension")), new ResourceLocation("black_archive", "time_vortex"));
        ResourceKey<Level> spaceDimension = ResourceKey.create(ResourceKey.createRegistryKey(new ResourceLocation("minecraft", "dimension")), new ResourceLocation("black_archive", "space"));


        DimensionRenderingRegistry.registerSkyRenderer(vortexDimension, context -> {
            VortexSkyRenderer.render(context.matrixStack(), context.camera());
        });

        DimensionRenderingRegistry.registerSkyRenderer(spaceDimension, context -> {
            StarSkyRenderer.render(context.matrixStack(), context.camera());
        });
    }
}