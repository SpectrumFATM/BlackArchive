package net.SpectrumFATM.black_archive.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.block.ModBlocks;
import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.item.custom.*;
import net.SpectrumFATM.black_archive.sound.ModSounds;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Formatting;
import whocraft.tardis_refined.common.items.KeyItem;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BlackArchive.MOD_ID, RegistryKeys.ITEM);

    public static final RegistrySupplier<Item> VORTEXMANIP = registerItem("vortex_manipulator", new VortexManipulatorItem(new FabricItemSettings().maxCount(1)));
    public static final RegistrySupplier<Item> CONTACTLENS = registerItem("contact_lens", new ContactLensItem(new FabricItemSettings().maxCount(1)));
    public static final RegistrySupplier<Item> TARDISKEYCLASSIC = registerItem("key_01", new KeyItem(new FabricItemSettings().maxCount(1)));
    public static final RegistrySupplier<Item> SUPERPHONE = registerItem("superphone", new DistressItem(new FabricItemSettings().maxCount(1), "item.superphone.tooltip"));
    public static final RegistrySupplier<Item> REMOTE = registerItem("remote", new RemoteItem(new FabricItemSettings().maxCount(1)));
    public static final RegistrySupplier<Item> SONIC10 = registerItem("10thsonic", new SonicItem(new FabricItemSettings().maxCount(1), "item.sonic10.tooltip", Formatting.BLUE));
    public static final RegistrySupplier<Item> SONIC11 = registerItem("11thsonic", new SonicItem(new FabricItemSettings().maxCount(1), "item.sonic11.tooltip", Formatting.DARK_GREEN));
    public static final RegistrySupplier<Item> SONIC12 = registerItem("12thsonic", new SonicItem(new FabricItemSettings().maxCount(1), "item.sonic12.tooltip", Formatting.BLUE));
    public static final RegistrySupplier<Item> SONIC14 = registerItem("14thsonic", new SonicItem(new FabricItemSettings().maxCount(1), "item.sonic.tooltip", Formatting.BLUE));
    public static final RegistrySupplier<Item> SONIC15 = registerItem("15thsonic", new SonicItem(new FabricItemSettings().maxCount(1), "item.sonic15.tooltip", Formatting.BLUE));
    public static final RegistrySupplier<Item> SONIC13 = registerItem("13thsonic", new SonicItem(new FabricItemSettings().maxCount(1), "item.sonic13.tooltip", Formatting.GOLD));
    //public static final RegistrySupplier<Item> DALEK_LASER_GUN = registerItem("dalek_gun_stick", new LaserGunItem(new FabricItemSettings().maxCount(1), "item.dalek_laser.tooltip", ModSounds.DALEK_LASER.get(), ModSounds.DALEK_MALFUNCTION.get()));
    public static final RegistrySupplier<Item> DALEK_BRACELET = registerItem("dalek_bracelet", new TooltipItem(new FabricItemSettings().maxCount(1), "item.dalek_bracelet.tooltip"));
    public static final RegistrySupplier<Item> TCE = registerItem("tce", new CompressorItem(new FabricItemSettings().maxCount(1), "item.tce.tooltip"));

    public static final RegistrySupplier<Item> STEEL_INGOT = registerItem("steel_ingot", new Item(new FabricItemSettings()));
    public static final RegistrySupplier<Item> RAW_STEEL = registerItem("raw_steel", new Item(new FabricItemSettings()));

    public static final RegistrySupplier<Item> DALEK_GRAV_GEN = registerItem("dalek_gravity_generator", new TooltipBlockItem(ModBlocks.DALEK_GRAVITY_GEN.get(), new FabricItemSettings(), "block.dalek_gravity.tooltip"));
    public static final RegistrySupplier<Item> GRAVITY_GEN = registerItem("gravity_generator", new TooltipBlockItem(ModBlocks.GRAVITY_GEN.get(), new FabricItemSettings(), "block.gravity.tooltip"));
    public static final RegistrySupplier<Item> OXYGEN_GEN = registerItem("oxygen_field", new TooltipBlockItem(ModBlocks.OXYGEN_GEN.get(), new FabricItemSettings(), "block.oxygen.tooltip"));
    public static final RegistrySupplier<Item> STEEL_BLOCK = registerItem("steel_block", new BlockItem(ModBlocks.STEEL_BLOCK.get(), new FabricItemSettings()));
    public static final RegistrySupplier<Item> CUT_STEEL = registerItem("cut_steel", new BlockItem(ModBlocks.CUT_STEEL.get(), new FabricItemSettings()));
    public static final RegistrySupplier<Item> ETCHED_STEEL = registerItem("etched_steel", new BlockItem(ModBlocks.ETCHED_STEEL.get(), new FabricItemSettings()));
    public static final RegistrySupplier<Item> STEEL_STAIRS = registerItem("steel_stairs", new BlockItem(ModBlocks.STEEL_STAIRS.get(), new FabricItemSettings()));
    public static final RegistrySupplier<Item> STEEL_SLAB = registerItem("steel_slab", new BlockItem(ModBlocks.STEEL_SLAB.get(), new FabricItemSettings()));

    public static final RegistrySupplier<Item> DALEK_EGG = registerItem("dalek_spawn_egg", new SpawnEggItem(ModEntities.DALEK.get(), 0xFFFFFF, 0xFFFFFF, new FabricItemSettings().maxCount(1)));
    public static final RegistrySupplier<Item> CYBERMAT_EGG = registerItem("cybermat_spawn_egg", new SpawnEggItem(ModEntities.CYBERMAT.get(), 0xFFFFFF, 0xFFFFFF, new FabricItemSettings().maxCount(1)));
    public static final RegistrySupplier<Item> CYBERMAN_EGG = registerItem("cyberman_spawn_egg", new SpawnEggItem(ModEntities.CYBERMAN.get(), 0xFFFFFF, 0xFFFFFF, new FabricItemSettings().maxCount(1)));

    private static RegistrySupplier<Item> registerItem(String name, Item item) {
        BlackArchive.LOGGER.info("Registered item: " + name);
        return ITEMS.register(name, () -> item);
    }
}