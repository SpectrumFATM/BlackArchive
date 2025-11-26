package net.SpectrumFATM.black_archive.blockentity;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.ad_astra_compat.blockentities.DalekLifeSupportBlockEntity;
import net.SpectrumFATM.black_archive.ad_astra_compat.blockentities.GravityFieldBlockEntity;
import net.SpectrumFATM.black_archive.ad_astra_compat.blockentities.OxygenFieldBlockEntity;
import net.SpectrumFATM.black_archive.block.ModBlocks;
import net.SpectrumFATM.black_archive.util.Platform;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ModBlockEntities {
    public static final DeferredRegistry<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegistry.create(BlackArchive.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<DalekLifeSupportBlockEntity>> DALEK_LIFE_SUPPORT_BE = Platform.isModLoaded("ad_astra") ? BLOCK_ENTITIES.register("dalek_life_support",
            () -> BlockEntityType.Builder.of(DalekLifeSupportBlockEntity::new, ModBlocks.DALEK_GRAVITY_GEN_AA.get()).build(null)) : null;

    public static final RegistrySupplier<BlockEntityType<OxygenFieldBlockEntity>> OXYGEN_SUPPORT_BE = Platform.isModLoaded("ad_astra") ? BLOCK_ENTITIES.register("oxygen_field",
            () -> BlockEntityType.Builder.of(OxygenFieldBlockEntity::new, ModBlocks.OXYGEN_GEN_AA.get()).build(null)) : null;

    public static final RegistrySupplier<BlockEntityType<GravityFieldBlockEntity>> GRAVITY_SUPPORT_BE = Platform.isModLoaded("ad_astra") ? BLOCK_ENTITIES.register("gravity_field",
            () -> BlockEntityType.Builder.of(GravityFieldBlockEntity::new, ModBlocks.GRAVITY_GEN_AA.get()).build(null)) : null;

}
