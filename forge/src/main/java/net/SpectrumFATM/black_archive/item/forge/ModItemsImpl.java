package net.SpectrumFATM.black_archive.item.forge;

import net.SpectrumFATM.black_archive.item.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class ModItemsImpl {
    public static ItemGroup getCreativeTab() {
        return ItemGroup.builder()
                .displayName(Text.translatable("itemGroup.black_archive"))
                .icon(() -> new ItemStack(ModItems.VORTEXMANIP.get()))
                .build();
    }
}