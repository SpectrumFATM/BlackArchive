package net.SpectrumFATM.black_archive.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.block.custom.DalekGravityGenBlock;
import net.SpectrumFATM.black_archive.block.custom.GravityGenBlock;
import net.SpectrumFATM.black_archive.block.custom.OxygenGenBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BlackArchive.MOD_ID, RegistryKeys.BLOCK);

    public static final RegistrySupplier<Block> DALEK_GRAVITY_GEN = BLOCKS.register("dalek_gravity_generator", () -> new DalekGravityGenBlock(AbstractBlock.Settings.create()));
    public static final RegistrySupplier<Block> GRAVITY_GEN = BLOCKS.register("gravity_generator", () -> new GravityGenBlock(AbstractBlock.Settings.create()));
    public static final RegistrySupplier<Block> OXYGEN_GEN = BLOCKS.register("oxygen_field", () -> new OxygenGenBlock(AbstractBlock.Settings.create()));
    public static final RegistrySupplier<Block> STEEL_BLOCK = BLOCKS.register("steel_block", () -> new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.COPPER).hardness(5.0F).resistance(6.0F)));
    public static final RegistrySupplier<Block> CUT_STEEL = BLOCKS.register("cut_steel", () -> new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.COPPER).hardness(5.0F).resistance(6.0F)));
    public static final RegistrySupplier<Block> ETCHED_STEEL = BLOCKS.register("etched_steel", () -> new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.COPPER).hardness(5.0F).resistance(6.0F)));

    public static final RegistrySupplier<StairsBlock> STEEL_STAIRS = BLOCKS.register("steel_stairs", () -> new StairsBlock(STEEL_BLOCK.get().getDefaultState(), AbstractBlock.Settings.create().sounds(BlockSoundGroup.COPPER).hardness(5.0F).resistance(6.0F)));

    public static final RegistrySupplier<SlabBlock> STEEL_SLAB = BLOCKS.register("steel_slab", () -> new SlabBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.COPPER).hardness(5.0F).resistance(6.0F)));
}