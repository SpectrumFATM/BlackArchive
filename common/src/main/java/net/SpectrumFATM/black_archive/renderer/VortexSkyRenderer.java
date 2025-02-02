package net.SpectrumFATM.black_archive.renderer;

import whocraft.tardis_refined.client.renderer.vortex.VortexRenderer;
import whocraft.tardis_refined.common.VortexRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;

public class VortexSkyRenderer {
    private static VortexRenderer instance = new VortexRenderer(VortexRegistry.CLOUDS.get());

    public static void render(PoseStack matrixStack, Camera camera) {

        matrixStack.pushPose();
        instance.time.speed = 1;
        instance.renderVortex(matrixStack, 0.5F, false);

        if (Minecraft.getInstance().level.getGameTime() % 600 == 0) {
            Set<Map.Entry<ResourceKey<VortexRegistry>, VortexRegistry>> vortexes = VortexRegistry.VORTEX_DEFERRED_REGISTRY.entrySet();

            if (!vortexes.isEmpty()) {
                List<Map.Entry<ResourceKey<VortexRegistry>, VortexRegistry>> vortexList = new ArrayList<>(vortexes);
                int randomIndex = ThreadLocalRandom.current().nextInt(vortexList.size());
                Map.Entry<ResourceKey<VortexRegistry>, VortexRegistry> randomVortex = vortexList.get(randomIndex);
                instance.vortexType = randomVortex.getValue();
            }
        }

        matrixStack.popPose();
    }
}
