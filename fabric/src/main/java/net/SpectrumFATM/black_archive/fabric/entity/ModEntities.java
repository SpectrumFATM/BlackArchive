package net.SpectrumFATM.black_archive.fabric.entity;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.entity.client.*;
import net.SpectrumFATM.black_archive.fabric.entity.custom.DalekEntity;
import net.SpectrumFATM.black_archive.fabric.entity.custom.DalekPuppetEntity;
import net.SpectrumFATM.black_archive.fabric.entity.custom.LaserEntity;
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

    public static void registerRenderers() {
        EntityRendererRegistry.register(ModEntities.DALEK, DalekRenderer::new);
        EntityRendererRegistry.register(ModEntities.LASER, LaserRenderer::new);
        EntityRendererRegistry.register(ModEntities.DALEK_PUPPET, DalekPuppetRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.DALEK_SLAVE, DalekPuppetModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.DALEK, DalekModel::getTexturedModelData);
    }

    public static void registerModEntities() {
        BlackArchive.LOGGER.info("Registering Entities for " + BlackArchive.MOD_ID);
    }
}