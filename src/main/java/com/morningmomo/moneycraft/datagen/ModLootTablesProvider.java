package com.morningmomo.moneycraft.datagen;

import com.morningmomo.moneycraft.blocks.FlaxCropBlock;
import com.morningmomo.moneycraft.init.ModBlocks;
import com.morningmomo.moneycraft.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.predicate.StatePredicate;

public class ModLootTablesProvider extends FabricBlockLootTableProvider {
    public ModLootTablesProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate(){
        BlockStatePropertyLootCondition.Builder builder = BlockStatePropertyLootCondition.builder(ModBlocks.FLAX_CROP_BLOCK).properties(StatePredicate.Builder.create().exactMatch(FlaxCropBlock.AGE, 7));
        addDrop(ModBlocks.FLAX_CROP_BLOCK, cropDrops(ModBlocks.FLAX_CROP_BLOCK, ModItems.FLAX, ModItems.FLAX_SEED, builder));
    }
}
