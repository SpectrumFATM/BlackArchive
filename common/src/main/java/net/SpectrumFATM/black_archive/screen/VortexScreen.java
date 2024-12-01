package net.SpectrumFATM.black_archive.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import io.netty.buffer.Unpooled;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.item.custom.VortexManipulatorItem;
import net.SpectrumFATM.black_archive.network.messages.C2SWaypointDeleteMessage;
import net.SpectrumFATM.black_archive.network.messages.C2SWaypointSaveMessage;
import net.SpectrumFATM.black_archive.network.messages.C2STeleportMessage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VortexScreen extends Screen {

    private static final Identifier TEXTURE = new Identifier(BlackArchive.MOD_ID, "textures/gui/vortex_gui.png");
    private static final int BACKGROUND_WIDTH = 256;
    private static final int BACKGROUND_HEIGHT = 256;

    private static TextFieldWidget textFieldWidgetX;
    private static TextFieldWidget textFieldWidgetY;
    private static TextFieldWidget textFieldWidgetZ;
    private static TextFieldWidget textFieldWidgetDimension;
    private static TextFieldWidget textFieldWidgetWaypointSaveName;
    private static TextFieldWidget textFieldWidgetDisplayWaypoint;
    private static ButtonWidget buttonLeft;
    private static ButtonWidget buttonRight;
    private static ButtonWidget buttonTeleport;
    private static ButtonWidget buttonSaveWaypoint;
    private static ButtonWidget buttonWaypointLeft;
    private static ButtonWidget buttonWaypointRight;
    private static ButtonWidget buttonWaypointDelete;

    private List<String> dimensions;
    private List<String> waypoints = new ArrayList<>();
    private int currentDimensionIndex = 0;
    private int currentWaypointIndex = 0;

    public VortexScreen(List<String> dimensions) {
        super(Text.literal("Vortex Manipulator"));
        this.dimensions = dimensions;
    }

    @Override
    protected void init() {
        super.init();
        int x = (width - BACKGROUND_WIDTH) / 2;
        int y = (height - BACKGROUND_HEIGHT) / 2;

        initTextFields(x, y);
        initButtons(x, y);

        fetchWaypoints();
        updateDimensionField();
    }

    private void initTextFields(int x, int y) {
        textFieldWidgetX = createTextField(x + (7 * 4), y + (15 * 4), 88, 20, String.valueOf(Math.round(MinecraftClient.getInstance().player.getX())));
        textFieldWidgetY = createTextField(x + (7 * 4), y + (21 * 4), 88, 20, String.valueOf(Math.round(MinecraftClient.getInstance().player.getY())));
        textFieldWidgetZ = createTextField(x + (7 * 4), y + (27 * 4), 88, 20, String.valueOf(Math.round(MinecraftClient.getInstance().player.getZ())));
        textFieldWidgetDimension = createTextField(x + (34 * 4), y + (21 * 4), 92, 20, "");
        textFieldWidgetWaypointSaveName = createTextField(x + (34 * 4), y + (35 * 4), 92, 20, "");
        textFieldWidgetDisplayWaypoint = createTextField(x + (7 * 4), y + (35 * 4), 92, 20, "");

        this.addSelectableChild(textFieldWidgetX);
        this.addSelectableChild(textFieldWidgetY);
        this.addSelectableChild(textFieldWidgetZ);
        this.addSelectableChild(textFieldWidgetWaypointSaveName);
    }

    private TextFieldWidget createTextField(int x, int y, int width, int height, String text) {
        TextFieldWidget textField = new TextFieldWidget(this.textRenderer, x, y, width, height, Text.literal(""));
        textField.setText(text);
        return textField;
    }

    private void initButtons(int x, int y) {
        buttonLeft = createButton(x + (34 * 4) - 1, y + (27 * 4), 20, 20, "<", (button) -> {
            currentDimensionIndex = (currentDimensionIndex - 1 + dimensions.size()) % dimensions.size();
            updateDimensionField();
        });

        buttonRight = createButton(x + (52 * 4), y + (27 * 4), 20, 20, ">", (button) -> {
            currentDimensionIndex = (currentDimensionIndex + 1) % dimensions.size();
            updateDimensionField();
        });

        buttonTeleport = createButton(x + (34 * 4), y + (15 * 4), 92, 20, "Teleport", (button) -> {
            teleportPlayer();
            this.close();
        });

        buttonSaveWaypoint = createButton(x + (34 * 4), y + (41 * 4), 92, 20, "Save Waypoint", (button) -> {
            saveWaypoint();
            this.close();
        });

        buttonWaypointLeft = createButton(x + (7 * 4) - 1, y + (41 * 4), 20, 20, "<", (button) -> {
            cycleWaypoint(-1);
        });

        buttonWaypointRight = createButton(x + (25 * 4), y + (41 * 4), 20, 20, ">", (button) -> {
            cycleWaypoint(1);
        });

        buttonWaypointDelete = createButton(x + (13 * 4), y + (41 * 4), 40, 20, "Delete", (button) -> {
            deleteWaypoint();
            this.close();
        });

        this.addSelectableChild(buttonLeft);
        this.addSelectableChild(buttonRight);
        this.addSelectableChild(buttonTeleport);
        this.addSelectableChild(buttonSaveWaypoint);
        this.addSelectableChild(buttonWaypointLeft);
        this.addSelectableChild(buttonWaypointRight);
        this.addSelectableChild(buttonWaypointDelete);
    }

    private ButtonWidget createButton(int x, int y, int width, int height, String text, ButtonWidget.PressAction onPress) {
        return ButtonWidget.builder(Text.literal(text), onPress).dimensions(x, y, width, height).build();
    }

    private void teleportPlayer() {
        if (currentDimensionIndex < 0 || currentDimensionIndex >= dimensions.size()) {
            BlackArchive.LOGGER.error("Invalid dimension index: " + currentDimensionIndex);
            return;
        }

        String xText = textFieldWidgetX.getText();
        String yText = textFieldWidgetY.getText();
        String zText = textFieldWidgetZ.getText();
        String dimension = dimensions.get(currentDimensionIndex);

        double xCoord = xText.isEmpty() ? client.player.getX() : Double.parseDouble(xText);
        double yCoord = yText.isEmpty() ? client.player.getY() : Double.parseDouble(yText);
        double zCoord = zText.isEmpty() ? client.player.getZ() : Double.parseDouble(zText);

        if (!dimension.isEmpty() && !dimension.startsWith("tardis_refined:")) {
            C2STeleportMessage packet = new C2STeleportMessage( xCoord, yCoord, zCoord, dimension);
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            packet.toBytes(buf);
            packet.send();
        }
    }

    private void saveWaypoint() {
        String name = textFieldWidgetWaypointSaveName.getText();
        double x = Double.parseDouble(textFieldWidgetX.getText());
        double y = Double.parseDouble(textFieldWidgetY.getText());
        double z = Double.parseDouble(textFieldWidgetZ.getText());
        String dimension = dimensions.get(currentDimensionIndex);

        C2SWaypointSaveMessage packet = new C2SWaypointSaveMessage(name, x, y, z, dimension); // Example data: 42
        packet.send();
    }

    private void deleteWaypoint() {
        if (!waypoints.isEmpty()) {
            String waypointName = waypoints.get(currentWaypointIndex);
            ItemStack heldItem = MinecraftClient.getInstance().player.getMainHandStack();
            C2SWaypointDeleteMessage packet = new C2SWaypointDeleteMessage(waypointName);
            packet.send();
            waypoints.remove(currentWaypointIndex);
            if (currentWaypointIndex >= waypoints.size()) {
                currentWaypointIndex = waypoints.size() - 1;
            }
            updateWaypointFields(); // Refresh the displayed waypoint
        }
    }

    private void fetchWaypoints() {
        ItemStack heldItem = MinecraftClient.getInstance().player.getMainHandStack();

        // Check if the main hand has a VortexManipulatorItem, else check the off-hand
        if (!(heldItem.getItem() instanceof VortexManipulatorItem)) {
            heldItem = MinecraftClient.getInstance().player.getOffHandStack();
        }

        if (!heldItem.isEmpty() && heldItem.hasNbt()) {
            NbtCompound nbt = heldItem.getNbt();
            if (nbt != null) {
                waypoints.clear(); // Clear current waypoints list

                for (String key : nbt.getKeys()) {
                    NbtCompound waypointData = nbt.getCompound(key);
                    if (waypointData.contains("x") && waypointData.contains("y") && waypointData.contains("z") && waypointData.contains("dimension")) {
                        // Extract waypoint data and log it
                        double x = waypointData.getDouble("x");
                        double y = waypointData.getDouble("y");
                        double z = waypointData.getDouble("z");
                        String dimension = waypointData.getString("dimension");

                        waypoints.add(key); // Add waypoint name to the list

                        BlackArchive.LOGGER.info("Waypoint: " + key + " (x=" + x + ", y=" + y + ", z=" + z + ", dimension=" + dimension + ")");
                    }
                }

                BlackArchive.LOGGER.info("Fetched waypoints: " + waypoints);
            }
        } else {
            BlackArchive.LOGGER.warn("No Vortex Manipulator or waypoints found in the player's inventory.");
        }
    }

    private void cycleWaypoint(int direction) {
        if (!waypoints.isEmpty()) {
            currentWaypointIndex = (currentWaypointIndex + direction + waypoints.size()) % waypoints.size();
            updateWaypointFields(); // Update the displayed waypoint data
        }
    }


    private void updateWaypointFields() {
        if (!waypoints.isEmpty()) {
            String waypointName = waypoints.get(currentWaypointIndex);
            ItemStack heldItem = MinecraftClient.getInstance().player.getMainHandStack();
            if (!heldItem.isEmpty() && heldItem.hasNbt()) {
                NbtCompound nbt = heldItem.getNbt();
                if (nbt != null && nbt.contains(waypointName)) {
                    NbtCompound waypointData = nbt.getCompound(waypointName);
                    textFieldWidgetX.setText(String.valueOf(Math.round(waypointData.getDouble("x"))));
                    textFieldWidgetY.setText(String.valueOf(Math.round(waypointData.getDouble("y"))));
                    textFieldWidgetZ.setText(String.valueOf(Math.round(waypointData.getDouble("z"))));
                    currentDimensionIndex = getDimensionIndex(waypointData.getString("dimension"));
                    textFieldWidgetDisplayWaypoint.setText(waypointName);
                    updateDimensionField();
                }
            }
            updateDimensionField();
            BlackArchive.LOGGER.info("Dimension Index: " + currentDimensionIndex);
        }
    }

    public int getDimensionIndex(String namespace) {
        int index = dimensions.indexOf(namespace);
        if (index == -1) {
            BlackArchive.LOGGER.warn("Dimension not found: " + namespace);
        } else {
            BlackArchive.LOGGER.info("Retrieved dimension index: " + index + " for namespace: " + namespace);
        }
        return index;
    }

    public void setWaypoints(List<String> waypoints) {
        this.waypoints = waypoints;
    }

    public void setDimensions(List<String> dimensions) {
        this.dimensions = dimensions;
        if (!dimensions.isEmpty()) {
            String currentDimension = MinecraftClient.getInstance().player.getWorld().getRegistryKey().getValue().toString();
            currentDimensionIndex = getDimensionIndex(currentDimension);
            updateDimensionField();
        }
    }

    private void updateDimensionField() {
        if (!dimensions.isEmpty() && currentDimensionIndex >= 0 && currentDimensionIndex < dimensions.size()) {
            String dimension = dimensions.get(currentDimensionIndex);
            dimension = formatDimension(dimension);
            textFieldWidgetDimension.setText(dimension);
        } else {
            textFieldWidgetDimension.setText("");
        }
    }

    private String formatDimension(String dimension) {
        dimension = dimension.replaceFirst("^[^:]+:", "");
        return Arrays.stream(dimension.split("_"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - BACKGROUND_WIDTH) / 2;
        int y = (height - BACKGROUND_HEIGHT) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        this.drawBackground(context, delta, mouseX, mouseY);
        renderTextFields(context, mouseX, mouseY, delta);
        renderButtons(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }

    private void renderTextFields(DrawContext context, int mouseX, int mouseY, float delta) {
        textFieldWidgetX.render(context, mouseX, mouseY, delta);
        textFieldWidgetY.render(context, mouseX, mouseY, delta);
        textFieldWidgetZ.render(context, mouseX, mouseY, delta);
        textFieldWidgetDimension.render(context, mouseX, mouseY, delta);
        textFieldWidgetWaypointSaveName.render(context, mouseX, mouseY, delta);
        textFieldWidgetDisplayWaypoint.render(context, mouseX, mouseY, delta);
    }

    private void renderButtons(DrawContext context, int mouseX, int mouseY, float delta) {
        buttonLeft.render(context, mouseX, mouseY, delta);
        buttonRight.render(context, mouseX, mouseY, delta);
        buttonTeleport.render(context, mouseX, mouseY, delta);
        buttonSaveWaypoint.render(context, mouseX, mouseY, delta);
        buttonWaypointLeft.render(context, mouseX, mouseY, delta);
        buttonWaypointRight.render(context, mouseX, mouseY, delta);
        buttonWaypointDelete.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (handleTextFieldClick(textFieldWidgetX, mouseX, mouseY, button)) return true;
        if (handleTextFieldClick(textFieldWidgetY, mouseX, mouseY, button)) return true;
        if (handleTextFieldClick(textFieldWidgetZ, mouseX, mouseY, button)) return true;
        if (handleTextFieldClick(textFieldWidgetWaypointSaveName, mouseX, mouseY, button)) return true;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean handleTextFieldClick(TextFieldWidget textField, double mouseX, double mouseY, int button) {
        if (textField.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(textField);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.close();
            return true;
        }
        if (handleTextFieldKeyPress(textFieldWidgetX, keyCode, scanCode, modifiers)) return true;
        if (handleTextFieldKeyPress(textFieldWidgetY, keyCode, scanCode, modifiers)) return true;
        if (handleTextFieldKeyPress(textFieldWidgetZ, keyCode, scanCode, modifiers)) return true;
        if (handleTextFieldKeyPress(textFieldWidgetWaypointSaveName, keyCode, scanCode, modifiers)) return true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private boolean handleTextFieldKeyPress(TextFieldWidget textField, int keyCode, int scanCode, int modifiers) {
        return textField.keyPressed(keyCode, scanCode, modifiers) || textField.isActive();
    }
}