package net.SpectrumFATM.black_archive.entity.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.mixin.StructureTemplateAccessor;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

import java.util.List;
import java.util.Optional;

public class ShipRenderer extends EntityRenderer<ShipEntity> {

    private final BlockRenderDispatcher blockRenderer;

    public ShipRenderer(EntityRendererProvider.Context context) {
        super(context);
        // Get the client-side block renderer
        this.blockRenderer = Minecraft.getInstance().getBlockRenderer();
    }


    @Override
    public void render(ShipEntity entity, float f, float g, PoseStack poseStack, MultiBufferSource bufferSource, int i) {
    if (blockRenderer == null) {
            BlackArchive.LOGGER.error("Block renderer is null. Skipping rendering.");
            return;
        }

        // Use the client world
        Level level = entity.level();
        if (level == null || !level.isClientSide()) {
            BlackArchive.LOGGER.warn("Not rendering ship because level is null or not client-side");
            return;
        }

        // Access the structure template manager from the client level
        if (Minecraft.getInstance().getSingleplayerServer() == null) {
            BlackArchive.LOGGER.warn("No integrated server found. Skipping rendering.");
            return;
        }

        StructureTemplateManager templateManager = Minecraft.getInstance().getSingleplayerServer().getStructureManager();
        ResourceLocation structureLocation = new ResourceLocation("black_archive", "sontaran_pod");
        Optional<StructureTemplate> optionalTemplate = templateManager.get(structureLocation);
        if (optionalTemplate.isEmpty()) {
            BlackArchive.LOGGER.error("Structure template not found at: " + structureLocation);
            return;
        }
        StructureTemplate template = optionalTemplate.get();

        // Get the block entity's position
        BlockPos pos = entity.blockPosition();

        // Retrieve block structure data
        List<StructureBlockInfo> blockInfos = ((StructureTemplateAccessor) template).getPalettes().get(0).blocks();
        if (blockInfos.isEmpty()) {
            BlackArchive.LOGGER.error("No blocks found in the structure template");
            return;
        }

        // Begin rendering the structure with the desired offset relative to the block entity
        poseStack.pushPose();
        poseStack.translate(-2.5, 0, -2.5);
        for (StructureBlockInfo blockInfo : blockInfos) {
            BlockState state = blockInfo.state();
            if (state != null && state.getBlock() != Blocks.AIR) {
                poseStack.pushPose();
                // Translate by the block's relative position from the template
                BlockPos relativePos = blockInfo.pos();
                poseStack.translate(relativePos.getX(), relativePos.getY(), relativePos.getZ());
                // Render the block with full brightness
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