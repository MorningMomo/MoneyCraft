package com.morningmomo.moneycraft.datagen;

import com.morningmomo.moneycraft.init.ModFluids;
import com.morningmomo.moneycraft.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.FluidTags;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagProvider extends FabricTagProvider.FluidTagProvider {
    public ModFluidTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        for (FlowableFluid fluid : ModFluids.FLOWING_FLUID_LIST) {
            this.getOrCreateTagBuilder(FluidTags.WATER)
                    .add(fluid);
        }
        for (FlowableFluid fluid : ModFluids.STILL_FLUID_LIST) {
            this.getOrCreateTagBuilder(FluidTags.WATER)
                    .add(fluid);
        }
    }
}