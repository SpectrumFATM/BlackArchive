package net.SpectrumFATM.black_archive.util;

import net.SpectrumFATM.black_archive.screen.SonicMainScreen;
import net.SpectrumFATM.black_archive.screen.VortexScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.Item;
import whocraft.tardis_refined.common.util.Platform;
import org.joml.Vector3f;

import java.util.List;

public class ScreenUtil {

    public static void openVortexScreen(List<String> dimensions) {
        if (Platform.isClient()) {
            Minecraft.getInstance().setScreen(new VortexScreen(dimensions));
        }
    }

    public static void openSonicScreen(int page) {
        if (Platform.isClient()) {
            Minecraft minecraft = Minecraft.getInstance();
            Item item = minecraft.player.getMainHandItem().getItem();
            minecraft.execute(() -> {
                Vector3f colour = SonicEngine.getSonicItemVector(item);
                Screen screen = new SonicMainScreen(colour.x, colour.y, colour.z);
                switch(page) {
                    case 0:
                        screen = new SonicMainScreen(colour.x, colour.y, colour.z);
                        break;
                }
                minecraft.setScreen(screen);
            });
        }
    }
}
