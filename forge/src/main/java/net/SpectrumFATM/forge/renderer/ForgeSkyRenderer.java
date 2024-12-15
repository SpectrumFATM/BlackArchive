package net.SpectrumFATM.forge.renderer;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.renderer.StarSkyRenderer;
import net.SpectrumFATM.black_archive.renderer.VortexSkyRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "black_archive", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeSkyRenderer {

    @SubscribeEvent
    public static void onRenderSky(RenderLevelStageEvent event) {
        MinecraftClient client = MinecraftClient.getInstance();
        RegistryKey<World> spaceDimension = RegistryKey.of(RegistryKey.ofRegistry(new Identifier("minecraft", "dimension")), new Identifier("black_archive", "space"));


        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SKY) {
            return; // Only render after the default sky
        }


        if (BlackArchiveConfig.CLIENT.shouldTimeVortexRender.get()) {
            if (client.world != null && client.world.getRegistryKey().getValue().getPath().equals("time_vortex")) {
                MatrixStack matrixStack = event.getPoseStack();
                VortexSkyRenderer.render(matrixStack, event.getCamera());
            }
        }

        if (client.world != null && client.world.getRegistryKey() == spaceDimension) {
            MatrixStack matrixStack = event.getPoseStack();
            StarSkyRenderer.render(matrixStack, event.getCamera());
        }
    }
}
