package net.SpectrumFATM.black_archive.fabric.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.world.dimension.ModDimensions;
import net.minecraft.client.render.Camera;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;

@Mixin(WorldRenderer.class)
public abstract class RenderMixin {

    @Shadow
    private ClientWorld world;

    @Shadow
    private static final Identifier SUN = new Identifier(BlackArchive.MOD_ID ,"textures/environment/blank.png");

    @Shadow
    private static final Identifier MOON_PHASES = new Identifier(BlackArchive.MOD_ID ,"textures/environment/blank.png");

    @Inject(method = "renderClouds", at = @At("HEAD"), cancellable = true)
    public void renderClouds(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, double d, double e, double f, CallbackInfo ci) {
        if (world.getRegistryKey() == ModDimensions.SPACEDIM_LEVEL_KEY) {
            // Skip rendering clouds in space dimension
            ci.cancel();
        }
    }
}