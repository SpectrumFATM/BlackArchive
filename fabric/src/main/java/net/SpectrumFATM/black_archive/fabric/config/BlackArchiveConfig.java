package net.SpectrumFATM.black_archive.fabric.config;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class BlackArchiveConfig {

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ClientConfig CLIENT;

    static {
        final ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();
        final ForgeConfigSpec.Builder clientBuilder = new ForgeConfigSpec.Builder();

        COMMON = new CommonConfig(commonBuilder);
        CLIENT = new ClientConfig(clientBuilder);

        COMMON_SPEC = commonBuilder.build();
        CLIENT_SPEC = clientBuilder.build();
    }

    public static void register() {
        ForgeConfigRegistry.INSTANCE.register("black_archive", ModConfig.Type.COMMON, COMMON_SPEC);
        ForgeConfigRegistry.INSTANCE.register("black_archive", ModConfig.Type.CLIENT, CLIENT_SPEC);
    }

    public static class CommonConfig {
        public final ForgeConfigSpec.IntValue vortexManipulatorCooldown;
        public final ForgeConfigSpec.IntValue tardisLifeSupportRange;
        public final ForgeConfigSpec.IntValue gravityFieldRange;
        public final ForgeConfigSpec.IntValue oxygenFieldRange;
        public final ForgeConfigSpec.IntValue telepathicCircuitRange;
        public final ForgeConfigSpec.BooleanValue shouldDalekGunStickDestroyDoors;
        public final ForgeConfigSpec.BooleanValue shouldCybermatSpawnAroundCybermen;

        CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("common");

            vortexManipulatorCooldown = builder
                    .comment("The cooldown time for the vortex manipulator in seconds.")
                    .defineInRange("vortexManipulatorCooldown", 10, 1, 60);

            tardisLifeSupportRange = builder
                    .comment("The range of the TARDIS life support in blocks.")
                    .defineInRange("tardisLifeSupportRange", 3, 1, 100);

            gravityFieldRange = builder
                    .comment("The range of the gravity field in blocks.")
                    .defineInRange("gravityFieldRange", 8, 1, 100);

            oxygenFieldRange = builder
                    .comment("The range of the oxygen field in blocks.")
                    .defineInRange("oxygenFieldRange", 8, 1, 100);

            telepathicCircuitRange = builder
                    .comment("The range of the telepathic circuit in blocks.")
                    .defineInRange("telepathicCircuitRange", 5000, 1, 10000);

            shouldDalekGunStickDestroyDoors = builder
                    .comment("Should the Dalek gun stick destroy doors?")
                    .define("shouldDalekGunStickDestroyDoors", true);

            shouldCybermatSpawnAroundCybermen = builder
                    .comment("Should Cybermats spawn around Cybermen?")
                    .define("shouldCybermatSpawnAroundCybermen", true);

            builder.pop();
        }
    }

    public static class ClientConfig {
        public final ForgeConfigSpec.BooleanValue shouldCacheSkins;

        ClientConfig(ForgeConfigSpec.Builder builder) {
            builder.push("client");

            shouldCacheSkins = builder
                    .comment("Should the client download and cache skins?")
                    .define("shouldCacheSkins", true);

            builder.pop();
        }
    }
}