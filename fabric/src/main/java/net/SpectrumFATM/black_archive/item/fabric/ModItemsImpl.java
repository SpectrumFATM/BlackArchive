package net.SpectrumFATM.black_archive.item.fabric;

import net.SpectrumFATM.black_archive.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ModItemsImpl {

    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.VORTEXMANIP.get()))
            .displayName(Text.translatable("itemgroup.black_archive"))
            .entries(((displayContext, entries) -> {
                for (RegistrySupplier<Item> tabItem : ModItems.TAB_ITEMS) {
                    entries.add(tabItem.get());
                }
            }))
            .build();

    public static ItemGroup getCreativeTab() {
        return ITEM_GROUP;
    }


}