package net.SpectrumFATM.black_archive.fabric.tardis.control;

import com.mojang.datafixers.util.Pair;
import net.SpectrumFATM.black_archive.fabric.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.fabric.sound.ModSounds;
import net.SpectrumFATM.black_archive.fabric.tardis.upgrades.ModUpgrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.Structure;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.registry.TRSoundRegistry;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

public class TelepathicControl extends Control {
    protected TelepathicControl(Identifier id, String langId, boolean isCriticalForTardisOperation) {
        super(id, langId, isCriticalForTardisOperation);
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator tardisLevelOperator, ConsoleTheme consoleTheme, ControlEntity controlEntity, PlayerEntity playerEntity) {

        UpgradeHandler upgradeHandler = tardisLevelOperator.getUpgradeHandler();
        if (!ModUpgrades.TELEPATHIC_UPGRADE.get().isUnlocked(upgradeHandler)) {
            return false;
        }

        Random random = new Random();

        if (random.nextInt(20) == 1) {
            playerEntity.sendMessage(Text.translatable("telepathic.black_archive.bad").formatted(Formatting.RED), true);
            controlEntity.playSound(ModSounds.TARDIS_GROAN, 1.0F, 1.0F);

            BlockPos pos = tardisLevelOperator.getPilotingManager().getCurrentLocation().getPosition();
            ServerWorld world = tardisLevelOperator.getPilotingManager().getCurrentLocation().getLevel();

            world.playSoundAtBlockCenter(pos, ModSounds.TARDIS_GROAN, SoundCategory.AMBIENT, 1.0F, 1.0F, true);
        } else {
            playerEntity.sendMessage(Text.translatable("telepathic.black_archive.happy"), true);
            controlEntity.playSound(TRSoundRegistry.CONSOLE_POWER_ON.get(), 1.0F, 1.0F);
        }

        return true;
    }

    @Override
    public boolean onRightClick(TardisLevelOperator tardisLevelOperator, ConsoleTheme consoleTheme, ControlEntity controlEntity, PlayerEntity playerEntity) {

        UpgradeHandler upgradeHandler = tardisLevelOperator.getUpgradeHandler();
        if (!ModUpgrades.TELEPATHIC_UPGRADE.get().isUnlocked(upgradeHandler)) {
            return false;
        }

        ServerWorld world = tardisLevelOperator.getPilotingManager().getTargetLocation().getLevel();
        BlockPos pos = tardisLevelOperator.getPilotingManager().getTargetLocation().getPosition();
        Random random = new Random();
        Registry<Structure> structureRegistry = world.getRegistryManager().get(RegistryKeys.STRUCTURE);
        List<RegistryEntry.Reference<Structure>> structureEntries = structureRegistry.streamEntries()
                .filter(isNotUnderwaterStructure())
                .toList();

        if (structureEntries.isEmpty()) {
            return false;
        }

        RegistryEntry<Structure> selectedStructure = structureEntries.get(random.nextInt(structureEntries.size()));
        RegistryEntryList<Structure> structureList = RegistryEntryList.of(selectedStructure);
        ChunkGenerator chunkGenerator = world.getChunkManager().getChunkGenerator();
        Optional<Pair<BlockPos, RegistryEntry<Structure>>> structurePos = Optional.ofNullable(
                chunkGenerator.locateStructure(
                        world,
                        structureList,
                        pos,
                        BlackArchiveConfig.COMMON.telepathicCircuitRange.get(),
                        false
                )
        );

        if (structurePos.isPresent()) {
            BlockPos foundPos = structurePos.get().getFirst();
            foundPos = foundPos.add(0, 250, 0);
            tardisLevelOperator.getPilotingManager().getTargetLocation().setPosition(foundPos);
            controlEntity.playSound(TRSoundRegistry.CORRIDOR_TELEPORTER_SUCCESS.get(), 1.0F, 1.0F);
            return true;
        } else {
            return false;
        }
    }

    private static Predicate<RegistryEntry<Structure>> isNotUnderwaterStructure() {
        return entry -> {
            Identifier structureId = entry.getKey().get().getValue();
            return !structureId.getPath().contains("ocean")
                    && !structureId.getPath().contains("shipwreck")
                    && !structureId.getPath().contains("underwater")
                    && !structureId.getPath().contains("monument");
        };
    }
}