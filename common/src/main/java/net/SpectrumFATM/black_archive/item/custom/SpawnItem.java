package net.SpectrumFATM.black_archive.item.custom;

import java.util.Objects;

import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;

public class SpawnItem extends TooltipItem {

    private final String id;

    public SpawnItem(Properties settings, String id, String tooltip) {
        super(settings, tooltip);
        this.id = id;
    }

    public SpawnItem(Properties settings, String id) {
        super(settings, "");
        this.id = id;
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        EntityType entityType = ModEntities.ENTITY_TYPES.get(new ResourceLocation(BlackArchive.MOD_ID, id));
        Level world = context.getLevel();
        if (!(world instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemStack = context.getItemInHand();
            BlockPos blockPos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.is(Blocks.SPAWNER)) {
                BlockEntity blockEntity = world.getBlockEntity(blockPos);
                if (blockEntity instanceof SpawnerBlockEntity) {
                    SpawnerBlockEntity mobSpawnerBlockEntity = (SpawnerBlockEntity)blockEntity;
                    mobSpawnerBlockEntity.setEntityId(entityType, world.getRandom());
                    blockEntity.setChanged();
                    world.sendBlockUpdated(blockPos, blockState, blockState, 3);
                    world.gameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
                    itemStack.shrink(1);
                    return InteractionResult.CONSUME;
                }
            }

            BlockPos blockPos2;
            if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
                blockPos2 = blockPos;
            } else {
                blockPos2 = blockPos.relative(direction);
            }

            if (entityType.spawn((ServerLevel)world, itemStack, context.getPlayer(), blockPos2, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockPos, blockPos2) && direction == Direction.UP) != null) {
                itemStack.shrink(1);
                world.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
            }

            return InteractionResult.CONSUME;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        EntityType entityType = ModEntities.ENTITY_TYPES.get(new ResourceLocation(BlackArchive.MOD_ID, id));
        ItemStack itemStack = user.getItemInHand(hand);
        BlockHitResult blockHitResult = getPlayerPOVHitResult(world, user, Fluid.SOURCE_ONLY);
        if (blockHitResult.getType() != Type.BLOCK) {
            return InteractionResultHolder.pass(itemStack);
        } else if (!(world instanceof ServerLevel)) {
            return InteractionResultHolder.success(itemStack);
        } else {
            BlockHitResult blockHitResult2 = blockHitResult;
            BlockPos blockPos = blockHitResult2.getBlockPos();
            if (!(world.getBlockState(blockPos).getBlock() instanceof LiquidBlock)) {
                return InteractionResultHolder.pass(itemStack);
            } else if (world.mayInteract(user, blockPos) && user.mayUseItemAt(blockPos, blockHitResult2.getDirection(), itemStack)) {
                Entity entity = entityType.spawn((ServerLevel)world, itemStack, user, blockPos, MobSpawnType.SPAWN_EGG, false, false);
                if (entity == null) {
                    return InteractionResultHolder.pass(itemStack);
                } else {
                    if (!user.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }

                    user.awardStat(Stats.ITEM_USED.get(this));
                    world.gameEvent(user, GameEvent.ENTITY_PLACE, entity.position());
                    return InteractionResultHolder.consume(itemStack);
                }
            } else {
                return InteractionResultHolder.fail(itemStack);
            }
        }
    }
}