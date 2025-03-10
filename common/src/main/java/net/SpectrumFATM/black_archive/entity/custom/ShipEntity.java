package net.SpectrumFATM.black_archive.entity.custom;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.block.ModBlocks;
import net.SpectrumFATM.black_archive.blockentity.entities.ShipDoorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class ShipEntity extends Entity {

    private static final EntityDataAccessor<String> DIMENSION = SynchedEntityData.defineId(ShipEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> FUEL = SynchedEntityData.defineId(ShipEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> OPEN = SynchedEntityData.defineId(ShipEntity.class, EntityDataSerializers.BOOLEAN);

    public ShipEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);

        if (!level.isClientSide && this.getEntityData().get(DIMENSION).isEmpty()) {
            this.getEntityData().get(DIMENSION);
        }
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand interactionHand) {
        player.displayClientMessage(Component.literal("sdad"), true);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void setPos(double x, double y, double z) {
        super.setPos(Math.floor(x) + 0.5, Math.floor(y), Math.floor(z) + 0.5);
    }

    @Override
    public void move(MoverType moverType, Vec3 vec3) {
        super.move(moverType, vec3);
        this.setPos(Math.floor(this.getX()) + 0.5, Math.floor(this.getY()), Math.floor(this.getZ()) + 0.5);

    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    private void generateStructure(ServerLevel level, BlockPos pos, BlockPos currentPos) {
        StructureTemplateManager structureManager = level.getStructureManager();
        Optional<StructureTemplate> template = structureManager.get(new ResourceLocation("black_archive", "sontaran_ship"));

        if (template != null) {
            BlockPos structurePos = pos.offset(-4, 127, -6); // Adjust the position as needed
            template.get().placeInWorld(level, structurePos, structurePos,
                    new StructurePlaceSettings(), level.random, 3);
        }

        BlockPos doorPos = new BlockPos(-4, 128, 0);

        level.setBlock(doorPos, ModBlocks.SHIP_DOOR.get().defaultBlockState(), 3);

        if (level.getBlockEntity(doorPos) instanceof ShipDoorEntity shipDoor) {
            shipDoor.setPos(currentPos.asLong());
            BlackArchive.LOGGER.info("Ship door entity created at: " + doorPos + "with exterior pos: " + currentPos.north(3));
        }
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DIMENSION, "");
        this.entityData.define(FUEL, 0);
        this.entityData.define(OPEN, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.contains("dimension")) {
            this.entityData.set(DIMENSION, compoundTag.getString("dimension"));
        }

        if (compoundTag.contains("fuel")) {
            this.entityData.set(FUEL, compoundTag.getInt("fuel"));
        }
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return NonNullList.create();
    }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        if (this.entityData.get(DIMENSION) != null) {
            compoundTag.putString("dimension", this.entityData.get(DIMENSION));
        }

        if (this.entityData.get(FUEL) != null) {
            compoundTag.putInt("fuel", this.entityData.get(FUEL));
        }
    }

    public boolean isOpen() {
        return this.entityData.get(OPEN);
    }

    public void setOpen(boolean open) {
        this.entityData.set(OPEN, open);
    }

    public int getFuel() {
        return this.entityData.get(FUEL);
    }

    public void setFuel(int fuel) {
        this.entityData.set(FUEL, fuel);
    }
}
