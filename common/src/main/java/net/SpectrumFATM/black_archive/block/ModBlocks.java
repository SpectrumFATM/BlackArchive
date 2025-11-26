package net.SpectrumFATM.black_archive.block;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.ad_astra_compat.blocks.DalekLifeSupport;
import net.SpectrumFATM.black_archive.ad_astra_compat.blocks.GravityField;
import net.SpectrumFATM.black_archive.ad_astra_compat.blocks.OxygenField;
import net.SpectrumFATM.black_archive.block.custom.DalekGravityGenBlock;
import net.SpectrumFATM.black_archive.block.custom.GravityGenBlock;
import net.SpectrumFATM.black_archive.block.custom.OxygenGenBlock;
import net.SpectrumFATM.black_archive.util.Platform;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegistry<Block> BLOCKS = DeferredRegistry.create(BlackArchive.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> DALEK_GRAVITY_GEN = registerBlock("dalek_gravity_generator", () -> new DalekGravityGenBlock(BlockBehaviour.Properties.of()), !Platform.isModLoaded("ad_astra"));
    public static final RegistrySupplier<Block> GRAVITY_GEN = registerBlock("gravity_generator", () -> new GravityGenBlock(BlockBehaviour.Properties.of()), !Platform.isModLoaded("ad_astra"));
    public static final RegistrySupplier<Block> OXYGEN_GEN = registerBlock("oxygen_field", () -> new OxygenGenBlock(BlockBehaviour.Properties.of()), !Platform.isModLoaded("ad_astra"));

    public static final RegistrySupplier<Block> DALEK_GRAVITY_GEN_AA = registerBlock("dalek_gravity_generator", () -> new DalekLifeSupport(BlockBehaviour.Properties.of(), 33, 18), Platform.isModLoaded("ad_astra"));
    public static final RegistrySupplier<Block> OXYGEN_GEN_AA = registerBlock("oxygen_field", () -> new OxygenField(BlockBehaviour.Properties.of()), Platform.isModLoaded("ad_astra"));
    public static final RegistrySupplier<Block> GRAVITY_GEN_AA = registerBlock("gravity_generator", () -> new GravityField(BlockBehaviour.Properties.of()), Platform.isModLoaded("ad_astra"));

    public static final RegistrySupplier<Block> STEEL_BLOCK = registerBlock("steel_block", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.COPPER).destroyTime(5.0F).explosionResistance(6.0F)), true);
    public static final RegistrySupplier<Block> CUT_STEEL = registerBlock("cut_steel", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.COPPER).destroyTime(5.0F).explosionResistance(6.0F)), true);
    public static final RegistrySupplier<Block> ETCHED_STEEL = registerBlock("etched_steel", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.COPPER).destroyTime(5.0F).explosionResistance(6.0F)), true);

    public static final RegistrySupplier<StairBlock> STEEL_STAIRS = BLOCKS.register("steel_stairs", () -> new StairBlock(STEEL_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of().sound(SoundType.COPPER).destroyTime(5.0F).explosionResistance(6.0F)));

    public static final RegistrySupplier<SlabBlock> STEEL_SLAB = BLOCKS.register("steel_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().sound(SoundType.COPPER).destroyTime(5.0F).explosionResistance(6.0F)));
    
    private static RegistrySupplier<Block> registerBlock(String name, Supplier<Block> block, boolean shouldRegister) {
        if (!shouldRegister) return null; // Prevent registration if not allowed
        RegistrySupplier<Block> blockSupplier = BLOCKS.register(name, block);
        return blockSupplier;
    }
}