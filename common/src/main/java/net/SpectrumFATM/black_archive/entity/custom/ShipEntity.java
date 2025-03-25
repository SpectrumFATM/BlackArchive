package net.SpectrumFATM.black_archive.entity.custom;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.block.ModBlocks;
import net.SpectrumFATM.black_archive.util.DimensionRegistry;
import net.SpectrumFATM.black_archive.util.ShipUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
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

    private static final EntityDataAccessor<Integer> FUEL = SynchedEntityData.defineId(ShipEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> OPEN = SynchedEntityData.defineId(ShipEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<String> TYPE = SynchedEntityData.defineId(ShipEntity.class, EntityDataSerializers.STRING);

    public ShipEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec3, InteractionHand interactionHand) {
        if (!player.level().isClientSide) {
            initializeDimension(player.level());
        }
        toggleOpenState();
        return InteractionResult.SUCCESS;
    }

    private void initializeDimension(Level level) {
        ResourceLocation levelLocation = new ResourceLocation(BlackArchive.MOD_ID, this.getStringUUID());
        ResourceKey<Level> levelKey = ResourceKey.create(Registries.DIMENSION, levelLocation);
        ServerLevel serverLevel = level.getServer().getLevel(levelKey);
        if (serverLevel == null) {
            DimensionRegistry.createDimension(level.getServer(), levelKey);
            generateStructure(level.getServer().getLevel(levelKey), BlockPos.ZERO, this.blockPosition());
        }
    }

    private void toggleOpenState() {
        if (this.isOpen()) {
            this.setOpen(false);
            this.playSound(SoundEvents.IRON_DOOR_CLOSE, 1.0f, 1.0f);
        } else {
            this.setOpen(true);
            this.playSound(SoundEvents.IRON_DOOR_OPEN, 1.0f, 1.0f);
        }
    }

    @Override
    public boolean mayInteract(Level level, BlockPos blockPos) {
        return true;
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
    public void playerTouch(Player player) {
        super.playerTouch(player);
        if (isOpen() && !player.level().isClientSide()) {
            teleportPlayerToShip(player);
        }
    }

    private void teleportPlayerToShip(Player player) {
        ServerLevel level = ShipUtil.getInteriorFromEntity(this);
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.teleportTo(level, 0.5, 128, -2.5, 0.0f, 0.0f);
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    private void generateStructure(ServerLevel level, BlockPos pos, BlockPos currentPos) {
        ResourceLocation structureLocation = ShipUtil.getShipStructureInterior(this.getShipType());
        StructureTemplateManager structureManager = level.getStructureManager();
        Optional<StructureTemplate> template = structureManager.get(structureLocation);

        if (template.isPresent()) {
            BlockPos structurePos = ShipUtil.calcuateInteriorOffset(pos, this.getShipType());
            template.get().placeInWorld(level, structurePos, structurePos, new StructurePlaceSettings(), level.random, 3);
        }

        BlockPos doorPos = ShipUtil.calculateInteriorDoorPosition(this.getShipType());
        BlockPos chairPos = ShipUtil.calculateChairPosition(this.getShipType());
        level.setBlock(doorPos, ModBlocks.SHIP_DOOR.get().defaultBlockState(), 3);
        level.setBlock(chairPos, ModBlocks.CHAIR.get().defaultBlockState(), 3);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(FUEL, 100);
        this.entityData.define(OPEN, false);
        this.entityData.define(TYPE, "sontaran");
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.contains("fuel")) {
            this.entityData.set(FUEL, compoundTag.getInt("fuel"));
        }
        if (compoundTag.contains("open")) {
            this.entityData.set(OPEN, compoundTag.getBoolean("open"));
        }
        if (compoundTag.contains("type")) {
            this.entityData.set(TYPE, compoundTag.getString("type"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putInt("fuel", this.entityData.get(FUEL));
        compoundTag.putBoolean("open", this.entityData.get(OPEN));
        compoundTag.putString("type", this.entityData.get(TYPE));
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return NonNullList.create();
    }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {
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

    public String getShipType() {
        return this.entityData.get(TYPE);
    }

    public void setShipType(String type) {
        this.entityData.set(TYPE, type);
    }

    @Override
    public boolean isAttackable() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return true;
    }
}