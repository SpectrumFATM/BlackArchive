package net.SpectrumFATM.forge.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.world.dimension.ModDimensions;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class RenderMixin {

    @Shadow
    private ClientWorld world;

    @Shadow
    private static Identifier SUN = new Identifier(BlackArchive.MOD_ID,"textures/environment/blank.png");

    @Shadow
    private static Identifier MOON_PHASES = new Identifier(BlackArchive.MOD_ID,"textures/environment/blank.png");

    @Inject(method = "renderClouds", at = @At("HEAD"), cancellable = true)
    public void renderClouds(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, double d, double e, double f, CallbackInfo ci) {
        if (world != null && (world.getRegistryKey() == ModDimensions.SPACEDIM_LEVEL_KEY || world.getRegistryKey() == ModDimensions.TIMEDIM_LEVEL_KEY)) {
            RenderSystem.setShaderTexture(0, SUN);
            RenderSystem.setShaderTexture(0, MOON_PHASES);
            ci.cancel();
        }
    }

    @Inject(method = "renderStars", at = @At("HEAD"), cancellable = true)
    private void renderStars(CallbackInfo ci) {
        if (world != null && world.getRegistryKey() == ModDimensions.TIMEDIM_LEVEL_KEY) {
            ci.cancel();
        }
    }
}