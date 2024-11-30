package net.SpectrumFATM.black_archive.fabric;

import net.SpectrumFATM.black_archive.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;

public class ModItemGroupsFabric {

    // Implement the method for Fabric
    public static void registerToVanillaItemGroups() {
        // Fabric-specific registration using ItemGroupEvents
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> {
            content.add(ModItems.DALEK_EGG.get());
            content.add(ModItems.CYBERMAN_EGG.get());
            content.add(ModItems.CYBERMAT_EGG.get());
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
            content.add(ModItems.STEEL_INGOT.get());
            content.add(ModItems.RAW_STEEL.get());
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {
            content.add(ModItems.STEEL_BLOCK.get());
            content.add(ModItems.CUT_STEEL.get());
            content.add(ModItems.ETCHED_STEEL.get());
            content.add(ModItems.STEEL_STAIRS.get());
            content.add(ModItems.STEEL_SLAB.get());
        });
    }
}
