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
import whocraft.tardis_refined.TRConfig;
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
	private final ModelPart twirly;
	private final ModelPart panel1;
	private final ModelPart panel2;
	private final ModelPart panel3;
	private final ModelPart root;
	public RaniConsole(ModelPart root) {
		this.pillar = root.getChild("pillar");
		this.console = root.getChild("console");
		this.panels = this.console.getChild("panels");
		this.twirly = root.getChild("twirly");
		this.panel1 = root.getChild("panel1");
		this.panel2 = root.getChild("panel2");
		this.panel3 = root.getChild("panel3");
		this.root = root;
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData pillar = modelPartData.addChild("pillar", ModelPartBuilder.create().uv(67, 37).cuboid(-7.0F, -21.0F, -4.0F, 14.0F, 21.0F, 8.0F, new Dilation(0.0F))
				.uv(0, 47).cuboid(6.0F, -25.0003F, -4.0F, 5.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData top_r1 = pillar.addChild("top_r1", ModelPartBuilder.create().uv(0, 47).cuboid(6.0F, -25.002F, -4.0F, 5.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, 1.0472F, 3.1416F));

		ModelPartData top_r2 = pillar.addChild("top_r2", ModelPartBuilder.create().uv(0, 47).cuboid(6.0F, -25.005F, -4.0F, 5.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

		ModelPartData top_r3 = pillar.addChild("top_r3", ModelPartBuilder.create().uv(23, 37).cuboid(6.0F, -24.0003F, -5.0F, 2.0F, 3.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

		ModelPartData top_r4 = pillar.addChild("top_r4", ModelPartBuilder.create().uv(23, 37).cuboid(6.0F, -24.002F, -5.0F, 2.0F, 3.0F, 10.0F, new Dilation(0.0F))
				.uv(67, 37).cuboid(-7.0F, -20.999F, -4.0F, 14.0F, 21.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		ModelPartData top_r5 = pillar.addChild("top_r5", ModelPartBuilder.create().uv(23, 37).cuboid(6.0F, -24.005F, -5.0F, 2.0F, 3.0F, 10.0F, new Dilation(0.0F))
				.uv(67, 37).cuboid(-7.0F, -21.001F, -4.0F, 14.0F, 21.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		ModelPartData console = modelPartData.addChild("console", ModelPartBuilder.create().uv(1, 0).cuboid(-10.0F, -1.0F, -17.0F, 20.0F, 3.0F, 34.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 8.0F, 0.0F));

		ModelPartData rim_r1 = console.addChild("rim_r1", ModelPartBuilder.create().uv(1, 0).cuboid(-10.0F, -16.999F, -17.0F, 20.0F, 3.0F, 34.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 16.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		ModelPartData rim_r2 = console.addChild("rim_r2", ModelPartBuilder.create().uv(1, 0).cuboid(-10.0F, -17.01F, -17.0F, 20.0F, 3.0F, 34.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 16.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		ModelPartData panels = console.addChild("panels", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData bar2_3_r1 = panels.addChild("bar2_3_r1", ModelPartBuilder.create().uv(35, 38).cuboid(-1.0F, -1.0F, 8.0F, 2.0F, 1.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, -0.3491F, -1.5708F, 0.0F));

		ModelPartData bar4_5_r1 = panels.addChild("bar4_5_r1", ModelPartBuilder.create().uv(35, 38).cuboid(-1.0F, -1.0F, 8.0F, 2.0F, 1.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, -0.3491F, 0.5236F, 0.0F));

		ModelPartData bar3_4_r1 = panels.addChild("bar3_4_r1", ModelPartBuilder.create().uv(35, 38).cuboid(-1.0F, -1.0F, 8.0F, 2.0F, 1.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, -0.3491F, -0.5236F, 0.0F));

		ModelPartData bar5_6_r1 = panels.addChild("bar5_6_r1", ModelPartBuilder.create().uv(35, 38).cuboid(-1.0F, -1.0F, -20.0F, 2.0F, 1.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.3491F, -1.5708F, 0.0F));

		ModelPartData bar1_2_r1 = panels.addChild("bar1_2_r1", ModelPartBuilder.create().uv(35, 38).cuboid(-1.0F, -1.0F, -20.0F, 2.0F, 1.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.3491F, 0.5236F, 0.0F));

		ModelPartData bar1_6_r1 = panels.addChild("bar1_6_r1", ModelPartBuilder.create().uv(35, 38).cuboid(-1.0F, -1.0F, -20.0F, 2.0F, 1.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.3491F, -0.5236F, 0.0F));

		ModelPartData panel6_r1 = panels.addChild("panel6_r1", ModelPartBuilder.create().uv(63, 1).cuboid(-10.0F, 0.0F, -18.0F, 20.0F, 0.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.3491F, -1.0472F, 0.0F));

		ModelPartData panle5_r1 = panels.addChild("panle5_r1", ModelPartBuilder.create().uv(63, 13).cuboid(-10.0F, 0.0F, 6.0F, 20.0F, 0.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, -0.3491F, 1.0472F, 0.0F));

		ModelPartData panel4_r1 = panels.addChild("panel4_r1", ModelPartBuilder.create().uv(63, 13).cuboid(-10.0F, 0.0F, 6.0F, 20.0F, 0.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

		ModelPartData panel3_r1 = panels.addChild("panel3_r1", ModelPartBuilder.create().uv(63, 13).mirrored().cuboid(-10.0F, 0.0F, 6.0F, 20.0F, 0.0F, 12.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, 0.0F, -0.3491F, -1.0472F, 0.0F));

		ModelPartData panel2_r1 = panels.addChild("panel2_r1", ModelPartBuilder.create().uv(63, 1).cuboid(-10.0F, 0.0F, -18.0F, 20.0F, 0.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.3491F, 1.0472F, 0.0F));

		ModelPartData panel1_r1 = panels.addChild("panel1_r1", ModelPartBuilder.create().uv(63, 1).cuboid(-10.0F, 0.0F, -18.0F, 20.0F, 0.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

		ModelPartData twirly = modelPartData.addChild("twirly", ModelPartBuilder.create().uv(0, 16).cuboid(-4.5F, -35.0F, 0.0F, 9.0F, 9.0F, 0.0F, new Dilation(0.0F))
				.uv(18, 16).cuboid(-1.0F, -26.0F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 0.0509F, 0.0F));

		ModelPartData inner3_r1 = twirly.addChild("inner3_r1", ModelPartBuilder.create().uv(0, 16).cuboid(-5.0F, -7.0F, 0.0F, 9.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, -30.5F, 0.5F, 0.0F, -1.5708F, 1.5708F));

		ModelPartData inner2_r1 = twirly.addChild("inner2_r1", ModelPartBuilder.create().uv(0, 16).cuboid(-5.0F, -7.0F, 0.0F, 9.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -28.0F, 0.5F, 0.0F, -1.5708F, 0.0F));

		ModelPartData outer2_r1 = twirly.addChild("outer2_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -12.0F, -0.5F, 15.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -31.5F, 4.5F, 1.2217F, 0.0F, 0.0F));

		ModelPartData outer1_r1 = twirly.addChild("outer1_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -12.0F, 0.0F, 15.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -26.0F, -4.0F, -1.2217F, 0.0F, 0.0F));

		ModelPartData panel1 = modelPartData.addChild("panel1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 6.0F, 0.0F));

		ModelPartData panel_r1 = panel1.addChild("panel_r1", ModelPartBuilder.create().uv(1, 37).cuboid(-3.0F, -1.0F, -3.0F, 6.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 11.0F, -0.3491F, 0.0F, 0.0F));

		ModelPartData speaker_r1 = panel1.addChild("speaker_r1", ModelPartBuilder.create().uv(24, 21).cuboid(-1.0F, -2.0F, -1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-10.95F, 1.35F, 6.5F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cuberight_r1 = panel1.addChild("cuberight_r1", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-11.7F, 0.9F, 3.95F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cubeleft_r1 = panel1.addChild("cubeleft_r1", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-9.2F, 0.9F, 8.45F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cubebottom_r1 = panel1.addChild("cubebottom_r1", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-12.35F, 2.2F, 7.25F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cubebottomleft_r1 = panel1.addChild("cubebottomleft_r1", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-11.15F, 2.15F, 9.5F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cubebottomright_r1 = panel1.addChild("cubebottomright_r1", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-13.65F, 2.15F, 5.0F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cubetop_r1 = panel1.addChild("cubetop_r1", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, 0.0F, 4.75F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData panel2 = modelPartData.addChild("panel2", ModelPartBuilder.create(), ModelTransform.of(0.0F, 6.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

		ModelPartData panel_r2 = panel2.addChild("panel_r2", ModelPartBuilder.create().uv(1, 37).cuboid(-3.0F, -1.0F, -3.0F, 6.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 11.0F, -0.3491F, 0.0F, 0.0F));

		ModelPartData speaker_r2 = panel2.addChild("speaker_r2", ModelPartBuilder.create().uv(24, 21).cuboid(-1.0F, -2.0F, -1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-10.95F, 1.35F, 6.5F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cuberight_r2 = panel2.addChild("cuberight_r2", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-11.7F, 0.9F, 3.95F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cubeleft_r2 = panel2.addChild("cubeleft_r2", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-9.2F, 0.9F, 8.45F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cubebottom_r2 = panel2.addChild("cubebottom_r2", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-12.35F, 2.2F, 7.25F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cubebottomleft_r2 = panel2.addChild("cubebottomleft_r2", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-11.15F, 2.15F, 9.5F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cubebottomright_r2 = panel2.addChild("cubebottomright_r2", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-13.65F, 2.15F, 5.0F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cubetop_r2 = panel2.addChild("cubetop_r2", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, 0.0F, 4.75F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData panel3 = modelPartData.addChild("panel3", ModelPartBuilder.create(), ModelTransform.of(0.0F, 6.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

		ModelPartData panel_r3 = panel3.addChild("panel_r3", ModelPartBuilder.create().uv(1, 37).cuboid(-3.0F, -1.0F, -3.0F, 6.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 11.0F, -0.3491F, 0.0F, 0.0F));

		ModelPartData speaker_r3 = panel3.addChild("speaker_r3", ModelPartBuilder.create().uv(24, 21).cuboid(-1.0F, -2.0F, -1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-10.95F, 1.35F, 6.5F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cuberight_r3 = panel3.addChild("cuberight_r3", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-11.7F, 0.9F, 3.95F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cubeleft_r3 = panel3.addChild("cubeleft_r3", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-9.2F, 0.9F, 8.45F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cubebottom_r3 = panel3.addChild("cubebottom_r3", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-12.35F, 2.2F, 7.25F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cubebottomleft_r3 = panel3.addChild("cubebottomleft_r3", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-11.15F, 2.15F, 9.5F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cubebottomright_r3 = panel3.addChild("cubebottomright_r3", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-13.65F, 2.15F, 5.0F, -0.195F, 0.4891F, -0.3979F));

		ModelPartData cubetop_r3 = panel3.addChild("cubetop_r3", ModelPartBuilder.create().uv(16, 23).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, 0.0F, 4.75F, -0.195F, 0.4891F, -0.3979F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		pillar.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		console.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		twirly.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
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
		FLIGHT = Animation.Builder.create(2.0F).looping()
				.addBoneAnimation("twirly", new Transformation(Transformation.Targets.ROTATE,
						new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
						new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 360.0F, 0.0F), Transformation.Interpolations.LINEAR)
				))
				.build();

		TEXTURE = new Identifier(BlackArchive.MOD_ID, "textures/blockentity/console/rani/rani.png");
	}
}