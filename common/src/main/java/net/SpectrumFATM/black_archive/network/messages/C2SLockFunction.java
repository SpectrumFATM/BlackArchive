package net.SpectrumFATM.black_archive.network.messages;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.SpectrumFATM.black_archive.util.TARDISBindUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.common.items.ScrewdriverMode;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.registry.TRSoundRegistry;

public class C2SLockFunction extends MessageC2S {

    public C2SLockFunction() {
    }

    public C2SLockFunction(FriendlyByteBuf buf) {
    }

    @Override
    public MessageType getType() {
        return BlackArchiveNetworkHandler.SONIC_LOCK;
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    @Override
    public void handle(MessageContext messageContext) {
        ServerPlayer player = messageContext.getPlayer();
        ItemStack stack = player.getMainHandItem();

        if (TARDISBindUtil.hasTardisLevelName(stack)) {
            String levelName = TARDISBindUtil.getTardisLevelName(stack);
            ResourceLocation dimensionId = new ResourceLocation(levelName);
            ResourceKey<Level> tardisDimension = ResourceKey.create(Registries.DIMENSION, dimensionId);
            ServerLevel tardisWorld = player.getServer().getLevel(tardisDimension);
            TardisLevelOperator operator = TardisLevelOperator.get(tardisWorld).get();

            if (operator.getInternalDoor().locked()) {
                operator.setDoorLocked(false);
            } else {
                operator.setDoorClosed(true);
                operator.setDoorLocked(true);
            }

            ScrewdriverItem sonic = (ScrewdriverItem) stack.getItem();
            sonic.playScrewdriverSound(player.serverLevel(), player.getOnPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());
        }
    }
}