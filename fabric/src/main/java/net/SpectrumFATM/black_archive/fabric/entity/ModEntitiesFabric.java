package net.SpectrumFATM.black_archive.fabric.entity;

import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.entity.custom.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class ModEntitiesFabric {

    public static void createEntityAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.DALEK.get(), DalekEntity.createDalekAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DALEK_PUPPET.get(), DalekPuppetEntity.createDalekSlaveAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CYBERMAN.get(), CybermanEntity.createCyberAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CYBERMAT.get(), CybermatEntity.createCyberAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.ANGEL.get(), WeepingAngelEntity.createAngelAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SILURIAN.get(), SilurianEntity.createSilurianAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.TIME_FISSURE.get(), TimeFissureEntity.createTimeFissureAttributes());
    }
}
