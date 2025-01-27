package net.SpectrumFATM.black_archive.entity;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.entity.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ModEntities {
    // Deferred Register for entity types
    public static final DeferredRegistry<EntityType<?>> ENTITY_TYPES = DeferredRegistry.create(BlackArchive.MOD_ID, Registries.ENTITY_TYPE);

    // Entity registrations
    public static final RegistrySupplier<EntityType<DalekEntity>> DALEK = ENTITY_TYPES.register("dalek",
            () -> EntityType.Builder.of(DalekEntity::new, MobCategory.CREATURE)
                    .sized(1f, 1.5f)
                    .build(new ResourceLocation(BlackArchive.MOD_ID, "dalek").toString())
    );

    public static final RegistrySupplier<EntityType<LaserEntity>> LASER = ENTITY_TYPES.register("laser",
            () -> EntityType.Builder.<LaserEntity>of(LaserEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .build(new ResourceLocation(BlackArchive.MOD_ID, "laser").toString())
    );

    public static final RegistrySupplier<EntityType<DalekPuppetEntity>> DALEK_PUPPET = ENTITY_TYPES.register("dalek_puppet",
            () -> EntityType.Builder.of(DalekPuppetEntity::new, MobCategory.CREATURE)
                    .sized(1f, 1.5f)
                    .build(new ResourceLocation(BlackArchive.MOD_ID, "dalek_puppet").toString())
    );

    public static final RegistrySupplier<EntityType<CybermanEntity>> CYBERMAN = ENTITY_TYPES.register("cyberman",
            () -> EntityType.Builder.of(CybermanEntity::new, MobCategory.CREATURE)
                    .sized(1f, 1.5f)
                    .build(new ResourceLocation(BlackArchive.MOD_ID, "cyberman").toString())
    );

    public static final RegistrySupplier<EntityType<CybermatEntity>> CYBERMAT = ENTITY_TYPES.register("cybermat",
            () -> EntityType.Builder.of(CybermatEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f)
                    .build(new ResourceLocation(BlackArchive.MOD_ID, "cybermat").toString())
    );

    public static final RegistrySupplier<EntityType<WeepingAngelEntity>> ANGEL = ENTITY_TYPES.register("weeping_angel",
            () -> EntityType.Builder.of(WeepingAngelEntity::new, MobCategory.CREATURE)
                    .sized(1f, 2f)
                    .build(new ResourceLocation(BlackArchive.MOD_ID, "weeping_angel").toString())
    );

    public static final RegistrySupplier<EntityType<SilurianEntity>> SILURIAN = ENTITY_TYPES.register("silurian",
            () -> EntityType.Builder.of(SilurianEntity::new, MobCategory.CREATURE)
                    .sized(1f, 2f)
                    .build(new ResourceLocation(BlackArchive.MOD_ID, "silurian").toString())
    );

    public static final RegistrySupplier<EntityType<TimeFissureEntity>> TIME_FISSURE = ENTITY_TYPES.register("time_fissure",
            () -> EntityType.Builder.of(TimeFissureEntity::new, MobCategory.CREATURE)
                    .sized(1f, 2f)
                    .build(new ResourceLocation(BlackArchive.MOD_ID, "time_fissure").toString())
    );
}