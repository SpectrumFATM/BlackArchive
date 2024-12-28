package net.SpectrumFATM.forge.entity;

import net.SpectrumFATM.black_archive.entity.features.BraceletFeatureRenderer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "black_archive", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeFeatureRenderer {
    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        // Retrieve the player renderer for the default skin
        LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> playerRenderer = event.getSkin("default");
        if (playerRenderer != null) {
            // Add the custom bracelet feature renderer
            playerRenderer.addLayer(new BraceletFeatureRenderer(playerRenderer));
        }

        // Retrieve the player renderer for the slim (Alex) skin
        LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> slimPlayerRenderer = event.getSkin("slim");
        if (slimPlayerRenderer != null) {
            // Add the custom bracelet feature renderer
            slimPlayerRenderer.addLayer(new BraceletFeatureRenderer(slimPlayerRenderer));
        }
    }
}

