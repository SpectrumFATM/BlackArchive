package net.SpectrumFATM.black_archive.fabric.item;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.SpectrumFATM.black_archive.fabric.item.custom.VortexManipulatorItem;

public class ModItems {

    public static final Item VORTEXMANIP = registerItem("vortex_manipulator", new VortexManipulatorItem(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(BlackArchive.MOD_ID, name), item);
    }

    public static void registerModItems() {
        BlackArchive.LOGGER.info("Registering mod items");

    }
}
