package net.SpectrumFATM.forge;

import net.SpectrumFATM.black_archive.renderer.TardisWarningRenderer;
import net.SpectrumFATM.forge.entity.ModEntityRenderers;
import net.SpectrumFATM.forge.renderer.ForgeSkyRenderer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;

import net.SpectrumFATM.BlackArchive;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BlackArchive.MOD_ID)
public final class BlackArchiveForge {
    public BlackArchiveForge() {
        // Run our common setup.
        BlackArchive.init();

        // Submit our event bus to let Architectury API register our content on the right time.
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register setup events
        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::onClientSetup);

        // Register renderer and model layer events
        modEventBus.addListener(ModEntityRenderers::onRegisterRenderers);
        modEventBus.addListener(ModEntityRenderers::onRegisterModelLayers);
    }

    private void onCommonSetup(final net.minecraftforge.eventbus.api.Event event) {
        // Common setup code here
    }

    private void onClientSetup(final net.minecraftforge.eventbus.api.Event event) {
        TardisWarningRenderer.register();
    }
}
