package net.SpectrumFATM.black_archive.fabric.entity;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.entity.client.*;
import net.SpectrumFATM.black_archive.fabric.entity.custom.*;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
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

    public static final EntityType<LaserEntity> LASER = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(BlackArchive.MOD_ID, "laser"),
            EntityType.Builder.<LaserEntity>create(LaserEntity::new, SpawnGroup.MISC)
                    .setDimensions(0.5f, 0.5f)
                    .build("laser")
    );

    public static final EntityType<DalekPuppetEntity> DALEK_PUPPET = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(BlackArchive.MOD_ID, "dalek_puppet"),
            EntityType.Builder.create(DalekPuppetEntity::new, SpawnGroup.CREATURE)
                    .setDimensions(1f, 1.5f)
                    .build("dalek_puppet")
    );

    public static final EntityType<CybermanEntity> CYBERMAN = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(BlackArchive.MOD_ID, "cyberman"),
            EntityType.Builder.create(CybermanEntity::new, SpawnGroup.CREATURE)
                    .setDimensions(1f, 1.5f)
                    .build("cyberman")
    );

    public static final EntityType<CybermatEntity> CYBERMAT = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(BlackArchive.MOD_ID, "cybermat"),
            EntityType.Builder.create(CybermatEntity::new, SpawnGroup.CREATURE)
                    .setDimensions(0.5f, 0.5f)
                    .build("cybermat")
    );

    public static void registerRenderers() {
        EntityRendererRegistry.register(ModEntities.DALEK, DalekRenderer::new);
        EntityRendererRegistry.register(ModEntities.LASER, LaserRenderer::new);
        EntityRendererRegistry.register(ModEntities.DALEK_PUPPET, DalekPuppetRenderer::new);
        EntityRendererRegistry.register(ModEntities.CYBERMAN, CybermanRenderer::new);
        EntityRendererRegistry.register(ModEntities.CYBERMAT, CybermatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.DALEK_SLAVE, DalekPuppetModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.DALEK, DalekModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CYBERMAN, CybermanModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CYBERMAT, CybermatModel::getTexturedModelData);
    }

    public static void registerModEntities() {
        BlackArchive.LOGGER.info("Registering Entities for " + BlackArchive.MOD_ID);
    }
}