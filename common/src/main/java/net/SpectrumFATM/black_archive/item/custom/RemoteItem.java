package net.SpectrumFATM.black_archive.item.custom;

import net.SpectrumFATM.black_archive.network.messages.C2SRemoteMessage;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.items.KeyItem;

import java.util.List;

public class RemoteItem  extends KeyItem {
    public RemoteItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        C2SRemoteMessage packet = new C2SRemoteMessage(context.getBlockPos()); // Example data: 42
        packet.send();
        return super.useOnBlock(context);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("item.remote.tooltip").formatted(Formatting.GOLD));
    }
}
