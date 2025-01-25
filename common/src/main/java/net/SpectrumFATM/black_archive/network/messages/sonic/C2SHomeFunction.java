package net.SpectrumFATM.black_archive.network.messages.sonic;

import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.SpectrumFATM.black_archive.util.TARDISBindUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.registry.TRSoundRegistry;

import java.util.Timer;
import java.util.TimerTask;

public class C2SHomeFunction extends MessageC2S {

    public C2SHomeFunction() {
    }

    public C2SHomeFunction(FriendlyByteBuf buf) {
    }

    @Override
    public MessageType getType() {
        return BlackArchiveNetworkHandler.SONIC_HOMING;
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    @Override
    public void handle(MessageContext messageContext) {
        ServerPlayer player = messageContext.getPlayer();
        ItemStack stack = player.getMainHandItem();

        player.displayClientMessage(Component.translatable("item.sonic.homing.begin"), true);
        playSound(stack, player, TRSoundRegistry.SCREWDRIVER_SHORT.get());

        //Wait 5 seconds before executing the code
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                locateTARDIS(stack, player);
            }
        }, 5000);
    }

    private static void locateTARDIS(ItemStack stack, ServerPlayer player) {
        if (TARDISBindUtil.hasTardisLevelName(stack)) {
            String levelName = TARDISBindUtil.getTardisLevelName(stack);
            ResourceLocation dimensionId = new ResourceLocation(levelName);
            ResourceKey<Level> tardisDimension = ResourceKey.create(Registries.DIMENSION, dimensionId);
            ServerLevel tardisWorld = player.getServer().getLevel(tardisDimension);
            TardisLevelOperator operator = TardisLevelOperator.get(tardisWorld).get();
            TardisPilotingManager pilotingManager = operator.getPilotingManager();

            TardisNavLocation location = pilotingManager.getCurrentLocation();
            String position = location.getPosition().getX() + ", " + location.getPosition().getY() + ", " + location.getPosition().getZ() + ")";

            if (player.serverLevel() == location.getLevel()) {
                player.displayClientMessage(Component.translatable("item.sonic.homing.found").append(" (").append(position), true);
            } else {
                player.displayClientMessage(Component.translatable("item.sonic.homing.error"), true);
            }

            playSound(stack, player, TRSoundRegistry.SCREWDRIVER_DISCARD.get());
        }
    }

    private static void playSound(ItemStack stack, ServerPlayer player, SoundEvent event) {
        ScrewdriverItem sonic = (ScrewdriverItem) stack.getItem();
        sonic.playScrewdriverSound(player.serverLevel(), player.getOnPos(), event);
    }
}