package net.SpectrumFATM.black_archive.item.custom;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.network.messages.C2SRemoteMessage;
import net.SpectrumFATM.black_archive.util.TARDISBindUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RemoteItem extends TooltipItem {
    public RemoteItem(Properties settings, String tooltip) {
        super(settings, tooltip);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        String levelName = level.dimension().location().toString();
        ItemStack itemStack = player.getItemInHand(interactionHand);

        if (levelName.startsWith("tardis_refined:") && !TARDISBindUtil.hasTardisLevelName(itemStack)) {
            TARDISBindUtil.setTardisLevelName(itemStack, levelName);
        }

        return super.use(level, player, interactionHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide) {
            C2SRemoteMessage packet = new C2SRemoteMessage(context.getClickedPos(), TARDISBindUtil.getTardisLevelName(context.getItemInHand())); // Example data: 42
            packet.send();
        }

        BlackArchive.LOGGER.info("RemoteItem used on " + context.getClickedPos().toString());
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);

        if (TARDISBindUtil.hasTardisLevelName(stack)) {
            String name = TARDISBindUtil.getTardisLevelName(stack).replaceFirst("tardis_refined:", "");
            tooltip.add(Component.literal(name.substring(0, 5)).withStyle(ChatFormatting.GRAY));
        }
    }
}
