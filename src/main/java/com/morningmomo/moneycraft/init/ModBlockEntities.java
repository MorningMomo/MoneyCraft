package com.morningmomo.moneycraft.init;

import com.morningmomo.moneycraft.MoneyCraft;
import com.morningmomo.moneycraft.entities.blockentities.BoilerBlockEntity;
import com.morningmomo.moneycraft.entities.blockentities.DigesterBlockEntity;
import com.morningmomo.moneycraft.entities.blockentities.FoodConverterBlockEntity;
import com.morningmomo.moneycraft.entities.blockentities.StripperBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;

import java.util.ArrayList;

public class ModBlockEntities extends ModInit{
    public static final ArrayList<BlockEntityType<? extends BlockEntity>> BLOCK_ENTITY_TYPE_LIST = new ArrayList<>();

    //Block Entities
    public static final BlockEntityType<StripperBlockEntity> STRIPPER = registerNormalBlockEntity("stripper", ModBlocks.STRIPPER, StripperBlockEntity::new);
    public static final BlockEntityType<FoodConverterBlockEntity> FOOD_CONVERTER = registerNormalBlockEntity("food_converter", ModBlocks.FOOD_CONVERTER, FoodConverterBlockEntity::new);
    public static final BlockEntityType<DigesterBlockEntity> DIGESTER = registerNormalBlockEntity("digester", ModBlocks.DIGESTER, DigesterBlockEntity::new);
    public static final BlockEntityType<BoilerBlockEntity> BOILER = registerNormalBlockEntity("boiler", ModBlocks.BOILER, BoilerBlockEntity::new);

    public static <T extends BlockEntity> BlockEntityType<T> registerNormalBlockEntity(String name, Block block, FabricBlockEntityTypeBuilder.Factory<T> factory){
        BlockEntityType<T> blockEntityType = FabricBlockEntityTypeBuilder.create(factory, block).build();
        BLOCK_ENTITY_TYPE_LIST.add(blockEntityType);
        return registerBlockEntity(name, blockEntityType);
    }

    public static void register(){
        MoneyCraft.LOGGER.info("Registering Mod Block Entities for " + MoneyCraft.MOD_ID);
    }
}
