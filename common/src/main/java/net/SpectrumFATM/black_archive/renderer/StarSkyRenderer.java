package net.SpectrumFATM.black_archive.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.SpectrumFATM.BlackArchive;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.random.Random;

public class StarSkyRenderer {
    private static VertexBuffer starsBuffer = null;

    // Initialize and generate the star buffer
    public static void initStars() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        // Close the existing buffer if it exists
        if (starsBuffer != null) {
            starsBuffer.close();
        }

        // Create a new VertexBuffer for the stars
        starsBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder.BuiltBuffer builtBuffer = generateStars(bufferBuilder);
        starsBuffer.bind();
        starsBuffer.upload(builtBuffer);
        VertexBuffer.unbind();
    }

    // Generate the star vertices
    public static BufferBuilder.BuiltBuffer generateStars(BufferBuilder buffer) {
        Random random = Random.create(10842L);
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);

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
                    buffer.vertex(j + af, k + ag, l + ah).next();
                }
            }
        }
        return buffer.end();
    }

    // Render the stars using the generated buffer
    public static void render(MatrixStack matrixStack, Camera camera) {
        // Initialize the stars buffer if it hasn't been initialized
        if (starsBuffer == null) {
            initStars();
        }

        // Enable blending and disable depth testing for rendering stars
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();

        // Set the shader to use for rendering
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);

        // Save the current matrix state
        matrixStack.push();
        // Align the stars to the camera's rotation

        // Bind the stars buffer and draw the stars
        starsBuffer.bind();
        starsBuffer.draw(matrixStack.peek().getPositionMatrix(), RenderSystem.getProjectionMatrix(), GameRenderer.getPositionProgram());
        VertexBuffer.unbind();

        matrixStack.translate(camera.getBlockPos().getX(), camera.getBlockPos().getY(), camera.getBlockPos().getZ());

        // Restore the previous matrix state
        matrixStack.pop();

        // Re-enable depth testing and disable blending
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }
}