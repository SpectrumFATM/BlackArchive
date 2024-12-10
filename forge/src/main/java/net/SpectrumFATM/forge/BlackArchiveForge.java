package net.SpectrumFATM.forge;

import net.SpectrumFATM.black_archive.blockentity.ModModels;
import net.SpectrumFATM.black_archive.blockentity.forge.ModModelsImpl;
import net.SpectrumFATM.forge.entity.ModEntityRenderers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

import net.SpectrumFATM.BlackArchive;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static net.SpectrumFATM.black_archive.config.BlackArchiveConfig.CLIENT_SPEC;
import static net.SpectrumFATM.black_archive.config.BlackArchiveConfig.COMMON_SPEC;

@Mod(BlackArchive.MOD_ID)
public final class BlackArchiveForge {
    public BlackArchiveForge() {
        BlackArchive.init();

        // Submit our event bus to let Architectury API register our content on the right time.
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Run our common setup.
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);

        // Register setup events
        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::onClientSetup);

        // Register renderer and model layer events
        modEventBus.addListener(ModEntityRenderers::onRegisterRenderers);
        modEventBus.addListener(ModEntityRenderers::onRegisterModelLayers);
    }


    private void onCommonSetup(FMLCommonSetupEvent event) {
        // Common setup code here
    }

    private void onClientSetup(FMLClientSetupEvent event) {
    }
}
