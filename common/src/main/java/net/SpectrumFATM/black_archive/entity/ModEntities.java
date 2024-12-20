package net.SpectrumFATM.black_archive.entity;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.entity.custom.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ModEntities {
    // Deferred Register for entity types
    public static final DeferredRegistry<EntityType<?>> ENTITY_TYPES = DeferredRegistry.create(BlackArchive.MOD_ID, RegistryKeys.ENTITY_TYPE);

    // Entity registrations
    public static final RegistrySupplier<EntityType<DalekEntity>> DALEK = ENTITY_TYPES.register("dalek",
            () -> EntityType.Builder.create(DalekEntity::new, SpawnGroup.CREATURE)
                    .setDimensions(1f, 1.5f)
                    .build(new Identifier(BlackArchive.MOD_ID, "dalek").toString())
    );

    public static final RegistrySupplier<EntityType<LaserEntity>> LASER = ENTITY_TYPES.register("laser",
            () -> EntityType.Builder.<LaserEntity>create(LaserEntity::new, SpawnGroup.MISC)
                    .setDimensions(0.5f, 0.5f)
                    .build(new Identifier(BlackArchive.MOD_ID, "laser").toString())
    );

    public static final RegistrySupplier<EntityType<DalekPuppetEntity>> DALEK_PUPPET = ENTITY_TYPES.register("dalek_puppet",
            () -> EntityType.Builder.create(DalekPuppetEntity::new, SpawnGroup.CREATURE)
                    .setDimensions(1f, 1.5f)
                    .build(new Identifier(BlackArchive.MOD_ID, "dalek_puppet").toString())
    );

    public static final RegistrySupplier<EntityType<CybermanEntity>> CYBERMAN = ENTITY_TYPES.register("cyberman",
            () -> EntityType.Builder.create(CybermanEntity::new, SpawnGroup.CREATURE)
                    .setDimensions(1f, 1.5f)
                    .build(new Identifier(BlackArchive.MOD_ID, "cyberman").toString())
    );

    public static final RegistrySupplier<EntityType<CybermatEntity>> CYBERMAT = ENTITY_TYPES.register("cybermat",
            () -> EntityType.Builder.create(CybermatEntity::new, SpawnGroup.CREATURE)
                    .setDimensions(0.5f, 0.5f)
                    .build(new Identifier(BlackArchive.MOD_ID, "cybermat").toString())
    );

    public static final RegistrySupplier<EntityType<WeepingAngelEntity>> ANGEL = ENTITY_TYPES.register("weeping_angel",
            () -> EntityType.Builder.create(WeepingAngelEntity::new, SpawnGroup.CREATURE)
                    .setDimensions(1f, 2f)
                    .build(new Identifier(BlackArchive.MOD_ID, "weeping_angel").toString())
    );

    public static final RegistrySupplier<EntityType<TimeFissureEntity>> TIME_FISSURE = ENTITY_TYPES.register("time_fissure",
            () -> EntityType.Builder.create(TimeFissureEntity::new, SpawnGroup.CREATURE)
                    .setDimensions(1f, 2f)
                    .build(new Identifier(BlackArchive.MOD_ID, "time_fissure").toString())
    );
}