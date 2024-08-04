package net.SpectrumFATM.black_archive.fabric.item.custom;

import net.SpectrumFATM.black_archive.fabric.screen.VortexScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class VortexManipulatorItem extends Item {
    public VortexManipulatorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (user.getWorld().isClient) {
            openVortexScreen();
        }
        return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
    }

    private void openVortexScreen() {
        MinecraftClient.getInstance().setScreen(new VortexScreen());
    }
}
