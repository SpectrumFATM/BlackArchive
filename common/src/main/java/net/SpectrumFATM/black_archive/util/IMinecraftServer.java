package net.SpectrumFATM.black_archive.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelStorageSource;

import java.util.concurrent.Executor;

public interface IMinecraftServer {
    ChunkProgressListenerFactory getProgressListenerFactory();
    Executor getExecutor();
    LevelStorageSource.LevelStorageAccess getStorageSource();
    void addDimensionToWorldMap(ResourceKey<Level> dim, ServerLevel world);

}