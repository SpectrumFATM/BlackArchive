package net.SpectrumFATM.black_archive.item;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.block.ModBlocks;
import net.SpectrumFATM.black_archive.item.custom.*;
import net.SpectrumFATM.black_archive.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.common.util.PlatformWarning;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModItems {

    public static final DeferredRegistry<CreativeModeTab> TABS = DeferredRegistry.create(BlackArchive.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> MAIN_TAB = TABS.register("main_tab", ModItems::getCreativeTab);

    public static List<RegistrySupplier<Item>> TAB_ITEMS = new ArrayList<>();

    public static final DeferredRegistry<Item> ITEMS = DeferredRegistry.create(BlackArchive.MOD_ID, Registries.ITEM);

    // Sonic Screwdriver Items - Conditionally registered
    public static final RegistrySupplier<Item> SONIC10 = registerItemConditional("10thsonic", () -> new SonicItem(new Item.Properties().stacksTo(1), "item.sonic10.tooltip", ChatFormatting.BLUE));
    public static final RegistrySupplier<Item> SONIC11 = registerItemConditional("11thsonic", () -> new SonicItem(new Item.Properties().stacksTo(1), "item.sonic11.tooltip", ChatFormatting.DARK_GREEN));
    public static final RegistrySupplier<Item> SONIC12 = registerItemConditional("12thsonic", () -> new SonicItem(new Item.Properties().stacksTo(1), "item.sonic12.tooltip", ChatFormatting.BLUE));
    public static final RegistrySupplier<Item> SONIC13 = registerItemConditional("13thsonic", () -> new SonicItem(new Item.Properties().stacksTo(1), "item.sonic13.tooltip", ChatFormatting.GOLD));
    public static final RegistrySupplier<Item> SONIC14 = registerItemConditional("14thsonic", () -> new SonicItem(new Item.Properties().stacksTo(1), "item.sonic.tooltip", ChatFormatting.BLUE));

    //Sonic Screwdriver Items - Always registered
    public static final RegistrySupplier<Item> SONIC15 = registerItem("15thsonic", () -> new SonicItem(new Item.Properties().stacksTo(1), "item.sonic15.tooltip", ChatFormatting.BLUE), true);

    // Other Custom Items
    public static final RegistrySupplier<Item> VORTEXMANIP = registerItem("vortex_manipulator", () -> new VortexManipulatorItem(new Item.Properties().stacksTo(1)), true);
    public static final RegistrySupplier<Item> CONTACTLENS = registerItem("contact_lens", () -> new ContactLensItem(new Item.Properties().stacksTo(1)), true);
    public static final RegistrySupplier<Item> TARDISKEYCLASSIC = registerItem("key_01", () -> new KeyItem(new Item.Properties().stacksTo(1)), true);
    public static final RegistrySupplier<Item> SUPERPHONE = registerItem("superphone", () -> new DistressItem(new Item.Properties().stacksTo(1), "item.superphone.tooltip"), true);
    public static final RegistrySupplier<Item> REMOTE = registerItem("remote", () -> new RemoteItem(new Item.Properties().stacksTo(1), "item.remote.tooltip"), true);

    // Weapon Items
    public static final RegistrySupplier<Item> DALEK_LASER_GUN = registerItem("dalek_gun_stick", () -> new LaserGunItem(new Item.Properties().stacksTo(1), "item.dalek_laser.tooltip", ModSounds.DALEK_LASER.get(), ModSounds.DALEK_MALFUNCTION.get()), true);
    public static final RegistrySupplier<Item> TCE = registerItem("tce", () -> new CompressorItem(new Item.Properties().stacksTo(1), "item.tce.tooltip"), true);
    public static final RegistrySupplier<Item> DALEK_BRACELET = registerItem("dalek_bracelet", () -> new TooltipItem(new Item.Properties().stacksTo(1), "item.dalek_bracelet.tooltip"), true);

    // Block Items
    public static final RegistrySupplier<Item> DALEK_GRAV_GEN = registerItem("dalek_gravity_generator", () -> new TooltipBlockItem(ModBlocks.DALEK_GRAVITY_GEN.get(), new Item.Properties(), "block.dalek_gravity.tooltip"), false);
    public static final RegistrySupplier<Item> GRAVITY_GEN = registerItem("gravity_generator", () -> new TooltipBlockItem(ModBlocks.GRAVITY_GEN.get(), new Item.Properties(), "block.gravity.tooltip"), true);
    public static final RegistrySupplier<Item> OXYGEN_GEN = registerItem("oxygen_field", () -> new TooltipBlockItem(ModBlocks.OXYGEN_GEN.get(), new Item.Properties(), "block.oxygen.tooltip"), true);
    public static final RegistrySupplier<Item> STEEL_BLOCK = registerItem("steel_block", () -> new BlockItem(ModBlocks.STEEL_BLOCK.get(), new Item.Properties()), true);
    public static final RegistrySupplier<Item> CUT_STEEL = registerItem("cut_steel", () -> new BlockItem(ModBlocks.CUT_STEEL.get(), new Item.Properties()), true);
    public static final RegistrySupplier<Item> ETCHED_STEEL = registerItem("etched_steel", () -> new BlockItem(ModBlocks.ETCHED_STEEL.get(), new Item.Properties()), true);
    public static final RegistrySupplier<Item> STEEL_STAIRS = registerItem("steel_stairs", () -> new BlockItem(ModBlocks.STEEL_STAIRS.get(), new Item.Properties()), true);
    public static final RegistrySupplier<Item> STEEL_SLAB = registerItem("steel_slab", () -> new BlockItem(ModBlocks.STEEL_SLAB.get(), new Item.Properties()), true);

    // Spawn Eggs
    public static final RegistrySupplier<Item> CYBERMAN_SPAWN_EGG = registerItem("cyberman_spawn_egg", () -> new SpawnItem(new Item.Properties(), "cyberman"), true);
    public static final RegistrySupplier<Item> CYBERMAT_EGG = registerItem("cybermat_spawn_egg", () -> new SpawnItem(new Item.Properties(), "cybermat"), true);
    public static final RegistrySupplier<Item> DALEK_SPAWN_EGG = registerItem("dalek_spawn_egg", () -> new SpawnItem(new Item.Properties(), "dalek"), true);
    public static final RegistrySupplier<Item> ANGEL_SPAWN_EGG = registerItem("weeping_angel_egg", () -> new SpawnItem(new Item.Properties(), "weeping_angel", "item.weeping_angel.tooltip"), true);

    // Materials
    public static final RegistrySupplier<Item> STEEL_INGOT = registerItem("steel_ingot", () -> new Item(new Item.Properties()), true);
    public static final RegistrySupplier<Item> RAW_STEEL = registerItem("raw_steel", () -> new Item(new Item.Properties()), true);

    // Generic registration helper
    public static RegistrySupplier<Item> registerItem(String name, Supplier<Item> item, boolean addToTab) {
        return registerItem(name, item, addToTab, true);
    }

    // Main registration method with condition
    private static RegistrySupplier<Item> registerItem(String name, Supplier<Item> item, boolean addToTab, boolean shouldRegister) {
        if (!shouldRegister) return null; // Prevent registration if not allowed
        RegistrySupplier<Item> itemSupplier = ITEMS.register(name, item);
        if (addToTab) {
            TAB_ITEMS.add(itemSupplier);
        }
        return itemSupplier;
    }

    // Helper for conditional registration based on `whocosmetics` mod
    private static RegistrySupplier<Item> registerItemConditional(String name, Supplier<Item> item) {
        boolean shouldRegister = !Platform.isModLoaded("whocosmetics");
        return registerItem(name, item, true, shouldRegister);
    }

    @ExpectPlatform
    public static CreativeModeTab getCreativeTab() {
        throw new RuntimeException(PlatformWarning.addWarning(ModItems.class));
    }
}