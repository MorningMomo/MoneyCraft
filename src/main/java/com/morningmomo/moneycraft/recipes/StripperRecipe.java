package com.morningmomo.moneycraft.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.morningmomo.moneycraft.init.ModBlocks;
import com.morningmomo.moneycraft.init.ModRecipes;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class StripperRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> recipeItems;

    public StripperRecipe(Identifier id, DefaultedList<Ingredient> inputs, ItemStack output) {
        this.id = id;
        this.recipeItems = inputs;
        this.output = output;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        return this.recipeItems.get(0).test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output;
    }

    public ItemStack createIcon() {
        return ModBlocks.STRIPPER.asItem().getDefaultStack();
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItems.size());
        list.addAll(recipeItems);
        return list;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.STRIPPER_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.STRIPPER_RECIPE_TYPE;
    }

    public static class Type implements RecipeType<StripperRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
    }

    public static final class Serializer implements RecipeSerializer<StripperRecipe> {
        public Serializer() {}

        public static final Serializer INSTANCE = new Serializer();

        @Override
        public StripperRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json,"output"));
            JsonArray ingredients = JsonHelper.getArray(json,"ingredients");

            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(1,Ingredient.EMPTY);

            for(int i=0;i<inputs.size();i++){
                inputs.set(i,Ingredient.fromJson(ingredients.get(i)));
            }

            return new StripperRecipe(id, inputs, output);
        }

        @Override
        public StripperRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(),Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));

            ItemStack output = buf.readItemStack();
            return new StripperRecipe(id, inputs, output);
        }

        @Override
        public void write(PacketByteBuf buf, StripperRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for(Ingredient ingredient : recipe.getIngredients()){
                ingredient.write(buf);
            }
            buf.writeItemStack(recipe.output);
        }
    }
}
