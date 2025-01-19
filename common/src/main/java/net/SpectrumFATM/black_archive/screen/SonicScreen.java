package net.SpectrumFATM.black_archive.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.SpectrumFATM.BlackArchive;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class SonicScreen extends Screen {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BlackArchive.MOD_ID, "textures/gui/sonic_gui.png");
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;
    private static final int BUTTON_RADIUS = 80; // Radius of the circle for buttons

    private float r;
    private float g;
    private float b;
    private Item item;

    public List<Button> buttons = new ArrayList<>();

    @Override
    public void tick() {
        super.tick();
    }

    public SonicScreen(float r, float g, float b) {
        super(Component.literal("Sonic Screwdriver"));
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void addCircularButton(Component text, Runnable action) {
        // Add the new button first, then recalculate all positions
        Button button = Button.builder(text, btn -> {
            // Execute the unique action passed for this button
            action.run();
            minecraft.setScreen(null); // Close the screen after action
        }).bounds(0, 0, 60, 20).build(); // Temporary position
        this.buttons.add(button);
        this.addRenderableWidget(button);

        // Recalculate positions for all buttons
        recalculateButtonPositions();
    }

    private void recalculateButtonPositions() {
        int totalButtons = this.buttons.size();
        double angleIncrement = 2 * Math.PI / totalButtons; // Equal spacing for all buttons
        double startAngle = -Math.PI / 2; // Start at the top (negative y-axis)

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        for (int i = 0; i < totalButtons; i++) {
            Button button = this.buttons.get(i);

            // Calculate angle for this button
            double angle = startAngle + (i * angleIncrement);

            // Calculate position based on angle
            int buttonX = (int) (centerX + BUTTON_RADIUS * Math.cos(angle)) - 30; // Adjusted for button width
            int buttonY = (int) (centerY + BUTTON_RADIUS * Math.sin(angle)) - 10; // Adjusted for button height

            // Update button position
            button.setX(buttonX);
            button.setY(buttonY);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);

        // Center of the screen
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Draw background texture
        int textureX = (this.width - TEXTURE_WIDTH) / 2;
        int textureY = (this.height - TEXTURE_HEIGHT) / 2;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(r, g, b, 0.5f); // Red, Green, Blue, Alpha
        guiGraphics.blit(TEXTURE, textureX, textureY, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        RenderSystem.disableBlend();

        // Call the super render to draw widgets and buttons
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }
}