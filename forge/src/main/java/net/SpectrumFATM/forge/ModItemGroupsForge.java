package net.SpectrumFATM.forge;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraftforge.common.CreativeModeTabRegistry;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ModItemGroupsForge {

    public static final DeferredRegistry<ItemGroup> BLACK_ARCHIVE = DeferredRegistry.create(BlackArchive.MOD_ID, Registries.ITEM_GROUP.getKey());

    public static final RegistrySupplier<ItemGroup> BLACK_ARCHIVE_GROUP = BLACK_ARCHIVE.register("black_archive", () -> ItemGroup.builder().icon(() -> new ItemStack(ModItems.VORTEXMANIP.get())).build());

    @SubscribeEvent
    public static void registerToVanillaItemGroups(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == CreativeModeTabRegistry.getTab(ItemGroups.SPAWN_EGGS.getValue())) {
            event.add(new ItemStack(ModItems.DALEK_EGG.get()));
            event.add(new ItemStack(ModItems.CYBERMAN_EGG.get()));
            event.add(new ItemStack(ModItems.CYBERMAT_EGG.get()));
        }

        if (event.getTab() == CreativeModeTabRegistry.getTab(ItemGroups.INGREDIENTS.getValue())) {
            event.add(new ItemStack(ModItems.STEEL_INGOT.get()));
            event.add(new ItemStack(ModItems.RAW_STEEL.get()));
        }

        if (event.getTab() == CreativeModeTabRegistry.getTab(ItemGroups.BUILDING_BLOCKS.getValue())) {
            event.add(new ItemStack(ModItems.STEEL_BLOCK.get()));
            event.add(new ItemStack(ModItems.CUT_STEEL.get()));
            event.add(new ItemStack(ModItems.ETCHED_STEEL.get()));
            event.add(new ItemStack(ModItems.STEEL_STAIRS.get()));
            event.add(new ItemStack(ModItems.STEEL_SLAB.get()));
        }


        if (event.getTab() == BLACK_ARCHIVE_GROUP.get()) {
            event.add(new ItemStack(ModItems.DALEK_EGG.get()));
            event.add(new ItemStack(ModItems.CYBERMAN_EGG.get()));
            event.add(new ItemStack(ModItems.CYBERMAT_EGG.get()));
            event.add(new ItemStack(ModItems.STEEL_INGOT.get()));
            event.add(new ItemStack(ModItems.RAW_STEEL.get()));
            event.add(new ItemStack(ModItems.STEEL_BLOCK.get()));
            event.add(new ItemStack(ModItems.CUT_STEEL.get()));
            event.add(new ItemStack(ModItems.ETCHED_STEEL.get()));
            event.add(new ItemStack(ModItems.STEEL_STAIRS.get()));
            event.add(new ItemStack(ModItems.STEEL_SLAB.get()));
            event.add(new ItemStack(ModItems.VORTEXMANIP.get()));
            event.add(new ItemStack(ModItems.CONTACTLENS.get()));
            event.add(new ItemStack(ModItems.TARDISKEYCLASSIC.get()));
            event.add(new ItemStack(ModItems.SUPERPHONE.get()));
            event.add(new ItemStack(ModItems.REMOTE.get()));
            event.add(new ItemStack(ModItems.SONIC10.get()));
            event.add(new ItemStack(ModItems.SONIC11.get()));
            event.add(new ItemStack(ModItems.SONIC12.get()));
            event.add(new ItemStack(ModItems.SONIC14.get()));
            event.add(new ItemStack(ModItems.SONIC15.get()));
            event.add(new ItemStack(ModItems.SONIC13.get()));
            event.add(new ItemStack(ModItems.DALEK_LASER_GUN.get()));
            event.add(new ItemStack(ModItems.DALEK_BRACELET.get()));
            event.add(new ItemStack(ModItems.TCE.get()));
        }
    }
}