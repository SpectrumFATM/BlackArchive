package net.SpectrumFATM.black_archive.fabric.entity;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.entity.custom.DalekEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<DalekEntity> DALEK = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(BlackArchive.MOD_ID, "dalek"),
            EntityType.Builder.create(DalekEntity::new, SpawnGroup.CREATURE)
                    .setDimensions(1f, 1.5f)
                    .build("dalek")
    );

    public static void registerModEntities() {
       BlackArchive.LOGGER.info("Registering Entities for " + BlackArchive.MOD_ID);
    }

}