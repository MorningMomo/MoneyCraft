package com.morningmomo.moneycraft.init;

import com.morningmomo.moneycraft.MoneyCraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.ArrayList;

import static net.minecraft.util.Rarity.COMMON;

public class ModItems {
    public static final ArrayList<Item> ITEM_LIST = new ArrayList<>();
    public static final ArrayList<BlockItem> BLOCK_ITEM_LIST = new ArrayList<>();

    //Normal Items
    public static final Item BANKNOTE_1S = registerNormalItem("banknote_1s",64,COMMON);
    public static final Item BANKNOTE_2S = registerNormalItem("banknote_2s",64,COMMON);
    public static final Item BANKNOTE_5S = registerNormalItem("banknote_5s",64,COMMON);
    public static final Item BANKNOTE_10S = registerNormalItem("banknote_10s",64,COMMON);
    public static final Item BANKNOTE_20S = registerNormalItem("banknote_20s",64,COMMON);
    public static final Item BANKNOTE_50S = registerNormalItem("banknote_50s",64,COMMON);
    public static final Item BANKNOTE_100S = registerNormalItem("banknote_100s",64,COMMON);
    public static final Item BANKNOTE_200S = registerNormalItem("banknote_200s",64,COMMON);
    public static final Item BANKNOTE_500S = registerNormalItem("banknote_500s",64,COMMON);
    public static final Item BANKNOTE_1000S = registerNormalItem("banknote_1000s",64,COMMON);
    public static final Item COIN_1S = registerNormalItem("coin_1s",64,COMMON);
    public static final Item COIN_2S = registerNormalItem("coin_2s",64,COMMON);
    public static final Item COIN_5S = registerNormalItem("coin_5s",64,COMMON);
    public static final Item COIN_1SS = registerNormalItem("coin_1ss",64,COMMON);
    public static final Item COIN_2SS = registerNormalItem("coin_2ss",64,COMMON);
    public static final Item COIN_5SS = registerNormalItem("coin_5ss",64,COMMON);
    public static final Item FAKE_BANKNOTE_1S = registerNormalItem("fake_banknote_1s",64,COMMON);
    public static final Item FAKE_BANKNOTE_2S = registerNormalItem("fake_banknote_2s",64,COMMON);
    public static final Item FAKE_BANKNOTE_5S = registerNormalItem("fake_banknote_5s",64,COMMON);
    public static final Item FAKE_BANKNOTE_10S = registerNormalItem("fake_banknote_10s",64,COMMON);
    public static final Item FAKE_BANKNOTE_20S = registerNormalItem("fake_banknote_20s",64,COMMON);
    public static final Item FAKE_BANKNOTE_50S = registerNormalItem("fake_banknote_50s",64,COMMON);
    public static final Item FAKE_BANKNOTE_100S = registerNormalItem("fake_banknote_100s",64,COMMON);
    public static final Item FAKE_BANKNOTE_200S = registerNormalItem("fake_banknote_200s",64,COMMON);
    public static final Item FAKE_BANKNOTE_500S = registerNormalItem("fake_banknote_500s",64,COMMON);
    public static final Item FAKE_BANKNOTE_1000S = registerNormalItem("fake_banknote_1000s",64,COMMON);
    public static final Item FAKE_COIN_1S = registerNormalItem("fake_coin_1s",64,COMMON);
    public static final Item FAKE_COIN_2S = registerNormalItem("fake_coin_2s",64,COMMON);
    public static final Item FAKE_COIN_5S = registerNormalItem("fake_coin_5s",64,COMMON);
    public static final Item FAKE_COIN_1SS = registerNormalItem("fake_coin_1ss",64,COMMON);
    public static final Item FAKE_COIN_2SS = registerNormalItem("fake_coin_2ss",64,COMMON);
    public static final Item FAKE_COIN_5SS = registerNormalItem("fake_coin_5ss",64,COMMON);
    public static final Item BANKNOTE_PAPER = registerNormalItem("banknote_paper",64,COMMON);
    public static final Item FAKE_BANKNOTE_PAPER = registerNormalItem("fake_banknote_paper",64,COMMON);
    public static final Item COIN = registerNormalItem("coin",64,COMMON);
    public static final Item FAKE_COIN = registerNormalItem("fake_coin",64,COMMON);
    public static final Item RAW_PAPER = registerNormalItem("raw_paper",64,COMMON);
    public static final Item BLEACHED_PAPER = registerNormalItem("bleached_paper",64,COMMON);
    public static final Item FLAX = registerNormalItem("flax",64,COMMON);
    public static final Item FLAX_FIBER = registerNormalItem("flax_fiber",64,COMMON);
    public static final Item WOOD_DUST = registerNormalItem("wood_dust",64,COMMON);
    public static final Item COTTON = registerNormalItem("cotton",64,COMMON);
    public static final Item COTTON_SEED = registerNormalItem("cotton_seed",64,COMMON);
    public static final Item COTTON_FIBER = registerNormalItem("cotton_fiber",64,COMMON);

