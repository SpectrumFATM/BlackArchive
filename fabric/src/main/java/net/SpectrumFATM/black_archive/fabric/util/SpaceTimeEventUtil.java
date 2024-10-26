package net.SpectrumFATM.black_archive.fabric.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.data.TrackedData;

import java.lang.reflect.Field;

public class SpaceTimeEventUtil {

    private static final TrackedData<Boolean> IS_COMPLEX_SPACE_TIME_EVENT;

    static {
        try {
            Field field = PlayerEntity.class.getDeclaredField("IS_COMPLEX_SPACE_TIME_EVENT");
            field.setAccessible(true);
            IS_COMPLEX_SPACE_TIME_EVENT = (TrackedData<Boolean>) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize IS_COMPLEX_SPACE_TIME_EVENT", e);
        }
    }

    public static void setComplexSpaceTimeEvent(PlayerEntity player, boolean status) {
        player.getDataTracker().set(IS_COMPLEX_SPACE_TIME_EVENT, status);
    }

    public static boolean isComplexSpaceTimeEvent(PlayerEntity player) {
        return player.getDataTracker().get(IS_COMPLEX_SPACE_TIME_EVENT);
    }
}