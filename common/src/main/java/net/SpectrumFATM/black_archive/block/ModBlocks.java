package net.SpectrumFATM.black_archive.block;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.block.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ModBlocks {

    public static final DeferredRegistry<Block> BLOCKS = DeferredRegistry.create(BlackArchive.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> DALEK_GRAVITY_GEN = BLOCKS.register("dalek_gravity_generator", () -> new DalekGravityGenBlock(BlockBehaviour.Properties.of()));
    public static final RegistrySupplier<Block> GRAVITY_GEN = BLOCKS.register("gravity_generator", () -> new GravityGenBlock(BlockBehaviour.Properties.of()));
    public static final RegistrySupplier<Block> OXYGEN_GEN = BLOCKS.register("oxygen_field", () -> new OxygenGenBlock(BlockBehaviour.Properties.of()));
    public static final RegistrySupplier<Block> STEEL_BLOCK = BLOCKS.register("steel_block", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.COPPER).destroyTime(5.0F).explosionResistance(6.0F)));
    public static final RegistrySupplier<Block> CUT_STEEL = BLOCKS.register("cut_steel", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.COPPER).destroyTime(5.0F).explosionResistance(6.0F)));
    public static final RegistrySupplier<Block> ETCHED_STEEL = BLOCKS.register("etched_steel", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.COPPER).destroyTime(5.0F).explosionResistance(6.0F)));

    public static final RegistrySupplier<StairBlock> STEEL_STAIRS = BLOCKS.register("steel_stairs", () -> new StairBlock(STEEL_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of().sound(SoundType.COPPER).destroyTime(5.0F).explosionResistance(6.0F)));

    public static final RegistrySupplier<SlabBlock> STEEL_SLAB = BLOCKS.register("steel_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().sound(SoundType.COPPER).destroyTime(5.0F).explosionResistance(6.0F)));

    public static final RegistrySupplier<Block> SHIP_DOOR = BLOCKS.register("ship_door", ShipDoor::new);
    public static final RegistrySupplier<Block> CHAIR = BLOCKS.register("sontaran_chair", ChairBlock::new);
}