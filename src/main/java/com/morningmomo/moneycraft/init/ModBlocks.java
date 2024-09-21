package com.morningmomo.moneycraft.init;

import com.morningmomo.moneycraft.MoneyCraft;
import com.morningmomo.moneycraft.blocks.CottonCropBlock;
import com.morningmomo.moneycraft.blocks.FlaxCropBlock;
import com.morningmomo.moneycraft.blocks.StripperBlock;
import com.morningmomo.moneycraft.blocks.WildCropBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.ArrayList;

import static com.morningmomo.moneycraft.init.ModItems.registerNormalBlockItem;
import static net.minecraft.util.Rarity.COMMON;

public class ModBlocks extends ModInit{
    public static final ArrayList<Block> BLOCK_LIST = new ArrayList<>();
    public static final ArrayList<BlockWithEntity> MACHINE_LIST = new ArrayList<>();
    public static final ArrayList<CropBlock> CROP_BLOCK_LIST = new ArrayList<>();
    public static final ArrayList<WildCropBlock> WILD_CROP_BLOCK_LIST = new ArrayList<>();
    public static final ArrayList<FluidBlock> FLUID_BLOCK_LIST = new ArrayList<>();

    //Normal Blocks
    public static final Block BANKNOTE_PAPER_BLOCK = registerNormalBlock("banknote_paper_block", 1.5f, 6.0f);
    public static final Block COIN_BLOCK = registerNormalBlock("coin_block", 1.5f, 6.0f);
    
    //Machine Blocks
    public static final BlockWithEntity STRIPPER = (BlockWithEntity) registerNormalBlock("stripper", new StripperBlock(setting(1.5f, 6.0f)), 0);


    //Crop Blocks
    public static final CropBlock FLAX_CROP_BLOCK = (CropBlock) registerNormalBlock("flax_crop_block", new FlaxCropBlock(cropSetting()), 1);
    public static final CropBlock COTTON_CROP_BLOCK = (CropBlock) registerNormalBlock("cotton_crop_block", new CottonCropBlock(cropSetting()), 1);

    //Wild Crop Blocks
    public static final Block WILD_FLAX = registerWildCropBlock("wild_flax");
    public static final Block WILD_COTTON = registerWildCropBlock("wild_cotton");

    //Fluid Blocks
    public static final Block PAPER_PULP_BLOCK = registerFluidBlock("paper_pulp", ModFluids.STILL_PAPER_PULP);

    public static Block registerNormalBlock(String name, float strength, float hardness) {
        BLOCK_LIST.add(ModInit.registerBlock(name, strength, hardness));
        return BLOCK_LIST.get(BLOCK_LIST.size()-1);
    }

    public static Block registerNormalBlock(String name, Block block, int x) {
        if (x == 0) {
            MACHINE_LIST.add((BlockWithEntity) registry(setId(name), block));
            return MACHINE_LIST.get(MACHINE_LIST.size()-1);
        }else if (x == 1) {
            CROP_BLOCK_LIST.add((CropBlock) registry(setId(name), block));
            return CROP_BLOCK_LIST.get(CROP_BLOCK_LIST.size() - 1);
        }
        return null;
    }



    public static Block registerWildCropBlock(String name) {
        WILD_CROP_BLOCK_LIST.add(Registry.register(Registries.BLOCK, setId(name),
                new WildCropBlock(FabricBlockSettings.create().mapColor(MapColor.GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY))));
        return WILD_CROP_BLOCK_LIST.get(WILD_CROP_BLOCK_LIST.size()-1);
    }

    public static FluidBlock registerFluidBlock(String name, FlowableFluid fluid) {
        FLUID_BLOCK_LIST.add(Registry.register(Registries.BLOCK, setId(name),
                new FluidBlock(fluid, FabricBlockSettings.create().replaceable().noCollision().strength(100.0F).pistonBehavior(PistonBehavior.DESTROY).dropsNothing().liquid().sounds(BlockSoundGroup.INTENTIONALLY_EMPTY))));
        return FLUID_BLOCK_LIST.get(FLUID_BLOCK_LIST.size()-1);
    }



    public static Block registry(Identifier id, Block block){
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static FabricBlockSettings setting(float strength, float hardness){
        return FabricBlockSettings.create().strength(strength,hardness);
    }

    public static AbstractBlock.Settings cropSetting() {
        return AbstractBlock.Settings.copy(Blocks.WHEAT);
    }



    public static void registerBlock(Block block, String name, int maxCount, Rarity rarity) {
        registerNormalBlockItem(block, name, maxCount, rarity);
    }

    public static void registerModBlocks() {
        MoneyCraft.LOGGER.info("Registering ModBlocks for " + MoneyCraft.MOD_ID + ":");
        for (Block block : BLOCK_LIST) {
            registerBlock(block, Registries.BLOCK.getId(block).getPath(), 64, COMMON);
            MoneyCraft.LOGGER.info(Registries.BLOCK.getId(block).getPath());
        }
        for (Block block : MACHINE_LIST) {
            registerBlock(block, Registries.BLOCK.getId(block).getPath(), 64, COMMON);
            MoneyCraft.LOGGER.info(Registries.BLOCK.getId(block).getPath());
        }
        for (CropBlock cropBlock : CROP_BLOCK_LIST){
            MoneyCraft.LOGGER.info(Registries.BLOCK.getId(cropBlock).getPath());
        }
    }
}