package net.SpectrumFATM.black_archive.mixin;

import net.SpectrumFATM.black_archive.network.messages.C2SLockFunction;
import net.SpectrumFATM.black_archive.util.ScreenUtil;
import net.SpectrumFATM.black_archive.util.TARDISBindUtil;
import net.SpectrumFATM.black_archive.util.SonicEngine;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.common.util.Platform;

@Mixin(ScrewdriverItem.class)
public class ScrewdriverMixin extends Item {

    ScrewdriverItem item = (ScrewdriverItem) (Object) this;

    public ScrewdriverMixin(Properties properties) {
        super(properties);
        SonicEngine.setSetting("block", false);
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
    @Overwrite
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        String levelName = level.dimension().location().toString();
        ItemStack itemStack = context.getItemInHand();

        if (context.getPlayer().isCrouching()) {
            if (levelName.startsWith("tardis_refined:") && !TARDISBindUtil.hasTardisLevelName(itemStack)) {
                TARDISBindUtil.setTardisLevelName(itemStack, levelName);
            } else {
                ScreenUtil.openSonicScreen(0);
            }
        } else {
            SonicEngine.blockActivate(context);
        }

        return InteractionResult.PASS;
    }
}
