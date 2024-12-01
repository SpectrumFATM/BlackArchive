package net.SpectrumFATM.forge.entity;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.SpectrumFATM.black_archive.entity.custom.*;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BlackArchive.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityAttributes {

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntities.DALEK.get(), DalekEntity.createDalekAttributes().build());
        event.put(ModEntities.DALEK_PUPPET.get(), DalekPuppetEntity.createDalekSlaveAttributes().build());
        event.put(ModEntities.CYBERMAN.get(), CybermanEntity.createCyberAttributes().build());
        event.put(ModEntities.CYBERMAT.get(), CybermatEntity.createCyberAttributes().build());
        event.put(ModEntities.TIME_FISSURE.get(), TimeFissureEntity.createTimeFissureAttributes().build());
    }
}
