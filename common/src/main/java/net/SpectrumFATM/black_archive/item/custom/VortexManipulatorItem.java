package net.SpectrumFATM.black_archive.item.custom;

import net.SpectrumFATM.black_archive.network.messages.C2SFetchDimensions;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.util.Platform;

public class VortexManipulatorItem extends Item {

    public VortexManipulatorItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);

        if (Platform.isClient()) {
                C2SFetchDimensions message = new C2SFetchDimensions();
                message.send();
            } else {
                user.displayClientMessage(Component.literal("Error: Unable to initiate Vortex Manipulator.").withStyle(ChatFormatting.RED), true);
            }

        return InteractionResultHolder.success(stack);
    }
}