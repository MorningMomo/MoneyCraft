package com.morningmomo.moneycraft.init;

import com.morningmomo.moneycraft.MoneyCraft;
import com.morningmomo.moneycraft.recipes.StripperRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;

public class ModRecipes extends ModInit{

    //RecipeType
    public static final RecipeType<?> STRIPPER_RECIPE_TYPE = registerRecipes("stripper", StripperRecipe.Type.INSTANCE);

    //RecipeSerializer
    public static final RecipeSerializer<?> STRIPPER_RECIPE_SERIALIZER = registerRecipes("stripper", StripperRecipe.Serializer.INSTANCE);

    public static void register() {
        MoneyCraft.LOGGER.info("Registering Mod Recipes for " + MoneyCraft.MOD_ID);
    }
}
