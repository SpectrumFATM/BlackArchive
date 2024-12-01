package net.SpectrumFATM.black_archive.entity;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.entity.custom.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {
    // Deferred Register for entity types
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BlackArchive.MOD_ID, RegistryKeys.ENTITY_TYPE);

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

    public static final RegistrySupplier<EntityType<TimeFissureEntity>> TIME_FISSURE = ENTITY_TYPES.register("time_fissure",
            () -> EntityType.Builder.create(TimeFissureEntity::new, SpawnGroup.CREATURE)
                    .setDimensions(1f, 2f)
                    .build(new Identifier(BlackArchive.MOD_ID, "time_fissure").toString())
    );

    public static void createEntityAttributes() {
        EntityAttributeRegistry.register(DALEK, DalekEntity::createDalekAttributes);
        EntityAttributeRegistry.register(DALEK_PUPPET, DalekPuppetEntity::createDalekSlaveAttributes);
        EntityAttributeRegistry.register(CYBERMAN, CybermanEntity::createCyberAttributes);
        EntityAttributeRegistry.register(CYBERMAT, CybermatEntity::createCyberAttributes);
        EntityAttributeRegistry.register(TIME_FISSURE, TimeFissureEntity::createTimeFissureAttributes);
    }

}