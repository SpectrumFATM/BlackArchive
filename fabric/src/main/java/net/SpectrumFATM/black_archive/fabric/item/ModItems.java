package net.SpectrumFATM.black_archive.fabric.item;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.item.custom.ContactLensItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.SpectrumFATM.black_archive.fabric.item.custom.VortexManipulatorItem;
import whocraft.tardis_refined.common.items.KeyItem;

public class ModItems {

    public static final Item VORTEXMANIP = registerItem("vortex_manipulator", new VortexManipulatorItem(new FabricItemSettings()));
    public static final Item CONTACTLENS = registerItem("contact_lens", new ContactLensItem(new FabricItemSettings()));
    public static final Item TARDISKEYCLASSIC = registerItem("key_01", new KeyItem(new FabricItemSettings()));
    public static final Item TARDISKEYMODERN = registerItem("key_02", new KeyItem(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(BlackArchive.MOD_ID, name), item);
    }

    public static void registerModItems() {
        BlackArchive.LOGGER.info("Registering mod items");

    }
}
