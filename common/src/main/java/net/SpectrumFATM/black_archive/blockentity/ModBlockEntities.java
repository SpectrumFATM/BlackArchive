package net.SpectrumFATM.black_archive.blockentity;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.block.ModBlocks;
import net.SpectrumFATM.black_archive.blockentity.entities.ShipDoorEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ModBlockEntities {
    public static final DeferredRegistry<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegistry.create(BlackArchive.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<ShipDoorEntity>> SHIP_DOOR_ENTITY = BLOCK_ENTITIES.register("ship_door",
            () -> BlockEntityType.Builder.of(ShipDoorEntity::new, ModBlocks.SHIP_DOOR.get()).build(null));
}