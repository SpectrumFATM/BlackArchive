package net.SpectrumFATM.black_archive.fabric.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.SpectrumFATM.black_archive.renderer.VortexSkyRenderer;

public class FabricSkyRenderer {
    public static void register() {
        RegistryKey<World> vortexDimension = RegistryKey.of(RegistryKey.ofRegistry(new Identifier("minecraft", "dimension")), new Identifier("black_archive", "time_vortex"));

        DimensionRenderingRegistry.registerSkyRenderer(vortexDimension, context -> {
            VortexSkyRenderer.render(context.matrixStack(), context.camera());
        });
    }
}