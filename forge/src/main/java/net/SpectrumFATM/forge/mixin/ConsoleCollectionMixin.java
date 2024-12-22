package net.SpectrumFATM.forge.mixin;

import net.SpectrumFATM.black_archive.blockentity.ModModels;
import net.SpectrumFATM.black_archive.blockentity.console.RaniConsole;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.client.model.blockentity.console.ConsoleModelCollection;
import whocraft.tardis_refined.client.model.blockentity.console.ConsoleUnit;

import java.util.List;

@Mixin(ConsoleModelCollection.class)
public class ConsoleCollectionMixin {

    @Shadow
    public static List<ConsoleUnit> CONSOLE_MODELS;
    ConsoleUnit rani;

    @Inject(method = "registerModels", at = @At("HEAD"), cancellable = true, remap = false)
    private void registerModels(EntityModelLoader loader, CallbackInfo callbackInfo) {
        this.rani = new RaniConsole(loader.getModelPart(ModModels.RANI_CONSOLE));
        CONSOLE_MODELS.add(this.rani);
    }
}
