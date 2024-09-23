package net.SpectrumFATM.black_archive.fabric.item;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup BLACK_ARCHIVE = Registry.register(Registries.ITEM_GROUP,
            new Identifier(BlackArchive.MOD_ID, "vortex_manipulator"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.black_archive"))
                    .icon(() -> new ItemStack(ModItems.VORTEXMANIP)).entries((displayContext, entries) -> {
                        entries.add(ModItems.VORTEXMANIP);
                        entries.add(ModItems.CONTACTLENS);
                        entries.add(ModItems.TARDISKEYCLASSIC);
                        entries.add(ModItems.TARDISKEYMODERN);
                        entries.add(ModItems.REMOTE);
                        entries.add(ModItems.SONIC10);
                        entries.add(ModItems.SONIC13);
                        entries.add(ModItems.SONIC14);
                        entries.add(ModItems.SONIC15);
                        entries.add(ModItems.DALEK_LASER_GUN);
                        entries.add(ModItems.DALEK_BRACELET);
                        entries.add(ModItems.GRAVITY_GEN);
                        entries.add(ModItems.OXYGEN_GEN);
                    }).build());


    public static void registerToVanillaItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> {
            content.add(ModItems.DALEK_EGG);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
            content.add(ModItems.STEEL_INGOT);
            content.add(ModItems.RAW_STEEL);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {
            content.add(ModItems.STEEL_BLOCK);
            content.add(ModItems.CUT_STEEL);
            content.add(ModItems.ETCHED_STEEL);
            content.add(ModItems.STEEL_STAIRS);
            content.add(ModItems.STEEL_SLAB);
        });
    }

    public static void registerItemGroups() {
        BlackArchive.LOGGER.info("Registering Item Groups for " + BlackArchive.MOD_ID);
    }
}