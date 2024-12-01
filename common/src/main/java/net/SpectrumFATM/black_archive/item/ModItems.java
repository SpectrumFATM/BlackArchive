package net.SpectrumFATM.black_archive.item;

import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.block.ModBlocks;
import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.item.custom.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Formatting;
import whocraft.tardis_refined.common.items.KeyItem;

public class ModItems {

    public static final DeferredRegistry<Item> ITEMS = DeferredRegistry.create(BlackArchive.MOD_ID, RegistryKeys.ITEM);

    public static final RegistrySupplier<Item> VORTEXMANIP = registerItem("vortex_manipulator", new VortexManipulatorItem(new Item.Settings().maxCount(1)));
    public static final RegistrySupplier<Item> CONTACTLENS = registerItem("contact_lens", new ContactLensItem(new Item.Settings().maxCount(1)));
    public static final RegistrySupplier<Item> TARDISKEYCLASSIC = registerItem("key_01", new KeyItem(new Item.Settings().maxCount(1)));
    public static final RegistrySupplier<Item> SUPERPHONE = registerItem("superphone", new DistressItem(new Item.Settings().maxCount(1), "item.superphone.tooltip"));
    public static final RegistrySupplier<Item> REMOTE = registerItem("remote", new RemoteItem(new Item.Settings().maxCount(1)));
    public static final RegistrySupplier<Item> SONIC10 = registerItem("10thsonic", new SonicItem(new Item.Settings().maxCount(1), "item.sonic10.tooltip", Formatting.BLUE));
    public static final RegistrySupplier<Item> SONIC11 = registerItem("11thsonic", new SonicItem(new Item.Settings().maxCount(1), "item.sonic11.tooltip", Formatting.DARK_GREEN));
    public static final RegistrySupplier<Item> SONIC12 = registerItem("12thsonic", new SonicItem(new Item.Settings().maxCount(1), "item.sonic12.tooltip", Formatting.BLUE));
    public static final RegistrySupplier<Item> SONIC14 = registerItem("14thsonic", new SonicItem(new Item.Settings().maxCount(1), "item.sonic.tooltip", Formatting.BLUE));
    public static final RegistrySupplier<Item> SONIC15 = registerItem("15thsonic", new SonicItem(new Item.Settings().maxCount(1), "item.sonic15.tooltip", Formatting.BLUE));
    public static final RegistrySupplier<Item> SONIC13 = registerItem("13thsonic", new SonicItem(new Item.Settings().maxCount(1), "item.sonic13.tooltip", Formatting.GOLD));
    //public static final RegistrySupplier<Item> DALEK_LASER_GUN = registerItem("dalek_gun_stick", new LaserGunItem(new Item.Settings().maxCount(1), "item.dalek_laser.tooltip", ModSounds.DALEK_LASER.get(), ModSounds.DALEK_MALFUNCTION.get()));
    public static final RegistrySupplier<Item> DALEK_BRACELET = registerItem("dalek_bracelet", new TooltipItem(new Item.Settings().maxCount(1), "item.dalek_bracelet.tooltip"));
    public static final RegistrySupplier<Item> TCE = registerItem("tce", new CompressorItem(new Item.Settings().maxCount(1), "item.tce.tooltip"));

    public static final RegistrySupplier<Item> STEEL_INGOT = registerItem("steel_ingot", new Item(new Item.Settings()));
    public static final RegistrySupplier<Item> RAW_STEEL = registerItem("raw_steel", new Item(new Item.Settings()));

    public static final RegistrySupplier<Item> DALEK_GRAV_GEN = registerItem("dalek_gravity_generator", new TooltipBlockItem(ModBlocks.DALEK_GRAVITY_GEN.get(), new Item.Settings(), "block.dalek_gravity.tooltip"));
    public static final RegistrySupplier<Item> GRAVITY_GEN = registerItem("gravity_generator", new TooltipBlockItem(ModBlocks.GRAVITY_GEN.get(), new Item.Settings(), "block.gravity.tooltip"));
    public static final RegistrySupplier<Item> OXYGEN_GEN = registerItem("oxygen_field", new TooltipBlockItem(ModBlocks.OXYGEN_GEN.get(), new Item.Settings(), "block.oxygen.tooltip"));
    public static final RegistrySupplier<Item> STEEL_BLOCK = registerItem("steel_block", new BlockItem(ModBlocks.STEEL_BLOCK.get(), new Item.Settings().arch$tab(ItemGroups.BUILDING_BLOCKS)));
    public static final RegistrySupplier<Item> CUT_STEEL = registerItem("cut_steel", new BlockItem(ModBlocks.CUT_STEEL.get(), new Item.Settings().arch$tab(ItemGroups.BUILDING_BLOCKS)));
    public static final RegistrySupplier<Item> ETCHED_STEEL = registerItem("etched_steel", new BlockItem(ModBlocks.ETCHED_STEEL.get(), new Item.Settings().arch$tab(ItemGroups.BUILDING_BLOCKS)));
    public static final RegistrySupplier<Item> STEEL_STAIRS = registerItem("steel_stairs", new BlockItem(ModBlocks.STEEL_STAIRS.get(), new Item.Settings().arch$tab(ItemGroups.BUILDING_BLOCKS)));
    public static final RegistrySupplier<Item> STEEL_SLAB = registerItem("steel_slab", new BlockItem(ModBlocks.STEEL_SLAB.get(), new Item.Settings().arch$tab(ItemGroups.BUILDING_BLOCKS)));

    public static final RegistrySupplier<Item> DALEK_EGG = registerItem("dalek_spawn_egg", new SpawnEggItem(ModEntities.DALEK.get(), 0xFFFFFF, 0xFFFFFF, new Item.Settings().maxCount(1).arch$tab(ItemGroups.SPAWN_EGGS)));
    public static final RegistrySupplier<Item> CYBERMAT_EGG = registerItem("cybermat_spawn_egg", new SpawnEggItem(ModEntities.CYBERMAT.get(), 0xFFFFFF, 0xFFFFFF, new Item.Settings().maxCount(1).arch$tab(ItemGroups.SPAWN_EGGS)));
    public static final RegistrySupplier<Item> CYBERMAN_EGG = registerItem("cyberman_spawn_egg", new SpawnEggItem(ModEntities.CYBERMAN.get(), 0xFFFFFF, 0xFFFFFF, new Item.Settings().maxCount(1).arch$tab(ItemGroups.SPAWN_EGGS)));

    private static RegistrySupplier<Item> registerItem(String name, Item item) {
        BlackArchive.LOGGER.info("Registered item: " + name);
        return ITEMS.register(name, () -> item);
    }
}