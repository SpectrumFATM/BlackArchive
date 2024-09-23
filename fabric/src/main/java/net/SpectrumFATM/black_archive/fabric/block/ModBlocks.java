package net.SpectrumFATM.black_archive.fabric.block;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.block.custom.DalekGravityGenBlock;
import net.SpectrumFATM.black_archive.fabric.block.custom.GravityGenBlock;
import net.SpectrumFATM.black_archive.fabric.block.custom.OxygenGenBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block DALEK_GRAVITY_GEN = registerBlock("dalek_gravity_generator", new DalekGravityGenBlock(AbstractBlock.Settings.create()));
    public static final Block GRAVITY_GEN = registerBlock("gravity_generator", new GravityGenBlock(AbstractBlock.Settings.create()));
    public static final Block OXYGEN_GEN = registerBlock("oxygen_field", new OxygenGenBlock(AbstractBlock.Settings.create()));
    public static final Block STEEL_BLOCK = registerBlock("steel_block", new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.COPPER).hardness(5.0F).resistance(6.0F)));
    public static final Block CUT_STEEL = registerBlock("cut_steel", new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.COPPER).hardness(5.0F).resistance(6.0F)));
    public static final Block ETCHED_STEEL = registerBlock("etched_steel", new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.COPPER).hardness(5.0F).resistance(6.0F)));

    public static final StairsBlock STEEL_STAIRS = (StairsBlock) registerBlock("steel_stairs", new StairsBlock(STEEL_BLOCK.getDefaultState(), AbstractBlock.Settings.create().sounds(BlockSoundGroup.COPPER).hardness(5.0F).resistance(6.0F)));

    public static final SlabBlock STEEL_SLAB = (SlabBlock) registerBlock("steel_slab", new SlabBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.COPPER).hardness(5.0F).resistance(6.0F)));

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(BlackArchive.MOD_ID, name), block);
    }
}
