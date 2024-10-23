package net.SpectrumFATM.black_archive.fabric.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class VortexSkyRenderer implements DimensionRenderingRegistry.SkyRenderer {

    private static final Identifier VORTEX_TEXTURE = new Identifier("black_archive", "textures/vortex.png");

    @Override
    public void render(WorldRenderContext context) {
        renderVortex(context.matrixStack(), 1f);
    }

    public static void renderVortex(MatrixStack matrixStack, float scale) {
        MinecraftClient client = MinecraftClient.getInstance();
        Camera camera = client.gameRenderer.getCamera();

        // Bind the vortex texture
        RenderSystem.setShaderTexture(0, VORTEX_TEXTURE);

        // Set up rendering parameters
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);

        matrixStack.push();

        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F)); // Rotate to face upwards

        // Rotate around the tube's axis
        float rotationAngle = (System.currentTimeMillis() % 3600) / 10.0f; // Adjust speed as needed
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotationAngle));

        // Define the vertices of the top and bottom faces of the octagon with a radius of 10 blocks
        float radius = 10.0F;
        float height = 255.0F; // Extrude significantly more
        float[][] topVertices = new float[8][3];
        float[][] bottomVertices = new float[8][3];
        for (int i = 0; i < 8; i++) {
            double angle = Math.PI / 4 * i;
            topVertices[i][0] = (float) (radius * Math.cos(angle));
            topVertices[i][1] = height;
            topVertices[i][2] = (float) (radius * Math.sin(angle));
            bottomVertices[i][0] = topVertices[i][0];
            bottomVertices[i][1] = -height;
            bottomVertices[i][2] = topVertices[i][2];
        }

        // Render the sides of the octagonal tube
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        for (int i = 0; i < topVertices.length; i++) {
            float[] topVertex = topVertices[i];
            float[] bottomVertex = bottomVertices[i];
            float[] nextTopVertex = topVertices[(i + 1) % topVertices.length];
            float[] nextBottomVertex = bottomVertices[(i + 1) % bottomVertices.length];

            // First triangle
            bufferBuilder.vertex(matrixStack.peek().getPositionMatrix(), topVertex[0], topVertex[1], topVertex[2]).texture(0.0F, 0.0F).next();
            bufferBuilder.vertex(matrixStack.peek().getPositionMatrix(), bottomVertex[0], bottomVertex[1], bottomVertex[2]).texture(0.0F, 1.0F).next();
            bufferBuilder.vertex(matrixStack.peek().getPositionMatrix(), nextBottomVertex[0], nextBottomVertex[1], nextBottomVertex[2]).texture(1.0F, 1.0F).next();
            bufferBuilder.vertex(matrixStack.peek().getPositionMatrix(), nextTopVertex[0], nextTopVertex[1], nextTopVertex[2]).texture(1.0F, 0.0F).next();
        }
        tessellator.draw();

        matrixStack.translate(camera.getBlockPos().getX(), camera.getBlockPos().getY(), camera.getBlockPos().getZ());

        matrixStack.pop();

        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }
}