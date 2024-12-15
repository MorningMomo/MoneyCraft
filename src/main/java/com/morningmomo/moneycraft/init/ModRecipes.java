package com.morningmomo.moneycraft.init;

import com.morningmomo.moneycraft.MoneyCraft;
import com.morningmomo.moneycraft.recipes.BoilerRecipe;
import com.morningmomo.moneycraft.recipes.FoodConverterRecipe;
import com.morningmomo.moneycraft.recipes.StripperRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;

import java.util.ArrayList;

public class ModRecipes extends ModInit{
    public static final ArrayList<RecipeType<?>> RECIPE_TYPE_LIST = new ArrayList<>();
    public static final ArrayList<RecipeSerializer<?>> RECIPE_SERIALIZER_LIST = new ArrayList<>();

    //RecipeType
    public static final RecipeType<?> STRIPPER_RECIPE_TYPE = registerNormalRecipeType("stripper", StripperRecipe.Type.INSTANCE);
    public static final RecipeType<?> FOOD_CONVERTER_TYPE = registerNormalRecipeType("food_converter", FoodConverterRecipe.Type.INSTANCE);
    public static final RecipeType<?> BOILER_TYPE = registerNormalRecipeType("boiler", BoilerRecipe.Type.INSTANCE);

    //RecipeSerializer
    public static final RecipeSerializer<?> STRIPPER_RECIPE_SERIALIZER = registerNormalRecipeSerializer("stripper", StripperRecipe.Serializer.INSTANCE);
    public static final RecipeSerializer<?> FOOD_CONVERTER_SERIALIZER = registerNormalRecipeSerializer("food_converter", FoodConverterRecipe.Serializer.INSTANCE);
    public static final RecipeSerializer<?> BOILER_SERIALIZER = registerNormalRecipeSerializer("boiler", BoilerRecipe.Serializer.INSTANCE);



    public static RecipeType<?> registerNormalRecipeType(String name, RecipeType<?> type) {
        RECIPE_TYPE_LIST.add(registerRecipes(name, type));
        return RECIPE_TYPE_LIST.get(RECIPE_TYPE_LIST.size() - 1);
    }
    public static RecipeSerializer<?> registerNormalRecipeSerializer(String name, RecipeSerializer<?> serializer) {
        RECIPE_SERIALIZER_LIST.add(registerRecipes(name, serializer));
        return RECIPE_SERIALIZER_LIST.get(RECIPE_SERIALIZER_LIST.size() - 1);
    }



    public static void register() {
        MoneyCraft.LOGGER.info("Registering Mod Recipes for " + MoneyCraft.MOD_ID);
    }
}
