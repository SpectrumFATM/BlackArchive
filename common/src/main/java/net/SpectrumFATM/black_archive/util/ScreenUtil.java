package net.SpectrumFATM.black_archive.util;

import net.SpectrumFATM.black_archive.screen.VortexScreen;
import net.minecraft.client.Minecraft;
import whocraft.tardis_refined.common.util.Platform;

import java.util.List;

public class ScreenUtil {

    public static void openVortexScreen(List<String> dimensions) {
        if (Platform.isClient()) {
            Minecraft.getInstance().setScreen(new VortexScreen(dimensions));
        }
    }
}
