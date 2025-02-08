package net.SpectrumFATM.black_archive.mixin;

import net.SpectrumFATM.black_archive.util.Platform;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinsPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {

        if (mixinClassName.equals("net.SpectrumFATM.black_archive.mixin.DWDSonicItemMixin")) {
            return Platform.isModLoaded("whocosmetics");
        }

        if (mixinClassName.equals("net.SpectrumFATM.black_archive.mixin.DWDCompatMixin")) {
            return Platform.isModLoaded("whocosmetics");
        }

        if (mixinClassName.equals("net.SpectrumFATM.black_archive.mixin.AAToyotaMixin")) {
            return Platform.isModLoaded("audreys_additions");
        }

        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return null;  // Allow mixins to be discovered from the config
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}