package net.SpectrumFATM.black_archive.fabric.block;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.block.custom.DalekGravityGenBlock;
import net.SpectrumFATM.black_archive.fabric.block.custom.GravityGenBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block DALEK_GRAVITY_GEN = registerBlock("dalek_gravity_generator", new DalekGravityGenBlock(AbstractBlock.Settings.create()));
    public static final Block GRAVITY_GEN = registerBlock("gravity_generator", new GravityGenBlock(AbstractBlock.Settings.create()));

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(BlackArchive.MOD_ID, name), block);
    }
}