    //Food Items
    public static final Item COIN_FOOD = registerNormalFoodItem("coin_food",64,COMMON,1,0.1f,true);

    //Crop Items
    public static final Item FLAX_SEED = registerNormalBlockItem(ModBlocks.FLAX_CROP_BLOCK, "flax_seed", 64, COMMON);

    //Register different kinds of Items
    public static Item registerNormalItem(String name, int maxCount, Rarity rarity){
        ITEM_LIST.add(Registry.register(Registries.ITEM,new Identifier(MoneyCraft.MOD_ID, name),registerModItem(maxCount, rarity)));
        return ITEM_LIST.get(ITEM_LIST.size()-1);
    }
    public static Item registerNormalFoodItem(String name, int maxCount, Rarity rarity, int hunger, float saturation, boolean isAlwaysEdible){
        ITEM_LIST.add(Registry.register(Registries.ITEM,new Identifier(MoneyCraft.MOD_ID, name),registerModItem(maxCount, rarity, hunger, saturation, isAlwaysEdible)));
        return ITEM_LIST.get(ITEM_LIST.size()-1);
    }

    //Register Items
    public static Item registerModItem(int maxCount, Rarity rarity){
        return new Item(new FabricItemSettings().maxCount(maxCount).rarity(rarity));
    }
    public static Item registerModItem(int maxCount, Rarity rarity, int hunger, float saturation, boolean isAlwaysEdible){
        if (isAlwaysEdible) {
            return new Item(new FabricItemSettings().maxCount(maxCount).rarity(rarity).food(new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation).alwaysEdible().build()));
        }
        else{
            return new Item(new FabricItemSettings().maxCount(maxCount).rarity(rarity).food(new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation).build()));
        }
    }

    //Register BlockItems
    public static BlockItem registerNormalBlockItem(Block block, String name, int maxCount, Rarity rarity){
        if (block instanceof CropBlock){
            BLOCK_ITEM_LIST.add(Registry.register(Registries.ITEM, new Identifier(MoneyCraft.MOD_ID, name), registerSeedModBlockItem(block, maxCount, rarity)));
            return BLOCK_ITEM_LIST.get(BLOCK_ITEM_LIST.size()-1);
        }
        BLOCK_ITEM_LIST.add(Registry.register(Registries.ITEM, new Identifier(MoneyCraft.MOD_ID, name), registerModBlockItem(block, maxCount, rarity)));
        return BLOCK_ITEM_LIST.get(BLOCK_ITEM_LIST.size()-1);
    }

    public static BlockItem registerModBlockItem(Block block, int maxCount, Rarity rarity){
        return new BlockItem(block, new FabricItemSettings().maxCount(maxCount).rarity(rarity));
    }
    public static BlockItem registerSeedModBlockItem(Block block, int maxCount, Rarity rarity){
        return new AliasedBlockItem(block, new FabricItemSettings().maxCount(maxCount).rarity(rarity));
    }

    public static void registerModItems() {
        MoneyCraft.LOGGER.info("Registering Mod Items for " + MoneyCraft.MOD_ID);
    }
}