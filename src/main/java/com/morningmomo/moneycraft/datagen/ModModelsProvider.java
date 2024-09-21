package com.morningmomo.moneycraft.datagen;

import com.morningmomo.moneycraft.blocks.CottonCropBlock;
import com.morningmomo.moneycraft.blocks.FlaxCropBlock;
import com.morningmomo.moneycraft.init.ModBlocks;
import com.morningmomo.moneycraft.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ModModelsProvider extends FabricModelProvider {
    public ModModelsProvider(FabricDataOutput output) {
        super(output);
    }
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator){
        //Blocks
        for (Block block : ModBlocks.BLOCK_LIST) {
            blockStateModelGenerator.registerSimpleCubeAll(block);
        }

        //Crops
        blockStateModelGenerator.registerCrop(ModBlocks.FLAX_CROP_BLOCK, FlaxCropBlock.AGE, 0,1,2,3,4,5,6,7);
        blockStateModelGenerator.registerCrop(ModBlocks.COTTON_CROP_BLOCK, CottonCropBlock.AGE, 0,1,2,3,4,5,6,7);

        //Wild Crops
        blockStateModelGenerator.registerTintableCross(ModBlocks.WILD_FLAX, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerTintableCross(ModBlocks.WILD_COTTON, BlockStateModelGenerator.TintType.NOT_TINTED);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator){
        //Items
        for (Item item : ModItems.ITEM_LIST) {
            itemModelGenerator.register(item, Models.GENERATED);
        }
        for (BucketItem bucketItem : ModItems.BUCKET_ITEM_LIST) {
            itemModelGenerator.register(bucketItem, Models.GENERATED);
        }
    }

    @Deprecated
    public void registerMachine(BlockStateModelGenerator blockStateModelGenerator, Block machineBlock) {
        final TextureMap textureMap =  new TextureMap()
                .put(TextureKey.PARTICLE, new Identifier("moneycraft:block/basic/" + Registries.BLOCK.getId(machineBlock).getPath() + ".png"))
                .put(TextureKey.EAST, new Identifier("moneycraft:block/basic/" + Registries.BLOCK.getId(machineBlock).getPath() + ".png"))
                .put(TextureKey.WEST, new Identifier("moneycraft:block/basic/back.png"))
                .put(TextureKey.UP, new Identifier("moneycraft:block/basic/top.png"))
                .put(TextureKey.DOWN, new Identifier("moneycraft:block/basic/bottom.png"))
                .put(TextureKey.SOUTH, new Identifier("moneycraft:block/basic/side.png"))
                .put(TextureKey.NORTH, new Identifier("moneycraft:block/basic/side.png"));
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(machineBlock, BlockStateVariant.create()
                .put(VariantSettings.MODEL, Models.CUBE.upload(machineBlock, textureMap, blockStateModelGenerator.modelCollector))));
    }
}

