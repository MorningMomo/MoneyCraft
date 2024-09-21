package com.morningmomo.moneycraft.datagen;

import com.morningmomo.moneycraft.init.ModBlocks;
import com.morningmomo.moneycraft.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        //Blocks
        for (Block block : ModBlocks.BLOCK_LIST){
            this.getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                    .add(block);
            this.getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                    .add(block);
        }
        for (Block block : ModBlocks.MACHINE_LIST) {
            this.getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                    .add(block);
            this.getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                    .add(block);
        }

        //Crops
        this.getOrCreateTagBuilder(BlockTags.CROPS)
                .add(ModBlocks.FLAX_CROP_BLOCK)
                .add(ModBlocks.COTTON_CROP_BLOCK);

        //Wild Crops
        this.getOrCreateTagBuilder(ModTags.WILD_CROPS)
                .add(ModBlocks.WILD_FLAX)
                .add(ModBlocks.WILD_COTTON);
    }
}
