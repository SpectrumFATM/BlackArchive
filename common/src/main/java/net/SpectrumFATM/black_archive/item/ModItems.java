package net.SpectrumFATM.black_archive.item;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.SpectrumFATM.black_archive.sound.ModSounds;
import net.minecraft.item.*;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.block.ModBlocks;
import net.SpectrumFATM.black_archive.item.custom.*;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Formatting;
import whocraft.tardis_refined.common.items.KeyItem;

import java.util.function.Supplier;

public class ModItems {

    public static final DeferredRegistry<ItemGroup> TABS = DeferredRegistry.create(BlackArchive.MOD_ID, RegistryKeys.ITEM_GROUP);
    public static final RegistrySupplier<ItemGroup> MAIN_TAB = TABS.register("main_tab", ModItems::getCreativeTab);

    public static final DeferredRegistry<Item> ITEMS = DeferredRegistry.create(BlackArchive.MOD_ID, RegistryKeys.ITEM);

    public static final RegistrySupplier<Item> VORTEXMANIP = registerItem("vortex_manipulator",() -> new VortexManipulatorItem(new Item.Settings().maxCount(1).arch$tab(MAIN_TAB.get())));
    public static final RegistrySupplier<Item> CONTACTLENS = registerItem("contact_lens",() -> new ContactLensItem(new Item.Settings().maxCount(1).arch$tab(MAIN_TAB.get())));
    public static final RegistrySupplier<Item> TARDISKEYCLASSIC = registerItem("key_01",() -> new KeyItem(new Item.Settings().maxCount(1).arch$tab(MAIN_TAB.get())));
    public static final RegistrySupplier<Item> SUPERPHONE = registerItem("superphone",() -> new DistressItem(new Item.Settings().maxCount(1).arch$tab(MAIN_TAB.get()), "item.superphone.tooltip"));
    public static final RegistrySupplier<Item> REMOTE = registerItem("remote", () ->new RemoteItem(new Item.Settings().maxCount(1).arch$tab(MAIN_TAB.get())));
    public static final RegistrySupplier<Item> SONIC10 = registerItem("10thsonic", () ->new SonicItem(new Item.Settings().maxCount(1).arch$tab(MAIN_TAB.get()), "item.sonic10.tooltip", Formatting.BLUE));
    public static final RegistrySupplier<Item> SONIC11 = registerItem("11thsonic", () ->new SonicItem(new Item.Settings().maxCount(1).arch$tab(MAIN_TAB.get()), "item.sonic11.tooltip", Formatting.DARK_GREEN));
    public static final RegistrySupplier<Item> SONIC12 = registerItem("12thsonic", () ->new SonicItem(new Item.Settings().maxCount(1).arch$tab(MAIN_TAB.get()), "item.sonic12.tooltip", Formatting.BLUE));
    public static final RegistrySupplier<Item> SONIC13 = registerItem("13thsonic", () ->new SonicItem(new Item.Settings().maxCount(1).arch$tab(MAIN_TAB.get()), "item.sonic13.tooltip", Formatting.GOLD));
    public static final RegistrySupplier<Item> SONIC14 = registerItem("14thsonic", () ->new SonicItem(new Item.Settings().maxCount(1).arch$tab(MAIN_TAB.get()), "item.sonic.tooltip", Formatting.BLUE));
    public static final RegistrySupplier<Item> SONIC15 = registerItem("15thsonic", () ->new SonicItem(new Item.Settings().maxCount(1).arch$tab(MAIN_TAB.get()), "item.sonic15.tooltip", Formatting.BLUE));
    public static final RegistrySupplier<Item> DALEK_LASER_GUN = registerItem("dalek_gun_stick", () -> new LaserGunItem(new Item.Settings().maxCount(1).arch$tab(MAIN_TAB.get()), "item.dalek_laser.tooltip", ModSounds.DALEK_LASER.get(), ModSounds.DALEK_MALFUNCTION.get()));
    public static final RegistrySupplier<Item> DALEK_BRACELET = registerItem("dalek_bracelet", () ->new TooltipItem(new Item.Settings().maxCount(1).arch$tab(MAIN_TAB.get()), "item.dalek_bracelet.tooltip"));
    public static final RegistrySupplier<Item> TCE = registerItem("tce",() -> new CompressorItem(new Item.Settings().maxCount(1).arch$tab(MAIN_TAB.get()), "item.tce.tooltip"));

    public static final RegistrySupplier<Item> STEEL_INGOT = registerItem("steel_ingot", () ->new Item(new Item.Settings()));
    public static final RegistrySupplier<Item> RAW_STEEL = registerItem("raw_steel", () ->new Item(new Item.Settings()));

    public static final RegistrySupplier<Item> DALEK_GRAV_GEN = registerItem("dalek_gravity_generator",() -> new TooltipBlockItem(ModBlocks.DALEK_GRAVITY_GEN.get(), new Item.Settings(), "block.dalek_gravity.tooltip"));
    public static final RegistrySupplier<Item> GRAVITY_GEN = registerItem("gravity_generator",() -> new TooltipBlockItem(ModBlocks.GRAVITY_GEN.get(), new Item.Settings().arch$tab(MAIN_TAB.get()), "block.gravity.tooltip"));
    public static final RegistrySupplier<Item> OXYGEN_GEN = registerItem("oxygen_field", () ->new TooltipBlockItem(ModBlocks.OXYGEN_GEN.get(), new Item.Settings().arch$tab(MAIN_TAB.get()), "block.oxygen.tooltip"));
    public static final RegistrySupplier<Item> STEEL_BLOCK = registerItem("steel_block",() -> new BlockItem(ModBlocks.STEEL_BLOCK.get(), new Item.Settings().arch$tab(ItemGroups.BUILDING_BLOCKS)));
    public static final RegistrySupplier<Item> CUT_STEEL = registerItem("cut_steel", () ->new BlockItem(ModBlocks.CUT_STEEL.get(), new Item.Settings().arch$tab(ItemGroups.BUILDING_BLOCKS)));
    public static final RegistrySupplier<Item> ETCHED_STEEL = registerItem("etched_steel", () ->new BlockItem(ModBlocks.ETCHED_STEEL.get(), new Item.Settings().arch$tab(ItemGroups.BUILDING_BLOCKS)));
    public static final RegistrySupplier<Item> STEEL_STAIRS = registerItem("steel_stairs", () ->new BlockItem(ModBlocks.STEEL_STAIRS.get(), new Item.Settings().arch$tab(ItemGroups.BUILDING_BLOCKS)));
    public static final RegistrySupplier<Item> STEEL_SLAB = registerItem("steel_slab", () ->new BlockItem(ModBlocks.STEEL_SLAB.get(), new Item.Settings().arch$tab(ItemGroups.BUILDING_BLOCKS)));

    private static RegistrySupplier<Item> registerItem(String name, Supplier<Item> item) {
        BlackArchive.LOGGER.info("Registered item: " + name);
        return ITEMS.register(name, item);
    }

    @ExpectPlatform
    public static ItemGroup getCreativeTab() {
        throw new RuntimeException(TardisRefined.PLATFORM_ERROR);
    }
}