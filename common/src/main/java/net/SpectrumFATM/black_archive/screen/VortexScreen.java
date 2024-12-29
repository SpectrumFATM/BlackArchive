package net.SpectrumFATM.black_archive.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import io.netty.buffer.Unpooled;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.item.custom.VortexManipulatorItem;
import net.SpectrumFATM.black_archive.network.messages.C2SWaypointDeleteMessage;
import net.SpectrumFATM.black_archive.network.messages.C2SWaypointSaveMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.SpectrumFATM.black_archive.network.messages.C2STeleportMessage;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VortexScreen extends Screen {

    private static final ResourceLocation TEXTURE = new ResourceLocation(BlackArchive.MOD_ID, "textures/gui/vortex_gui.png");
    private static final int BACKGROUND_WIDTH = 256;
    private static final int BACKGROUND_HEIGHT = 256;

    private static EditBox textFieldWidgetX;
    private static EditBox textFieldWidgetY;
    private static EditBox textFieldWidgetZ;
    private static EditBox textFieldWidgetDimension;
    private static EditBox textFieldWidgetWaypointSaveName;
    private static EditBox textFieldWidgetDisplayWaypoint;
    private static Button buttonLeft;
    private static Button buttonRight;
    private static Button buttonTeleport;
    private static Button buttonSaveWaypoint;
    private static Button buttonWaypointLeft;
    private static Button buttonWaypointRight;
    private static Button buttonWaypointDelete;

    private List<String> dimensions;
    private List<String> waypoints = new ArrayList<>();
    private int currentDimensionIndex = 0;
    private int currentWaypointIndex = 0;

    public VortexScreen(List<String> dimensions) {
        super(Component.literal("Vortex Manipulator"));
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
        textFieldWidgetX = createTextField(x + (7 * 4), y + (15 * 4), 88, 20, String.valueOf(Math.round(Minecraft.getInstance().player.getX())));
        textFieldWidgetY = createTextField(x + (7 * 4), y + (21 * 4), 88, 20, String.valueOf(Math.round(Minecraft.getInstance().player.getY())));
        textFieldWidgetZ = createTextField(x + (7 * 4), y + (27 * 4), 88, 20, String.valueOf(Math.round(Minecraft.getInstance().player.getZ())));
        textFieldWidgetDimension = createTextField(x + (34 * 4), y + (21 * 4), 92, 20, "");
        textFieldWidgetWaypointSaveName = createTextField(x + (34 * 4), y + (35 * 4), 92, 20, "");
        textFieldWidgetDisplayWaypoint = createTextField(x + (7 * 4), y + (35 * 4), 92, 20, "");

        this.addWidget(textFieldWidgetX);
        this.addWidget(textFieldWidgetY);
        this.addWidget(textFieldWidgetZ);
        this.addWidget(textFieldWidgetWaypointSaveName);
    }

    private EditBox createTextField(int x, int y, int width, int height, String text) {
        EditBox textField = new EditBox(this.font, x, y, width, height, Component.literal(""));
        textField.setValue(text);
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
            this.onClose();
        });

        buttonSaveWaypoint = createButton(x + (34 * 4), y + (41 * 4), 92, 20, "Save Waypoint", (button) -> {
            saveWaypoint();
            this.onClose();
        });

        buttonWaypointLeft = createButton(x + (7 * 4) - 1, y + (41 * 4), 20, 20, "<", (button) -> {
            cycleWaypoint(-1);
        });

        buttonWaypointRight = createButton(x + (25 * 4), y + (41 * 4), 20, 20, ">", (button) -> {
            cycleWaypoint(1);
        });

        buttonWaypointDelete = createButton(x + (13 * 4), y + (41 * 4), 40, 20, "Delete", (button) -> {
            deleteWaypoint();
            this.onClose();
        });

        this.addWidget(buttonLeft);
        this.addWidget(buttonRight);
        this.addWidget(buttonTeleport);
        this.addWidget(buttonSaveWaypoint);
        this.addWidget(buttonWaypointLeft);
        this.addWidget(buttonWaypointRight);
        this.addWidget(buttonWaypointDelete);
    }

    private Button createButton(int x, int y, int width, int height, String text, Button.OnPress onPress) {
        return Button.builder(Component.literal(text), onPress).bounds(x, y, width, height).build();
    }

    private void teleportPlayer() {
        if (currentDimensionIndex < 0 || currentDimensionIndex >= dimensions.size()) {
            return;
        }

        String xText = textFieldWidgetX.getValue();
        String yText = textFieldWidgetY.getValue();
        String zText = textFieldWidgetZ.getValue();
        String dimension = dimensions.get(currentDimensionIndex);

        double xCoord = xText.isEmpty() ? minecraft.player.getX() : Double.parseDouble(xText);
        double yCoord = yText.isEmpty() ? minecraft.player.getY() : Double.parseDouble(yText);
        double zCoord = zText.isEmpty() ? minecraft.player.getZ() : Double.parseDouble(zText);

        if (!dimension.isEmpty() && !dimension.startsWith("tardis_refined:")) {
            C2STeleportMessage packet = new C2STeleportMessage( xCoord, yCoord, zCoord, dimension);
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            packet.toBytes(buf);
            packet.send();
        }
    }

    private void saveWaypoint() {
        String name = textFieldWidgetWaypointSaveName.getValue();
        double x = Double.parseDouble(textFieldWidgetX.getValue());
        double y = Double.parseDouble(textFieldWidgetY.getValue());
        double z = Double.parseDouble(textFieldWidgetZ.getValue());
        String dimension = dimensions.get(currentDimensionIndex);

        C2SWaypointSaveMessage packet = new C2SWaypointSaveMessage(name, x, y, z, dimension); // Example data: 42
        packet.send();
    }

    private void deleteWaypoint() {
        if (!waypoints.isEmpty()) {
            String waypointName = waypoints.get(currentWaypointIndex);
            ItemStack heldItem = Minecraft.getInstance().player.getMainHandItem();
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
        ItemStack heldItem = Minecraft.getInstance().player.getMainHandItem();

        // Check if the main hand has a VortexManipulatorItem, else check the off-hand
        if (!(heldItem.getItem() instanceof VortexManipulatorItem)) {
            heldItem = Minecraft.getInstance().player.getOffhandItem();
        }

        if (!heldItem.isEmpty() && heldItem.hasTag()) {
            CompoundTag nbt = heldItem.getTag();
            if (nbt != null) {
                waypoints.clear(); // Clear current waypoints list

                for (String key : nbt.getAllKeys()) {
                    CompoundTag waypointData = nbt.getCompound(key);
                    if (waypointData.contains("x") && waypointData.contains("y") && waypointData.contains("z") && waypointData.contains("dimension")) {
                        // Extract waypoint data and log it
                        double x = waypointData.getDouble("x");
                        double y = waypointData.getDouble("y");
                        double z = waypointData.getDouble("z");
                        String dimension = waypointData.getString("dimension");

                        waypoints.add(key); // Add waypoint name to the list
                    }
                }
            }
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
            ItemStack heldItem = Minecraft.getInstance().player.getMainHandItem();
            if (!heldItem.isEmpty() && heldItem.hasTag()) {
                CompoundTag nbt = heldItem.getTag();
                if (nbt != null && nbt.contains(waypointName)) {
                    CompoundTag waypointData = nbt.getCompound(waypointName);
                    textFieldWidgetX.setValue(String.valueOf(Math.round(waypointData.getDouble("x"))));
                    textFieldWidgetY.setValue(String.valueOf(Math.round(waypointData.getDouble("y"))));
                    textFieldWidgetZ.setValue(String.valueOf(Math.round(waypointData.getDouble("z"))));
                    currentDimensionIndex = getDimensionIndex(waypointData.getString("dimension"));
                    textFieldWidgetDisplayWaypoint.setValue(waypointName);
                    updateDimensionField();
                }
            }
            updateDimensionField();
        }
    }

    public int getDimensionIndex(String namespace) {
        return dimensions.indexOf(namespace);
    }

    public void setWaypoints(List<String> waypoints) {
        this.waypoints = waypoints;
    }

    public void setDimensions(List<String> dimensions) {
        this.dimensions = dimensions;
        if (!dimensions.isEmpty()) {
            String currentDimension = Minecraft.getInstance().player.level().dimension().location().toString();
            currentDimensionIndex = getDimensionIndex(currentDimension);
            updateDimensionField();
        }
    }

    private void updateDimensionField() {
        if (!dimensions.isEmpty() && currentDimensionIndex >= 0 && currentDimensionIndex < dimensions.size()) {
            String dimension = dimensions.get(currentDimensionIndex);
            dimension = formatDimension(dimension);
            textFieldWidgetDimension.setValue(dimension);
        } else {
            textFieldWidgetDimension.setValue("");
        }
    }

    private String formatDimension(String dimension) {
        dimension = dimension.replaceFirst("^[^:]+:", "");
        return Arrays.stream(dimension.split("_"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    protected void drawBackground(GuiGraphics context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - BACKGROUND_WIDTH) / 2;
        int y = (height - BACKGROUND_HEIGHT) / 2;
        context.blit(TEXTURE, x, y, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        this.drawBackground(context, delta, mouseX, mouseY);
        renderTextFields(context, mouseX, mouseY, delta);
        renderButtons(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }

    private void renderTextFields(GuiGraphics context, int mouseX, int mouseY, float delta) {
        textFieldWidgetX.render(context, mouseX, mouseY, delta);
        textFieldWidgetY.render(context, mouseX, mouseY, delta);
        textFieldWidgetZ.render(context, mouseX, mouseY, delta);
        textFieldWidgetDimension.render(context, mouseX, mouseY, delta);
        textFieldWidgetWaypointSaveName.render(context, mouseX, mouseY, delta);
        textFieldWidgetDisplayWaypoint.render(context, mouseX, mouseY, delta);
    }

    private void renderButtons(GuiGraphics context, int mouseX, int mouseY, float delta) {
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

    private boolean handleTextFieldClick(EditBox textField, double mouseX, double mouseY, int button) {
        if (textField.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(textField);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.onClose();
            return true;
        }
        if (handleTextFieldKeyPress(textFieldWidgetX, keyCode, scanCode, modifiers)) return true;
        if (handleTextFieldKeyPress(textFieldWidgetY, keyCode, scanCode, modifiers)) return true;
        if (handleTextFieldKeyPress(textFieldWidgetZ, keyCode, scanCode, modifiers)) return true;
        if (handleTextFieldKeyPress(textFieldWidgetWaypointSaveName, keyCode, scanCode, modifiers)) return true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private boolean handleTextFieldKeyPress(EditBox textField, int keyCode, int scanCode, int modifiers) {
        return textField.keyPressed(keyCode, scanCode, modifiers) || textField.canConsumeInput();
    }
}