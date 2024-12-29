package net.SpectrumFATM.black_archive.tardis.control;

import com.mojang.datafixers.util.Pair;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.sound.ModSounds;
import net.SpectrumFATM.black_archive.tardis.upgrades.ModUpgrades;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.Structure;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.tardis.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.registry.TRSoundRegistry;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class TelepathicControl extends Control {
    protected TelepathicControl(ResourceLocation id, String langId, boolean isCriticalForTardisOperation) {
        super(id, langId, isCriticalForTardisOperation);
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator tardisLevelOperator, ConsoleTheme consoleTheme, ControlEntity controlEntity, Player playerEntity) {

        UpgradeHandler upgradeHandler = tardisLevelOperator.getUpgradeHandler();
        if (!ModUpgrades.TELEPATHIC_UPGRADE.get().isUnlocked(upgradeHandler)) {
            return false;
        }

        RandomSource random = tardisLevelOperator.getLevel().getRandom();
        if (random.nextInt(20) == 1) {
            playerEntity.displayClientMessage(Component.translatable("telepathic.black_archive.bad").withStyle(ChatFormatting.RED), true);

            controlEntity.playSound(ModSounds.TARDIS_GROAN.get(), 1.0F, 1.0F);

            BlockPos pos = tardisLevelOperator.getPilotingManager().getCurrentLocation().getPosition();
            ServerLevel world = tardisLevelOperator.getPilotingManager().getCurrentLocation().getLevel();

            world.playLocalSound(pos, ModSounds.TARDIS_GROAN.get(), SoundSource.AMBIENT, 1.0F, 1.0F, true);
        } else {
            playerEntity.displayClientMessage(Component.translatable("telepathic.black_archive.happy"), true);
            controlEntity.playSound(TRSoundRegistry.CONSOLE_POWER_ON.get(), 1.0F, 1.0F);
        }


        return true;
    }

    @Override
    public boolean onRightClick(TardisLevelOperator tardisLevelOperator, ConsoleTheme consoleTheme, ControlEntity controlEntity, Player playerEntity) {

        UpgradeHandler upgradeHandler = tardisLevelOperator.getUpgradeHandler();
        if (!ModUpgrades.TELEPATHIC_UPGRADE.get().isUnlocked(upgradeHandler)) {
            return false;
        }

        ServerLevel world = tardisLevelOperator.getPilotingManager().getTargetLocation().getLevel();
        BlockPos pos = tardisLevelOperator.getPilotingManager().getTargetLocation().getPosition();
        RandomSource random = tardisLevelOperator.getLevel().getRandom();
        Registry<Structure> structureRegistry = world.registryAccess().registryOrThrow(Registries.STRUCTURE);
        List<Holder.Reference<Structure>> structureEntries = structureRegistry.holders()
                .filter(isNotUnderwaterStructure())
                .toList();

        if (structureEntries.isEmpty()) {
            return false;
        }

        Holder<Structure> selectedStructure = structureEntries.get(random.nextInt(structureEntries.size()));
        HolderSet<Structure> structureList = HolderSet.direct(selectedStructure);
        ChunkGenerator chunkGenerator = world.getChunkSource().getGenerator();
        Optional<Pair<BlockPos, Holder<Structure>>> structurePos = Optional.ofNullable(
                chunkGenerator.findNearestMapStructure(
                        world,
                        structureList,
                        pos,
                        BlackArchiveConfig.COMMON.telepathicCircuitRange.get(),
                        false
                )
        );

        if (structurePos.isPresent()) {
            BlockPos foundPos = structurePos.get().getFirst();
            foundPos = foundPos.offset(0, 250, 0);
            tardisLevelOperator.getPilotingManager().getTargetLocation().setPosition(foundPos);
            controlEntity.playSound(TRSoundRegistry.CORRIDOR_TELEPORTER_SUCCESS.get(), 1.0F, 1.0F);
            return true;
        } else {
            return false;
        }
    }

    private static Predicate<Holder<Structure>> isNotUnderwaterStructure() {
        return entry -> {
            ResourceLocation structureId = entry.unwrapKey().get().location();
            return !structureId.getPath().contains("ocean")
                    && !structureId.getPath().contains("shipwreck")
                    && !structureId.getPath().contains("underwater")
                    && !structureId.getPath().contains("monument");
        };
    }
}