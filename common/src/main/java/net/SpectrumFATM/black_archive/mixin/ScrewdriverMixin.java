package net.SpectrumFATM.black_archive.mixin;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.util.ScreenUtil;
import net.SpectrumFATM.black_archive.util.TARDISBindUtil;
import net.SpectrumFATM.black_archive.util.SonicEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.common.items.ScrewdriverMode;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.registry.TRBlockRegistry;

@Mixin(ScrewdriverItem.class)
public class ScrewdriverMixin extends Item {

    @Shadow
    private void addBlockPosToScrewdriver(ServerLevel serverLevel, Player player, ItemStack stack, BlockPos pos) {}

    public ScrewdriverMixin(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (level.isClientSide()) {
            SonicEngine.miscUse(level, player, interactionHand);
        }
        return super.use(level, player, interactionHand);
    }

    /**
     * @author SpectrumFATM
     * @reason Prevent mode change, this will be done in the GUI
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        String levelName = level.dimension().location().toString();
        ItemStack itemStack = context.getItemInHand();
        ScrewdriverItem itemInHand = (ScrewdriverItem) itemStack.getItem();

        if (context.getPlayer().isCrouching() && Platform.isClient()) {
            if (levelName.startsWith("tardis_refined:") && !TARDISBindUtil.hasTardisLevelName(itemStack)) {
                TARDISBindUtil.setTardisLevelName(itemStack, levelName);
            } else {
                ScreenUtil.openSonicScreen(0);
            }
        } else if (itemInHand.isScrewdriverMode(itemStack, ScrewdriverMode.DRAWING)
                && level.getBlockState(context.getClickedPos()).getBlock() != TRBlockRegistry.ASTRAL_MANIPULATOR_BLOCK.get()) {
            if (level instanceof ServerLevel serverLevel) {
                this.addBlockPosToScrewdriver(serverLevel, context.getPlayer(), itemStack, context.getClickedPos());
            }
        } else {
            SonicEngine.blockActivate(context);
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        SonicEngine.entityActivate(itemStack, player, livingEntity);
        BlackArchive.LOGGER.info("ScrewdriverMixin: interactLivingEntity");
        return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand);
    }
}