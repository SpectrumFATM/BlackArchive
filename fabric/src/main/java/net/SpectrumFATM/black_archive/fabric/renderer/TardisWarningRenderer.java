package net.SpectrumFATM.black_archive.fabric.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class TardisWarningRenderer {

    private static final int TOTAL_DURATION = 120;
    private static int redFlashTicks = 0;

    public static void triggerRedFlash() {
        redFlashTicks = TOTAL_DURATION;
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (redFlashTicks > 0) {
                redFlashTicks--;
            }
        });

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            if (redFlashTicks > 0) {
                renderRedFlash(drawContext, tickDelta);
            }
        });
    }

    private static void renderRedFlash(DrawContext drawContext, float tickDelta) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);

        float progress = (float) redFlashTicks / TOTAL_DURATION;
        float alpha;

        if (progress > 0.5) {
            alpha = (1.0F - progress) * 2;
        } else {
            alpha = progress * 2;
        }

        RenderSystem.setShaderColor(1.0F, 0.0F, 0.0F, alpha / 3);
        MinecraftClient client = MinecraftClient.getInstance();

        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        drawContext.fill(0, 0, width, height, ((int) (alpha * 255) << 24) | 0x00FF0000);

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }
}