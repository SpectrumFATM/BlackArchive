package net.SpectrumFATM.black_archive.entity.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.mixin.StructureTemplateAccessor;
import net.SpectrumFATM.black_archive.util.ShipUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.joml.Vector3d;

import java.util.List;
import java.util.Optional;

public class ShipRenderer extends EntityRenderer<ShipEntity> {

    private final BlockRenderDispatcher blockRenderer;

    public ShipRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.blockRenderer = Minecraft.getInstance().getBlockRenderer();
    }

    @Override
    public void render(ShipEntity entity, float f, float g, PoseStack poseStack, MultiBufferSource bufferSource, int i) {
        if (blockRenderer == null) {
            BlackArchive.LOGGER.error("Block renderer is null. Skipping rendering.");
            return;
        }

        Level level = entity.level();
        if (level == null || !level.isClientSide()) {
            BlackArchive.LOGGER.warn("Not rendering ship because level is null or not client-side");
            return;
        }

        StructureTemplateManager templateManager = Minecraft.getInstance().getSingleplayerServer().getStructureManager();
        ResourceLocation structureLocation = ShipUtil.getShipStructureExterior(entity.getShipType());
        Optional<StructureTemplate> optionalTemplate = templateManager.get(structureLocation);
        if (optionalTemplate.isEmpty()) {
            BlackArchive.LOGGER.error("Structure template not found at: " + structureLocation);
            return;
        }
        StructureTemplate template = optionalTemplate.get();

        List<StructureBlockInfo> blockInfos = ((StructureTemplateAccessor) template).getPalettes().get(0).blocks();
        if (blockInfos.isEmpty()) {
            BlackArchive.LOGGER.error("No blocks found in the structure template");
            return;
        }

        float snappedYaw = Math.round(entity.getYRot() / 90.0F) * 90.0F;
        poseStack.mulPose(Axis.YP.rotationDegrees(-snappedYaw));

        poseStack.pushPose();
        Vector3d offset = ShipUtil.calculateExteriorOffset(entity.getShipType());
        poseStack.translate(offset.x, offset.y, offset.z);
        for (StructureBlockInfo blockInfo : blockInfos) {
            BlockState state = blockInfo.state();
            if (state != null && state.getBlock() != Blocks.AIR) {
                if (state.getBlock() == Blocks.IRON_DOOR && entity.isOpen() && state.hasProperty(DoorBlock.OPEN)) {
                    state = state.setValue(DoorBlock.OPEN, true);
                }

                poseStack.pushPose();
                BlockPos relativePos = blockInfo.pos();
                poseStack.translate(relativePos.getX(), relativePos.getY(), relativePos.getZ());
                blockRenderer.renderSingleBlock(state, poseStack, bufferSource, 15728880, OverlayTexture.NO_OVERLAY);
                poseStack.popPose();
            }
        }
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(ShipEntity entity) {
        return null;
    }
}