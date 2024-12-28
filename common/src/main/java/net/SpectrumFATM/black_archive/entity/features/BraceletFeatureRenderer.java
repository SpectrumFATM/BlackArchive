package net.SpectrumFATM.black_archive.entity.features;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.SpectrumFATM.black_archive.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class BraceletFeatureRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public BraceletFeatureRenderer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> context) {
        super(context);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, AbstractClientPlayer player, float limbAngle, float limbDistance, float tickDelta, float age, float headYaw, float headPitch) {
        if (player == null) {
            return;
        }

        // Render bracelet only if the player is visible and is an instance of AbstractClientPlayerEntity
        if (!player.isInvisible() && player != null) {
            matrices.pushPose();

            // Check if the player is left-handed or right-handed
            HumanoidArm mainArm = player.getMainArm(); // LEFT or RIGHT

            // Get the correct arm model based on the main arm
            PlayerModel<AbstractClientPlayer> playerRenderer = this.getParentModel();
            ModelPart arm = (mainArm == HumanoidArm.RIGHT) ? playerRenderer.rightArm : playerRenderer.leftArm;

            // Apply the arm's transformation to the MatrixStack
            arm.translateAndRotate(matrices);

            // Check if the player uses a slim model (Alex style)
            boolean isSlim = Minecraft.getInstance().player.getModelName().equals("slim");
            if (isSlim) {
                // Adjust for slim (Alex) model
                matrices.translate(-0.03525, 0.525, -0.1); // Adjust for slim arms
                matrices.scale(0.75f, 1.0f, 1.0f); // Scale it slightly smaller on X-axis to fit slim arm
            } else {
                // Default model (Steve) adjustments
                matrices.translate(-0.065, 0.525, -0.1); // Default translation for regular arms
            }

            if (player.getMainHandItem().getItem() != ModItems.DALEK_BRACELET.get() && player.getOffhandItem().getItem() != ModItems.DALEK_BRACELET.get()) {
                renderBraceletAsHeldItem(player, matrices, vertexConsumers, light, mainArm, isSlim);
            }

            matrices.popPose();
        }
    }

    private void renderBraceletAsHeldItem(Player player, PoseStack matrices, MultiBufferSource vertexConsumers, int light, HumanoidArm mainArm, boolean isSlim) {
        // Get bracelet item from player inventory
        ItemStack braceletStack = findBraceletInHotbar(player);
        if (!braceletStack.isEmpty()) {
            // Get the model for the bracelet item
            BakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(braceletStack);

            // Use the appropriate transformation for the arm
            if (mainArm == HumanoidArm.RIGHT) {
                // Right hand - rotate as if held in the right hand
                matrices.mulPose(Axis.XP.rotationDegrees(-90));
                matrices.translate(0, 0.025, -0.05);  // Adjust for right hand if needed
            } else {
                // Left hand - rotate as if held in the left hand
                matrices.mulPose(Axis.XP.rotationDegrees(-90));
                if (!isSlim) {
                    matrices.translate(0.1275, 0.025, -0.05);
                } else {
                    matrices.translate(0.0855, 0.025, -0.05);
                }
            }

            // Render the bracelet item
            Minecraft.getInstance().getItemRenderer().render(
                    braceletStack,
                    ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,  // Use the same mode, both hands should render like held items
                    false,
                    matrices,
                    vertexConsumers,
                    light,
                    OverlayTexture.NO_OVERLAY,
                    model
            );
        }
    }

    private ItemStack findBraceletInHotbar(Player player) {
        // Iterate through the player's hotbar to find the bracelet item
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() == ModItems.DALEK_BRACELET.get()) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}