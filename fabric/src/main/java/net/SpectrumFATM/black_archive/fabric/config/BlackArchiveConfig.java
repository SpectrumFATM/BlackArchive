package net.SpectrumFATM.black_archive.fabric.config;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class BlackArchiveConfig {

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        COMMON = new CommonConfig(builder);
        COMMON_SPEC = builder.build();
    }

    public static void register() {
        ForgeConfigRegistry.INSTANCE.register("black_archive", ModConfig.Type.COMMON, COMMON_SPEC);
    }

    public static class CommonConfig {
        public final ForgeConfigSpec.IntValue vortexManipulatorCooldown;
        public final ForgeConfigSpec.IntValue gravityFieldRange;
        public final ForgeConfigSpec.IntValue oxygenFieldRange;
        public final ForgeConfigSpec.BooleanValue shouldDalekGunStickDestroyDoors;

        CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("common");

            vortexManipulatorCooldown = builder
                    .comment("The cooldown time for the vortex manipulator in seconds.")
                    .defineInRange("vortexManipulatorCooldown", 10, 1, 60);

            gravityFieldRange = builder
                    .comment("The range of the gravity field in blocks.")
                    .defineInRange("gravityFieldRange", 8, 1, 100);

            oxygenFieldRange = builder
                    .comment("The range of the oxygen field in blocks.")
                    .defineInRange("oxygenFieldRange", 8, 1, 100);

            shouldDalekGunStickDestroyDoors = builder
                    .comment("Should the Dalek gun stick destroy doors?")
                    .define("shouldDalekGunStickDestroyDoors", true);

            builder.pop();
        }
    }
}