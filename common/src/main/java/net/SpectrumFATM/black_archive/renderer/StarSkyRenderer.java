package net.SpectrumFATM.black_archive.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.RandomSource;

public class StarSkyRenderer {
    private static VertexBuffer starsBuffer = null;

    // Initialize and generate the star buffer
    public static void initStars() {
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();

        // Close the existing buffer if it exists
        if (starsBuffer != null) {
            starsBuffer.close();
        }

        // Create a new VertexBuffer for the stars
        starsBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder.RenderedBuffer builtBuffer = generateStars(bufferBuilder);
        starsBuffer.bind();
        starsBuffer.upload(builtBuffer);
        VertexBuffer.unbind();
    }

    // Generate the star vertices
    public static BufferBuilder.RenderedBuffer generateStars(BufferBuilder buffer) {
        RandomSource random = RandomSource.create(10842L);
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        for(int i = 0; i < 2000; ++i) {
            double d = (double) (random.nextFloat() * 2.0F - 1.0F);
            double e = (double) (random.nextFloat() * 2.0F - 1.0F);
            double f = (double) (random.nextFloat() * 2.0F - 1.0F);
            double g = (double) (0.15F + random.nextFloat() * 0.1F);
            double h = d * d + e * e + f * f;
            if (h < 1.0 && h > 0.01) {
                h = 1.0 / Math.sqrt(h);
                d *= h;
                e *= h;
                f *= h;
                double j = d * 100.0;
                double k = e * 100.0;
                double l = f * 100.0;
                double m = Math.atan2(d, f);
                double n = Math.sin(m);
                double o = Math.cos(m);
                double p = Math.atan2(Math.sqrt(d * d + f * f), e);
                double q = Math.sin(p);
                double r = Math.cos(p);
                double s = random.nextDouble() * Math.PI * 2.0;
                double t = Math.sin(s);
                double u = Math.cos(s);

                for (int v = 0; v < 4; ++v) {
                    double w = 0.0;
                    double x = (double) ((v & 2) - 1) * g;
                    double y = (double) ((v + 1 & 2) - 1) * g;
                    double z = 0.0;
                    double aa = x * u - y * t;
                    double ab = y * u + x * t;
                    double ac = ab;
                    double ad = aa * q + 0.0 * r;
                    double ae = 0.0 * q - aa * r;
                    double af = ae * n - ac * o;
                    double ag = ad;
                    double ah = ac * n + ae * o;
                    buffer.vertex(j + af, k + ag, l + ah).endVertex();
                }
            }
        }
        return buffer.end();
    }

    // Render the stars using the generated buffer
    public static void render(PoseStack matrixStack, Camera camera) {
        // Initialize the stars buffer if it hasn't been initialized
        if (starsBuffer == null) {
            initStars();
        }

        // Enable blending and disable depth testing for rendering stars
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();

        // Set the shader to use for rendering
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        // Save the current matrix state
        matrixStack.pushPose();
        // Align the stars to the camera's rotation

        // Bind the stars buffer and draw the stars
        starsBuffer.bind();
        starsBuffer.drawWithShader(matrixStack.last().pose(), RenderSystem.getProjectionMatrix(), GameRenderer.getPositionShader());
        VertexBuffer.unbind();

        matrixStack.translate(camera.getBlockPosition().getX(), camera.getBlockPosition().getY(), camera.getBlockPosition().getZ());

        // Restore the previous matrix state
        matrixStack.popPose();

        // Re-enable depth testing and disable blending
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }
}