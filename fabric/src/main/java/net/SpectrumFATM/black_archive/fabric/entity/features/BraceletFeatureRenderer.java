package net.SpectrumFATM.black_archive.fabric.entity.features;

import net.SpectrumFATM.black_archive.fabric.item.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

import net.minecraft.util.Arm;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Arm;

import java.util.Objects;

public class BraceletFeatureRenderer extends FeatureRenderer<PlayerEntity, PlayerEntityModel<PlayerEntity>> {

    public BraceletFeatureRenderer(FeatureRendererContext<PlayerEntity, PlayerEntityModel<PlayerEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float age, float headYaw, float headPitch) {
        // Render bracelet only if the player is visible and is an instance of AbstractClientPlayerEntity
        if (!player.isInvisible() && player instanceof AbstractClientPlayerEntity clientPlayer) {
            matrices.push();

            // Check if the player is left-handed or right-handed
            Arm mainArm = player.getMainArm(); // LEFT or RIGHT

            // Get the correct arm model based on the main arm
            PlayerEntityModel<PlayerEntity> playerRenderer = this.getContextModel();
            ModelPart arm = (mainArm == Arm.RIGHT) ? playerRenderer.rightArm : playerRenderer.leftArm;

            // Apply the arm's transformation to the MatrixStack
            arm.rotate(matrices);

            // Check if the player uses a slim model (Alex style)
            boolean isSlim = MinecraftClient.getInstance().player.getModel().equals("slim");
            if (isSlim) {
                // Adjust for slim (Alex) model
                matrices.translate(-0.065, 0.525, -0.1); // Adjust for slim arms
                matrices.scale(0.75f, 1.0f, 1.0f); // Scale it slightly smaller on X-axis to fit slim arm
            } else {
                // Default model (Steve) adjustments
                matrices.translate(-0.065, 0.525, -0.1); // Default translation for regular arms
            }

            if (clientPlayer.getMainHandStack().getItem() != ModItems.DALEK_BRACELET && clientPlayer.getOffHandStack().getItem() != ModItems.DALEK_BRACELET) {
                renderBraceletAsHeldItem(player, matrices, vertexConsumers, light, mainArm);
            }

            matrices.pop();
        }
    }

    private void renderBraceletAsHeldItem(PlayerEntity player, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Arm mainArm) {
        // Get bracelet item from player inventory
        ItemStack braceletStack = findBraceletInHotbar(player);
        if (!braceletStack.isEmpty()) {
            // Get the model for the bracelet item
            BakedModel model = MinecraftClient.getInstance().getItemRenderer().getModels().getModel(braceletStack);

            // Use the appropriate transformation for the arm
            if (mainArm == Arm.RIGHT) {
                // Right hand - rotate as if held in the right hand
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));
                matrices.translate(0, 0.025, -0.05);  // Adjust for right hand if needed
            } else {
                // Left hand - rotate as if held in the left hand
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));
                matrices.translate(0.1275, 0.025, -0.05);  // Adjust for left hand if needed (mirrored translation)
            }

            // Render the bracelet item
            MinecraftClient.getInstance().getItemRenderer().renderItem(
                    braceletStack,
                    ModelTransformationMode.THIRD_PERSON_RIGHT_HAND,  // Use the same mode, both hands should render like held items
                    false,
                    matrices,
                    vertexConsumers,
                    light,
                    OverlayTexture.DEFAULT_UV,
                    model
            );
        }
    }

    private ItemStack findBraceletInHotbar(PlayerEntity player) {
        // Iterate through the player's hotbar to find the bracelet item
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.getItem() == ModItems.DALEK_BRACELET) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}