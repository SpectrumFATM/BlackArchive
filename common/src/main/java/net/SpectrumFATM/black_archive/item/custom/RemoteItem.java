package net.SpectrumFATM.black_archive.item.custom;

import net.SpectrumFATM.black_archive.network.messages.C2SRemoteMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.items.KeyItem;

import java.util.List;

public class RemoteItem  extends KeyItem {
    public RemoteItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        C2SRemoteMessage packet = new C2SRemoteMessage(context.getClickedPos()); // Example data: 42
        packet.send();
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);
        tooltip.add(Component.translatable("item.remote.tooltip").withStyle(ChatFormatting.GOLD));
    }
}
