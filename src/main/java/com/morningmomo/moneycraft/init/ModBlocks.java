package com.morningmomo.moneycraft.init;

import com.morningmomo.moneycraft.MoneyCraft;
import com.morningmomo.moneycraft.blocks.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.sound.BlockSoundGroup;

import java.util.ArrayList;

import static com.morningmomo.moneycraft.init.ModItems.registerNormalBlockItem;
import static net.minecraft.util.Rarity.COMMON;

public class ModBlocks extends ModInit{
    public static final ArrayList<Block> BLOCK_LIST = new ArrayList<>();
    public static final ArrayList<BlockWithEntity> MACHINE_LIST = new ArrayList<>();
    public static final ArrayList<Block> BLOCK_MACHINE_LIST = new ArrayList<>();
    public static final ArrayList<CropBlock> CROP_BLOCK_LIST = new ArrayList<>();
    public static final ArrayList<WildCropBlock> WILD_CROP_BLOCK_LIST = new ArrayList<>();
    public static final ArrayList<FluidBlock> FLUID_BLOCK_LIST = new ArrayList<>();

    //Normal Blocks
    public static final Block BANKNOTE_PAPER_BLOCK = registerNormalBlock("banknote_paper_block", 1.5f, 6.0f);
    public static final Block COIN_BLOCK = registerNormalBlock("coin_block", 1.5f, 6.0f);
    
    //Machine Blocks
    public static final BlockWithEntity STRIPPER = (BlockWithEntity) registerNormalBlock("stripper", new StripperBlock(blockSettings(1.5f, 6.0f)), ModEnums.MACHINE_BLOCK_ENTITY);
    public static final Block GRINDER = registerNormalBlock("grinder", new GrinderBlock(blockSettings(1.5f, 6.0f)), ModEnums.MACHINE_BLOCK);
    public static final BlockWithEntity FOOD_CONVERTER = (BlockWithEntity) registerNormalBlock("food_converter", new FoodConverterBlock(blockSettings(1.5f, 6.0f)), ModEnums.MACHINE_BLOCK_ENTITY);
    public static final BlockWithEntity DIGESTER = (BlockWithEntity) registerNormalBlock("digester", new DigesterBlock(blockSettings(1.5f, 6.0f)), ModEnums.MACHINE_BLOCK_ENTITY);
    public static final BlockWithEntity BLENDER = (BlockWithEntity) registerNormalBlock("blender", new BlenderBlock(blockSettings(1.5f,6.0f)), ModEnums.MACHINE_BLOCK_ENTITY);

    //Crop Blocks
    public static final CropBlock FLAX_CROP_BLOCK = (CropBlock) registerNormalBlock("flax_crop_block", new FlaxCropBlock(cropSettings()), ModEnums.CROP_BLOCK);
    public static final CropBlock COTTON_CROP_BLOCK = (CropBlock) registerNormalBlock("cotton_crop_block", new CottonCropBlock(cropSettings()), ModEnums.CROP_BLOCK);

    //Wild Crop Blocks
    public static final Block WILD_FLAX = registerWildCropBlock("wild_flax");
    public static final Block WILD_COTTON = registerWildCropBlock("wild_cotton");

    //Fluid Blocks
    public static final Block PAPER_PULP_BLOCK = registerFluidBlock("paper_pulp", ModFluids.STILL_PAPER_PULP, 100.0F, PistonBehavior.DESTROY, BlockSoundGroup.INTENTIONALLY_EMPTY);

    public static  Block registerNormalBlock(String name, float strength, float hardness) {
        BLOCK_LIST.add(BlockRegistry(name, strength, hardness));
        return BLOCK_LIST.get(BLOCK_LIST.size()-1);
    }

    public static Block registerNormalBlock(String name, Block block, ModEnums x) {
        Block block1 = BlockRegistry(name, block);
        switch (x) {
            case MACHINE_BLOCK_ENTITY -> {
                MACHINE_LIST.add((BlockWithEntity) block1);
                return MACHINE_LIST.get(MACHINE_LIST.size() - 1);
            }
            case CROP_BLOCK -> {
                CROP_BLOCK_LIST.add((CropBlock) block1);
                return CROP_BLOCK_LIST.get(CROP_BLOCK_LIST.size() - 1);
            }
            case MACHINE_BLOCK -> {
                BLOCK_MACHINE_LIST.add(block1);
                return BLOCK_MACHINE_LIST.get(BLOCK_MACHINE_LIST.size() - 1);
            }
        }
        return null;
    }



    public static Block registerWildCropBlock(String name) {
        WILD_CROP_BLOCK_LIST.add((WildCropBlock) BlockRegistry(name,
                new WildCropBlock(FabricBlockSettings
                        .create()
                        .mapColor(MapColor.GREEN)
                        .noCollision()
                        .breakInstantly()
                        .sounds(BlockSoundGroup.CROP)
                        .pistonBehavior(PistonBehavior.DESTROY))));
        return WILD_CROP_BLOCK_LIST.get(WILD_CROP_BLOCK_LIST.size()-1);
    }

    public static FluidBlock registerFluidBlock(String name, FlowableFluid fluid, float strength, PistonBehavior behavior, BlockSoundGroup sound) {
        FLUID_BLOCK_LIST.add((FluidBlock) BlockRegistry(name,
                new FluidBlock(fluid, FabricBlockSettings
                        .create()
                        .replaceable()
                        .noCollision()
                        .strength(strength)
                        .pistonBehavior(behavior)
                        .dropsNothing()
                        .liquid()
                        .sounds(sound))));
        return FLUID_BLOCK_LIST.get(FLUID_BLOCK_LIST.size()-1);
    }



    public static void register() {
        MoneyCraft.LOGGER.info("Registering ModBlocks for " + MoneyCraft.MOD_ID + ":");
        for (Block block : BLOCK_LIST) {
            registerNormalBlockItem(block, getBlockName(block), 64, COMMON, ModEnums.BLOCK);
            //MoneyCraft.LOGGER.info(Registries.BLOCK.getId(block).getPath());
        }
        for (Block block : MACHINE_LIST) {
            registerNormalBlockItem(block, getBlockName(block), 64, COMMON, ModEnums.MACHINE_BLOCK_ENTITY);
        }
        for (Block block : BLOCK_MACHINE_LIST) {
            registerNormalBlockItem(block, getBlockName(block), 64, COMMON, ModEnums.MACHINE_BLOCK);
        }
    }
}