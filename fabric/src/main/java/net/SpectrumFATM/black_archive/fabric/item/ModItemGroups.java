package net.SpectrumFATM.black_archive.fabric.item;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
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
                    }).build());


    public static void registerItemGroups() {
        BlackArchive.LOGGER.info("Registering Item Groups for " + BlackArchive.MOD_ID);
    }
}