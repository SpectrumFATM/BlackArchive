package net.SpectrumFATM.black_archive.fabric.item;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.block.ModBlocks;
import net.SpectrumFATM.black_archive.fabric.entity.ModEntities;
import net.SpectrumFATM.black_archive.fabric.item.custom.*;
import net.SpectrumFATM.black_archive.fabric.sound.ModSounds;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import whocraft.tardis_refined.common.items.KeyItem;

public class ModItems {

    public static final Item VORTEXMANIP = registerItem("vortex_manipulator", new VortexManipulatorItem(new FabricItemSettings().maxCount(1)));
    public static final Item CONTACTLENS = registerItem("contact_lens", new ContactLensItem(new FabricItemSettings().maxCount(1)));
    public static final Item TARDISKEYCLASSIC = registerItem("key_01", new KeyItem(new FabricItemSettings().maxCount(1)));
    public static final Item TARDISKEYMODERN = registerItem("key_02", new KeyItem(new FabricItemSettings().maxCount(1)));
    public static final Item REMOTE = registerItem("remote", new RemoteItem(new FabricItemSettings().maxCount(1)));
    public static final Item SONIC10 = registerItem("10thsonic", new SonicItem(new FabricItemSettings().maxCount(1), "item.sonic10.tooltip", Formatting.BLUE));
    public static final Item SONIC11 = registerItem("11thsonic", new SonicItem(new FabricItemSettings().maxCount(1), "item.sonic11.tooltip", Formatting.DARK_GREEN));
    public static final Item SONIC12 = registerItem("12thsonic", new SonicItem(new FabricItemSettings().maxCount(1), "item.sonic12.tooltip", Formatting.BLUE));
    public static final Item SONIC14 = registerItem("14thsonic", new SonicItem(new FabricItemSettings().maxCount(1), "item.sonic.tooltip", Formatting.BLUE));
    public static final Item SONIC15 = registerItem("15thsonic", new SonicItem(new FabricItemSettings().maxCount(1), "item.sonic15.tooltip", Formatting.BLUE));
    public static final Item SONIC13 = registerItem("13thsonic", new SonicItem(new FabricItemSettings().maxCount(1), "item.sonic13.tooltip", Formatting.GOLD));
    public static final Item DALEK_LASER_GUN = registerItem("dalek_gun_stick", new LaserGunItem(new FabricItemSettings().maxCount(1), "item.dalek_laser.tooltip", ModSounds.DALEK_LASER, ModSounds.DALEK_MALFUNCTION));
    public static final Item DALEK_BRACELET = registerItem("dalek_bracelet", new TooltipItem(new FabricItemSettings().maxCount(1), "item.dalek_bracelet.tooltip"));

    public static final Item STEEL_INGOT = registerItem("steel_ingot", new Item(new FabricItemSettings()));
    public static final Item RAW_STEEL = registerItem("raw_steel", new Item(new FabricItemSettings()));

    public static final Item DALEK_GRAV_GEN = registerItem("dalek_gravity_generator", (Item)new TooltipBlockItem(ModBlocks.DALEK_GRAVITY_GEN, new FabricItemSettings(), "block.dalek_gravity.tooltip"));
    public static final Item GRAVITY_GEN = registerItem("gravity_generator", (Item)new TooltipBlockItem(ModBlocks.GRAVITY_GEN, new FabricItemSettings(), "block.gravity.tooltip"));
    public static final Item OXYGEN_GEN = registerItem("oxygen_field", (Item)new TooltipBlockItem(ModBlocks.OXYGEN_GEN, new FabricItemSettings(), "block.oxygen.tooltip"));
    public static final Item STEEL_BLOCK = registerItem("steel_block", new BlockItem(ModBlocks.STEEL_BLOCK, new FabricItemSettings()));
    public static final Item CUT_STEEL = registerItem("cut_steel", new BlockItem(ModBlocks.CUT_STEEL, new FabricItemSettings()));
    public static final Item ETCHED_STEEL = registerItem("etched_steel", new BlockItem(ModBlocks.ETCHED_STEEL, new FabricItemSettings()));
    public static final Item STEEL_STAIRS = registerItem("steel_stairs", new BlockItem(ModBlocks.STEEL_STAIRS, new FabricItemSettings()));
    public static final Item STEEL_SLAB = registerItem("steel_slab", new BlockItem(ModBlocks.STEEL_SLAB, new FabricItemSettings()));

    public static final Item DALEK_EGG = registerItem("dalek_spawn_egg", (Item)new SpawnEggItem(ModEntities.DALEK, 0xFFFFFF, 0xFFFFFF, new FabricItemSettings().maxCount(1)));
    public static final Item CYBERMAT_EGG = registerItem("cybermat_spawn_egg", (Item)new SpawnEggItem(ModEntities.CYBERMAT, 0xFFFFFF, 0xFFFFFF, new FabricItemSettings().maxCount(1)));
    public static final Item CYBERMAN_EGG = registerItem("cyberman_spawn_egg", (Item)new SpawnEggItem(ModEntities.CYBERMAN, 0xFFFFFF, 0xFFFFFF, new FabricItemSettings().maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(BlackArchive.MOD_ID, name), item);
    }

    public static void registerModItems() {
        BlackArchive.LOGGER.info("Registering mod items for black_archive");

    }
}