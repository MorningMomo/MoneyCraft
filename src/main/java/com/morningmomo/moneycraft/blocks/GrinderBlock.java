package com.morningmomo.moneycraft.blocks;

import com.morningmomo.moneycraft.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GrinderBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public GrinderBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = new ItemStack(ModItems.WOOD_DUST, 8);
        PlayerInventory inventory = player.getInventory();
        ItemStack handItem = player.getMainHandStack();

        int slot = inventory.selectedSlot;
        int count = handItem.getCount();

        if (!handItem.isEmpty() && handItem.isIn(ItemTags.LOGS_THAT_BURN)) {
            if (inventory.insertStack(itemStack)) {
                if (count >= 1) {
                    inventory.removeStack(slot, 1);
                    player.giveItemStack(itemStack);
                    return ActionResult.SUCCESS;
                }
            } else {
                player.sendMessage(Text.of("Your backpack is full."));
                return ActionResult.SUCCESS;
            }
        } else {
            player.sendMessage(Text.of("Your right hand does not have any log."));
            return ActionResult.SUCCESS;
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING,rotation.rotate(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }
}