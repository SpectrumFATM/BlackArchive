package net.SpectrumFATM.black_archive.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class TardisWarningRenderer {

    private static final int TOTAL_DURATION = 120;
    private static int redFlashTicks = 0;

    public static void triggerRedFlash() {
        redFlashTicks = TOTAL_DURATION;
    }

    public static void onClientTick() {
        if (redFlashTicks > 0) {
            redFlashTicks--;
        }
    }

    public static void onRenderHud(DrawContext drawContext, float tickDelta) {
        if (redFlashTicks > 0) {
            renderRedFlash(drawContext, tickDelta);
        }
    }

    private static void renderRedFlash(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        float progress = (float) redFlashTicks / TOTAL_DURATION;
        float alpha = progress > 0.5 ? (1.0F - progress) * 2 : progress * 2;

        // Ensure alpha does not become completely opaque
        alpha = Math.min(alpha, 0.5F);

        drawContext.fill(0, 0, width, height, ((int) (alpha * 255) << 24) | 0x00FF0000);
    }
}
