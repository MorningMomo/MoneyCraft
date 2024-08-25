package com.morningmomo.moneycraft.init;

import com.morningmomo.moneycraft.MoneyCraft;
import com.morningmomo.moneycraft.blocks.FlaxCropBlock;
import com.morningmomo.moneycraft.blocks.StripperBlock;
import com.morningmomo.moneycraft.blocks.WildCropBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.ArrayList;

import static com.morningmomo.moneycraft.init.ModItems.registerNormalBlockItem;
import static net.minecraft.util.Rarity.COMMON;

public class ModBlocks {
    public static final ArrayList<Block> BLOCK_LIST = new ArrayList<>();
    public static final ArrayList<CropBlock> CROP_BLOCK_LIST = new ArrayList<>();
    public static final ArrayList<WildCropBlock> WILD_CROP_BLOCK_LIST = new ArrayList<>();

    //Normal Blocks
    public static final Block BANKNOTE_PAPER_BLOCK = registerNormalBlock("banknote_paper_block", 1.5f, 6.0f);
    public static final Block COIN_BLOCK = registerNormalBlock("coin_block", 1.5f, 6.0f);
    public static final Block STRIPPER = registerNormalBlock("stripper", 1.5f, 6.0f);
    public static final CropBlock FLAX_CROP_BLOCK = (CropBlock) registerNormalBlock("flax_crop_block", 1.5f, 6.0f);
    public static final Block WILD_FLAX = registerWildCropBlock("wild_flax");

    public static Block registerNormalBlock(String BlockIdName, float strength, float hardness) {
        Identifier id = new Identifier(MoneyCraft.MOD_ID, BlockIdName);
        switch (BlockIdName) {
            case "stripper":
                BLOCK_LIST.add(registry(id,
                        new StripperBlock(setting(strength, hardness))));
                return returnList();
            case "flax_crop_block":
                CROP_BLOCK_LIST.add((CropBlock) registry(id,
                        new FlaxCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT))));
                return CROP_BLOCK_LIST.get(CROP_BLOCK_LIST.size() - 1);
                default :
                BLOCK_LIST.add(registry(id,
                        new Block(setting(strength, hardness))));
                return returnList();
        }
    }

    public static Block registerWildCropBlock(String name) {
        Identifier id = new Identifier(MoneyCraft.MOD_ID, name);
        WILD_CROP_BLOCK_LIST.add(Registry.register(Registries.BLOCK, id, new WildCropBlock(FabricBlockSettings.create().mapColor(MapColor.GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY))));
        return WILD_CROP_BLOCK_LIST.get(WILD_CROP_BLOCK_LIST.size()-1);
    }

    public static Block registry(Identifier id, Block block){
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static FabricBlockSettings setting(float strength, float hardness){
        return FabricBlockSettings.create().strength(strength,hardness);
    }

    public static void registerBlock(Block block, String BlockIdName, int maxCount, Rarity rarity) {
        registerNormalBlockItem(block, BlockIdName, maxCount, rarity);
    }

    public static void registerModBlocks() {
        MoneyCraft.LOGGER.info("Registering ModBlocks for " + MoneyCraft.MOD_ID + ":");
        for (Block block : BLOCK_LIST) {
            registerBlock(block, Registries.BLOCK.getId(block).getPath(), 64, COMMON);
            MoneyCraft.LOGGER.info(Registries.BLOCK.getId(block).getPath());
        }
        for (CropBlock cropBlock : CROP_BLOCK_LIST){
            MoneyCraft.LOGGER.info(Registries.BLOCK.getId(cropBlock).getPath());
        }
    }

    private static Block returnList(){
        return BLOCK_LIST.get(BLOCK_LIST.size()-1);
    }
}