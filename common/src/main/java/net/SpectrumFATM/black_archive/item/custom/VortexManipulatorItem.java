package net.SpectrumFATM.black_archive.item.custom;

import net.SpectrumFATM.black_archive.screen.VortexScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.util.DimensionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VortexManipulatorItem extends Item {

    private final List<String> allowedDimensions;

    public VortexManipulatorItem(Properties settings) {
        super(settings);
        this.allowedDimensions = new ArrayList<>();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);

        if (world.isClientSide) {
            String registryKey = world.dimension().location().toString();

            if (!registryKey.startsWith("tardis_refined:") && !registryKey.equals("black_archive:time_vortex")) {
                ensureAllowedDimensions();
                openVortexScreen(allowedDimensions);
            } else {
                user.displayClientMessage(Component.literal("Error: Unable to initiate Vortex Manipulator.").withStyle(ChatFormatting.RED), true);
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide);
    }

    /**
     * Ensure allowed dimensions are populated (avoiding potential null server issues).
     */
    private void ensureAllowedDimensions() {
        if (allowedDimensions.isEmpty()) {
            Minecraft client = Minecraft.getInstance();
            if (client != null && client.getSingleplayerServer() != null) {
                allowedDimensions.addAll(client.getSingleplayerServer().levelKeys().stream()
                        .filter(level -> DimensionUtil.isAllowedDimension(level) &&
                                !level.location().equals(new ResourceLocation("black_archive:time_vortex")))
                        .map(level -> level.location().toString())
                        .collect(Collectors.toList()));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    private void openVortexScreen(List<String> dimensions) {
        Minecraft.getInstance().setScreen(new VortexScreen(dimensions));
    }
}