package net.SpectrumFATM.black_archive.blockentity.console;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.blockentity.ModConsoles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.model.blockentity.console.ConsoleUnit;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;

public class RaniConsole extends HierarchicalModel implements ConsoleUnit {
	private static final AnimationDefinition FLIGHT = AnimationDefinition.Builder.withLength(2f).looping()
			.addAnimation("twirly_inner",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 2f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("twirly_outer",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 360f, 0f),
									AnimationChannel.Interpolations.LINEAR))).build();

	private static final ResourceLocation TEXTURE = new ResourceLocation(BlackArchive.MOD_ID, "textures/blockentity/console/rani/rani.png");

	private final ModelPart root;
	private final ModelPart pillar;
	private final ModelPart console;
	private final ModelPart twirly_inner;
	private final ModelPart twirly_outer;
	private final ModelPart panel1;
	private final ModelPart panel2;
	private final ModelPart panel3;

	public RaniConsole(ModelPart root) {
		this.root = root;
		this.pillar = root.getChild("pillar");
		this.console = root.getChild("console");
		this.twirly_inner = root.getChild("twirly_inner");
		this.twirly_outer = root.getChild("twirly_outer");
		this.panel1 = root.getChild("panel1");
		this.panel2 = root.getChild("panel2");
		this.panel3 = root.getChild("panel3");
	}

	@Override
	public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		pillar.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		console.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		twirly_inner.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		twirly_outer.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		panel1.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		panel2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		panel3.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		// No animation setup needed
	}

	public void renderConsole(GlobalConsoleBlockEntity globalConsoleBlock, Level level, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		TardisClientData reactions = TardisClientData.getInstance(level.dimension());
		if (globalConsoleBlock != null && (Boolean) globalConsoleBlock.getBlockState().getValue(GlobalConsoleBlock.POWERED)) {
			if (reactions.isFlying()) {
				this.animate(reactions.ROTOR_ANIMATION, FLIGHT, (float) Minecraft.getInstance().player.tickCount);
			}
		}

		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		pillar.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		console.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		twirly_inner.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		twirly_outer.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		panel1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		panel2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		panel3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ResourceLocation getDefaultTexture() {
		return TEXTURE;
	}

	@Override
	public ResourceLocation getConsoleTheme() {
		return ModConsoles.RANI.getId();
	}
}