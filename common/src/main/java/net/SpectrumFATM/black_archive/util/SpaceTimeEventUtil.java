package net.SpectrumFATM.black_archive.util;

import java.lang.reflect.Field;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.player.Player;

public class SpaceTimeEventUtil {

    private static final EntityDataAccessor<Boolean> IS_COMPLEX_SPACE_TIME_EVENT;

    static {
        try {
            Field field = Player.class.getDeclaredField("IS_COMPLEX_SPACE_TIME_EVENT");
            field.setAccessible(true);
            IS_COMPLEX_SPACE_TIME_EVENT = (EntityDataAccessor<Boolean>) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize IS_COMPLEX_SPACE_TIME_EVENT", e);
        }
    }

    public static void setComplexSpaceTimeEvent(Player player, boolean status) {
        player.getEntityData().set(IS_COMPLEX_SPACE_TIME_EVENT, status);
    }

    public static boolean isComplexSpaceTimeEvent(Player player) {
        if (player.getEntityData().get(IS_COMPLEX_SPACE_TIME_EVENT) != null) {
            return player.getEntityData().get(IS_COMPLEX_SPACE_TIME_EVENT);
        }
        return false;
    }
}