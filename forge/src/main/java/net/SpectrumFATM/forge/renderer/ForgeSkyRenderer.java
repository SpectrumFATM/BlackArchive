package net.SpectrumFATM.forge.renderer;

import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.renderer.VortexSkyRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "black_archive", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeSkyRenderer {
    @SubscribeEvent
    public static void onRenderSky(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SKY) {
            return; // Only render after the default sky
        }


        if (BlackArchiveConfig.CLIENT.shouldTimeVortexRender.get()) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.world != null && client.world.getRegistryKey().getValue().getPath().equals("time_vortex")) {
                MatrixStack matrixStack = event.getPoseStack();
                VortexSkyRenderer.render(matrixStack, event.getCamera());
            }
        }
    }
}
