package net.SpectrumFATM.black_archive.blockentity;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ModBlockEntities {
    public static final DeferredRegistry<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegistry.create(BlackArchive.MOD_ID, Registries.BLOCK_ENTITY_TYPE);
}