package net.SpectrumFATM.black_archive.item.custom;

import net.SpectrumFATM.black_archive.screen.VortexScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import whocraft.tardis_refined.common.util.DimensionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VortexManipulatorItem extends Item {

    private final List<String> allowedDimensions;

    public VortexManipulatorItem(Settings settings) {
        super(settings);
        this.allowedDimensions = new ArrayList<>();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world.isClient) {
            String registryKey = world.getRegistryKey().getValue().toString();

            if (!registryKey.startsWith("tardis_refined:") && !registryKey.equals("black_archive:time_vortex")) {
                ensureAllowedDimensions();
                openVortexScreen(allowedDimensions);
            } else {
                user.sendMessage(Text.literal("Error: Unable to initiate Vortex Manipulator.").formatted(Formatting.RED), true);
            }
        }

        return TypedActionResult.success(stack, world.isClient);
    }

    /**
     * Ensure allowed dimensions are populated (avoiding potential null server issues).
     */
    private void ensureAllowedDimensions() {
        if (allowedDimensions.isEmpty()) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null && client.getServer() != null) {
                allowedDimensions.addAll(client.getServer().getWorldRegistryKeys().stream()
                        .filter(level -> DimensionUtil.isAllowedDimension(level) &&
                                !level.getValue().equals(new Identifier("black_archive:time_vortex")))
                        .map(level -> level.getValue().toString())
                        .collect(Collectors.toList()));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    private void openVortexScreen(List<String> dimensions) {
        MinecraftClient.getInstance().setScreen(new VortexScreen(dimensions));
    }
}