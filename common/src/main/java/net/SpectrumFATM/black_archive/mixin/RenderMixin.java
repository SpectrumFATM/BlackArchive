package net.SpectrumFATM.black_archive.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.world.dimension.ModDimensions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class RenderMixin {

    @Shadow
    private ClientLevel level;

    private static ResourceLocation SUN_LOCATION = new ResourceLocation(BlackArchive.MOD_ID,"textures/environment/blank.png");
    private static ResourceLocation MOON_LOCATION = new ResourceLocation(BlackArchive.MOD_ID,"textures/environment/blank.png");

    @Inject(method = "renderClouds(Lcom/mojang/blaze3d/vertex/PoseStack;Lorg/joml/Matrix4f;FDDD)V", at = @At("HEAD"), cancellable = true)
    public void renderClouds(PoseStack poseStack, Matrix4f projectionMatrix, float tickDelta, double cameraX, double cameraY, double cameraZ, CallbackInfo ci) {
        if (level != null && (level.dimension() == ModDimensions.SPACEDIM_LEVEL_KEY || level.dimension() == ModDimensions.TIMEDIM_LEVEL_KEY)) {
            RenderSystem.setShaderTexture(0, SUN_LOCATION);
            RenderSystem.setShaderTexture(0, MOON_LOCATION);
            ci.cancel();
        }
    }

}