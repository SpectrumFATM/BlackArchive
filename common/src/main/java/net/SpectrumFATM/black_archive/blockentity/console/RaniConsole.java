package net.SpectrumFATM.black_archive.blockentity.console;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.blockentity.ModConsoles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.model.blockentity.console.ConsoleUnit;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;

public class RaniConsole extends SinglePartEntityModel implements ConsoleUnit {
	private static final Animation FLIGHT;
	private static final Identifier TEXTURE;
	private final ModelPart pillar;
	private final ModelPart console;
	private final ModelPart panels;
	private final ModelPart twirly_inner;
	private final ModelPart twirly_outer;
	private final ModelPart panel1;
	private final ModelPart panel2;
	private final ModelPart panel3;
	private final ModelPart root;

	public RaniConsole(ModelPart root) {
		this.pillar = root.getChild("pillar");
		this.console = root.getChild("console");
		this.panels = this.console.getChild("panels");
		this.twirly_inner = root.getChild("twirly_inner");
		this.twirly_outer = root.getChild("twirly_outer");
		this.panel1 = root.getChild("panel1");
		this.panel2 = root.getChild("panel2");
		this.panel3 = root.getChild("panel3");
		this.root = root;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		pillar.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		console.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		twirly_outer.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		twirly_inner.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		panel1.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		panel2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		panel3.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return root;
	}

	@Override
	public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	public void renderConsole(GlobalConsoleBlockEntity globalConsoleBlock, World level, MatrixStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		TardisClientData reactions = TardisClientData.getInstance(level.getRegistryKey());
		if (globalConsoleBlock != null && (Boolean)globalConsoleBlock.getCachedState().get(GlobalConsoleBlock.POWERED)) {
			if (reactions.isFlying()) {
				this.updateAnimation(reactions.ROTOR_ANIMATION, FLIGHT, (float)MinecraftClient.getInstance().player.age);
			}
		}
		this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public Identifier getDefaultTexture() {
		return TEXTURE;
	}

	@Override
	public Identifier getConsoleTheme() {
		return ModConsoles.RANI.getId();
	}
	
	static {
		FLIGHT = Animation.Builder.create(2f).looping()
				.addBoneAnimation("twirly_inner",
						new Transformation(Transformation.Targets.TRANSLATE,
								new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
										Transformation.Interpolations.CUBIC),
								new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 2f, 0f),
										Transformation.Interpolations.CUBIC),
								new Keyframe(2f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
										Transformation.Interpolations.CUBIC)))
				.addBoneAnimation("twirly_outer",
						new Transformation(Transformation.Targets.ROTATE,
								new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
										Transformation.Interpolations.LINEAR),
								new Keyframe(2f, AnimationHelper.createRotationalVector(0f, 360f, 0f),
										Transformation.Interpolations.LINEAR))).build();

		TEXTURE = new Identifier(BlackArchive.MOD_ID, "textures/blockentity/console/rani/rani.png");
	}
}