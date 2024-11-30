package net.SpectrumFATM.black_archive.item;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.SpectrumFATM.BlackArchive;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

// Importing ExpectPlatform


public class ModItemGroups {

    public static final ItemGroup BLACK_ARCHIVE = Registry.register(Registries.ITEM_GROUP,
            new Identifier(BlackArchive.MOD_ID, "vortex_manipulator"),
            FabricItemGroup.builder()  // Fabric-specific
                    .displayName(Text.translatable("itemgroup.black_archive"))
                    .icon(() -> new ItemStack(ModItems.VORTEXMANIP.get()))
                    .entries((displayContext, entries) -> {
                        // Add items to the group
                        entries.add(ModItems.VORTEXMANIP.get());
                        entries.add(ModItems.CONTACTLENS.get());
                        entries.add(ModItems.TARDISKEYCLASSIC.get());
                        entries.add(ModItems.REMOTE.get());
                        entries.add(ModItems.SUPERPHONE.get());
                        entries.add(ModItems.SONIC10.get());
                        entries.add(ModItems.SONIC11.get());
                        entries.add(ModItems.SONIC12.get());
                        entries.add(ModItems.SONIC13.get());
                        entries.add(ModItems.SONIC14.get());
                        entries.add(ModItems.SONIC15.get());
                        entries.add(ModItems.DALEK_LASER_GUN.get());
                        entries.add(ModItems.DALEK_BRACELET.get());
                        entries.add(ModItems.TCE.get());
                        entries.add(ModItems.GRAVITY_GEN.get());
                        entries.add(ModItems.OXYGEN_GEN.get());
                    }).build());

    // Common method for adding items to item groups
    @ExpectPlatform
    public static void registerToVanillaItemGroups() {
        throw new AssertionError("This method should be implemented on the platform side.");
    }

    public static void registerItemGroups() {
        BlackArchive.LOGGER.info("Registering Item Groups for " + BlackArchive.MOD_ID);
        registerToVanillaItemGroups();  // Call to the platform-specific implementation
    }
}
