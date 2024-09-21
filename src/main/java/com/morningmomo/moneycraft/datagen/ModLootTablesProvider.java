package com.morningmomo.moneycraft.datagen;

import com.morningmomo.moneycraft.blocks.CottonCropBlock;
import com.morningmomo.moneycraft.blocks.FlaxCropBlock;
import com.morningmomo.moneycraft.init.ModBlocks;
import com.morningmomo.moneycraft.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;

public class ModLootTablesProvider extends FabricBlockLootTableProvider {
    public ModLootTablesProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate(){
        //Blocks
        for (Block block : ModBlocks.BLOCK_LIST) {
            addDrop(block);
        }
        for (Block block : ModBlocks.MACHINE_LIST) {
            addDrop(block);
        }

        //Crops
        addDrop(ModBlocks.FLAX_CROP_BLOCK, cropDrops(ModBlocks.FLAX_CROP_BLOCK, ModItems.FLAX, ModItems.FLAX_SEED, BlockStatePropertyLootCondition.builder(ModBlocks.FLAX_CROP_BLOCK).properties(StatePredicate.Builder.create().exactMatch(FlaxCropBlock.AGE, 7))));
        addDrop(ModBlocks.COTTON_CROP_BLOCK, cropDrops(ModBlocks.COTTON_CROP_BLOCK, ModItems.COTTON, ModItems.COTTON_SEED, BlockStatePropertyLootCondition.builder(ModBlocks.COTTON_CROP_BLOCK).properties(StatePredicate.Builder.create().exactMatch(CottonCropBlock.AGE, 7))));

        //Wild crops
        addDrop(ModBlocks.WILD_FLAX, wildCropDrops(ModItems.FLAX_SEED, ModItems.FLAX));
        addDrop(ModBlocks.WILD_COTTON, wildCropDrops(ModItems.COTTON_SEED, ModItems.COTTON));
    }

    public LootTable.Builder wildCropDrops(Item seed, Item drop) {
        return new LootTable.Builder()
                .pool(LootPool.builder().apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F))).with(ItemEntry.builder(drop)))
                .pool(LootPool.builder().apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F))).with(ItemEntry.builder(seed)));
    }
}
