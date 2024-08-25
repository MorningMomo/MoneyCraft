package com.morningmomo.moneycraft.datagen;

import com.morningmomo.moneycraft.blocks.FlaxCropBlock;
import com.morningmomo.moneycraft.init.ModBlocks;
import com.morningmomo.moneycraft.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;

public class ModModelsProvider extends FabricModelProvider {
    public ModModelsProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator){
        for (Block block : ModBlocks.BLOCK_LIST) {
            blockStateModelGenerator.registerSimpleCubeAll(block);
        }
        blockStateModelGenerator.registerCrop(ModBlocks.FLAX_CROP_BLOCK, FlaxCropBlock.AGE, 0,1,2,3,4,5,6,7);
        blockStateModelGenerator.registerTintableCross(ModBlocks.WILD_FLAX, BlockStateModelGenerator.TintType.NOT_TINTED);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator){
        for (Item item : ModItems.ITEM_LIST) {
            itemModelGenerator.register(item, Models.GENERATED);
        }
    }
}
