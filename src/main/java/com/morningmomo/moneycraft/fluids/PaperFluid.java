package com.morningmomo.moneycraft.fluids;

import com.morningmomo.moneycraft.init.ModBlocks;
import com.morningmomo.moneycraft.init.ModFluids;
import com.morningmomo.moneycraft.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class PaperFluid extends FlowableFluid {
    public PaperFluid() {
    }

    public Fluid getFlowing() {
        return ModFluids.FLOWING_PAPER_PULP;
    }

    public Fluid getStill() {
        return ModFluids.STILL_PAPER_PULP;
    }

    public Item getBucketItem() {
        return ModItems.PAPER_PULP_BUCKET;
    }

    @Nullable
    public ParticleEffect getParticle() {
        return ParticleTypes.FALLING_WATER;
    }

    protected boolean isInfinite(World world) {
        return false;
    }

    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    public int getFlowSpeed(WorldView world) {
        return 1;
    }

    public BlockState toBlockState(FluidState state) {
        return ModBlocks.PAPER_PULP_BLOCK.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

    public int getLevelDecreasePerBlock(WorldView world) {
        return 2;
    }

    public int getTickRate(WorldView world) {
        return 20;
    }

    public boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }

    protected float getBlastResistance() {
        return 100.0F;
    }

    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.ITEM_BUCKET_FILL);
    }


    public static class Flowing extends PaperFluid {
        public Flowing() {
        }

        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

        public boolean isStill(FluidState state) {
            return false;
        }
    }
    
    public static class Still extends PaperFluid {
        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}
