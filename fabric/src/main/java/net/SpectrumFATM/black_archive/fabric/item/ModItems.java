package net.SpectrumFATM.black_archive.fabric.item;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.block.ModBlocks;
import net.SpectrumFATM.black_archive.fabric.entity.ModEntities;
import net.SpectrumFATM.black_archive.fabric.item.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import whocraft.tardis_refined.common.items.KeyItem;

public class ModItems {

    public static final Item VORTEXMANIP = registerItem("vortex_manipulator", new VortexManipulatorItem(new FabricItemSettings()));
    public static final Item CONTACTLENS = registerItem("contact_lens", new ContactLensItem(new FabricItemSettings()));
    public static final Item TARDISKEYCLASSIC = registerItem("key_01", new KeyItem(new FabricItemSettings()));
    public static final Item TARDISKEYMODERN = registerItem("key_02", new KeyItem(new FabricItemSettings()));
    public static final Item REMOTE = registerItem("remote", new RemoteItem(new FabricItemSettings()));
    public static final Item SONIC14 = registerItem("14thsonic", new SonicItem(new FabricItemSettings(), "item.sonic.tooltip", Formatting.BLUE));
    public static final Item SONIC15 = registerItem("15thsonic", new SonicItem(new FabricItemSettings(), "item.sonic15.tooltip", Formatting.BLUE));
    public static final Item SONIC13 = registerItem("13thsonic", new SonicItem(new FabricItemSettings(), "item.sonic13.tooltip", Formatting.GOLD));

    public static final Item DALEK_GRAV_GEN = registerItem("dalek_gravity_generator", (Item)new TooltipBlockItem(ModBlocks.DALEK_GRAVITY_GEN, new FabricItemSettings(), "block.dalek_gravity.tooltip"));
    public static final Item GRAVITY_GEN = registerItem("gravity_generator", (Item)new TooltipBlockItem(ModBlocks.GRAVITY_GEN, new FabricItemSettings(), "block.gravity.tooltip"));
    public static final Item OXYGEN_GEN = registerItem("oxygen_field", (Item)new TooltipBlockItem(ModBlocks.OXYGEN_GEN, new FabricItemSettings(), "block.oxygen.tooltip"));

    public static final Item DALEK_EGG = registerItem("dalek_spawn_egg", (Item)new SpawnEggItem(ModEntities.DALEK, 0xFFFFFF, 0xFFFFFF, new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(BlackArchive.MOD_ID, name), item);
    }

    public static void registerModItems() {
        BlackArchive.LOGGER.info("Registering mod items");

    }
}