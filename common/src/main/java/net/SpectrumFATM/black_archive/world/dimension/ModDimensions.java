package net.SpectrumFATM.black_archive.world.dimension;

import net.SpectrumFATM.BlackArchive;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import java.util.OptionalLong;

public class ModDimensions {
    public static final ResourceKey<LevelStem> SPACEDIM_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(BlackArchive.MOD_ID, "space"));
    public static final ResourceKey<Level> SPACEDIM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(BlackArchive.MOD_ID, "space"));

    public static final ResourceKey<DimensionType> SPACE_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(BlackArchive.MOD_ID, "space_type"));

    public static final ResourceKey<LevelStem> TIMEDIM_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(BlackArchive.MOD_ID, "time_vortex"));
    public static final ResourceKey<Level> TIMEDIM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(BlackArchive.MOD_ID, "time_vortex"));


}