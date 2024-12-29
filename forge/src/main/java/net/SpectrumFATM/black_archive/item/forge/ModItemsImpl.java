package net.SpectrumFATM.black_archive.item.forge;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.TRItemRegistry;

import java.util.Map;

@Mod.EventBusSubscriber(modid = BlackArchive.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModItemsImpl {
    public static CreativeModeTab getCreativeTab() {
        return CreativeModeTab.builder()
                .title(Component.translatable("itemgroup.black_archive"))
                .icon(() -> new ItemStack(ModItems.VORTEXMANIP.get()))
                .build();
    }

    @SubscribeEvent
    public static void onBuildTabsContent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == ModItems.MAIN_TAB.get()) {
            for (RegistrySupplier<Item> tabItem : ModItems.TAB_ITEMS) {
                event.accept(tabItem.get());
            }
        }
    }
}