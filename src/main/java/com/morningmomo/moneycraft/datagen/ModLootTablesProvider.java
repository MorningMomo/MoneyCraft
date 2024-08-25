package com.morningmomo.moneycraft.datagen;

import com.morningmomo.moneycraft.blocks.FlaxCropBlock;
import com.morningmomo.moneycraft.init.ModBlocks;
import com.morningmomo.moneycraft.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;

import java.util.ArrayList;

public class ModLootTablesProvider extends FabricBlockLootTableProvider {
    public ModLootTablesProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }
    public static final ArrayList<Item> WILD_FLAX_DROP = new ArrayList<>();

    @Override
    public void generate(){
        WILD_FLAX_DROP.add(ModItems.FLAX);
        WILD_FLAX_DROP.add(ModItems.FLAX_SEED);
        BlockStatePropertyLootCondition.Builder builder = BlockStatePropertyLootCondition.builder(ModBlocks.FLAX_CROP_BLOCK).properties(StatePredicate.Builder.create().exactMatch(FlaxCropBlock.AGE, 7));
        addDrop(ModBlocks.FLAX_CROP_BLOCK, cropDrops(ModBlocks.FLAX_CROP_BLOCK, ModItems.FLAX, ModItems.FLAX_SEED, builder));
        addDrop(ModBlocks.WILD_FLAX, wildCropDrops(ModItems.FLAX_SEED, ModItems.FLAX));
    }

    public LootTable.Builder wildCropDrops(Item seed, Item drop) {
        return new LootTable.Builder()
                .pool(LootPool.builder().apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F))).with(ItemEntry.builder(drop)))
                .pool(LootPool.builder().apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F))).with(ItemEntry.builder(seed)));
    }
}
