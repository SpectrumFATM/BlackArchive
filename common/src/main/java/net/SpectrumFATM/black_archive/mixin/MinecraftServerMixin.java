package net.SpectrumFATM.black_archive.mixin;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import net.SpectrumFATM.black_archive.util.IMinecraftServer;

import java.util.Map;
import java.util.concurrent.Executor;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin
        extends ReentrantBlockableEventLoop<TickTask> implements IMinecraftServer {

    @Mutable
    @Shadow
    @Final
    private Map<ResourceKey<Level>, ServerLevel> levels;

    @Shadow
    private ChunkProgressListenerFactory progressListenerFactory;

    @Shadow
    private Executor executor;

    @Shadow
    protected LevelStorageSource.LevelStorageAccess storageSource;

    public MinecraftServerMixin(String string) {
        super(string);
    }

    @Override
    public ChunkProgressListenerFactory getProgressListenerFactory() {
        return progressListenerFactory;
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }

    @Override
    public LevelStorageSource.LevelStorageAccess getStorageSource() {
        return storageSource;
    }

    public void addDimensionToWorldMap(ResourceKey<Level> dim, ServerLevel world) {
        this.levels.put(dim, world);
    }
}