package net.SpectrumFATM.black_archive.item.fabric;

import net.SpectrumFATM.black_archive.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.TRItemRegistry;

public class ModItemsImpl {

    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.VORTEXMANIP.get()))
            .displayName(Text.translatable("itemGroup.black_archive"))
            .build();

    public static ItemGroup getCreativeTab() {
        return ITEM_GROUP;
    }


}