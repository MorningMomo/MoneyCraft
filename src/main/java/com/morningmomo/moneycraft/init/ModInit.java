package com.morningmomo.moneycraft.init;

import com.morningmomo.moneycraft.MoneyCraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModInit {
    public ModInit() {

    }



    //Register Identifiers
    public static Identifier setId(String name) {
        return new Identifier(MoneyCraft.MOD_ID, name);
    }

    /*public static Identifier setRecipeId(String name) {
        return new Identifier(MoneyCraft.MOD_ID + ":" + MoneyCraft.MOD_ID + "/" + name);
    }*/



    //Register Items
    public static Item registerItem(String name, int maxCount, Rarity rarity) {
        return Registry.register(Registries.ITEM, setId(name),registerModItem(maxCount, rarity));
    }

    public static Item registerItem(String name, int maxCount, Rarity rarity, int hunger, float saturation, boolean isAlwaysEdible){
        return Registry.register(Registries.ITEM, setId(name),registerModItem(maxCount, rarity, hunger, saturation, isAlwaysEdible));
    }

    public static BlockItem registerItem(String name, int maxCount, Rarity rarity,Block block, int x) {
        return Registry.register(Registries.ITEM, setId(name), registerBlockItem(block, maxCount, rarity, x));
    }

    public static BucketItem registerItem(String name, Fluid fluid, Rarity rarity) {
        return Registry.register(Registries.ITEM, setId(name), registerBucketItem(fluid, rarity));
    }

    public static Item registerModItem(int maxCount, Rarity rarity){
        return new Item(new FabricItemSettings().maxCount(maxCount).rarity(rarity));
    }

    public static Item registerModItem(int maxCount, Rarity rarity, int hunger, float saturation, boolean isAlwaysEdible){
        if (isAlwaysEdible) {
            return new Item(itemSettings(maxCount, rarity).food(foodSettings(hunger, saturation).alwaysEdible().build()));
        }
        else{
            return new Item(itemSettings(maxCount, rarity).food(foodSettings(hunger, saturation).build()));
        }
    }

    public static FabricItemSettings itemSettings(int maxCount, Rarity rarity) {
        return new FabricItemSettings().maxCount(maxCount).rarity(rarity);
    }

    public static FoodComponent.Builder foodSettings(int hunger, float saturation) {
        return new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation);
    }



    //Register Block Items
    public static BlockItem registerBlockItem(Block block, int maxCount, Rarity rarity, int x){
        if (x == 0) {
            return new BlockItem(block, itemSettings(maxCount, rarity));
        } else if (x == 1) {
            return new AliasedBlockItem(block, itemSettings(maxCount, rarity));
        }
        return null;
    }

    public static BucketItem registerBucketItem(Fluid fluid, Rarity rarity) {
        return new BucketItem(fluid, new Item.Settings().recipeRemainder(Items.BUCKET).rarity(rarity));
    }


    //Register Blocks
    public static Block registerBlock(String name, float hardness, float strength) {
        return Registry.register(Registries.BLOCK, setId(name), registerModBlock(hardness, strength));
    }

    public static Block registerBlock(String name, Block block){
        return Registry.register(Registries.BLOCK, setId(name), block);
    }

    public static Block registerModBlock(float hardness, float strength) {
        return new Block(blockSettings(hardness, strength));
    }

    public static FabricBlockSettings blockSettings(float hardness, float strength) {
        return FabricBlockSettings.create().strength(hardness, strength);
    }



    //Register Recipes
    /*public static RecipeType<?> registerRecipes(String name) {
        return Registry.register(Registries.RECIPE_TYPE, setId(name), new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        });
    }*/

    public static RecipeType<?> registerRecipes(String name, RecipeType<?> type) {
        return Registry.register(Registries.RECIPE_TYPE, setId(name), type);
    }

    public static RecipeSerializer<?> registerRecipes(String name, RecipeSerializer<?> serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, setId(name), serializer);
    }



    public static void registerInit() {
        MoneyCraft.LOGGER.info("Registering Mod Init for " + MoneyCraft.MOD_ID);
    }
}
