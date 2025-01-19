package net.SpectrumFATM.black_archive.network.messages;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.network.BlackArchiveNetworkHandler;
import net.SpectrumFATM.black_archive.util.TARDISBindUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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

public class C2SSetLocation extends MessageC2S {

    BlockPos pos;

    public C2SSetLocation(BlockPos pos) {
        this.pos = pos;
    }

    public C2SSetLocation(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    @Override
    public MessageType getType() {
        return BlackArchiveNetworkHandler.SONIC_LOCATION;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public void handle(MessageContext messageContext) {
        ServerPlayer player = messageContext.getPlayer();
        ItemStack stack = player.getMainHandItem();

        if (TARDISBindUtil.hasTardisLevelName(stack)) {
            ScrewdriverItem item = (ScrewdriverItem) stack.getItem();
            String levelName = TARDISBindUtil.getTardisLevelName(stack);
            ResourceLocation dimensionId = new ResourceLocation(levelName);
            ResourceKey<Level> tardisDimension = ResourceKey.create(Registries.DIMENSION, dimensionId);
            ServerLevel tardisWorld = player.getServer().getLevel(tardisDimension);
            TardisLevelOperator operator = TardisLevelOperator.get(tardisWorld).get();
            TardisPilotingManager pilotingManager = operator.getPilotingManager();


            pilotingManager.setTargetLocation(new TardisNavLocation(pos, player.getDirection().getOpposite(), player.serverLevel()));
            player.displayClientMessage(Component.translatable("item.sonic.locator.set"), true);
            item.playScrewdriverSound(player.serverLevel(), pos, TRSoundRegistry.SCREWDRIVER_SHORT.get());
        }
    }
}