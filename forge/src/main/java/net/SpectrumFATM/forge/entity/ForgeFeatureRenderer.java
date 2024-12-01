package net.SpectrumFATM.forge.entity;

import net.SpectrumFATM.black_archive.entity.features.BraceletFeatureRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "black_archive", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeFeatureRenderer {
    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        // Retrieve the player renderer for the default skin
        LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> playerRenderer = event.getSkin("default");
        if (playerRenderer != null) {
            // Add the custom bracelet feature renderer
            playerRenderer.addFeature(new BraceletFeatureRenderer(playerRenderer));
        }

        // Retrieve the player renderer for the slim (Alex) skin
        LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> slimPlayerRenderer = event.getSkin("slim");
        if (slimPlayerRenderer != null) {
            // Add the custom bracelet feature renderer
            slimPlayerRenderer.addFeature(new BraceletFeatureRenderer(slimPlayerRenderer));
        }
    }
}

