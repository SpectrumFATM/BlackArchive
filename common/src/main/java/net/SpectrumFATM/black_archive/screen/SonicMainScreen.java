package net.SpectrumFATM.black_archive.screen;

import net.SpectrumFATM.black_archive.network.messages.C2SSonicMode;
import net.SpectrumFATM.black_archive.util.TARDISBindUtil;
import net.SpectrumFATM.black_archive.util.sonic.SonicEngine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class SonicMainScreen extends SonicScreen {

    private static boolean isBound = false;
    private static String boundTo;
    private static Component setting;
    private int r, g, b;

    public SonicMainScreen(float r, float g, float b) {
        super(r, g, b);
        this.r = (int) (r * 255);
        this.g = (int) (g * 255);
        this.b = (int) (b * 255);
    }

    @Override
    protected void init() {
        super.init();
        String settingString = SonicEngine.getSonicSetting(Minecraft.getInstance().player.getMainHandItem());
        setting = Component.translatable("item.sonic.setting_name").append(Component.translatable(SonicEngine.getSettingKey(settingString)));

        buttons.clear(); // Clear any previous buttons to avoid conflicts

        // Add buttons with unique actions
        addCircularButton(Component.translatable("item.sonic.setting.power"), () -> {
            C2SSonicMode message = new C2SSonicMode();
            message.send(); // Send the mode change message
        });

        if (TARDISBindUtil.hasTardisLevelName(Minecraft.getInstance().player.getMainHandItem())) {
            isBound = true;
            boundTo = TARDISBindUtil.getTardisLevelName(Minecraft.getInstance().player.getMainHandItem()).replaceFirst("tardis_refined:", "").substring(0, 5);
            addCircularButton(Component.translatable("item.sonic.setting.lock"), SonicEngine::lock);
            addCircularButton(Component.translatable("item.sonic.setting.set"), () -> {
                if (SonicEngine.getSonicSetting(Minecraft.getInstance().player.getMainHandItem()).equals("location")) {
                    SonicEngine.setSetting("block", true);
                } else {
                    SonicEngine.setSetting("location", true);
                }
            });
            addCircularButton(Component.translatable("item.sonic.homing_name"), () -> {
                if (SonicEngine.getSonicSetting(Minecraft.getInstance().player.getMainHandItem()).equals("homing")) {
                    SonicEngine.setSetting("block", true);
                } else {
                    SonicEngine.setSetting("homing", true);
                }
            });
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        guiGraphics.drawCenteredString(font, setting, width / 2, height / 2 + 5, textColour(r, g, b));
        if (isBound) {
            guiGraphics.drawCenteredString(font, Component.translatable("item.sonic.bound_name").append(boundTo), width / 2, height / 2 - 5, textColour(r, g, b));

        }
    }

    private int textColour(int r, int g, int b) {
        return (r << 16) + (g << 8) + b;
    }
}
