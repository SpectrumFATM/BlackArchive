package net.SpectrumFATM.forge.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.renderer.StarSkyRenderer;
import net.SpectrumFATM.black_archive.renderer.VortexSkyRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "black_archive", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeSkyRenderer {

    @SubscribeEvent
    public static void onRenderSky(RenderLevelStageEvent event) {
        Minecraft client = Minecraft.getInstance();
        ResourceKey<Level> spaceDimension = ResourceKey.create(ResourceKey.createRegistryKey(new ResourceLocation("minecraft", "dimension")), new ResourceLocation("black_archive", "space"));


        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SKY) {
            return; // Only render after the default sky
        }


        if (BlackArchiveConfig.CLIENT.shouldTimeVortexRender.get()) {
            if (client.level != null && client.level.dimension().location().getPath().equals("time_vortex")) {
                PoseStack matrixStack = event.getPoseStack();
                VortexSkyRenderer.render(matrixStack, event.getCamera());
            }
        }

        if (client.level != null && client.level.dimension() == spaceDimension) {
            PoseStack matrixStack = event.getPoseStack();
            StarSkyRenderer.render(matrixStack, event.getCamera());
        }
    }
}
