package net.SpectrumFATM.black_archive.util;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Lifecycle;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.world.dimension.ModDimensions;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WorldData;
import org.slf4j.Logger;
import whocraft.tardis_refined.common.network.messages.sync.S2CSyncLevelList;

public class DimensionRegistry {

    private static final Logger LOGGER = BlackArchive.LOGGER;

    public static void createDimension(MinecraftServer server, ResourceKey<Level> dimensionKey, ServerPlayer player) {

        WorldData worldData = server.getWorldData();
        ServerLevelData serverLevelData = worldData.overworldData();

        DerivedLevelData derivedLevelData = new DerivedLevelData(worldData, serverLevelData);

        ServerLevel space = server.getLevel(ModDimensions.SPACEDIM_LEVEL_KEY);
        ChunkGenerator chunkGenerator = space.getChunkSource().getGenerator();

        LevelStem stem = new LevelStem(server.registryAccess().registryOrThrow(Registries.DIMENSION_TYPE).getHolderOrThrow(ModDimensions.SPACE_DIM_TYPE), chunkGenerator);

        long seed = worldData.worldGenOptions().seed();
        long obfuscatedSeed = BiomeManager.obfuscateSeed(seed);

        ServerLevel newWorld = new ServerLevel(
                server,
                ((IMinecraftServer) server).getExecutor(),
                ((IMinecraftServer) server).getStorageSource(),
                derivedLevelData,
                dimensionKey,
                stem,
                ((IMinecraftServer) server).getProgressListenerFactory().create(11),
                false, // isDebug
                obfuscatedSeed,
                ImmutableList.of(),
                false, // only true for overworld
                space.getRandomSequences()
        );

        MappedRegistry<LevelStem> levelStemRegistry = (MappedRegistry<LevelStem>) server.registryAccess().registryOrThrow(Registries.LEVEL_STEM);
        if (((IMappedRegistry) levelStemRegistry).isFrozen()) {
            ((IMappedRegistry) levelStemRegistry).setIsFrozen(false);
        }
        levelStemRegistry.register(
                ResourceKey.create(Registries.LEVEL_STEM, dimensionKey.location()),
                stem,
                Lifecycle.stable() // use built-in registration info for now
        );
        ((IMappedRegistry) levelStemRegistry).setIsFrozen(true);
        ((IMinecraftServer) server).addDimensionToWorldMap(dimensionKey, newWorld);

        (ServerWorldEvents.LOAD.invoker()).onWorldLoad(server, newWorld);
        (new S2CSyncLevelList(newWorld.dimension(), true)).sendToAll();
    }}