package net.SpectrumFATM.black_archive.fabric.item.custom;

import net.SpectrumFATM.black_archive.fabric.BlackArchive;
import net.SpectrumFATM.black_archive.fabric.screen.VortexScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class VortexManipulatorItem extends Item {
    public VortexManipulatorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (user.getWorld().isClient) {
            String registryKey = user.getWorld().getRegistryKey().getValue().toString();
            if (!registryKey.startsWith("tardis_refined:") && !registryKey.equals("black_archive:time_vortex")) {
                openVortexScreen();
            } else {
                user.sendMessage(Text.literal("Error: Unable to initiate Vortex Manipulator.").formatted(Formatting.RED), true);
            }
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    @Environment(EnvType.CLIENT)
    private void openVortexScreen() {
        MinecraftClient.getInstance().setScreen(new VortexScreen());
    }
}